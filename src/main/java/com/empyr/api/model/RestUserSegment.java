/**
 * 
 */
package com.empyr.api.model;

import java.util.Date;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="userSegment", description="A segment associated with the user" )
public class RestUserSegment extends RestBase
{
	@ApiField( "The id of the user segment association." )
	public Integer id;
	
	@ApiField( value="The expiration date of the user segment.", nullable=true )
	public Date expirationDate;
	
	@ApiField( value="The name of the segment" )
	public String segment;
}
