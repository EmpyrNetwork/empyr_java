package com.empyr.api.model;

import java.util.Date;

/**
 * @author Royce Birnbaum
 */

public class RestIndexStatus extends RestBase
{
	public Integer id;
	public String type;
	public int shard;
	
	public Date lastUpdate;
	public int lastUpdateSize;
	public int failureCount;
	public String failureText;
	
	public Date rebuildBegin;
	public Date rebuildEnd;
	public int rebuildSize;
	public String rebuildNode;
}
