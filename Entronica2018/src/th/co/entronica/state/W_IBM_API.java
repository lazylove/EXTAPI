package th.co.entronica.state;

import java.util.List;

import ec02.af.abstracts.AbstractAF;
import ec02.af.data.AFDataFactory;
import ec02.af.interfaces.IAFState;
import ec02.af.utils.EquinoxUtils;
import ec02.data.interfaces.ECDialogue;
import ec02.data.interfaces.EquinoxPropertiesAF;
import ec02.data.interfaces.EquinoxRawData;
import ec02.data.interfaces.InstanceData;
import th.co.entronica.bean.GetServiceCredentialResponse;
import th.co.entronica.bean.OutMessages;
import th.co.entronica.bean.Url;
import th.co.entronica.enums.EEntronica2018State;
import th.co.entronica.enums.EquinoxEvent;
import th.co.entronica.instance.AppInstance;
import th.co.entronica.interfaces.Event;
import th.co.entronica.parser.EquinoxMessageModel;
import th.co.entronica.parser.MessageParser;
import th.co.entronica.utils.APPMethod;
import th.co.entronica.utils.IDGenerator;

public class W_IBM_API implements IAFState{

	@Override
	public ECDialogue doAction(AbstractAF abstractAF, InstanceData instance,List<EquinoxRawData> eqxRawDataList) 
	{
		EEntronica2018State nextstate = EEntronica2018State.IDLE;
		AppInstance appInstance = (AppInstance) instance.getObject();
		EquinoxUtils equinoxUtils = abstractAF.getEquinoxUtils();
		int index = 0;
		
		for( EquinoxRawData equinoxRawData : eqxRawDataList)
		{
			EquinoxEvent equinoxEvent = EquinoxEvent.getEquinoxEventFrom(equinoxRawData.getRet());
			if ( EquinoxEvent.NORMAL.equals(equinoxEvent))
			{
				if ( appInstance.getOutMessage().containsKey( equinoxRawData.getInvoke() ) )
				{
					
					try {
						appInstance.getOutMessage().remove(equinoxRawData.getInvoke());
						
						EquinoxMessageModel equinoxMessageModel = (EquinoxMessageModel) equinoxRawData.getRawDataMessageObject();
						
						appInstance.setResultERDData(equinoxMessageModel.getContentMessage());
						
						OutMessages outMessages = new OutMessages();
						outMessages.setObjectType("ErrorPartner");
						
						//Get Method from first message
						String method = appInstance.getOutMessage().get(appInstance.getInitInvoke()).getOptionalAttribute().get("method");
						
						Url url = appInstance.getUrl();
						
						String [] key = new String[]{"0", appInstance.getUrl().getPartner(),
													 method, url.getServiceCategory(),
													 appInstance.getUrlAPI(),"0",
													 equinoxRawData.getRawDataAttribute("ecode"),"",
													 "",""
													};
						
						outMessages.setKeyE01( key );
						
						String id = IDGenerator.genInvoke(Event.SEARCH_ERROR_PARTNER, index);
						outMessages.setId(id);
						
						appInstance.getOutMessage().put(id, outMessages);
						
						equinoxUtils.getGlobalData().setTransactionId(id);
						equinoxUtils.getGlobalData().search(outMessages.getObjectType(), outMessages.getKeyE01(), id);
						nextstate = EEntronica2018State.W_SEARCH_ERROR_PARTNER;
						
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			else
			{
				switch ( equinoxEvent ) {
				case ERROR:
					
					break;
				case REJECT:
					break;
				case ABORT:
					break;
				case TIMEOUT:
					break;
				default:
					break;
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
