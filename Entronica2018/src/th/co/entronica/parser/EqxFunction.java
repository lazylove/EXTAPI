/**
 * 
 */
package th.co.entronica.parser;

import ec02.data.enums.EEquinoxRawData.CTypeHTTP;
import ec02.data.interfaces.EquinoxRawData;


/**
 * @author Thanakhan I. <thanakhi@ais.co.th>
 *
 */
public class EqxFunction {
	public static EquinoxMessageModel findIncomingData( EquinoxRawData equinoxRawData, boolean isUseHeader ) {
		String content = null;
		if ( "HTTP".equalsIgnoreCase( equinoxRawData.getName() ) ) {
			if ( CTypeHTTP.TEXT_XML.getCType().equals( equinoxRawData.getCType() ) ) {
				if ( equinoxRawData.getRawDataMessage() != null ) {
					String rawDataMessage = equinoxRawData.getRawDataMessage().trim();
					if (rawDataMessage.startsWith( "<ERDHeader>" ) ) {				
						EquinoxMessageModel eqxModel = new EquinoxMessageParser().parseModel( rawDataMessage, null, isUseHeader );
						if( eqxModel != null ){
							return eqxModel;
						}
					}
					content = rawDataMessage;
				}
			} else if ( CTypeHTTP.TEXT_HTML.getCType().equals( equinoxRawData.getCType() ) || CTypeHTTP.TEXT_PLAIN.getCType().equals( equinoxRawData.getCType() ) ) {
				content = equinoxRawData.getRawDataAttribute( "val" );
			}else{
				content = equinoxRawData.getRawDataAttribute( "val" ); 
			}
		} else {
			content = equinoxRawData.getRawDataMessage();
		}

		EquinoxMessageModel equinoxMessageModel = new EquinoxMessageModel();
		equinoxMessageModel.setContentMessage(content);
		equinoxMessageModel.setRawData(content);

		return equinoxMessageModel;
	}

	
}
