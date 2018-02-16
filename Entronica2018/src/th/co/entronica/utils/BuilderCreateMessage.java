package th.co.entronica.utils;

import ec02.data.interfaces.IMessageBuilder;

public class BuilderCreateMessage implements IMessageBuilder{
	public String valueMessage;
	
	public BuilderCreateMessage(String valueMessage) {
		this.valueMessage = valueMessage;
	}
	
	public String buildMessage()
	{
		return this.valueMessage;
	}
}
