/**
 * Copyright (c) 2010 Michael A. MacDonald
 */
package com.codenow.droidlink.view.antlersoft.android.bc;

import android.view.MotionEvent;


public interface IBCMotionEvent {
	/**
	 * Obtain the number of pointers active in the event
	 * @see MotionEvent#getPointerCount()
	 * @param evt
	 * @return number of pointers
	 */
	int getPointerCount(MotionEvent evt);
}
