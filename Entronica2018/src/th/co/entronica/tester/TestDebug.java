package th.co.entronica.tester;

import java.io.IOException;

import ec02.tools.dev.AFTest;
import th.co.entronica.utils.IDGenerator;

public class TestDebug {

	public static void main(String[] args) throws IOException {

        AFTest af = AFTest.getInstance();
        	
        IDGenerator.isInvoke = true; 
//        af.run("NEWBIE","./debug/conf/NEWBIE.EC02.NEWBIE.0","./debug/dummymsgs/debug1.xml","./","./lib");
        af.run("Entronica2018","./debug/conf/Entronica2018.EC02.0.0","./debug/dummymsgs/debug.xml","./","./lib");
        
	}
	
	public static String getPrettyCommand(String command){
		String result = "";
		String cmd = command.toString().toLowerCase().replace("get_", "").replace("post_", "").replace("put_", "").replace("delete_", "");
		String[] cmdlist = cmd.split("_");
		for(int i=0 ; i<cmdlist.length ; ++i){
			String tmp = cmdlist[i];
			String replace = ""+tmp.charAt(0);
			if(i>0) replace = replace.toUpperCase();
			result = result + replace + tmp.substring(1, tmp.length());
		}
		return result;
	}
}
