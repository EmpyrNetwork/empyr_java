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
@Documented( name="invoice", description="Invoices that would be generated against a merchant/account." )
public class RestInvoice
{
	@ApiField( "The id of the invoice" )
	public Integer id;
	
	@ApiField( "The account associated with the invoice" )
	public RestBillingAccount account;
	
	@ApiField( "The business associated with this invoice" )
	public RestCompactBusiness business;
	
	@ApiField( "Date invoice generated" )
	public Date dateGenerated;
	
	@ApiField( value="Date the invoice was closed", nullable=true )
	public Date dateClosed;
	
	@ApiField( "The date the invoice will be billed." )
	public Date dateBilled;
	
	@ApiField( "The start date this invoice corresponds to" )
	public Date startDate;
	
	@ApiField( "The end date the invoice corresponds to" )
	public Date endDate;
	
	@ApiField( "The state of the invoice [PENDING,\n" + 
			"		POSTED,\n" + 
			"		COMPLETED,\n" + 
			"		FAILED,\n" + 
			"		PAST_DUE]" )
	public String state;
	
	@ApiField( "The amount paid agaisnt the invoice" )
	public double payments;
	
	@ApiField( "The total of the invoice" )
	public double total;
	
}
