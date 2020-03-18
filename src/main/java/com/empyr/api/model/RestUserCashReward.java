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
@Documented( name="cashReward", description="A cash reward that is given to the user." )
public class RestUserCashReward
{
	@ApiField( "The id of the reward" )
	public Integer id;
	
	@ApiField( "The date the reward was given" )
	public Date dateAdded;
	
	@ApiField( "The amount of the reward" )
	public double amount;
	
	@ApiField( value="The description of the reward", nullable=true )
	public String description;
}
