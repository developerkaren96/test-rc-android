/**
 * Copyright (C) 2009 Michael A. MacDonald
 */
package com.iiordanov.util;

/**
 * Synchronized object pool
 *
 * @author Michael A. MacDonald
 */
public abstract class SafeObjectPool<R> extends ObjectPool<R> {

    /* (non-Javadoc)
     * @see com.antlersoft.util.ObjectPool#release(com.antlersoft.util.ObjectPool.Entry)
     */
    @Override
    public synchronized void release(Entry<R> entry) {
        super.release(entry);
    }

    /* (non-Javadoc)
     * @see com.antlersoft.util.ObjectPool#reserve()
     */
    @Override
    public synchronized Entry<R> reserve() {
        return super.reserve();
    }

}
