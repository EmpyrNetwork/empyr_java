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
@Documented( name="compactUser", description="Represents a compact version of the user." )
public class RestCompactUser extends RestBase
{
	public enum PrivacyLevel
	{
		NO_ONE,
		EVERYONE,
		FRIENDS
	}
	
	@ApiField( "A unique id for the user." )
	public Integer id;
	@ApiField( value="A user's first name.", nullable=true )
	public String firstname;
	@ApiField( value="A user's last name.", nullable=true )
	public String lastname;
	@ApiField( "The user's thumbnail url.")
	public RestUrl thumbnailUrl;
	
	@ApiField( value="The user's registered email address. This will only be available for the user who is logged in.", optional=true )
	public String email;
	
	@ApiField( "Privacy level for this user" )
	public PrivacyLevel privacyLevel;
	
	@ApiField( "The user's home address." )
	public RestAddress address;
	
	@ApiField( "The amount to default the meal donation percentage to." )
	public Float donatePercent;
	
	@ApiField( value="The number of notification alerts that the user currently has on their account. Only available on the logged in user and specific api calls.", optional=true )
	public int numAlerts;
	
	@ApiField( "The membership tier of the user." )
	public String membershipTier;
}
