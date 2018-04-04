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
@Documented( name="donation", description="Represents a user donation." )
public class RestUserDonation extends RestBase
{
	@ApiField( "The id of the donation" )
	public Integer id;
	
	@ApiField( "The string representation for how long ago the transaction occurred." )
	public String age;
	
	@ApiField( "The transaction that this donation come from." )
	public RestTransaction transaction;
	
	@ApiField( "The jackpot that this donation came from." )
	public RestBusinessUserTotal jackpot;
	
	@ApiField( "The fundraiser that this donation is for." )
	public RestFundraiser fundraiser;
	
	@ApiField( "The cash value of the donation." )
	public Double cashValue;
	
	@ApiField( "The amount of the donation." )
	public Double donationAmount;
	
	@ApiField( "The date that the donation corresponds to." )
	public Date dateAdded;
}
