package th.co.entronica.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import th.co.entronica.bean.Url;
import th.co.entronica.enums.ConfigName;

public class APPMethod {
	
	public static Url getUrl (String value){
		
		int subKey = 0;
		String [] urlString = value.split("\\?");
		String [] att = urlString[0].split("/");
		
		
		Url url = new Url();
		url.setAppVersion(att[1]);
		url.setAppName(att[2]);
		url.setPartner(att[3]);
		url.setServiceCategory(att[4]);
		url.setApiName(att[5]);
		url.setQueryString(urlString[1]);
		
		for ( int i=3; i<att.length; i++ )
		{
			url.getApiParameter().put("resource_"+ ++subKey, att[i]);
		}
		
		return url;
	}
	
	public static String getService(String value)
	{
		return value.replaceAll("^[^.]+.[^.]+.", "");
	}
	
	
	public static JsonObject parserJsonObject(String value)
	{
		try {
			return ( JsonObject ) new JsonParser().parse(value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	public static String[] getKeyE01DNSPartner ( HashMap<String, String> dataKey ) {
		  
		  ArrayList<String> key = new ArrayList<>();
		  String key0="0";
		  String def ="";
		  String value;
		  int countData = 1;
		  int size =dataKey.size() ;
		  
		  size = size % 4 != 0 ? ( ( size / 4 ) + 1 ) * 5 : ( ( size / 4 ) ) * 5 ;
		  
		  for( int i = 0; i < size  ; i ++ ) {
		    
		   if( i % 5 == 0  ) {
		    key.add( key0 );
		   }
		   else {
		    value = dataKey.get( "resource_"+countData );
		    if ( value != null  ) {
		     key.add( value ) ;
		    }
		    else {
		     key.add( def );
		    }
		    countData++ ;
		   }
		    
		  }

		  return key.toArray(new String[key.size()]);
		 }
	
	public static String replaceUrlApiPartner ( HashMap<String, String> apiParameter ,String urlNew ) {
		   
		   String value = "";
		   
		   for ( String result : urlNew.split( "/" ) ) {
		    if ( result.startsWith( "$resource_" ) ) {
		     
		     value = apiParameter.get( result.substring( 1 ) );
		     
		     if ( value != null ) {
		      urlNew = urlNew.replaceAll( "\\"+result, value );
		     }
		    }
		   }
		   return urlNew;
		  }
	
	public static String checkBackSlash( String api_endpoint,String api_url )
    {
     StringBuilder stringBuilder = new StringBuilder();
     
     stringBuilder.append( api_endpoint.replaceAll(  "/+$" , "" ) )
     .append( "/" )
     .append( api_url.replaceAll( "^/+", "" ) );
     
     return stringBuilder.toString();
    }
	
	
	   public static String[] gatherResponseMapper( String resultMsgRes, String dataCondition )   {
			
//			Pattern cp = Pattern.compile( "(and|or|AND|OR|\\(|\\))?\\s*(.*?)\\s+(==|eq|!=|ne)\\s+(\"+(\\{.*\\}|.*?)\"+)\\s+" );
			Pattern cp = Pattern.compile( "(and|or|AND|OR)?\\s*(.*?)\\s+(==|eq|!=|ne)\\s+(\"+(\\{.*\\}|.*?)\"+)\\s+");
			Matcher cm = null;
			String logicalOperator = null;
			JsonObject jsonObject = null;
			
			jsonObject = parserJsonObject( resultMsgRes );	
			
			for( String val : dataCondition.split( "\\|" ) ) {
				String[] data = val.split( ";\\s+" );
				
				cm = cp.matcher( data[0] );
				
				boolean matchConditionalResult = false;
				String candidateValue = "";
				String relationalOperator = "";
				String holdValue = "";
				
				while ( cm.find() ) {
					candidateValue 	= getValue( jsonObject , cm.group( 2 ) );	
					relationalOperator 	= cm.group( 3 );
					holdValue			= cm.group( 5 );
					
					if ( cm.group( 1 ) != null ) {
						
						logicalOperator = cm.group( 1 );
						
						if ( "and".equalsIgnoreCase( logicalOperator ) ) {
							matchConditionalResult = matchConditionalResult && executeOperator( candidateValue, relationalOperator, holdValue );
						}
						else if ( "or".equalsIgnoreCase( logicalOperator )  ) {
							matchConditionalResult = matchConditionalResult || executeOperator( candidateValue, relationalOperator, holdValue );
						}
						
					}
					else {
						matchConditionalResult = executeOperator( candidateValue, relationalOperator, holdValue );
					}
				}
				
				if( matchConditionalResult || "default".equalsIgnoreCase( data[0].trim() ) ) {
					data[1] = replaceAllData( jsonObject , data[1] ) ;	
					
					return data;
				}
				
			}
			return null;
	}
	
	public static boolean executeOperator ( String candidateValue, String relationalOperator, String holdValue ) {
		
		if ( candidateValue == null ) {
			return false;
		}
		
		if ( isFloat( candidateValue ) && isFloat( holdValue ) )
		{
			if( "eq".equals( relationalOperator ) || "==".equals( relationalOperator ) ){
				
				if ( valueFloat( candidateValue ).equals( valueFloat( holdValue ) ) )
				{
					return true;
				}
				else
				{
					return false;
				}
			} 
			else if( "ne".equals( relationalOperator ) || "!=".equals( relationalOperator ) ){

				if ( valueFloat( candidateValue ).equals(  valueFloat( holdValue ) ) )
				{
					return false;
				}
				else
				{
					return true;
				}
				
			}
			
		}
		
		
		
		if ( parserJsonObject( candidateValue ) != null && parserJsonObject( holdValue ) != null)
		{
			JsonObject jsonObject1 = parserJsonObject( candidateValue );	
			JsonObject jsonObject2 = parserJsonObject( holdValue );	
			
			if( "eq".equals( relationalOperator ) || "==".equals( relationalOperator ) ){
				
				if ( jsonObject1.equals(jsonObject2) )
				{
					return true;
				}
				else
				{
					return false;
				}
			} 
			else if( "ne".equals( relationalOperator ) || "!=".equals( relationalOperator ) ){

				if ( jsonObject1.equals(jsonObject2) )
				{
					return false;
				}
				else
				{
					return true;
				}
				
			}
		}
		
		
		if( "eq".equals( relationalOperator ) || "==".equals( relationalOperator ) ){
			
			if( holdValue.isEmpty() || holdValue.equals( "null" ) ) {
				return candidateValue.equals( holdValue );
			}
			else {
				return candidateValue.contains( holdValue );
			}
			
		} 
		else if( "ne".equals( relationalOperator ) || "!=".equals( relationalOperator ) ){

			if( holdValue.isEmpty() || holdValue.equals( "null" ) ) {
				return ! ( candidateValue.equals( holdValue ) );
			}
			else {
				return ! ( candidateValue.contains( holdValue ) );
			}
			
		}
		
		return false;
	}
	
	public static String getValue( JsonObject jsonObject, String keys ) {
		
		JsonObject jsonObj =  jsonObject	;
		
		if( jsonObj == null ) {
			return "";
		}
		
		for( String key : keys.trim().split("\\.") ) {
			
	    		if( jsonObj.get( key ) == null ) {
	    			return  "$response".equalsIgnoreCase(key) ? jsonObj.getAsJsonObject().toString() : null ;
	    		}
	    		else if(  jsonObj.get( key ).isJsonArray() ) {
	    			jsonObj = jsonObj.get( key ).getAsJsonArray().get( 0 ).getAsJsonObject();
	    		} 
	    		
	    		else if ( jsonObj.get( key ).isJsonObject() ) {
	    			jsonObj = jsonObj.get( key ).getAsJsonObject();
	    		}
	    		else
	    		{
	    			return jsonObj.get( key ).getAsString() ;
	    		}
	    }
	    
	    	return jsonObj.getAsJsonObject().toString();
		
	}
	
	public static String getBodyResponse ( JsonObject jsonObject, String keys ) {
		
		JsonObject jsonObj =  jsonObject	;
		
		if( jsonObj == null ) {
			return "";
		}
		keys = keys.replaceAll("^\\.", "").trim();
		String [] keyValue = keys.split("\\.");
		String lastValue = keyValue[keyValue.length-1];
		String indexArray = null;
		String regexPattern = "(\\w+)\\[([0-9])+\\]";
		Pattern cp = Pattern.compile( regexPattern );
		
		for( String key : keyValue ) {
			
			Matcher cm = cp.matcher( key );
			
			if ( cm.matches() )
			{
				key = cm.group( 1 );
   			indexArray = cm.group( 2 );
			}
			
   		if( jsonObj.get( key ) == null ) {
   			return  "$response".equalsIgnoreCase(key) ? jsonObj.getAsJsonObject().toString() : "" ;
   		}
   		else if(  jsonObj.get( key ).isJsonArray() ) {
   			
   			if ( indexArray != null )
   			{
   				try
   				{
   					jsonObj = jsonObj.get( key ).getAsJsonArray().get( Integer.parseInt( indexArray ) ).getAsJsonObject();
   					indexArray =null;
   				}catch (Exception e) {
						// TODO: handle exception
					}
   					
   			}
   			else if ( lastValue.equals( key ) )
   			{
   				return jsonObj.get( key ).getAsJsonArray().toString();
   			}
	    			
   		} 
	    		
   		else if ( jsonObj.get( key ).isJsonObject() ) {
   			jsonObj = jsonObj.get( key ).getAsJsonObject();
   		}
   		
   		else
   		{
   			return addDoublQuote( jsonObj.get( key ).getAsString() );
   		}
	    		
	    }
	    
	    	return jsonObj.getAsJsonObject().toString();
		
	}
	
	private static String replaceAllData (JsonObject jsonObj,String data) 
	{
		String result="";
		String regexPattern = "\\{@data((\\.\\w+(\\[[0-9]+\\])?)*)\\}";
		
		Pattern cp = Pattern.compile( regexPattern );
		Matcher cm = cp.matcher( data );
		
		while ( cm.find() ) {
			result = cm.group();
			result = result.replaceAll("\\{", "\\\\{");
			result = result.replaceAll("\\}", "\\\\}");
			if ( result.equals( "\\{@data\\}" ) )
			{
				data = data.replaceAll( result , jsonObj.getAsJsonObject().toString() );	
			}
			else
			{
				result = result.replaceAll("\\[", "\\\\[");
				result = result.replaceAll("\\]", "\\\\]");
				data = data.replaceAll("(\"" + result + "\"|\'" + result + "\'|" + result + ")", getBodyResponse ( jsonObj, cm.group( 1 ) ));	
			}
	
		}
		
		return data;
	}
	
	private static String addDoublQuote(String value)
	{
		if ( !( isFloat( value ) || isValidJSON( value )) )
		{
			return "\""+value+"\"";
		}
		else
		{
			return  value;
		}
	}
	
   public static boolean isValidJSON ( String json ) {
		try {
			new Gson().fromJson( json, Object.class );
		}
		catch ( JsonSyntaxException e ) {
			return false;
		}
		
		return true;
	}
   
   private static boolean isFloat (String value)
	{
		try
		{
			Float.parseFloat(value);
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	    
	private static Float valueFloat (String value)
	{
		try
		{
			return Float.parseFloat(value);
		}catch (Exception e) {
			// TODO: handle exception
			return 0.0f;
		}
	}
	
	public static JsonObject setErrorCode (JsonObject jsonObj )
    {
     String  resultErrorCode = ConfigureTool.getConfigure( ConfigName.ERROR_CODE, jsonObj.get( "resultCode" ).getAsString()  );
  if ( resultErrorCode !=null )
  {
   for (String result : resultErrorCode.split( "\\|" ) )
   {
    result = result.trim();
    if (result.startsWith("moreInfo="))
    {
     jsonObj.addProperty( "moreInfo", result.replaceAll ("moreInfo=", "") );
    }
    else if ( result.startsWith("userMessage=") )
    {
     jsonObj.addProperty( "userMessage", result.replaceAll ("userMessage=", "") );
    }
   }
  }
     return jsonObj;
    }
}
