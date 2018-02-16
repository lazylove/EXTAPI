package th.co.entronica.parser;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;





public class EquinoxMessageParser extends SAXParser {
	private EquinoxMessageModel equinoxMessageModel;
	private Document doc;
	private String rootName = "";
	private boolean isUseHeader = false;
	
	public EquinoxMessageParser() {
		equinoxMessageModel = new EquinoxMessageModel();
	}

	protected void onElementStarted(String path, String qName, Attributes attr) {
		String root = this.rootName;
		String name ;
		if(path.startsWith(root + "/XML-Head")){
			root += "/XML-Head";
			if ( isUseHeader ) {
				if(path.startsWith(root + "/ERDHeader")){
					root += "/ERDHeader";
					if(path.startsWith(root + "/Header")){
						name = attr.getValue("name");
						if ( ! ( name.startsWith( "GET" ) || name.startsWith( "PUT" ) || name.startsWith( "POST" ) || name.startsWith( "DELETE" ) ) ) {
							equinoxMessageModel.addHeader(new HttpHeader( name ,attr.getValue("value")));
						}
					}
				}				
			}
			if(path.startsWith(root + "/ERDData")){
				equinoxMessageModel.setContentMessage(attr.getValue("value"));
			}
		}
	}

	protected void onElementCaptured(String path, String qName, String token) {

	}

	public EquinoxMessageModel parseModel(String message,String root, boolean isUseHeader){
		this.isUseHeader = isUseHeader;
		this.equinoxMessageModel.setRawData(message);
		
		if ( !this.isUseHeader ) {
			equinoxMessageModel.setHeader( null );
		}
		
		if(message != null){
			if( root==null ){
				message = new StringBuilder()
				.append("<XML-Head>")
				.append(message)
				.append("</XML-Head>").toString();
			}
			else{
				rootName = root;
			}
			parseXMLText(message);
			return (EquinoxMessageModel)onGetParsedObject();
		}
		return null;
	}
	
	public EquinoxMessageModel parseModel(String message){
		return parseModel(message,null,false);
	}
	
//	public EquinoxMessageModel parseModel(String message){
//		if(message != null){
//			if(!isXmlWellForm(message)){
//				message = "<XML-Head>"+message+"</XML-Head>";
////				System.out.println(message);
//				parseXMLText(message);
//			}
//			else{
//				message = replaceXmlHead(message);
////				System.out.println(message);
//				parseXMLText(message);
//			}
//			return (EquinoxMessageModel)onGetParsedObject();
//		}
//		return null;
//		
//	}
	
	protected Object onGetParsedObject() {
		return equinoxMessageModel;
	}
	public boolean isXmlWellForm(String xml) {
		try {
			// Create a new factory to create parsers
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dBF.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xml)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String replaceXmlHead(String xml) {
		String rootName = doc.getDocumentElement().getNodeName();
//		System.out.println("rootName :" + rootName);
		xml = xml.replace(rootName, "XML-Head");
		return xml;
	}
}
