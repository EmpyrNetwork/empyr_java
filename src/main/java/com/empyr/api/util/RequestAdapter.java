package com.empyr.api.util;

import com.empyr.api.Request;

public interface RequestAdapter
{
	public abstract <T> void adapt( Request<T> r, String response );

	public abstract <T> T adapt( Class<T> clazz, String response );
}