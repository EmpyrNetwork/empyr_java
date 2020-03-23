package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

@Documented( name="user", description="A user response object. Includes all fields of the compactUser." )
public class RestUser extends RestCompactUser
{
	@ApiField( value="Represents the relationship between the logged in user and this user. Not all calls.", optional=true )
	public RestFriendship friendship;
	
	@ApiField( value="The application id of the application that enrolled this user or not provided if not signed up by a 3rd party application.", optional=true )
	public Integer applicationId;
	
	@ApiField( value="The fundraiser associated with the user, if any.", optional=true )
	public RestFundraiser fundraiser;
}