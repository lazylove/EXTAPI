package th.co.entronica.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import cauldron.eqx.log.LogDetailData;


//import th.co.ais.pantry.scf.log.LogDetailData;

public class EquinoxMessageModel {
	@LogDetailData
	private List<HttpHeader> header;
	// @LogDetailData
	// @SerializedName(value="body")
	// private SCF_Response responseBodyModel; // For LogDetail
	@LogDetailData
	private Object body; // For LogDetail
	private String contentMessage;
	private String rawData;

	public EquinoxMessageModel() {
		header = new ArrayList<HttpHeader>();
		// this.rawData = rawData;
	}

	public boolean addHeader(HttpHeader e) {
		return header.add(e);
	}

	public String getHeader(String key, boolean isIgnoreCalse) {
		if (!isIgnoreCalse) {
			return getHeader(key);
		}
		String result = null;
		for (HttpHeader h : header) {
			if (h.getName().equalsIgnoreCase(key)) {
				result = h.getValue();
				break;
			}
		}

		return result;
	}

	public String getHeader(String key) {
		String result = null;
		for (HttpHeader h : header) {
			if (h.getName().equals(key)) {
				result = h.getValue();
				break;
			}
		}

		return result;
	}
	
	public void removeHeader(String key) {
		for (HttpHeader h : header) {
			if (h.getName().equals(key)) {
				header.remove(h);
				break;
			}
		}

	}

	public List<HttpHeader> getHeader() {
		return header;
	}

	public void setHeader(List<HttpHeader> header) {
		this.header = header;
	}

	public String getContentMessage() {
		return contentMessage;
	}

	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String toString() {
		StringBuilder message = new StringBuilder();
		message.append("<ERDHeader>");
		if (header != null) {
			for (HttpHeader h : header) {
				message.append("<Header name=\"" + h.getName() + "\" value=\"" + h.getValue() + "\" />");
			}
		}
		message.append("</ERDHeader>");
		message.append("<ERDData value=\"").append( StringEscapeUtils.escapeXml( this.contentMessage ) ).append("\"/>");
//		message.append("<ERDData value=\"").append( APPMethod.replaceAll( this.contentMessage ) ).append("\"/>");
		return message.toString();
	}
}
