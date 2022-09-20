package com.empyr.api.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Royce Birnbaum
 */
public class RestIndexStatus
{
	private Integer id;
	private String type;
	private int shard;
	
	private Date lastUpdate;
	private int lastUpdateSize = -1;
	private int failureCount = 0;
	private String failureText;
	
	private Date rebuildBegin;
	private Date rebuildEnd;
	private int rebuildSize = -1;
	private String rebuildNode;
	
	protected RestIndexStatus()
	{
	}
	
	public RestIndexStatus( String type, int shard )
	{
		this.type = type;
		this.shard = shard;
	}
	
	/**
	 * @Return the id of the indexstatus
	 */
	public Integer getId()
	{
		return id;
	}
	
	public void setId( Integer id )
	{
		this.id = id;
	}	
	
	public String getType()
	{
		return type;
	}
	
	public void setType( String type )
	{
		this.type = type;
	}
	
	public int getShard()
	{
		return shard;
	}
	
	public void setShard( int shard )
	{
		this.shard = shard;
	}
	
	/**
	 * @Return the lastupdate.
	 */
	public Date getLastUpdate()
	{
		return lastUpdate;
	}
	
	public void setLastUpdate( Date lastUpdate )
	{
		this.lastUpdate = lastUpdate;
	}

	public int getLastUpdateSize()
	{
		return lastUpdateSize;
	}
	
	public void setLastUpdateSize( int lastUpdateSize )
	{
		this.lastUpdateSize = lastUpdateSize;
	}

	public int getFailureCount()
	{
		return failureCount;
	}
	
	public void setFailureCount( int failureCount )
	{
		this.failureCount = failureCount;
	}

	public String getFailureText()
	{
		return failureText;
	}
	
	public void setFailureText( String failureText )
	{
		this.failureText = failureText;
	}
	
	public Date getRebuildBegin()
	{
		return rebuildBegin;
	}
	
	public void setRebuildBegin( Date rebuildBegin )
	{
		this.rebuildBegin = rebuildBegin;
	}

	public Date getRebuildEnd()
	{
		return rebuildEnd;
	}
	
	public void setRebuildEnd( Date rebuildEnd )
	{
		this.rebuildEnd = rebuildEnd;
	}
	
	public int getRebuildSize()
	{
		return rebuildSize;
	}
	
	public void setRebuildSize( int rebuildSize )
	{
		this.rebuildSize = rebuildSize;
	}
	
	/**
	 * @Return the rebuildNode
	 */
	public String getRebuildNode()
	{
		return rebuildNode;
	}
	
	public void setRebuildNode( String rebuildNode )
	{
		this.rebuildNode = rebuildNode;
	}
	
	/* (non-Javadoc)
	 * @see com.mojopages.model.BaseObject#toString()
	 */
	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}
	
	/* (non-Javadoc)
	 * @see com.mojopages.model.BaseObject#equals(java.lang.Object)
	 */
	public boolean equals( Object o )
	{
		return EqualsBuilder.reflectionEquals( this, o );
	}
	
	/* (non-Javadoc)
	 * @see com.mojopages.model.BaseObject#hashCode()
	 */
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode( this );
	}
}
