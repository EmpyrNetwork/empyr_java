/**
 * 
 */
package com.empyr.partner.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVPrint;
import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.ExcelCSVPrinter;
import com.Ostermiller.util.LabeledCSVParser;
import com.empyr.api.EmpyrClient;
import com.empyr.api.model.RestApplication;
import com.empyr.api.model.RestCard;
import com.empyr.api.model.RestResponse;
import com.empyr.api.model.RestUser;

/**
 * @author jcuzens
 *
 */
public class CSVImportClient
{
	@Option( name="-c", required=true, usage="Specifies the client id." )
	private String clientId;
	
	@Option( name="-s", required=true, usage="Specifies the client secret." )
	private String clientSecret;
	
	@Option( name="-f", required=true, usage="Specifies an input file." )
	private File inputFile;
	
	@Option( name="-e", required=true, usage="Specifies the error output file." )
	private File errorFile;
	
	@Option( name="-o", required=true, usage="Specifies the success output file." )
	private File successFile;
	
	@Option( name="-l", required=false, usage="Specifies what line of the input file to start at." )
	private int startLine = -1;
	
	@Option( name="-t", required=false, usage="Points the client at the mogl test server at test.mogl.com." )
	private boolean test = false;
	
	private int rowsProcessed = 0;
	
	private CSVPrint getPrinter( OutputStream out )
	{
		return new ExcelCSVPrinter( out );
	}

	private CSVParse getParser( InputStream in )
	{
		return new ExcelCSVParser( in );
	}	
	
	public static class Card
	{
		public String number;
		public int expirationMonth;
		public int expirationYear;
		
		public Card( String number, String expirationMonth, String expirationYear )
		{
			this.number = number;
			this.expirationMonth = Integer.parseInt( expirationMonth );
			this.expirationYear = Integer.parseInt( expirationYear );
		}

		public String toString()
		{
			return ReflectionToStringBuilder.toString( this );
		}
	}
	
