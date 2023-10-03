/**
 * Copyright (C) 2009 Michael A. MacDonald
 */
package com.codenow.droidlink.view.antlersoft.util;

public abstract class ObjectPool<R> {
	public static class Entry<S> {
		S item;
		Entry<S> nextEntry;
		
		Entry(S i, Entry<S> n)
		{
			item = i;
			nextEntry = n;
		}
		
		public S get() {
			return item;
		}
	}
	
	private Entry<R> next;
	public ObjectPool()
	{
		next = null;
	}
	
	public Entry<R> reserve()
	{
		if (next == null)
		{
			next = new Entry<R>(itemForPool(), null);
		}
		Entry<R> result = next;
		next = result.nextEntry;
		result.nextEntry = null;
		
		return result;
	}
	
	public void release(Entry<R> entry)
	{
		entry.nextEntry = next;
		next = entry;
	}
	
	protected abstract R itemForPool();
}
