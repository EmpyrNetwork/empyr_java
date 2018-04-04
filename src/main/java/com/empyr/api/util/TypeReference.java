package com.empyr.api.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This ultimately is a direct copy of the Jackson TypeReference class
 * that was copied so as to avoid a direct dependency on Jackson in the
 * base EmpyrClient.
 * 
 * @author jcuzens
 *
 * @param <T>
 */
public abstract class TypeReference<T> implements Comparable<TypeReference<T>>
{
	protected final Type _type;

	protected TypeReference()
	{
		Type superClass = getClass().getGenericSuperclass();
		if( superClass instanceof Class<?> )
		{ // sanity check, should never happen
			throw new IllegalArgumentException(
					"Internal error: TypeReference constructed without actual type information" );
		}
		/*
		 * 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect it is
		 * possible to make it fail? But let's deal with specific case when we
		 * know an actual use case, and thereby suitable workarounds for valid
		 * case(s) and/or error to throw on invalid one(s).
		 */
		_type = ( (ParameterizedType)superClass ).getActualTypeArguments()[0];
	}

	public Type getType()
	{
		return _type;
	}

	/**
	 * The only reason we define this method (and require implementation of
	 * <code>Comparable</code>) is to prevent constructing a reference without
	 * type information.
	 */
	// @Override
	public int compareTo( TypeReference<T> o )
	{
		// just need an implementation, not a good one... hence:
		return 0;
	}
}
