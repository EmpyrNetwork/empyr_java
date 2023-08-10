/**
 * 
 */
package com.empyr.api.util.adapters.ios;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * @author jarrodcuzens
 *
 */
public class RestKitAdapter
{
	private Reflections reflections;
	private static final String outputDir = "restkit2";
	
	private static final String clientHeaderBody = "#import <Foundation/Foundation.h>\n" + 
			"\n" + 
			"@interface _MOGLClient : NSObject\n" + 
			"\n" + 
			"@property (nonatomic, strong) NSMutableDictionary* objectMappings;\n" + 
			"\n" + 
			"@end";
	
	private static final String clientImplIntro = "#import \"_MOGLClient.h\"\n" + 
			"#import \"MOGLModel.h\"\n" + 
			"#import \"RestKit.h\"\n" + 
			"\n" + 
			"@implementation _MOGLClient\n" + 
			"\n" + 
			"-(_MOGLClient*)init\n" + 
			"{\n" + 
			"	[self setObjectMappings:[[NSMutableDictionary alloc] init]];";
	
	private static final String clientImplTrailer = "	return self;\n" + 
			"}\n" + 
			"@end\n";
	
	// Holds general type converions (like int to NSNumber)
	private static Map<String,String>typeMappings = new HashMap<String,String>();
	// Holds translations from the source property name to a different dest property name like description -> descriptionText
	private static Map<String,String>nameMappings = new HashMap<String,String>();
	// Holds the mapping from subclasses to superclasses (RestCompactBusiness -> RestBusiness) because
	// we don't worry about the subclassed objects.
	private static Map<String,String>condensedMappings = new HashMap<String,String>();
	
	static
	{
		typeMappings.put( "int", "NSNumber" );
		typeMappings.put( "double", "NSNumber" );
		typeMappings.put( "float", "NSNumber" );
		typeMappings.put( "Float", "NSNumber" );
		typeMappings.put( "String", "NSString" );
		typeMappings.put( "boolean", "BOOL" );
		typeMappings.put( "Boolean", "BOOL" );
		typeMappings.put( "Integer", "NSNumber" );
		typeMappings.put( "Double", "NSNumber" );
		typeMappings.put( "long", "NSNumber" );
		typeMappings.put( "Long", "NSNumber" );
		typeMappings.put( "Collection", "NSArray" );
		typeMappings.put( "List", "NSArray" );
		typeMappings.put( "Set", "NSSet" );
		typeMappings.put( "Map", "NSDictionary" );
		typeMappings.put( "Date", "NSDate" );
		typeMappings.put( "RestUrl", "NSString" );
		typeMappings.put( "CardType", "NSString" );
		typeMappings.put( "CardState", "NSString" );
		typeMappings.put( "AlertType", "NSString" );
		typeMappings.put( "PrivacyLevel", "NSString" );
		typeMappings.put( "FriendState", "NSString" );
		typeMappings.put( "DeviceType", "NSString" );
		typeMappings.put( "UpgradeActionType", "NSString" );
		typeMappings.put( "EventType", "NSString" );
		typeMappings.put( "Processor", "NSString" );
		typeMappings.put( "OfferRewardType", "NSString" );
		typeMappings.put( "OfferScheduleType", "NSString" );
		typeMappings.put( "OfferInterval", "NSString" );
		typeMappings.put( "RedeemState", "NSString" );
		typeMappings.put( "RestUrl", "NSString" );
		typeMappings.put( "T", "id" );
		
		nameMappings.put( "description", "descriptionText" );
		
		// Condense the hierarchy so the supertype becomes the subtype.
		condensedMappings.put( "RestFullBusiness", "RestBusiness" );
		condensedMappings.put( "RestCompactBusiness", "RestBusiness" );
		condensedMappings.put( "RestCompactUser", "RestUser" );
		
		// Trick the system into not processing these:
		condensedMappings.put( "RestBase", "" );
		condensedMappings.put( "RestUrl", "" );
	}
	
	public static class RKRelationship
	{
		public String srcType;
		public String srcProperty;
		public String dstProperty;
		public String dstType;
		
