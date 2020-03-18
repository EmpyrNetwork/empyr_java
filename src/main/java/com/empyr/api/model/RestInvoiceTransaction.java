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
@Documented( name="invoiceTransaction", description="This represents an attempt to bill for an invoice." )
public class RestInvoiceTransaction
{
	@ApiField( "The id of the invoice transaction." )
	public Integer id;
	
	@ApiField( "The amount of the transaction" )
	public double amount;
	
	@ApiField( "The id of the invoice associated with this transaction." )
	public Integer invoiceId;
	
	@ApiField( "The date that the transaction was run. " )
	public Date dateAdded;
	
	@ApiField( value="The transaction error code", nullable=true )
	public String transactionErrorCode;
	
	@ApiField( "The type of the transaction (e.g. PAYMENT or REFUND)" )
	public String txType;
	
	@ApiField( "The state of the transaction [		PENDING,\n" + 
			"		SUCCESS,\n" + 
			"		FAILED,\n" + 
			"		REFUNDED]." )
	public String state;
}
