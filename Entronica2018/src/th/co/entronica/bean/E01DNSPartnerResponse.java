package th.co.entronica.bean;

import com.google.gson.annotations.SerializedName;

public class E01DNSPartnerResponse {

	@SerializedName("service")
	private String service;
	@SerializedName("url")
	private String url;
	@SerializedName("authenType")
	private String authenType;
	@SerializedName("requestHeader")
	private String [] requestHeader;
	
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthenType() {
		return authenType;
	}
	public void setAuthenType(String authenType) {
		this.authenType = authenType;
	}
	public String[] getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(String[] requestHeader) {
		this.requestHeader = requestHeader;
	}
	
	
}
