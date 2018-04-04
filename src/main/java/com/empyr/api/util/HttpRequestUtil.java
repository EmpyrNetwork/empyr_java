package com.empyr.api.util;

import java.util.Map;

public interface HttpRequestUtil
{	
	public String executeMethod( MethodType type, String endPoint, Map<String,Object> params );
}