/**
 * 
 */
package com.empyr.api.exceptions;

/**
 * @author jcuzens
 *
 */
public class GatewayTimeoutException extends RuntimeException
{
	public GatewayTimeoutException( String msg )
	{
		super( msg );
	}
}
