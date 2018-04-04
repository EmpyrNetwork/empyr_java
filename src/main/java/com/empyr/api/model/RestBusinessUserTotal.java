/**
 * 
 */
package com.empyr.api.model;

import java.util.Date;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jcuzens
 *
 */
@Documented( name="userVenueTotal", description="An object that summarizes stats a user at a given venue at a given month in time." )
public class RestBusinessUserTotal extends RestBase
{
	@ApiField( "The id of the total" )
	public Integer id;
	
	@ApiField( "The user this total corresponds to" )
	public RestCompactUser user;
	
	@ApiField( "The venue this total corresponds to" )
	public RestCompactBusiness business;
	
	@ApiField( "The last visit date for this user" )
	public Date lastVisit;
	
	@ApiField( "The month this total corresponds to" )
	public Date date;
	@ApiField( "The rank of the user at the venue this time period" )
	public int	rank;
	
	@ApiField( "Amount of cashback earned this month." )
	public Double curMonthCashback;
	@ApiField( "The total cashback earned at this business for all time." )
	public Double totalCashback;
	
	@ApiField( "The amont spent this month." )
	public Double curMonthRevenue;
	
	@ApiField( "The total spent for all time." )
	public Double totalRevenue;
	
	@ApiField( "The amount of the jackpot won if any. Note that this is not always included unless querying specifically for jackpots." )
	public Double jackpotAmount;
	
	@ApiField( "Number of visits by this user to this place." )
	public int numVisits;
}