	public void doImport( String clientId, String clientSecret, boolean testMode, InputStream in, OutputStream success, OutputStream error ) throws IOException
	{
		try
		{
			CSVParse parser = getParser( in );
			CSVPrint successPrinter = getPrinter( success );
			CSVPrint errorPrinter = getPrinter( error );
			
			LabeledCSVParser input = new LabeledCSVParser( parser );
			
			EmpyrClient mogl = new EmpyrClient( clientId, clientSecret );
			mogl.setAutoRetryTimes( 1 );
			
			if( testMode )
			{
				mogl.setHost( "https://test.mogl.com" );
			}
			
			RestResponse<RestApplication> ra = mogl.getApplicationInfo();
			
			if( ra.meta.code != 200 )
			{
				throw new RuntimeException( "Failed to get application information. Aborting..." );
			}
			
			int applicationId = ra.response.id;
			
			while( input.getLine() != null )
			{	
				String email = input.getValueByLabel( "email" );
				
				if( rowsProcessed < startLine )
				{
					System.out.println( "Skipping row... " + email );
					rowsProcessed++;
					continue;
				}				
				
				if( StringUtils.isEmpty( email ) )
				{
					throw new RuntimeException( "Couldn't find a value for the email column " +
							"this could be because you don't have a header row. Please check " +
							"the input file." );
				}
				
				System.out.println( email );
				boolean skipUser = false;
				boolean skipSignup = false;
				boolean skipCards = false;
				
				// A list of cards that we will add to the user's
				// account we will check if any of them are already registered.
				Collection<Card> cards = new ArrayList<Card>();
				
				// First let's determine if we are trying to remove or add a card.
				String action = input.getValueByLabel( "action" );
				
				if( !StringUtils.isEmpty( action ) && action.equalsIgnoreCase( "R" ) )
				{
					// We should always skip the user if we are removing a card.
					skipUser = true;
					
					// This is a removal of the card
					String cardData = input.getValueByLabel( "cards" );
					cardData = cardData.split( " " )[0];
					
					// We will use only the PAN to remove the card. In the case
					// of a removal the only thing that should be in this column
					// is the card PAN (no expiration month/year).
					RestResponse<Boolean> removeResponse = mogl.cardsRemove( cardData, email );
					
					if( removeResponse.meta.code != 200 )
					{
						printError( errorPrinter, email, "Unexpected remove response: " + removeResponse );
					}
				}
				// Implicitly there was no other state so add the card.
				else
				{	
					String cardsData = input.getValueByLabel( "cards" );
					if( !StringUtils.isEmpty( cardsData ) )
					{
						String cardsStrings[] = cardsData.split( "&" );
						if( cardsStrings != null && cardsStrings.length > 0 )
						{
							for( String card : cardsStrings )
							{
								String[] cardParts = card.split( "\\s" );
								cards.add( new Card( cardParts[0], cardParts[2], cardParts[1] ) );
								
								RestResponse<Boolean> cardCheckResponse = mogl.cardsExists( cardParts[0] );
								
								if( cardCheckResponse.meta.code != 200 )
								{
									skipUser = true;
									printError( errorPrinter, email, "Unexpected card check response: " + cardCheckResponse );
									break;
								}
								
								if( cardCheckResponse.response )
								{
									skipUser = true;
									printError( errorPrinter, email, "Card already registered: " + (rowsProcessed + 1) );
									break;
								}
							}
						}
					}
				}
				
				// Now check and see if this user's email already exists.
				if( !skipUser )
				{
					RestResponse<RestUser> userResponse = mogl.usersLookup( email );
					
					if( userResponse.meta.code == 403 )
					{
						skipUser = true;
						printError( errorPrinter, email, "User account has been disabled." );
					}
					else if( userResponse.meta.code != 404 )
					{
						if( userResponse.meta.code != 200 )
						{
							throw new RuntimeException( "Unexpected response received: " + userResponse );
						}
						
						if( userResponse.response.applicationId == null || applicationId != userResponse.response.applicationId )
						{
							skipUser = true;
							printError( errorPrinter, email, "User's email already registered by another application" );
						}else
						{
							skipSignup = true;
						}
					}
				}
				
				// The user and their cards did not exist so try to signup the user.
				if( !skipUser )
				{
					if( !skipSignup )
					{
						Map<String,Object> additionalParams = new HashMap<String,Object>();
						additionalParams.put( "sendWelcomeEmail", false );
						
						String password = input.getValueByLabel( "password" );
						if( StringUtils.isEmpty( password ) )
						{
							additionalParams.put( "generatePassword", true );
							password = null;
						}
						
						String referralCode = input.getValueByLabel( "referralCode" );
						if( StringUtils.isEmpty( referralCode ) )
						{
							referralCode = null;
						}
						
						String postalCode = input.getValueByLabel( "postalcode" );
						if( postalCode.matches( "\\d{5}.*" ) )
						{
							postalCode = postalCode.substring( 0, 5 );
						}					
						
						RestResponse<RestUser> userResponse = mogl.usersSignup( 
								input.getValueByLabel( "firstname" ), 
								input.getValueByLabel( "lastname" ), 
								email, 
								postalCode, 
								password, 
								referralCode, 
								additionalParams 
								);
						
						if( userResponse.meta.code != 200 )
						{
							printError( errorPrinter, email, "Got error signing up user: " + userResponse );
							skipCards = true;
						}
					}
					
					if( !skipCards )
					{
						// Now add the card to the user we just signed up.
						for( Card c : cards )
						{
							RestResponse<RestCard> cardResponse = mogl.cardsAdd( 
								c.number, 
								c.expirationMonth, 
								c.expirationYear, 
								email 
							);
							
							if( cardResponse.meta.code != 200 )
							{
								printError( errorPrinter, email, "Error adding card: " + cardResponse );
							}
						}
						
						printSuccess( successPrinter, email );
					}
				}
				
				rowsProcessed++;
				
				if( rowsProcessed % 100 == 0 )
				{
					System.out.println( "Processing... " + rowsProcessed );
				}
			}
		}finally
		{
			in.close();
			success.close();
			error.close();
		}		
	}
	
	public void doImport() throws Exception
	{
		InputStream in = new FileInputStream( inputFile );
		OutputStream sStream = new FileOutputStream( successFile );
		OutputStream eStream = new FileOutputStream( errorFile );
		
		doImport( clientId, clientSecret, test, in, sStream, eStream );
	}

	private void printSuccess( CSVPrint successPrinter, String email )
	{
		successPrinter.println( new String[]{ email } );
	}

	private void printError( CSVPrint errorPrinter, String email, String message )
	{
		errorPrinter.println( new String[]{ email, message } );
	}

	public static void main( String args[] ) throws Exception
	{	
		CSVImportClient importer = new CSVImportClient();
		
		CmdLineParser parser = new CmdLineParser( importer );
		
		try
		{
			parser.parseArgument( args );
		}catch( CmdLineException e )
		{
			System.err.println( e.getMessage() );
			parser.printUsage( System.err );
			System.exit( -1 );
		}
		
		System.out.println( "Starting MOGL Importer..." );
		
		importer.doImport();
		
		System.out.printf( "Finished import %d rows processed.", importer.rowsProcessed );
		
		System.exit( 0 );
	}
}
