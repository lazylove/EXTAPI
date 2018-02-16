package th.co.entronica.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import ec02.af.utils.EquinoxUtils;
import ec02.data.enums.EEquinoxRawData.CTypeHTTP;
import ec02.data.interfaces.IMessageBuilder;
import th.co.entronica.bean.E01DNSPartnerResponse;
import th.co.entronica.bean.OutMessages;
import th.co.entronica.bean.Url;
import th.co.entronica.enums.ConfigName;
import th.co.entronica.instance.AppInstance;
import th.co.entronica.interfaces.Event;
import th.co.entronica.parser.EquinoxMessageModel;
import th.co.entronica.parser.HttpHeader;
import th.co.entronica.parser.MessageParser;

public class CreateMessage {
	
		public static void credential(EquinoxUtils equinoxUtils, AppInstance appInstance, EquinoxMessageModel equinoxMessageModel,Url url,int index)
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append( ConfigureTool.getConfigure(ConfigName.URL_SERVICE_CREDENTIAL))
			.append("/").append(url.getServiceCategory())
			.append("?appName=");

			String appName = equinoxMessageModel.getHeader("x-app");
			appName = appName.replaceAll("^[^=]+=|;.+", "");
			
			stringBuilder.append(appName);
			
			 Map<String, String> optionalAttribute = new HashMap<>();
			 optionalAttribute.put("url", stringBuilder.toString());
			 optionalAttribute.put("method", "GET");
			 
			 String invoke = IDGenerator.genInvoke(Event.GETSERVICECREDENTIAL, index);
			 String service = ConfigureTool.getConfigure(ConfigName.SERVICE_INTERFACE);
			 service = APPMethod.getService(service);
			 
			 EquinoxMessageModel equinoxMessage = new EquinoxMessageModel();
			 equinoxMessage.addHeader(new HttpHeader("x-command-id", equinoxMessageModel.getHeader("x-command-id")));
			 equinoxMessage.setContentMessage("");
			 
			 OutMessages outMessages = new OutMessages();
			 outMessages.setEqxMsgModel(equinoxMessage);
			 outMessages.setService(service);
			 
			 appInstance.getOutMessage().put(invoke, outMessages);
			 
			 
			 IMessageBuilder iMessage = new BuilderCreateMessage(equinoxMessage.toString());
			 
			 try {
				equinoxUtils.sendHTTPRequestMessage(iMessage, CTypeHTTP.TEXT_XML, invoke, service, optionalAttribute);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void ibmAPIRequest (EquinoxUtils equinoxUtils, AppInstance appInstance, E01DNSPartnerResponse e01dns, String urlNew, int index )
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append( APPMethod.checkBackSlash(appInstance.getGetService().getUrl(), urlNew) )
			.append( "?" );
			
			EquinoxMessageModel equinoxMessageModel = new EquinoxMessageModel();
			OutMessages outMessages = appInstance.getOutMessage().get(appInstance.getInitInvoke());
			
			
			for (String head:e01dns.getRequestHeader())
			{
				String value = outMessages.getEqxMsgModel().getHeader(head);
				if ( value != null)
				{
					equinoxMessageModel.addHeader(new HttpHeader(head, value));
				}
			}
			
			
			String method = outMessages.getOptionalAttribute().get("method");
			if("GET".equals( method ))
			{
				equinoxMessageModel.setContentMessage("");
			}
			else
			{
				equinoxMessageModel.setContentMessage(outMessages.getEqxMsgModel().getContentMessage());
			}
			
			if ( e01dns.getAuthenType().equals("api_key"))
			{
				try {
					stringBuilder.append("api_key=").append(URLEncoder.encode(appInstance.getGetService().getApiKey(), "UTF-8") ).append("&");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (e01dns.getAuthenType().equals("userPwd"))
			{
				String autEncode = appInstance.getGetService().getUsername()+":"+appInstance.getGetService().getPassword();
				try {
					equinoxMessageModel.addHeader(new HttpHeader("Authorization","Basic "+Base64.getEncoder().encodeToString(autEncode.getBytes( "UTF-8") )));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			stringBuilder.append( appInstance.getUrl().getQueryString() );
			
			equinoxMessageModel.addHeader(new HttpHeader("x-redirect-url", stringBuilder.toString()));
			
			Map<String, String> optionalAttribute = new HashMap<>();
			optionalAttribute.put("method", method);
			optionalAttribute.put("url", ConfigureTool.getConfigure( ConfigName.URL_SACF ));
			
			String invoke = IDGenerator.genInvoke( Event.IBM_API , index);
			
			appInstance.getOutMessage().put(invoke, outMessages);
			
			IMessageBuilder iMessage = new BuilderCreateMessage(equinoxMessageModel.toString());
			
			try {
				equinoxUtils.sendHTTPRequestMessage(iMessage, CTypeHTTP.TEXT_XML, invoke, "SACF", optionalAttribute);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public static void ibmAPIResponse ( EquinoxUtils equinoxUtils, AppInstance appInstance, JsonObject jsonObj )
		{
			EquinoxMessageModel equinoxMessageModel = new EquinoxMessageModel();
			equinoxMessageModel.addHeader(new HttpHeader("Content-Type", "application/json"));
			equinoxMessageModel.setContentMessage(MessageParser.toJson( jsonObj ));
			
			String invoke = appInstance.getInitInvoke();
			
			Map<String, String> optionalAttibute = new HashMap<>();
			optionalAttibute.put("method", appInstance.getOutMessage().get(invoke).getOptionalAttribute().get("method"));
			
			IMessageBuilder iMessage = new BuilderCreateMessage(equinoxMessageModel.toString());
			
			try {
				equinoxUtils.sendHTTPResponseMessage(iMessage, CTypeHTTP.TEXT_XML, invoke, "SACF", optionalAttibute);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
