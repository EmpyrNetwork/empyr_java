/**
 * 
 */
package com.empyr.api.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author jcuzens
 *
 */
public class RestBase implements Serializable
{
	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString( this );
	}
}
