/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="category", description="Represents a full category with possible parents which can be used when searching." )
public class RestCategory extends RestBase
{
	@ApiField( "The id of the category." )
	public Integer id;
	
	@ApiField( "The full name of the category." )
	public String name;
	
	@ApiField( "The alias of the category." )
	public String alias;
	
	@ApiField( "A liast of the parent aliases." )
	public String[] parents;
}
