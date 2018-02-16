package th.co.entronica.utils;

public class IDGenerator {
	
	public static boolean isInvoke = false;
	private static int numberInvoke = 0;
	
	
	public static String genInvoke (String command, int index)
	{
	 StringBuilder stringBuilder = new StringBuilder();
		if ( isInvoke )
		{
			stringBuilder.append( ++numberInvoke );
		}
		else{
			
			stringBuilder.append(command).append(":").append(index).append(":").append(System.currentTimeMillis());
			
		}
		return stringBuilder.toString();
	}
}
