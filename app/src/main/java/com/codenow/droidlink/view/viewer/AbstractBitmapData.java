
package com.codenow.droidlink.view.viewer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;

import java.io.IOException;


abstract class AbstractBitmapData {
	int framebufferwidth;
	int framebufferheight;
	int bitmapwidth;
	int bitmapheight;
	RfbProto rfb;
	Bitmap mbitmap;
	int bitmapPixels[];
	Canvas memGraphics;
	boolean waitingForInput;
	VncCanvas vncCanvas;
	private AbstractBitmapDrawable drawable;

	AbstractBitmapData( RfbProto p, VncCanvas c)
	{
		rfb=p;
		vncCanvas = c;
		framebufferwidth=rfb.framebufferWidth;
		framebufferheight=rfb.framebufferHeight;
	}
	
	synchronized void doneWaiting()
	{
		waitingForInput=false;
	}
	
	final void invalidateMousePosition()
	{
		if (vncCanvas.settings.getUseLocalCursor())
		{
			if (drawable==null)
				drawable = createDrawable();
			drawable.setCursorRect(vncCanvas.mouseX,vncCanvas.mouseY);
			vncCanvas.invalidate(drawable.cursorRect);
		}
	}
	

	float getMinimumScale()
	{
		double scale = 0.75;
		int displayWidth = vncCanvas.getWidth();
		int displayHeight = vncCanvas.getHeight();
		for (; scale >= 0; scale -= 0.25)
		{
			if (scale * bitmapwidth < displayWidth || scale * bitmapheight < displayHeight)
				break;
		}
		return (float)(scale + 0.25);
	}
	

	abstract void writeFullUpdateRequest( boolean incremental) throws IOException;

	abstract boolean validDraw( int x, int y, int w, int h);
	

	abstract int offset( int x, int y);
	

	abstract void updateBitmap( int x, int y, int w, int h);
	

	abstract AbstractBitmapDrawable createDrawable();
	

	void updateView(ImageView v)
	{
		if (drawable==null)
			drawable = createDrawable();
		v.setImageDrawable(drawable);
		v.invalidate();
	}
	

	abstract void copyRect( Rect src, Rect dest, Paint paint);
	

	abstract void drawRect( int x, int y, int w, int h, Paint paint);
	

	abstract void scrollChanged( int newx, int newy);
	

	abstract void syncScroll();

	void dispose()
	{
		if ( mbitmap!=null )
			mbitmap.recycle();
		memGraphics = null;
		bitmapPixels = null;
	}
}
