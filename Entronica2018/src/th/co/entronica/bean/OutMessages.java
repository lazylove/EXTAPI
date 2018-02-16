package th.co.entronica.bean;

import java.util.Map;

import ec02.data.interfaces.KeyObject;
import th.co.entronica.parser.EquinoxMessageModel;

public class OutMessages {
	
	EquinoxMessageModel eqxMsgModel;
	String service;
	String id;
	
	String objectType;
	String [] keyE01;
	
	
	
	Map <String,String> optionalAttribute;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public EquinoxMessageModel getEqxMsgModel() {
		return eqxMsgModel;
	}
	public void setEqxMsgModel(EquinoxMessageModel eqxMsgModel) {
		this.eqxMsgModel = eqxMsgModel;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
	public Map<String, String> getOptionalAttribute() {
		return optionalAttribute;
	}
	public void setOptionalAttribute(Map<String, String> optionalAttribute) {
		this.optionalAttribute = optionalAttribute;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String[] getKeyE01() {
		return keyE01;
	}
	public void setKeyE01(String[] keyE01) {
		this.keyE01 = keyE01;
	}
	
	
	
}
