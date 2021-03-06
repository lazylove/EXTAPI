package th.co.entronica.state;

import java.lang.reflect.Method;
import java.util.List;

import com.google.gson.JsonObject;

import ec02.af.abstracts.AbstractAF;
import ec02.af.data.AFDataFactory;
import ec02.af.interfaces.IAFState;
import ec02.af.utils.EquinoxUtils;
import ec02.data.interfaces.E01Data;
import ec02.data.interfaces.ECDialogue;
import ec02.data.interfaces.EquinoxPropertiesAF;
import ec02.data.interfaces.EquinoxRawData;
import ec02.data.interfaces.InstanceData;
import th.co.entronica.bean.E01DNSPartnerResponse;
import th.co.entronica.enums.EEntronica2018State;
import th.co.entronica.enums.EquinoxEvent;
import th.co.entronica.instance.AppInstance;
import th.co.entronica.parser.MessageParser;
import th.co.entronica.utils.APPMethod;
import th.co.entronica.utils.CreateMessage;

public class W_SEARCH_ERROR_PARTNER implements IAFState{

	@Override
	public ECDialogue doAction(AbstractAF abstractAF, InstanceData instance, List<EquinoxRawData> incomingEquinoxRawDatas) {
		EEntronica2018State nextstate = EEntronica2018State.IDLE;
		AppInstance appInstance = (AppInstance) instance.getObject();
		EquinoxUtils equinoxUtils = abstractAF.getEquinoxUtils();
		int index = 0;
		
		if ( equinoxUtils.getGlobalData().isReceive() )
		{
			EquinoxEvent equinoxEvent = EquinoxEvent.getEquinoxEventFrom( equinoxUtils.getGlobalData().getGlobalDataEventType());
			
			if ( EquinoxEvent.E01_SUCCEED.equals(equinoxEvent))
			{
				String invoke = equinoxUtils.getGlobalData().getTransactionId();
				
				if ( appInstance.getOutMessage().containsKey(invoke) )
				{
					for ( E01Data e01Data : equinoxUtils.getGlobalData().getDataResultSet() )
					{
						if ( appInstance.getOutMessage().get(invoke).getId().equals(e01Data.getId()) )
						{
							switch ( e01Data.getResultCode() ) {
							case "0":
								String [] data = APPMethod.gatherResponseMapper(appInstance.getResultERDData(), e01Data.getData());
								
								JsonObject json = APPMethod.parserJsonObject(data[1]);
								
								json = APPMethod.setErrorCode(json);
								
								CreateMessage.ibmAPIResponse(equinoxUtils, appInstance, json);
								
								break;

							default:
								break;
							}
						}
					}
				}
			}
			
		}
		
		EquinoxPropertiesAF outGoingEquinoxPropertiesAF = AFDataFactory.createEquinoxProperties(nextstate.name(), "10");
		return AFDataFactory.createECDialogue(outGoingEquinoxPropertiesAF);
	}

	@Override
	public ECDialogue doAged(AbstractAF af, InstanceData incomingInstance,
			List<EquinoxRawData> incomingEquinoxRawDatas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ECDialogue doShutdown(AbstractAF af, InstanceData incomingInstance,
			List<EquinoxRawData> incomingEquinoxRawDatas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ECDialogue doTimeout(AbstractAF af, InstanceData incomingInstance,
			List<EquinoxRawData> incomingEquinoxRawDatas) {
		// TODO Auto-generated method stub
		return null;
	}

}
