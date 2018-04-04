/**
 * 
 */
package com.empyr.api.model;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;
import com.empyr.api.model.RestOffer.OfferRewardType;

/**
 * @author jarrodcuzens
 *
 */
@Documented( name="variableReward", description="Represents a variable reward that has been offered by the venue." )
public class RestVariableReward extends RestBase
{
	@ApiField( "The id of the variable reward." )
	public Integer id;
	
	@ApiField( "The time this offer starts." )
	public Integer startsAt;
	
	@ApiField( "The time this offer ends." )
	public Integer endsAt;
	
	@ApiField( "The type of the reward value." )
	public OfferRewardType rewardType;
	
	@ApiField( "The discount you can expect." )
	public Double discount;
	
	@ApiField( "Whether or not this offer is currently active." )
	public boolean active;
	
	@ApiField( "Day of week." )
	public String dayOfWeek;
}
