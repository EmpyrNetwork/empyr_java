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
@Documented( name="offerLink", description="Represents a link between a user and the offer." )
public class RestOfferLink
{
	@ApiField( "The id of the offer user link." )
	public Integer id;
	
	@ApiField( "The date that the link was created." )
	public Date dateAdded;
	
	@ApiField( value="The date that the link was last activated.", nullable=true )
	public Date lastActivationDate;
	
	@ApiField( "The status of the linkage [ACTIVE,INACTIVE]." )
	public String status;
	
	@ApiField( value="The offer associated with the link. To avoid circular references this will not be provided when being accessed from an offer.link.", optional=true )
	public RestOffer offer;
}

