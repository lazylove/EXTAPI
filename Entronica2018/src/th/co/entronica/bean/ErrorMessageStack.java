package th.co.entronica.bean;

import com.google.gson.annotations.SerializedName;

public class ErrorMessageStack {
	
	@SerializedName("node")
	private String node;
	
	@SerializedName("resultcode")
	private String resultcode;
	
	@SerializedName("developerMessage")
	private String developerMessage;
	
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
}
