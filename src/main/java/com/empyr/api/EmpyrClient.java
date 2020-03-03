/**
 * 
 */
package com.empyr.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.empyr.api.exceptions.LoginException;
import com.empyr.api.model.OAuthResponse;
import com.empyr.api.model.RestApplication;
import com.empyr.api.model.RestBusiness;
import com.empyr.api.model.RestCard;
import com.empyr.api.model.RestResponse;
import com.empyr.api.model.RestResults;
import com.empyr.api.model.RestTransaction;
import com.empyr.api.model.RestUser;
import com.empyr.api.util.CommonsHttpRequestUtil;
import com.empyr.api.util.FileUpload;
import com.empyr.api.util.HttpRequestUtil;
import com.empyr.api.util.JacksonRequestAdapter;
import com.empyr.api.util.MethodType;
import com.empyr.api.util.RequestAdapter;
import com.empyr.api.util.TypeReference;

/**
 * Main interface for interacting with the Empyr API. This class does not
 * implement ALL the methods of the Empyr API. It is recommended that you
 * subclass and extend the EmpyrClient with your own and add extension methods
 * to the subclass.
 * 
 * @author jcuzens
 *
 */
public class EmpyrClient
{
	private static volatile EmpyrClient mainInstance = null;
	
	private static final String OAUTH_ENDPOINT = "/oauth/token";
	private static final String API_ENDPOINT = "/api/v2";
	private String host = "https://api.mogl.com";
	private String clientId;
	private String clientSecret;
	private String accessToken;
	private String userToken;

	private int autoRetryTimes = -1;
	
	private HttpRequestUtil requestUtil = new CommonsHttpRequestUtil();
	private RequestAdapter requestAdapter = new JacksonRequestAdapter();
	
	private List<ClientListener> listeners = new ArrayList<ClientListener>();
	
	public EmpyrClient( String clientId )
	{
		this.clientId = clientId;
	}
	
	public EmpyrClient( String clientId, String clientSecret )
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	public void addListener( ClientListener cl )
	{
		this.listeners.add( cl );
	}
	
	public void removeListener( ClientListener cl )
	{
		this.listeners.remove( cl );
	}
	
