package th.co.entronica.enums;

import java.util.HashMap;

public enum EquinoxEvent {
	NORMAL("0"),
	ERROR("1"),
	REJECT("2"),
	ABORT("3"),
	TIMEOUT("4"),
	END("10"),
	
	 E01_SUCCEED( "E01_SUCCEED" ),
	 E01_ERROR( "E01_ERROR" ),
	 E01_REJECT( "E01_REJECT" ),
	 E01_ABORT( "E01_ABORT" ),
	 E01_TIMEOUT( "E01_TIMEOUT" ),;
	
	
	
	private String code;

	public String getCode() {
		return code;
	}
	
	private EquinoxEvent(String code) {
		// TODO Auto-generated constructor stub
		this.code = code;
	}
	
	private static final HashMap<String, EquinoxEvent> lookup = new HashMap<String,EquinoxEvent>();
	
	static
	{
		for ( EquinoxEvent e : EquinoxEvent.values())
		{
			lookup.put(e.getCode(), e);
		}
	}
	
	public static EquinoxEvent getEquinoxEventFrom (String code)
	{
		return lookup.get(code);
	}
	
}
