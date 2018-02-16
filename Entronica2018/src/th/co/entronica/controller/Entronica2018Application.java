package th.co.entronica.controller;

import java.util.List;

import ec02.af.abstracts.AbstractAF;
import ec02.af.data.AFDataFactory;
import ec02.af.exception.ActionProcessException;
import ec02.af.utils.AFLog;
import ec02.data.enums.EStdLogDelimiter;
import ec02.data.interfaces.ECDialogue;
import ec02.data.interfaces.EquinoxPropertiesAF;
import ec02.data.interfaces.EquinoxRawData;
import ec02.data.interfaces.InstanceData;
import ec02.data.interfaces.StdCDRData;
import ec02.data.interfaces.StdEDRFactory;
import th.co.entronica.utils.ConfigureTool;

/** TODO : Example code for annotation feature.  
 
import ec02.tools.utils.annotation.equinox.Alarm;
import ec02.tools.utils.annotation.equinox.EquinoxAlarms;
import ec02.tools.utils.annotation.equinox.EquinoxConfiguration;
import ec02.tools.utils.annotation.equinox.EquinoxEC02;
import ec02.tools.utils.annotation.equinox.EquinoxLogs;
import ec02.tools.utils.annotation.equinox.EquinoxStats;
import ec02.tools.utils.annotation.equinox.Log;
import ec02.tools.utils.annotation.equinox.Properties;
import ec02.tools.utils.annotation.equinox.Stat;

@EquinoxConfiguration(applicationName = "AF-ANNO")

@EquinoxEC02({afLibrary="@args:applicationName}"
					  , warmConfiguration = {@Properties(name = "timeout", value = "4")
													,@Properties(name = "machine-name", value = "Anno-1")
												    ,@Properties(name = "machine-group", value = "AnnoApp") 
												  	})

@EquinoxStats({@Stat(name = "AF-ANNO Success")
					  ,@Stat(name = "AF-ANNO Error") })

@EquinoxAlarms({@Alarm(name = "AF-ANNO Fail", id = "001")
						,@Alarm(name = "AF-ANNO Error", id = "002") })

@EquinoxLogs({@Log(name = "AF-ANNO SummaryLog")
					  ,@Log(name = "AF-ANNO DetailLog") })
*/

public class Entronica2018Application extends AbstractAF {
	
	@Override
	public ECDialogue actionProcess(EquinoxPropertiesAF equinoxPropertiesAF
			, List<EquinoxRawData> eqxRawDataList, InstanceData instance) throws ActionProcessException {
		AFLog.d("[Start Process]");
		AFLog.d("[CURRENT STATE] : " + equinoxPropertiesAF.getState());
        EQX4Wrapper.extractInstance(instance);
		EQX4Wrapper.extractRawData(eqxRawDataList);
		Entronica2018StateManager sm = new Entronica2018StateManager(equinoxPropertiesAF.getState());
		ECDialogue ecDialogue = sm.doProcess(this, instance, eqxRawDataList);
		EQX4Wrapper.composeInsatance(this, instance);	
		AFLog.d("[End Process]");
		return ecDialogue;
	}

	@Override
	public boolean verifyAFConfiguration(String arg0) {
			ConfigureTool.initConfigureTool(this.getEquinoxUtils().getHmWarmConfig());
		return true;
	}
	
	@Override
	public StdCDRData initializedCallDetailRecord() {

		return null;
	}

	@Override
	public StdEDRFactory initializedEventDetailRecord() {
		
		
		return null;
	}
}
