/**
 * 
 */
package com.empyr.api.model;

import java.util.Date;

import com.empyr.api.annotations.ApiField;

/**
 * @author jcuzens
 *
 */
public class RestFriendship extends RestBase
{
	public enum FriendState
	{
		CONFIRMED,
		BLOCKED,
		PENDING,
		REQUEST
	}
	
	@ApiField( "The id of this friendship" )
	public Integer id;
	
	@ApiField( "The user who owns the relationship." )
	public RestCompactUser user;
	
	@ApiField( "The friend." )
	public RestCompactUser friend;
	
	@ApiField( "The date the friendship was made." )
	public Date dateAdded;
	
	@ApiField( "The current state of the friendship." )
	public FriendState state;
}
