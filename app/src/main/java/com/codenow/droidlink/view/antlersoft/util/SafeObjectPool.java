/**
 * Copyright (C) 2009 Michael A. MacDonald
 */
package com.codenow.droidlink.view.antlersoft.util;

public abstract class SafeObjectPool<R> extends ObjectPool<R> {

	@Override
	public synchronized void release(ObjectPool.Entry<R> entry) {
		super.release(entry);
	}

	/* (non-Javadoc)
	 * @see com.antlersoft.util.ObjectPool#reserve()
	 */
	@Override
	public synchronized ObjectPool.Entry<R> reserve() {
		return super.reserve();
	}

}
