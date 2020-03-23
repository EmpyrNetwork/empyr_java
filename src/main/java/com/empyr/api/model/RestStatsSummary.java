/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="statsSummary", description="Represents stats report summary." )
public class RestStatsSummary extends RestBase
{
	@ApiField( value="The value of the grouped by option.", optional=true )
	public String groupedByValue;
	
	@ApiField( "The type of stat collected." )
	public String statsType;
	
	@ApiField( "The average value" )
	public double averageValue;
	
	@ApiField( "The summed value" )
	public double summedValue;
	
	@ApiField( "The min value" )
	public double minValue;
	
	@ApiField( "The max value" )
	public double maxValue;
}
