package th.co.entronica.state;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec02.af.abstracts.AbstractAF;
import ec02.af.data.AFDataFactory;
import ec02.af.interfaces.IAFState;
import ec02.af.utils.AFLog;
import ec02.af.utils.EquinoxUtils;
import ec02.data.enums.EEquinoxMessage.Ret;
import ec02.data.interfaces.ECDialogue;
import ec02.data.interfaces.EquinoxPropertiesAF;
import ec02.data.interfaces.EquinoxRawData;
import ec02.data.interfaces.InstanceData;
import ec02.data.interfaces.KeyObject;
import ec02.enums.EAlarmCategory;
import ec02.enums.EAlarmSeverity;
import ec02.enums.EAlarmType;
import th.co.entronica.bean.OutMessages;
import th.co.entronica.bean.Url;
import th.co.entronica.enums.EEntronica2018State;
import th.co.entronica.instance.AppInstance;
import th.co.entronica.parser.EquinoxMessageModel;
import th.co.entronica.utils.APPMethod;
import th.co.entronica.utils.CreateMessage;
public class IDLE implements IAFState {
	@Override
	public ECDialogue doAction(AbstractAF abstractAF, InstanceData instance, List<EquinoxRawData> eqxRawDataList) {
		
		EEntronica2018State nextstate = EEntronica2018State.IDLE;
		AppInstance appInstance = (AppInstance) instance.getObject();
		EquinoxUtils equinoxUtils= abstractAF.getEquinoxUtils();
		
		int index = 0;
		
		
		for( EquinoxRawData eqxRawData: eqxRawDataList )
		{
			appInstance.setInitInvoke(eqxRawData.getInvoke());
			
			
			if (Ret.NORMAL.getRet().equals(eqxRawData.getRet())){
				
				try {
					EquinoxMessageModel equinoxMessageModel = (EquinoxMessageModel) eqxRawData.getRawDataMessageObject();
					String url = eqxRawData.getRawDataAttribute("url");
					Url urlBean = APPMethod.getUrl(url);
					
					OutMessages outMessages = new OutMessages();
					outMessages.setEqxMsgModel(equinoxMessageModel);
					
					Map<String, String> optionalAttribute = new HashMap<String, String> ();
					optionalAttribute.put("method", eqxRawData.getRawDataAttribute("method"));
					
					outMessages.setOptionalAttribute(optionalAttribute);
					appInstance.getOutMessage().put( eqxRawData.getInvoke() , outMessages);
					
					appInstance.setUrl(urlBean);
					
					if (urlBean.getPartner().equals("IBM"))
					{
						CreateMessage.credential(equinoxUtils, appInstance, equinoxMessageModel, urlBean, ++index);
						nextstate = EEntronica2018State.W_SERVICE_CREDENTIAL;
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				
			}
		}
		
		
		//TODO : Build outgoing message...
		EquinoxPropertiesAF outGoingEquinoxPropertiesAF = AFDataFactory.createEquinoxProperties(nextstate.name(), "10");
		
		return AFDataFactory.createECDialogue(outGoingEquinoxPropertiesAF);
 	}
	
	@Override
	public ECDialogue doAged(AbstractAF arg0, InstanceData arg1, List<EquinoxRawData> arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ECDialogue doShutdown(AbstractAF arg0, InstanceData arg1, List<EquinoxRawData> arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ECDialogue doTimeout(AbstractAF arg0, InstanceData arg1, List<EquinoxRawData> arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
