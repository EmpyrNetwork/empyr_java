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
@Documented( name="businessSummary", description="An object that summarizes stats about the given business in the given month." )
public class RestBusinessTxSummary extends RestBase
{
	@ApiField( "The id of the user summary object." )
	public Integer id;
	
	@ApiField( "The month that this summary is over." )
	public Date date;
	
	@ApiField( "The cashback earned this month" )
	public Double curMonthCashback;
	
	@ApiField( "The total cashback earned up to this month." )
	public Double totalCashback;
	
	@ApiField( "The spend this month." )
	public Double curMonthRevenue;
	
	@ApiField( "The total spend up to this month." )
	public Double totalRevenue;
	
	@ApiField( "The number of meals donated this month." )
	public int curMonthMealsDonated;
	
	@ApiField( "The total number of meals donated up to this month." )
	public int totalMealsDonated;
	
	@ApiField( "The first place jackpot award amount." )
	public Double firstAmount;
	
	@ApiField( "The second place jackpot award amount." )
	public Double secondAmount;
	
	@ApiField( "The third place jackpot award amount." )
	public Double thirdAmount;
	
	@ApiField( "The meals donated over the last 90 days." )
	public int mealsDonated90;	
	
	@ApiField( "The rank of this object in a leaderboard. Note that this will only be provided on leaderboard methods." )
	public Integer rank;	
	
	@ApiField( "The compact business for this summary." )
	public RestCompactBusiness business;
}
