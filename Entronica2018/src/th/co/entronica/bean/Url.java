package th.co.entronica.bean;

import java.util.HashMap;

public class Url {
	String appVersion;
	String appName;
	String partner;
	String serviceCategory;
	String apiName;
	String queryString;
	
	HashMap<String, String> apiParameter;
	
	public Url() {
		apiParameter = new HashMap<String,String>();
	}
	
	
	
	public HashMap<String, String> getApiParameter() {
		return apiParameter;
	}



	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
}
