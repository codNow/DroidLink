/**
 * Copyright (C) 2009 Michael A. MacDonald
 */
package com.codenow.droidlink.view.antlersoft.android.bc;

import android.annotation.SuppressLint;
import android.app.ActivityManager;


@SuppressLint("NewApi")
public class BCActivityManagerV5 implements IBCActivityManager {


	@SuppressLint("NewApi")
	@Override
	public int getMemoryClass(ActivityManager am) {
		return am.getMemoryClass();
	}

}
