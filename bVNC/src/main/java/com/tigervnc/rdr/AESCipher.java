/* Copyright (C) 2022 Dinglan Peng
 *
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */

package com.tigervnc.rdr;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

    private static final byte[] zeroBlock = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final byte[] prefixBlock0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final byte[] prefixBlock1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
    private static final byte[] prefixBlock2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2};
    private static final int[] lut = {0x0, 0x87, 0x0e, 0x89};
    private SecretKeySpec keySpec;
    private Cipher ctrCipher;
    private Cipher cbcCipher;
    private byte[] subKey1;
    private byte[] subKey2;

    public AESCipher(byte[] key) throws Exception {
        try {
            Cipher blockCipher = Cipher.getInstance("AES");
            cbcCipher = Cipher.getInstance("AES/CBC/NOPADDING");
            ctrCipher = Cipher.getInstance("AES/CTR/NOPADDING");
            keySpec = new SecretKeySpec(key, "AES");
            blockCipher.init(Cipher.ENCRYPT_MODE, keySpec);
            cbcCipher.init(Cipher.ENCRYPT_MODE, keySpec,
                    new IvParameterSpec(zeroBlock));
            subKey1 = Arrays.copyOfRange(blockCipher.doFinal(zeroBlock), 0, 16);
            subKey2 = new byte[16];
            int v = (subKey1[0] & 0xff) >>> 6;
            for (int i = 0; i < 15; i++) {
                subKey2[i] = (byte) (((subKey1[i + 1] & 0xff) >>> 6) |
                        ((subKey1[i] & 0xff) << 2));
                subKey1[i] = (byte) (((subKey1[i + 1] & 0xff) >>> 7) |
                        ((subKey1[i] & 0xff) << 1));
            }
            subKey2[14] ^= v >>> 1;
            subKey2[15] = (byte) (((subKey1[15] & 0xff) << 2) ^ lut[v]);
            subKey1[15] = (byte) (((subKey1[15] & 0xff) << 1) ^ lut[v >>> 1]);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new Exception("AESEAXCipher: AES algorithm is not supported");
        } catch (IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new Exception("AESEAXCipher: invalid key");
        }
    }

    private void encryptCTR(byte[] input, int inputOffset, int inputLength,
                            byte[] output, int outputOffset, byte[] iv) throws IOException {
        try {
            ctrCipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            ctrCipher.doFinal(input, inputOffset, inputLength, output, outputOffset);
        } catch (Exception e) {
            throw new IOException("AESEAXCipher: " + e.toString());
        }
    }

    private byte[] computeCMAC(byte[] input, int offset,
                               int length, byte[] prefix) throws IOException {
        int n = length / 16;
        int m = (length + 15) / 16;
        int r = length - n * 16;
        byte[] cbcData = new byte[(m + 1) * 16];
        System.arraycopy(prefix, 0, cbcData, 0, 16);
        System.arraycopy(input, offset, cbcData, 16, length);

        if (r == 0) {
            for (int i = 0; i < 16; i++) {
                cbcData[n * 16 + i] ^= subKey1[i] & 0xff;
            }
        } else {
            cbcData[(n + 1) * 16 + r] = (byte) 0x80;
            for (int i = 0; i < 16; i++) {
                cbcData[(n + 1) * 16 + i] ^= subKey2[i] & 0xff;
            }
        }
        try {
            byte[] encrypted = cbcCipher.doFinal(cbcData);
            return Arrays.copyOfRange(encrypted, encrypted.length - 16, encrypted.length - 0);
        } catch (Exception e) {
            throw new IOException("AESEAXCipher: " + e.getMessage());
        }
    }

    public void encrypt(byte[] input, int inputOffset, int inputLength,
                        byte[] ad, int adOffset, int adLength,
                        byte[] nonce,
                        byte[] output, int outputOffset,
                        byte[] mac, int macOffset) throws IOException {
        byte[] nCMAC = computeCMAC(nonce, 0, nonce.length, prefixBlock0);
        encryptCTR(input, inputOffset, inputLength, output, outputOffset, nCMAC);
        byte[] adCMAC = computeCMAC(ad, adOffset, adLength, prefixBlock1);
        byte[] m = computeCMAC(output, outputOffset, inputLength, prefixBlock2);
        for (int i = 0; i < 16; i++) {
            mac[macOffset + i] = (byte) ((m[i] & 0xff) ^
                    (nCMAC[i] & 0xff) ^
                    (adCMAC[i] & 0xff));
        }
    }

    public void decrypt(byte[] input, int inputOffset, int inputLength,
                        byte[] ad, int adOffset, int adLength,
                        byte[] nonce,
                        byte[] output, int outputOffset,
                        byte[] mac, int macOffset) throws IOException {
        byte[] nCMAC = computeCMAC(nonce, 0, nonce.length, prefixBlock0);
        byte[] adCMAC = computeCMAC(ad, adOffset, adLength, prefixBlock1);
        byte[] m = computeCMAC(input, inputOffset, inputLength, prefixBlock2);
        for (int i = 0; i < 16; i++) {
            byte x = (byte) ((m[i] & 0xff) ^ (nCMAC[i] & 0xff) ^ (adCMAC[i] & 0xff));
            if (x != mac[macOffset + i])
                throw new IOException("AESEAXCipher: failed to authenticate message");
        }
        encryptCTR(input, inputOffset, inputLength, output, outputOffset, nCMAC);
    }
}
