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
@Documented( name="businessStatsReport", description="Report statistics about a given business." )
public class RestBusinessStatsReport extends RestBase
{
	@ApiField( name="businessId", value="The id of the business that the statistics are referring to." )
	public int businessId;
	
	@ApiField( name="statsType", value="The kind of statistic that is being reported on." )
	public String statsType;
	
	@ApiField( name="value", value="The value/aggregated amount of the given stat." )
	public double value;
	
	@ApiField( name="period", value="The month of the reporting period." )
	public Date period;
}
