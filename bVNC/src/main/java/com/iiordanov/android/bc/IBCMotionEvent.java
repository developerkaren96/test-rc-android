/**
 * Copyright (c) 2010 Michael A. MacDonald
 */
package com.iiordanov.android.bc;

import android.view.MotionEvent;

/**
 * Access to SDK-dependent features of MotionEvent
 *
 * @author Michael A. MacDonald
 * @see MotionEvent
 */
public interface IBCMotionEvent {
    /**
     * Obtain the number of pointers active in the event
     *
     * @param evt
     * @return number of pointers
     * @see MotionEvent#getPointerCount()
     */
    int getPointerCount(MotionEvent evt);
}
