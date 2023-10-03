/**
 * Copyright (c) 2010 Michael A. MacDonald
 */
package com.codenow.droidlink.view.antlersoft.android.bc;

import android.content.Context;

import java.io.File;


public interface IBCStorageContext {
	/**
	 * 
	 * @param context Context within the application with which the storage will be associated
	 * @param type May be null; if specified, references a sub-directory within the base directory
	 * for the app in the external storage
	 * @return File representing abstract path of storage directory; refer to android.os.Environment to
	 * see if the path is actually accessible
	 */
	public File getExternalStorageDir(Context context, String type);
}
