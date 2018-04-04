/**
 * 
 */
package com.empyr.api;

import java.util.Map;

/**
 * Listens for specific errors from the Empyr client. For example, this listener could 
 * be used from within a UI and when an authorizationError occurs it would know to prompt 
 * the user to log in.
 * 
 * <p>This class should be registered with the EmpyrClient class</p>
 * 
 * @author jcuzens
 */
public interface ClientListener
{
	public void authorizationError( String error );
	
	public void connectionError( String error );
	
	public void validationError( String error, Map<String,String> errorDetails );
	
	public void unexpectedError( String error );
}
