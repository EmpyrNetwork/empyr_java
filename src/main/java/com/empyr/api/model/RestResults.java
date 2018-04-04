/**
 * 
 */
package com.empyr.api.model;

import java.util.ArrayList;
import java.util.List;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="results", description="A container for many valued data which may require paging." )
public class RestResults<T> extends RestBase
{
	@ApiField( "Contains an array of objects." )
	public List<T> results = new ArrayList<T>();
	@ApiField( "Represents the total number of objects that could be returned." )
	public int hits = 0;
}
