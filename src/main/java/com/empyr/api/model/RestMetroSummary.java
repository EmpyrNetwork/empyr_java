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
public class RestMetroSummary extends RestBase
{
	@ApiField( "The id of the metro summary object." )
	public Integer id;
	
	@ApiField( "The month that this summary details." )
	public Date date;
	
	@ApiField( "The number of meals donated this month." )
	public int curMonthMealsDonated;
	
	@ApiField( "The total number of meals donated up to this month." )
	public int totalMealsDonated;
	
	@ApiField( "The meals donated over the last 90 days." )
	public int mealsDonated90;
}