	/**
	 * @return the clientId
	 */
	public String getClientId()
	{
		return clientId;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret()
	{
		return clientSecret;
	}
	
	/**
	 * @return the userToken
	 */
	public String getUserToken()
	{
		return userToken;
	}
	
	/**
	 * @return the accessToken
	 */
	public String getAccessToken()
	{
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken( String accessToken )
	{
		this.accessToken = accessToken;
	}	
	
	/**
	 * @param autoRetryTimes the autoRetryTimes to set
	 */
	public void setAutoRetryTimes( int autoRetryTimes )
	{
		this.autoRetryTimes = autoRetryTimes;
	}
	
	/**
	 * @param host Sets the host that we will attempt to connect to.
	 */
	public void setHost( String host )
	{
		this.host = host;
	}
	
	/**
	 * @return Returns the oauth endpoint url.
	 */
	private String getOAuthEndpoint()
	{
		return host + OAUTH_ENDPOINT;
	}
	
	/**
	 * @return Returns the api endpoint url.
	 */
	private String getApiEndpoint()
	{
		return host + API_ENDPOINT;
	}
	
	/**
	 * Convenience method for returning a previously initialized EmpyrClient.
	 * 
	 * @return Return a previously initialized EmpyrClient.
	 */
	public static EmpyrClient getInstance()
	{
		if( mainInstance == null )
		{
			throw new RuntimeException( "Empyr client instance should have been previously initialized through a getInstance call that had the client id." );
		}
		
		return mainInstance;
	}

	/**
	 * Initializes a main EmpyrClient instance with the provided client
	 * id. This is the most common way that the Empyr library would be used
	 * when in a mobile environment since the clientSecret should only be
	 * on the server side. 
	 * 
	 * @param clientId The client id of the application.
	 * 
	 * @return Returns the EmpyrClient main instance.
	 */
	public static EmpyrClient getInstance( 
			String clientId
		)
	{
		if( mainInstance == null )
		{
			mainInstance = new EmpyrClient( clientId );
		}
		
		return mainInstance;
	}
	
	/**
	 * Initializes a main EmpyrClient instance with the provided client id
	 * and client secret. Note that this would only be called from a controlled
	 * environment (e.g. server and not mobile) because of the exposure of 
	 * the client secret.
	 * 
	 * @param clientId The client id of the application.
	 * @param clientSecret The client secret of the application.
	 * @return Returns the EmpyrClient main instance.
	 */
	public static EmpyrClient getInstance( 
			String clientId,
			String clientSecret
		)
	{
		if( mainInstance == null )
		{
			mainInstance = new EmpyrClient( clientId, clientSecret );
		}
		
		return mainInstance;
	}
	
	/**
	 * Identifies the user by providing the userToken.
	 * 
	 * @param userToken The userToken for the user.
	 */
	public void identify( String userToken )
	{
		this.userToken = userToken;
	}
	
	
	/**
	 * Signs up a user with the Empyr backend.
	 * 
	 * @param firstname The first name of the user
	 * @param lastname The last name of the user
	 * @param email The email address or usertoken for the user. Needs to be in email form.
	 * @param postalCode The postal code of the user.
	 * @param password The password for the user.
	 * @param referralCode The referralcode the user signed up with.
	 * @param additionalParams Any additional params to pass to the API.
	 * @return The user object.
	 */
	public RestResponse<RestUser> usersSignup(
				String firstname,
				String lastname,
				String email,
				String postalCode,
				String password,
				String referralCode,
				Map<String,Object> additionalParams
			)
	{
		Request<RestUser> r = Request.<RestUser> createRequest( MethodType.POST, "/users", true )
			.addParams( "firstName", firstname,
					"lastName", lastname,
					"email", email,
					"address.postalCode", postalCode,
					"password", password,
					"userWhoInvited", referralCode
				)
			.addParams( additionalParams )
			.expects( "user", RestUser.class );
		
		return executeRequest( r );
	}
	
	/**
	 * @param userId The userId to lookup the user.
	 * @return The user object.
	 */
	public RestResponse<RestUser> usersGet(
			String userId
		)
	{
		Request<RestUser> r = Request.<RestUser> createRequest( MethodType.GET, "/users/" + userId )
			.expects( "user", RestUser.class );
		
		return executeRequest( r ); 
	}
	
	/**
	 * @param fu The photo to upload to the user.
	 * @return The user object.
	 */
	public RestResponse<RestUser> uploadPhoto(
			FileUpload fu
		)
	{
		Request<RestUser> r = Request.<RestUser> createRequest( MethodType.POST, "/users/updatePhoto" )
			.addParams( "file", fu )
			.expects( "user", RestUser.class );
		
		return executeRequest( r ); 
	}	
	
	/**
	 * @param email The user to lookup by email or usertoken.
	 * @return The user object.
	 */
	public RestResponse<RestUser> usersLookup(
			String email
		)
	{
		Request<RestUser> r = Request.<RestUser> createRequest( MethodType.GET, "/users/lookup", true )
			.addParams( "email", email )
			.expects( "user", RestUser.class );
		
		return executeRequest( r );
	}
	
	/**
	 * @param userId The userId to get transactions.
	 * @param userToken The userToken for the user.
	 * @return A list of transactions for the user.
	 */
	public RestResponse<RestResults<RestTransaction>> usersGetTransactions(
			Integer userId,
			String userToken
		)
	{
		new AssertionError( userId != null );
		
		Request<RestResults<RestTransaction>> r = Request.<RestResults<RestTransaction>> createRequest( MethodType.GET, "/users/" + userId + "/transactions", true, userToken )
			.expects( "transactions", new TypeReference<RestResults<RestTransaction>>(){} )
			;
		
		return executeRequest( r );
	}
	
	public synchronized String usersLogin( String username, String password )
	{
		String endPoint = getOAuthEndpoint();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put( "client_id", clientId );
		params.put( "client_secret", clientSecret );
		params.put( "grant_type", "password" );
		params.put( "username", username );
		params.put( "password", password );
		params.put( "code", "token" );
		
		String strResponse = requestUtil.executeMethod( MethodType.POST, endPoint, params );
		
		OAuthResponse or = requestAdapter.adapt( OAuthResponse.class, strResponse );
		
		if( or != null && or.access_token != null )
		{
			this.accessToken = or.access_token;
		}else
		{
			authorizationError( or.error_description );
			throw new LoginException( or.error_description );
		}
		
		return this.accessToken;
	}	
	
	public RestResponse<Map<String, String>> getTopCategories()
	{
		Request<Map<String, String>> r = Request.<Map<String, String>> 
				createRequest( MethodType.GET,	"/utilities/categories" )
				.expects( "results",	new TypeReference<Map<String, String>>() {} );

		return executeRequest( r );
	}
	
	public RestResponse<Map<String, String>> getFeatures()
	{
		Request<Map<String, String>> r = Request.<Map<String, String>> 
				createRequest( MethodType.GET,	"/utilities/features" )
				.expects( "results",	new TypeReference<Map<String, String>>() {} );

		return executeRequest( r );
	}
	
	public RestResponse<RestCard> cardsAdd(
			String cardNumber,
			int expirationMonth,
			int expirationYear,
			String userToken
		)
	{
		return cardsAdd( cardNumber, expirationMonth, expirationYear, userToken, null );
	}
	
	public RestResponse<RestCard> cardsAdd(
			String cardNumber,
			int expirationMonth,
			int expirationYear,
			String userToken,
			Map<String,Object> additionalParams
		)
	{
		Request<RestCard> r = Request.<RestCard> createRequest( MethodType.POST, "/cards", true, userToken )
			.addParams( "cardNumber", cardNumber, 
					"expirationMonth", expirationMonth,
					"expirationYear", expirationYear
				)
			.addParams( additionalParams )
			.expects( "card", RestCard.class )
			;
		
		return executeRequest( r );
	}
	
	/**
	 * @param cardNumber The card number to lookup.
	 * @return True if the card exists.
	 */
	public RestResponse<Boolean> cardsExists(
			String cardNumber
		)
	{
		new AssertionError( cardNumber != null );
		
		Request<Boolean> r = Request.<Boolean> createRequest( MethodType.GET, "/cards/checkExists", true )
			.addParams( "cardNumber", cardNumber )
			.expects( "exists", Boolean.class );
		
		return executeRequest( r );
	}
	
	public RestResponse<RestResults<RestCard>> cardsList( String usertoken )
	{
		Request<RestResults<RestCard>> r = Request.<RestResults<RestCard>> createRequest( MethodType.GET, "/cards", true, usertoken )
				.expects( "cards", new TypeReference<RestResults<RestCard>>(){} );

		return executeRequest( r );
	}
	
	public RestResponse<Boolean> cardsRemove(
			String cardNumber,
			String email
		)
	{
		new AssertionError( cardNumber != null );
		
		Request<Boolean> r = Request.<Boolean> createRequest( MethodType.POST, "/cards/deleteByNumber", true, email )
			.addParams( "cardNumber", cardNumber )
			.expects( "removed", Boolean.class );
		
		return executeRequest( r );
	}

	public RestResponse<Boolean> cardsRemoveById(
			String cardId,
			String userToken
		)
	{
		new AssertionError( cardId != null );

		Request<Boolean> r = Request.<Boolean> createRequest( MethodType.POST, String.format("/cards/%s/delete", cardId), true, userToken )
						.expects( "result", Boolean.class );

		return executeRequest( r );
	}
	
	public RestResponse<Map<String,String>> getCategories()
	{
		Request<Map<String,String>> r = Request.<Map<String,String>> createRequest( MethodType.GET, "/utilities/categories" )
			.expects( "results", new TypeReference<Map<String,String>>(){} );
		
		return executeRequest( r );
	}
	
	public RestResponse<RestApplication> getApplicationInfo()
	{
		Request<RestApplication> r = Request.<RestApplication> createRequest( MethodType.GET, "/utilities/info" )
			.expects( "app", RestApplication.class );
		
		return executeRequest( r );
	}
	
	public RestResponse<RestBusiness> venuesSave( 
			String businessToken,
			String name, 
			String phone,
			Integer ownerId, 
			String streetName, 
			String postalCode,
			String category, 
			String description, 
			String merchantId,
			String amexId,
			String parent,
			Double discount,
			Double referralPercent )
	{
		Request<RestBusiness> r = Request.<RestBusiness> createRequest( MethodType.POST, "/venues/add", true )
				.addParams( 
						"businessToken", businessToken,
						"name", name,
						"fullPhone", phone,
						"owner", ownerId,
						"address.streetName", streetName,
						"address.postalCode", postalCode,
						"category", category,
						"description", description,
						"merchantInfo.merchantId", merchantId,
						"merchantInfo.amexId", amexId,
						"parent", parent,
						"discount", discount,
						"referralPercent", referralPercent
					)
				.expects( "venue", RestBusiness.class );
		
		return executeRequest( r );
	}
	
	public RestResponse<RestBusiness> venuesSave( 
			String businessToken,
			String name, 
			Integer ownerId, 
			String streetName, 
			String postalCode,
			String category, 
			String description )
	{
		Request<RestBusiness> r = Request.<RestBusiness> createRequest( MethodType.POST, "/venues/add", true )
				.addParams( 
						"businessToken", businessToken,
						"name", name,
						"owner", ownerId,
						"address.streetName", streetName,
						"address.postalCode", postalCode,
						"category", category,
						"description", description
					)
				.expects( "venue", RestBusiness.class );
		
		return executeRequest( r );
	}
	
	public RestResponse<RestBusiness> venuesAddPhoto(
			String venueId,
			String type,
			FileUpload fu
		)
	{
		Request<RestBusiness> r = Request.<RestBusiness> createRequest( MethodType.POST, "/venues/images/" + venueId + "/addPhoto" )
			.addParams( "file", fu,
						"type", type
					)
			.expects( "venue", RestBusiness.class );
		
		return executeRequest( r ); 
	}

	public RestResponse<RestBusiness> venuesRemovePhoto( 
			String venueId,
			Integer mediaId )
	{
		Request<RestBusiness> r = Request.<RestBusiness> createRequest( MethodType.POST, "/venues/images/" + venueId + "/removePhoto" )
			.addParams( "media", mediaId )
			.expects( "venue", RestBusiness.class );
		
		return executeRequest( r );
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	protected <T> RestResponse<T> executeRequest( Request<T> r )
	{
		return executeRequest( r, autoRetryTimes );
	}
	
	protected <T> RestResponse<T> executeRequest( Request<T> r, int numRetries )
	{
		if( accessToken != null )
		{
			r.addParams( "access_token", accessToken );
		}

		String endPoint = getApiEndpoint() + r.getEndPoint();

		endPoint = endPoint + ( endPoint.contains( "?" ) ? "&" : "?" )
				+ "client_id=" + clientId;

		try
		{
			String strResponse = requestUtil.executeMethod( r.getMethod(),
					endPoint, r.getRequestParams() );
	
			requestAdapter.adapt( r, strResponse );
	
			RestResponse<T> response = r.getResponse();
			if( response.meta.code == 403 && r.isRequiresToken() && numRetries > 0 )
			{
				refreshAccessToken();
				executeRequest( r, --numRetries );
			}
			
			if( response.meta.code == 403 )
			{
				authorizationError( response.meta.error );
			}
			else if( response.meta.code >= 500 && response.meta.code <= 599 )
			{
				unexpectedError( response.meta.error );
			}
			else if( response.meta.code >= 400 )
			{
				validationError( response.meta.error, response.meta.errorDetails );
			}
		}catch( RuntimeException e )
		{
			if( e.getCause() != null )
			{
				connectionError( e.getCause().getMessage() );
			}else
			{
				connectionError( e.getMessage() );
			}
			
			throw e;
		}

		return r.getResponse();
	}	
	
	private synchronized void refreshAccessToken()
	{
		OAuthResponse or = getAccessToken( "client_credentials", new HashMap<String,Object>() );
		
		if( or != null && or.access_token != null )
		{
			this.accessToken = or.access_token;
		}else
		{
			throw new RuntimeException( "Unable to refresh token" );
		}
	}
	
	public synchronized OAuthResponse getAccessToken(
			String grantType,
			Map<String,Object> params
		)
	{
		OAuthResponse or = getAccessToken( clientId, clientSecret, grantType, params );
		return or;
	}
	
	protected synchronized OAuthResponse getAccessToken(
				String clientId,
				String clientSecret,
				String grantType,
				Map<String,Object> params
			)
	{
		String endPoint = getOAuthEndpoint();
		
		params.put( "client_id", clientId );
		params.put( "client_secret", clientSecret );
		params.put( "grant_type", grantType);
		params.put( "code", "token" );
		
		String strResponse = requestUtil.executeMethod( MethodType.GET, endPoint, params );
		
		OAuthResponse or = requestAdapter.adapt( OAuthResponse.class, strResponse );
		
		return or;
	}
	
	private synchronized void authorizationError( String error_description )
	{
		for( ClientListener cl : listeners )
		{
			cl.authorizationError( error_description );
		}
	}
	
	private synchronized void connectionError( String error )
	{
		for( ClientListener cl : listeners )
		{
			cl.connectionError( error );
		}		
	}
	
	private synchronized void validationError( String global, Map<String,String> errorDetails )
	{
		for( ClientListener cl : listeners )
		{
			cl.validationError( global, errorDetails );
		}		
	}
	
	private synchronized void unexpectedError( String error )
	{
		for( ClientListener cl : listeners )
		{
			cl.unexpectedError( error );
		}		
	}
}
