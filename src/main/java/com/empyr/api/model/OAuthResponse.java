/**
 * 
 */
package com.empyr.api.model;

/**
 * @author jcuzens
 *
 */
public class OAuthResponse
{
	// Sent on error
	public String error;
	public String error_description;
	
	// Sent on success
	public String access_token;
	public String token_type;
	public Integer expires_in;
	public String refresh_token;
}
