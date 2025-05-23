/**
 * Copyright (C) 2012 Iordan Iordanov
 * Copyright (C) 2010 Michael A. MacDonald
 * <p>
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this software; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
 * USA.
 */

package com.iiordanov.bVNC;

public class ColorModel64 {

    public final static int[] colors;

    static {
        colors = new int[256];
        colors[0] = 0xff000000;
        colors[1] = 0xff000055;
        colors[2] = 0xff0000aa;
        colors[3] = 0xff0000ff;
        colors[4] = 0xff005500;
        colors[5] = 0xff005555;
        colors[6] = 0xff0055aa;
        colors[7] = 0xff0055ff;
        colors[8] = 0xff00aa00;
        colors[9] = 0xff00aa55;
        colors[10] = 0xff00aaaa;
        colors[11] = 0xff00aaff;
        colors[12] = 0xff00ff00;
        colors[13] = 0xff00ff55;
        colors[14] = 0xff00ffaa;
        colors[15] = 0xff00ffff;
        colors[16] = 0xff550000;
        colors[17] = 0xff550055;
        colors[18] = 0xff5500aa;
        colors[19] = 0xff5500ff;
        colors[20] = 0xff555500;
        colors[21] = 0xff555555;
        colors[22] = 0xff5555aa;
        colors[23] = 0xff5555ff;
        colors[24] = 0xff55aa00;
        colors[25] = 0xff55aa55;
        colors[26] = 0xff55aaaa;
        colors[27] = 0xff55aaff;
        colors[28] = 0xff55ff00;
        colors[29] = 0xff55ff55;
        colors[30] = 0xff55ffaa;
        colors[31] = 0xff55ffff;
        colors[32] = 0xffaa0000;
        colors[33] = 0xffaa0055;
        colors[34] = 0xffaa00aa;
        colors[35] = 0xffaa00ff;
        colors[36] = 0xffaa5500;
        colors[37] = 0xffaa5555;
        colors[38] = 0xffaa55aa;
        colors[39] = 0xffaa55ff;
        colors[40] = 0xffaaaa00;
        colors[41] = 0xffaaaa55;
        colors[42] = 0xffaaaaaa;
        colors[43] = 0xffaaaaff;
        colors[44] = 0xffaaff00;
        colors[45] = 0xffaaff55;
        colors[46] = 0xffaaffaa;
        colors[47] = 0xffaaffff;
        colors[48] = 0xffff0000;
        colors[49] = 0xffff0055;
        colors[50] = 0xffff00aa;
        colors[51] = 0xffff00ff;
        colors[52] = 0xffff5500;
        colors[53] = 0xffff5555;
        colors[54] = 0xffff55aa;
        colors[55] = 0xffff55ff;
        colors[56] = 0xffffaa00;
        colors[57] = 0xffffaa55;
        colors[58] = 0xffffaaaa;
        colors[59] = 0xffffaaff;
        colors[60] = 0xffffff00;
        colors[61] = 0xffffff55;
        colors[62] = 0xffffffaa;
        colors[63] = 0xffffffff;
        colors[64] = 0xff000000;
        colors[65] = 0xff000055;
        colors[66] = 0xff0000aa;
        colors[67] = 0xff0000ff;
        colors[68] = 0xff005500;
        colors[69] = 0xff005555;
        colors[70] = 0xff0055aa;
        colors[71] = 0xff0055ff;
        colors[72] = 0xff00aa00;
        colors[73] = 0xff00aa55;
        colors[74] = 0xff00aaaa;
        colors[75] = 0xff00aaff;
        colors[76] = 0xff00ff00;
        colors[77] = 0xff00ff55;
        colors[78] = 0xff00ffaa;
        colors[79] = 0xff00ffff;
        colors[80] = 0xff550000;
        colors[81] = 0xff550055;
        colors[82] = 0xff5500aa;
        colors[83] = 0xff5500ff;
        colors[84] = 0xff555500;
        colors[85] = 0xff555555;
        colors[86] = 0xff5555aa;
        colors[87] = 0xff5555ff;
        colors[88] = 0xff55aa00;
        colors[89] = 0xff55aa55;
        colors[90] = 0xff55aaaa;
        colors[91] = 0xff55aaff;
        colors[92] = 0xff55ff00;
        colors[93] = 0xff55ff55;
        colors[94] = 0xff55ffaa;
        colors[95] = 0xff55ffff;
        colors[96] = 0xffaa0000;
        colors[97] = 0xffaa0055;
        colors[98] = 0xffaa00aa;
        colors[99] = 0xffaa00ff;
        colors[100] = 0xffaa5500;
        colors[101] = 0xffaa5555;
        colors[102] = 0xffaa55aa;
        colors[103] = 0xffaa55ff;
        colors[104] = 0xffaaaa00;
        colors[105] = 0xffaaaa55;
        colors[106] = 0xffaaaaaa;
        colors[107] = 0xffaaaaff;
        colors[108] = 0xffaaff00;
        colors[109] = 0xffaaff55;
        colors[110] = 0xffaaffaa;
        colors[111] = 0xffaaffff;
        colors[112] = 0xffff0000;
        colors[113] = 0xffff0055;
        colors[114] = 0xffff00aa;
        colors[115] = 0xffff00ff;
        colors[116] = 0xffff5500;
        colors[117] = 0xffff5555;
        colors[118] = 0xffff55aa;
        colors[119] = 0xffff55ff;
        colors[120] = 0xffffaa00;
        colors[121] = 0xffffaa55;
        colors[122] = 0xffffaaaa;
        colors[123] = 0xffffaaff;
        colors[124] = 0xffffff00;
        colors[125] = 0xffffff55;
        colors[126] = 0xffffffaa;
        colors[127] = 0xffffffff;
        colors[128] = 0xff000000;
        colors[129] = 0xff000055;
        colors[130] = 0xff0000aa;
        colors[131] = 0xff0000ff;
        colors[132] = 0xff005500;
        colors[133] = 0xff005555;
        colors[134] = 0xff0055aa;
        colors[135] = 0xff0055ff;
        colors[136] = 0xff00aa00;
        colors[137] = 0xff00aa55;
        colors[138] = 0xff00aaaa;
        colors[139] = 0xff00aaff;
        colors[140] = 0xff00ff00;
        colors[141] = 0xff00ff55;
        colors[142] = 0xff00ffaa;
        colors[143] = 0xff00ffff;
        colors[144] = 0xff550000;
        colors[145] = 0xff550055;
        colors[146] = 0xff5500aa;
        colors[147] = 0xff5500ff;
        colors[148] = 0xff555500;
        colors[149] = 0xff555555;
        colors[150] = 0xff5555aa;
        colors[151] = 0xff5555ff;
        colors[152] = 0xff55aa00;
        colors[153] = 0xff55aa55;
        colors[154] = 0xff55aaaa;
        colors[155] = 0xff55aaff;
        colors[156] = 0xff55ff00;
        colors[157] = 0xff55ff55;
        colors[158] = 0xff55ffaa;
        colors[159] = 0xff55ffff;
        colors[160] = 0xffaa0000;
        colors[161] = 0xffaa0055;
        colors[162] = 0xffaa00aa;
        colors[163] = 0xffaa00ff;
        colors[164] = 0xffaa5500;
        colors[165] = 0xffaa5555;
        colors[166] = 0xffaa55aa;
        colors[167] = 0xffaa55ff;
        colors[168] = 0xffaaaa00;
        colors[169] = 0xffaaaa55;
        colors[170] = 0xffaaaaaa;
        colors[171] = 0xffaaaaff;
        colors[172] = 0xffaaff00;
        colors[173] = 0xffaaff55;
        colors[174] = 0xffaaffaa;
        colors[175] = 0xffaaffff;
        colors[176] = 0xffff0000;
        colors[177] = 0xffff0055;
        colors[178] = 0xffff00aa;
        colors[179] = 0xffff00ff;
        colors[180] = 0xffff5500;
        colors[181] = 0xffff5555;
        colors[182] = 0xffff55aa;
        colors[183] = 0xffff55ff;
        colors[184] = 0xffffaa00;
        colors[185] = 0xffffaa55;
        colors[186] = 0xffffaaaa;
        colors[187] = 0xffffaaff;
        colors[188] = 0xffffff00;
        colors[189] = 0xffffff55;
        colors[190] = 0xffffffaa;
        colors[191] = 0xffffffff;
        colors[192] = 0xff000000;
        colors[193] = 0xff000055;
        colors[194] = 0xff0000aa;
        colors[195] = 0xff0000ff;
        colors[196] = 0xff005500;
        colors[197] = 0xff005555;
        colors[198] = 0xff0055aa;
        colors[199] = 0xff0055ff;
        colors[200] = 0xff00aa00;
        colors[201] = 0xff00aa55;
        colors[202] = 0xff00aaaa;
        colors[203] = 0xff00aaff;
        colors[204] = 0xff00ff00;
        colors[205] = 0xff00ff55;
        colors[206] = 0xff00ffaa;
        colors[207] = 0xff00ffff;
        colors[208] = 0xff550000;
        colors[209] = 0xff550055;
        colors[210] = 0xff5500aa;
        colors[211] = 0xff5500ff;
        colors[212] = 0xff555500;
        colors[213] = 0xff555555;
        colors[214] = 0xff5555aa;
        colors[215] = 0xff5555ff;
        colors[216] = 0xff55aa00;
        colors[217] = 0xff55aa55;
        colors[218] = 0xff55aaaa;
        colors[219] = 0xff55aaff;
        colors[220] = 0xff55ff00;
        colors[221] = 0xff55ff55;
        colors[222] = 0xff55ffaa;
        colors[223] = 0xff55ffff;
        colors[224] = 0xffaa0000;
        colors[225] = 0xffaa0055;
        colors[226] = 0xffaa00aa;
        colors[227] = 0xffaa00ff;
        colors[228] = 0xffaa5500;
        colors[229] = 0xffaa5555;
        colors[230] = 0xffaa55aa;
        colors[231] = 0xffaa55ff;
        colors[232] = 0xffaaaa00;
        colors[233] = 0xffaaaa55;
        colors[234] = 0xffaaaaaa;
        colors[235] = 0xffaaaaff;
        colors[236] = 0xffaaff00;
        colors[237] = 0xffaaff55;
        colors[238] = 0xffaaffaa;
        colors[239] = 0xffaaffff;
        colors[240] = 0xffff0000;
        colors[241] = 0xffff0055;
        colors[242] = 0xffff00aa;
        colors[243] = 0xffff00ff;
        colors[244] = 0xffff5500;
        colors[245] = 0xffff5555;
        colors[246] = 0xffff55aa;
        colors[247] = 0xffff55ff;
        colors[248] = 0xffffaa00;
        colors[249] = 0xffffaa55;
        colors[250] = 0xffffaaaa;
        colors[251] = 0xffffaaff;
        colors[252] = 0xffffff00;
        colors[253] = 0xffffff55;
        colors[254] = 0xffffffaa;
        colors[255] = 0xffffffff;

    }
}
