/**
 * 
 */
package com.empyr.api.model;

import java.util.Map;

/**
 * @author jcuzens
 *
 */
public class RestMeta extends RestBase
{
	public int code;
	public String error;
	public Map<String,String> errorDetails;
}
