/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="metro", description="Represents a metro." )
public class RestMetro extends RestBase
{
	@ApiField( "The id of the metro." )
	public Integer id;
	
	@ApiField( "The name of the metro." )
	public String name;
	
	@ApiField( "An image representative of the metro." )
	public RestUrl imageUrl;
	
	@ApiField( "A percentage representing the progress towards solving hunger." )
	public Float gapPercent;
	
	@ApiField( "The number of members needed to solve hunger." )
	public int gapMembersNeeded;
	
	@ApiField( "The number of users signed up in the metro." )
	public int numMembers;
	
	@ApiField( "The number of members required to activate the metro." )
	public int membersRequired;
	
	@ApiField( "The number of businesses signed up in the metro." )
	public int numBusinesses;
	
	@ApiField( "The number of businesses required to activate the metro." )
	public int businessesRequired;
	
	@ApiField( "Whether the metro is active." )
	public boolean metroActive;
}
