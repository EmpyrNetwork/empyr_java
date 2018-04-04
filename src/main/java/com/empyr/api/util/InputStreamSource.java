/**
 * 
 */
package com.empyr.api.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.methods.multipart.PartSource;

/**
 * @author jcuzens
 *
 */
public class InputStreamSource implements PartSource
{

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.methods.multipart.PartSource#getLength()
	 */
	@Override
	public long getLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.methods.multipart.PartSource#getFileName()
	 */
	@Override
	public String getFileName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.methods.multipart.PartSource#createInputStream()
	 */
	@Override
	public InputStream createInputStream() throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
