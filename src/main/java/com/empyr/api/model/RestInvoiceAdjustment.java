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
@Documented( name="invoiceAdjustment", description="A charge (positive) or credit (negative) that is applied to an invoice." )
public class RestInvoiceAdjustment
{
	@ApiField( "The id of the adjustment" )
	public Integer id;
	
	@ApiField( "The description of the adjustment" )
	public String description;
	
	@ApiField( "The id of the invoice this adjustment is applied to." )
	public Integer invoiceId;
	
	@ApiField( "The amount of the adjustment" )
	public double amount;
}
