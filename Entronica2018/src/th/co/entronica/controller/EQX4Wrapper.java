package th.co.entronica.controller;

import java.util.List;

import ec02.af.abstracts.AbstractAF;
import ec02.data.interfaces.EquinoxRawData;
import ec02.data.interfaces.InstanceData;
import th.co.entronica.instance.AppInstance;
import th.co.entronica.parser.EquinoxMessageModel;
import th.co.entronica.parser.EqxFunction;
import th.co.entronica.parser.MessageParser;

public class EQX4Wrapper {

	public static void extractRawData(List<EquinoxRawData> eqxRawDataList) {
		for (EquinoxRawData eqxRawData : eqxRawDataList){
			try
				{
					EquinoxMessageModel equinoxMessageModel = EqxFunction.findIncomingData(eqxRawData, true);
					eqxRawData.setRawDataMessageObject(equinoxMessageModel);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}
	}
	
	public static void extractInstance(InstanceData instance) {
	 
		String instanceString = instance.toString();
		
		Object obj = MessageParser.fromJson(instanceString, AppInstance.class);
		
		if( obj == null )
			{
				obj = new AppInstance();
			}		
		
		AppInstance appInstance = (AppInstance) obj;
		instance.setObject(appInstance);
			
	}
	
	public static void composeInsatance( AbstractAF abstractAF, InstanceData instanceData)
	{
		AppInstance appInstance = (AppInstance) instanceData.getObject();
		
		String instanceString = MessageParser.toJson(appInstance);
		
		abstractAF.getEquinoxUtils().setInstanceMessage(instanceString);
		
	}

}