		public RKRelationship( String srcType, String srcProperty, String dstProperty, String dstType )
		{
			this.srcType = srcType;
			this.srcProperty = srcProperty;
			this.dstProperty = dstProperty;
			this.dstType = dstType;
		}
		
		public void printMapping( PrintWriter p )
		{
			p.printf( "	[[[self objectMappings] objectForKey:[%s class]] addPropertyMapping:[RKRelationshipMapping\n", srcType );
			p.printf( "				relationshipMappingFromKeyPath:@\"%s\"\n", srcProperty );
			p.printf( "				toKeyPath:@\"%s\"\n", dstProperty );
			p.printf( "				withMapping:[[self objectMappings] objectForKey:[%s class]]\n", dstType );
			p.printf( "	]];\n\n" );
		}
	}
	
	public static class RKNullRelationship extends RKRelationship
	{
		public RKNullRelationship()
		{
			super( null, null, null, null );
		}
		
		public void printMapping( PrintWriter p )
		{
		}
	}
	
	/**
	 * This is used when we have a dictionary and a subobject that requires
	 * a relationship mapping.
	 * @author jarrodcuzens
	 */
	public static class RKDictionaryRelationship extends RKRelationship
	{
		public RKDictionaryRelationship( String srcType, String srcProperty,
				String dstProperty, String dstType )
		{
			super( srcType, srcProperty, dstProperty, dstType );
		}
		
		public void printMapping( PrintWriter p )
		{
			p.printf( "	{\n" + 
					"		RKDynamicMapping* mapping = [RKDynamicMapping new];\n" + 
					"		\n" + 
					"		[mapping setObjectMappingForRepresentationBlock:^RKObjectMapping *(id representation) {\n" + 
					"			NSDictionary *dict = representation;\n" + 
					"			NSArray *keys = [dict allKeys];\n" + 
					"			RKObjectMapping *dataMapping = [RKObjectMapping mappingForClass:[NSMutableDictionary class]];\n" + 
					"			for( NSString* key in keys )\n" + 
					"			{\n" + 
					"				[dataMapping addRelationshipMappingWithSourceKeyPath:key mapping:[[self objectMappings] objectForKey:[%s class]]];\n" + 
					"			}\n" + 
					"			return dataMapping;\n" + 
					"		}];\n" + 
					"		\n" + 
					"		[[[self objectMappings] objectForKey:[%s class]] addPropertyMapping:[RKRelationshipMapping\n" + 
					"																					   relationshipMappingFromKeyPath:@\"%s\"\n" + 
					"																					   toKeyPath:@\"%s\"\n" + 
					"																					   withMapping:mapping\n" + 
					"																					   ]];\n" + 
					"	}\n", dstType, srcType, srcProperty, dstProperty );
		}
	}
	
	private PrintWriter getWriter( String filename ) throws Exception
	{
		return new PrintWriter( new FileWriter( filename ) );
	}
	
