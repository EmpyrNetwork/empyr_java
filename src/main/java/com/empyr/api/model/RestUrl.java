/**
 * 
 */
package com.empyr.api.model;

import java.io.Serializable;

/**
 * @author jcuzens
 *
 */
public class RestUrl implements Serializable
{
	private String value;
	
	public RestUrl( String value )
	{
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return value;
	}
}
