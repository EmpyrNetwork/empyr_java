/**
 * 
 */
package com.empyr.api.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.empyr.api.ClientListener;

/**
 * @author jcuzens
 *
 */
public class LoggingClientListener implements ClientListener
{
	private static transient final Log log = LogFactory.getLog( LoggingClientListener.class );
	
	@Override
	public void authorizationError( String error )
	{
		log.warn( "AuthError: " + error );
	}

	@Override
	public void connectionError( String error )
	{
		log.fatal( "Connection error access backend from mogl client." );
	}

	@Override
	public void validationError( String error, Map<String, String> errorDetails )
	{
		log.warn( "Detected validation errors:\n\tGlobal: " + error + "\n\tDetails: " + errorDetails );
	}

	@Override
	public void unexpectedError( String error )
	{
		log.fatal( "Unexpected error returned from the server: " + error );
	}
}
