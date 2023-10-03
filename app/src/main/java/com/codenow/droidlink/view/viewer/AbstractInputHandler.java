
package com.codenow.droidlink.view.viewer;

import android.view.KeyEvent;
import android.view.MotionEvent;


interface AbstractInputHandler {

	boolean onKeyDown(int keyCode, KeyEvent evt);

	boolean onKeyUp(int keyCode, KeyEvent evt);

	boolean onTrackballEvent( MotionEvent evt);

	boolean onTouchEvent( MotionEvent evt);
	boolean onGenericMotionEvent(MotionEvent event);

	boolean onMouseMove( MotionEvent evt );
	boolean onMouseButton(MotionEvent event);


	CharSequence getHandlerDescription();
	

	String getName();
}
