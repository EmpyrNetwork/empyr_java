package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

@Documented( name="card", description="Represents a user's credit card." )
public class RestCard extends RestBase
{
	public enum CardState
	{
		ACTIVE,
		INACTIVE,
		ERROR
	}
	
	public enum CardType
	{
		VISA,
		MASTERCARD,
		AMERICAN_EXPRESS,
		DISCOVER,
		ENROUTE,
		JCB,
		DINERS_CLUB,
		AMERICAN_DINERS,
		CARTE_BLANCHE
	}
	
	@ApiField( "The id of the card." )
	public Integer id;
	@ApiField( "The last 4 of the credit card." )
	public String last4;
	@ApiField( "A valid expiration month 1 to 12" )
	public Integer expirationMonth;
	@ApiField( "A valid expiration year" )
	public Integer expirationYear;
	@ApiField( "Whether this is the card the user will be payed back on." )
	public boolean primary;
	@ApiField( "Indicates the current status of the card." )
	public CardState cardState;
	@ApiField( "Synchronization state." )
	public boolean synchronizationState;
	@ApiField( "The type of the credit card." )
	public CardType cardType;
	
	@ApiField( "The user who owns the card. Only populated CARD_ADD/CARD_REMOVE webhook events." )
	public RestCompactUser user;
}