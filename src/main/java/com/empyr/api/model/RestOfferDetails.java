package com.empyr.api.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.empyr.api.annotations.ApiField;
import com.empyr.api.annotations.Documented;

@Documented( name="offerDetails", description="Represents all the complex details of an offer." )
public class RestOfferDetails extends RestBase
{
	public enum OfferInterval {
		DAY,
		WEEK,
		MONTH
	}
	
	public enum OfferScheduleType {
		INCLUDE,
		EXCLUDE,
		REWARD
	}
	
	@ApiField( "The date the offer was created." )
	public Date					dateAdded;
	
	@ApiField( "The date that the offer started or will start." )
	public Date					startDate;
	@ApiField( value="The date that the offer will end.", nullable=true )
	public Date					endDate;
	
	@ApiField( value="The maximum number of times that an offer can be linked across all users.", nullable=true )
	public Integer				maxActivations;
	
	@ApiField( value="The minimum spend necessary for the offer to apply.", nullable=true )
	public Integer				minPurchaseAmount;
	
	@ApiField( "Whether this offer has a schedule associated with it. Schedule type determines how the schedule applies." )
	public boolean				hasSchedule = false;
	@ApiField( "The schedule type determines how the schedule applies, can be INCLUDE, EXCLUDE, or REWARD" )
	public OfferScheduleType 	scheduleType;
	@ApiField( value="A list of the complete schedule for the offer", type=RestVariableReward.class )
	public Map<String,List<RestVariableReward>> schedule = new LinkedHashMap<String,List<RestVariableReward>>();
	@ApiField( "Applies when an offer schedule is REWARD and a transaction occurs during a time not specified in the schedule. If true and a transaction occurs outside of the schedule then the reward specified at the offer level will apply." )
	public boolean				hasDefault = false;
	// Need the schedules
	
	@ApiField( value="The maximum number of times users can redeem the offer.", nullable=true )
	public Integer				maxRedemptions;
	@ApiField( value="The minimum time (in seconds) in which an offer could be redeemed. For example, a user can't redeem more than once an hour.", nullable=true )
	public Integer				minRedemptionInterval;
	
	@ApiField( value="The maximum number of times a user can redeem an offer within the specified userRedemptionInterval.", nullable=true )
	public Integer				maxUserRedemptionsPerInterval;
	
	@ApiField( value="The maximum number of times a user can redeem an offer regardless of interval.", nullable=true )
	public Integer				maxTotalUserRedemptions;
	
	@ApiField( value="The maximum qualified spend that can be applied to an offer per interval (for example $500 of spend will be rewarded).", nullable=true )
	public Double				maxQualifiedUserSpendPerInterval;
	
	@ApiField( value="The interval period as applied to the interval type. For example, if this was 2 and the interval type was DAY then this would mean 2 days.", nullable=true )
	public Integer				userRedemptionRenewalInterval;
	@ApiField( "The interval type in which the redemption window renews. Can be DAY, WEEK, MONTH. If WEEK or MONTH then it's applied for a calendar month/week." )
	public OfferInterval		userRedemptionRenewalIntervalType = OfferInterval.DAY;
	
	@ApiField( value="The minimum number of purchases necessary for the offer to be redeemed.", nullable=true)
	public Integer				minPurchases;
	@ApiField( value="The minimum amount of time (in seconds) between purchases in a multi-purchase offer.", nullable=true )
	public Integer				purchaseMinInterval;
	@ApiField( value="The maximum amount of time (in seconds) between purchases in a multi-purchase offer.", nullable=true )
	public Integer				purchaseMaxInterval;
	@ApiField( "Whether the spend thresholds for the offer are cumulative over multiple purcahses or indvidually applied per purchase." )
	public boolean				cumulative = false;
	
	@ApiField( value="The budget for the offer. Once the budget has been exceeded the offer will no longer apply.", nullable=true )
	public Integer				budget = 0;
	
	@ApiField( value="The spent budget for the offer. This is the sum of (cashback amout + referral fees) .", nullable=true, optional=true  )
	public Double				spentBudget;
	
	@ApiField( value="If specified this allows an offer to apply after the offer has expired. Typically this will be the same as the offer end date.", nullable=true )
	public Date					maxRedeemDate;
	
	@ApiField( "Whether the offer is a dynamic offer (meaning the reward/fee can change). If it is changed the dateRewardChanged will be set to the last time the reward changed." )
	public boolean				dynamic = false;
	
	@ApiField( value="The targeting segment which the user must belong to in order for a transaction to match.", optional=true )
	public String				targetingSegment;
}
