package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

@Documented( name="user", description="A user response object. Includes all fields of the compactUser." )
public class RestUser extends RestCompactUser
{
	@ApiField( "Represents the relationship between the logged in user and this user. Not all calls." )
	public RestFriendship friendship;
	
	@ApiField( "The application id of the application that enrolled this user or not provided if not signed up by a 3rd party application." )
	public Integer applicationId;
	
	@ApiField( "The fundraiser associated with the user, if any." )
	public RestFundraiser fundraiser;
}