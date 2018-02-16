package th.co.entronica.utils;

import java.util.List;
import java.util.Map;

import th.co.entronica.enums.ConfigName;

public class ConfigureTool {
	private static Map<String, List<String>> warmConfig;
	
	public static void initConfigureTool(Map<String, List<String>> hmwarmConfig){
		warmConfig = hmwarmConfig;
		
	}
	
	public static String getConfigure (ConfigName configName){
		try {
			return warmConfig.get(configName.getName()).get(0);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static String getConfigure ( ConfigName configName,String errorCode )
	 {
	  try 
	  {
	   return warmConfig.get( configName.getName()+ errorCode ).get( 0 );
	  } catch ( Exception e ) 
	  {
	   return null;
	  }
	 }
}
