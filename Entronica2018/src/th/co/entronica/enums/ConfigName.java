package th.co.entronica.enums;

public enum ConfigName {
	
	URL_SERVICE_CREDENTIAL("url-service-credentail"),
	SERVICE_INTERFACE("Service-Interface"),
	URL_SACF("url-SACF"),
	ERROR_CODE("ErrorCode-");
	
	
	String name;

	public String getName() {
		return name;
	}

	private ConfigName(String value) {
		this.name = value;
	}
}