	private void adapt() throws Exception
	{
		// First we will get all the classes in our "model" package.
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		reflections = new Reflections( 
				new ConfigurationBuilder().setScanners(
						new SubTypesScanner( false /* don't exclude Object.class */ ),
						new ResourcesScanner() 
				)
				.setUrls( ClasspathHelper.forClassLoader( classLoadersList.toArray( new ClassLoader[0] ) ) )
				.filterInputsBy( new FilterBuilder().include( FilterBuilder.prefix( "com.mogl.api.v2.model" ) ) ) 
		);
		
		new File( outputDir ).mkdirs();
		
		PrintWriter globalHeader = getWriter( outputDir + "/MOGLModel.h" );
		PrintWriter clientHeader = getWriter( outputDir + "/_MOGLClient.h" );
		PrintWriter clientImpl = getWriter( outputDir + "/_MOGLClient.m" );
		
		try
		{	
			// Storage for the relationship mappings (e.g. RestBusiness.address -> RestAddress)
			Set<RKRelationship> relationships = new HashSet<RKRelationship>();
			
			// Print out the "global" header
			globalHeader.println( "#ifndef _MOGLHEADERS\n#define _MOGLHEADERS\n\n" );
			
			// Print out the "main" mogl client header
			clientHeader.println( clientHeaderBody );
			
			// Print out the "main" mogl client implementation intro
			clientImpl.println( clientImplIntro );
			
			// Now we will process each one of them
			for( Class<?> c : reflections.getSubTypesOf( Object.class ) )
			{
				System.out.println( c.getName() );
				
				String name = c.getSimpleName();
				
				// Don't worry about condensed mappings (e.g. RestCompactUser)
				// because they are all mapped under the base type (e.g. RestUser).
				if( !condensedMappings.containsKey( name ) )
				{
					PrintWriter header = getWriter( outputDir + "/" + name + ".h" );
					PrintWriter impl = getWriter( outputDir + "/" + name + ".m" );
					
					try
					{
						// We need to add this class to the "global header"
						globalHeader.println( "#import \"" + name + ".h\"" );
						
						// We need to create a class header
						header.println( "#import <Foundation/Foundation.h>" );
						
						// We need to print a class implementation
						impl.printf( "#import \"%s.h\"\n" + 
								"\n" + 
								"@implementation %s\n" + 
								"@end", name, name );
						
						// In the main mogl client we will add an object mapping
						clientImpl.printf( "	{\n" + 
								"		RKObjectMapping *mapping = [RKObjectMapping mappingForClass:[%s class]];\n" + 
								"		[mapping addAttributeMappingsFromDictionary: @{\n", name );
						
						// Go through all the fields and add to the clientImpl and header as necessary
						// this is also where we will store the relationship mappings for later.
						StringWriter propsStr = new StringWriter();
						PrintWriter props = new PrintWriter( propsStr );
						for( Field f : buildFields( c ) )
						{
							String simpleType = f.getType().getSimpleName();
							String propertyName = f.getName();
							String mappedName = propertyName;
							System.out.printf("\t%-30.30s  %-30.60s%n", propertyName, simpleType );
							
							// Look for any overrides where the name of the java field must be
							// translated.
							if( nameMappings.containsKey( propertyName ) )
							{
								mappedName = nameMappings.get( propertyName );
							}
							
							// First let's look for the type we are trying to map.
							String mappedType = typeMappings.get( simpleType );
							
							// Handle arrays by mapping them to NSArray
							if( f.getType().isArray() )
							{
								mappedType = "NSArray";
							}
							
							// Translate the generic object mapping to the "id" mapping.
							if( simpleType.equals( "Object" ) )
							{
								simpleType = "id";
							}
							
							RKRelationship r = null;
							
							// Now handle the properties
							if( mappedType != null )
							{
								if( mappedType.equals( "BOOL" ) )
								{
									props.printf( "\t@property (nonatomic) BOOL %s;\n", mappedName );
								}else
								{
									props.printf( "\t@property (nonatomic, copy) %s* %s; // standard\n", mappedType, mappedName );
									
									// Maps get handled specially and we look at the "value" portion of it to see
									// if it is a complex mapping in need of a relationship.
									boolean dictionary = false;
									Type genericType = f.getGenericType();
									if( mappedType.equals( "NSDictionary" ) )
									{
										genericType = ((ParameterizedType)genericType).getActualTypeArguments()[1];
										dictionary = true;
									}
									
									// If this is a generic then we need to check the generic
									// type to see if it needs a relationship mapping.
									if( genericType instanceof ParameterizedType )
									{
										ParameterizedType paramType = (ParameterizedType)genericType;
										genericType = paramType.getActualTypeArguments()[0];
									}
									
									// The genericType.getTypeName can have all sorts of extra stuff on it.
									//		RestMedia[]
									//		com.mogl.api.v2.model.RestMedia
									//		com.mogl.api.v2.model.RestMedia$OfferType
									// Let's remove it since the relationship mapping doesn't want it.
									String genericClassName = genericType.getTypeName();
									if( genericClassName.contains( "$" ) )
										genericClassName = StringUtils.substringAfterLast( genericClassName, "$" );
									
									if( genericClassName.contains( "." ) )
										genericClassName = StringUtils.substringAfterLast( genericClassName, "." );
									
									if( genericClassName.contains( "[" ) )
									{
										genericClassName = StringUtils.substringBeforeLast( genericClassName, "[" );
									}
									
									// Translate subtypes to their parents (e.g. RestCompactUser to RestUser)
									if( condensedMappings.containsKey( genericClassName ) )
									{
										genericClassName = condensedMappings.get( genericClassName );
									}
									
									if( genericClassName.equals( "T" ) )
									{
										// Defer a mapping because we don't know what "T" is.
										r = new RKNullRelationship();
									}
									
									//System.out.println( "*" + genericClassName );
									// If it's a "Rest" mapping we need to create a mapping relationship.
									if( genericClassName.startsWith( "Rest" ) )
									{
										if( dictionary )
										{
											r = new RKDictionaryRelationship( name, propertyName, mappedName, genericClassName );
										}else
										{
											r = new RKRelationship( name, propertyName, mappedName, genericClassName );
										}
									}
								}
							}
							else if( simpleType.equals( "id" ) )
							{
								// We create a property but we need to defer the relationship
								// since this is defined when calling the method.
								props.printf( "\t@property (nonatomic, strong) %s %s; //id mapping\n", simpleType, mappedName );
								r = new RKNullRelationship();
							}
							else if( simpleType.startsWith( "Rest" ) )
							{
								// Translate subtypes to their parents (e.g. RestCompactUser to RestUser)
								if( condensedMappings.containsKey( simpleType ) )
								{
									simpleType = condensedMappings.get( simpleType );
								}
								
								header.printf( "@class %s;\n", simpleType );
								props.printf( "\t@property (nonatomic, strong) %s* %s;\n", simpleType, mappedName );
								r = new RKRelationship( name, propertyName, mappedName, simpleType );
							}
							else
							{
								throw new RuntimeException( "Couldn't map type: " + simpleType );
							}
							
							if( r != null )
							{
								relationships.add( r );
							}else
							{
								// Print the field mappings to the client implementation
								clientImpl.printf( "\t\t\t@\"%s\" : @\"%s\",\n", propertyName, mappedName );
							}
						}
						
						// Now finish up
						clientImpl.printf( "		}];\n		[[self objectMappings] setObject:mapping forKey:(id<NSCopying>)[%s class]];\n" + 
								"	}\n", name );
						
						header.println( "\n@interface " + name + " : NSObject" );
						header.print( propsStr.toString() );
						header.println( "@end" );
					}finally
					{
						header.close();
						impl.close();
					}
				}
			}
			
			globalHeader.println( "#endif // _MOGLHEADERS" );
			
			// Print out the "main" mogl client implementation trailer this is where we
			// will also process all the relationship mappings. These look like the below.
			//
			//	[[[self objectMappings] objectForKey:[RestResponse class]] addPropertyMapping:[RKRelationshipMapping 
			//			relationshipMappingFromKeyPath:@"meta"
			//		     toKeyPath:@"meta"
			//		   withMapping:[[self objectMappings] objectForKey:[RestMeta class]]
			//		]
			//	];
			for( RKRelationship r : relationships )
			{
				r.printMapping( clientImpl );
			}
			
			clientImpl.println( clientImplTrailer );
		}finally
		{
			globalHeader.close();
			clientHeader.close();
			clientImpl.close();
		}
	}
	
	private<T> Set<Field> buildFields( Class<T> c )
	{
		Set<Field> fields = new HashSet<Field>();
		fields.addAll( Arrays.asList( c.getFields() ) );
		
		for( Class<?> sub : reflections.getSubTypesOf( c ) )
		{
			fields.addAll( buildFields( sub ) );
		}
		
		return fields;
	}
	
	public static void main( String args[] ) throws Exception
	{
		RestKitAdapter rka = new RestKitAdapter();
		rka.adapt();
	}
}
