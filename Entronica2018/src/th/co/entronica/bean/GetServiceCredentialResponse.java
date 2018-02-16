package th.co.entronica.bean;

import com.google.gson.annotations.SerializedName;

public class GetServiceCredentialResponse {
	
	@SerializedName("resultCode")
	private String resultCode;
	
	@SerializedName("developerMessage")
	private String developerMessage;
	
	@SerializedName("errorMessageStack")
	private ErrorMessageStack [] errorMessageStack;
	
	@SerializedName("username")
	private String username;
	
	@SerializedName("password")
	private String password;
	
	@SerializedName("apiKey")
	private String apiKey;
	
	@SerializedName("url")
	private String url;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	public ErrorMessageStack[] getErrorMessageStack() {
		return errorMessageStack;
	}
	public void setErrorMessageStack(ErrorMessageStack[] errorMessageStack) {
		this.errorMessageStack = errorMessageStack;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
