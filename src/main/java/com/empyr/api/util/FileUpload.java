package com.empyr.api.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.methods.multipart.PartSource;

public class FileUpload implements PartSource
{
	private String fileName;
	private String contentType;
	private InputStream inputStream;
	private long length;
	
	public FileUpload( String name, String contentType, InputStream stream, long size )
	{
		this.fileName = name;
		this.contentType = contentType;
		this.inputStream = stream;
		this.length = size;
	}

	@Override
	public long getLength()
	{
		return length;
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}

	@Override
	public InputStream createInputStream() throws IOException
	{
		return inputStream;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType()
	{
		return contentType;
	}

}
