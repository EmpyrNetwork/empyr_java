/**
 * 
 */
package com.empyr.api.model;

/**
 * @author jcuzens
 *
 */
public class RestResponse<T> extends RestBase
{
	public RestMeta meta;
	public T response;
}
