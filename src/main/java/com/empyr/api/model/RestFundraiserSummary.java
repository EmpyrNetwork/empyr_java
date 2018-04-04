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
@Documented( name="fundraiserSummary", description="Represents summary/donation information about the given fundraiser." )
public class RestFundraiserSummary
{
	@ApiField( "The id of the fundraiser summary" )
	public Integer id;
	@ApiField( "The current month's cashback donations." )
	public double curMonthCashback;
	@ApiField( "The total cashback donations for all time." )
	public double totalCashback;
	@ApiField( "The date that this summary represents." )
	public Date date;
}
