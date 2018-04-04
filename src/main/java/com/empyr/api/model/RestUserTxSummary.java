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
@Documented( name="userSummary", description="An object that summarizes stats about the given user in the current month." )
public class RestUserTxSummary extends RestBase
{
	@ApiField( "The id of the user summary object." )
	public Integer id;
	
	@ApiField( "The month that this summary is over." )
	public Date date;
	
	@ApiField( "The balance of the user." )
	public Double balance;
	
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
	
	@ApiField( "The number of transactions this month." )
	public int curMonthTransactions;
	
	@ApiField( "The total number of transactions up to this month." )
	public int totalTransactions;
	
	@ApiField( "The amount of donations this month." )
	public Double curMonthDonationValue;
	
	@ApiField( "The amount of donations all time." )
	public Double totalDonationValue;
	
	@ApiField( "The total number of jackpots won up to this month." )
	public int totalJackpotsWon;
	
	@ApiField( "The total number of distinct places visited." )
	public int totalPlaces;	
	
	@ApiField( "The meals donated over the last 90 days." )
	public int mealsDonated90;
	
	@ApiField( "The total donation value over the last 90 days." )
	public Double donationLast90;
	
	@ApiField( "The rank of this object in a leaderboard. Note that this will only be provided on leaderboard methods." )
	public Integer rank;	
	
	@ApiField( "The compact user for this summary." )
	public RestCompactUser user;
	
	@ApiField( "The next period for which the user can expect a payment." )
	public Date nextPayPeriodDate;

	//@ApiField( "The amount that the user should expect in the next payment period. Adjusts on the 5th of the month." )
	public Double nextPayPeriodAmount;
	
	@ApiField( "The expected payout to the user." )
	public Double expectedPayout;
	
	@ApiField( "The total amount of money earned from jackpots, cashback, friends etc." )
	public Double moneyEarned;
}
