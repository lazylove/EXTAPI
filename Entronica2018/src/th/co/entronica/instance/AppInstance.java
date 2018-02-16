package th.co.entronica.instance;

import java.util.HashMap;

import th.co.entronica.bean.GetServiceCredentialResponse;
import th.co.entronica.bean.OutMessages;
import th.co.entronica.bean.Url;

public class AppInstance {
	
	String initInvoke;
	String resultERDData;
	String urlAPI;
	
	Url url;
	
	HashMap<String, OutMessages> outMessage;
	GetServiceCredentialResponse getService;
	
	public String getUrlAPI() {
		return urlAPI;
	}

	public void setUrlAPI(String urlAPI) {
		this.urlAPI = urlAPI;
	}
	
	public GetServiceCredentialResponse getGetService() {
		return getService;
	}

	public void setGetService(GetServiceCredentialResponse getService) {
		this.getService = getService;
	}

	public String getResultERDData() {
		return resultERDData;
	}

	public void setResultERDData(String resultERDData) {
		this.resultERDData = resultERDData;
	}
	
	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}
	
	
	public AppInstance() {
		outMessage = new HashMap<>();
	}
	
	public String getInitInvoke() {
		return initInvoke;
	}
	public void setInitInvoke(String initInvoke) {
		this.initInvoke = initInvoke;
	}
	public HashMap<String, OutMessages> getOutMessage() {
		return outMessage;
	}
	public void setOutMessage(HashMap<String, OutMessages> outMessage) {
		this.outMessage = outMessage;
	}
	
	
}
