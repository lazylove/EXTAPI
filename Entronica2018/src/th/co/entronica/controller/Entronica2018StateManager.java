package th.co.entronica.controller;
 
import ec02.af.abstracts.AbstractAFStateManager;
import th.co.entronica.enums.EEntronica2018State;
import th.co.entronica.state.IDLE;
import th.co.entronica.state.W_IBM_API;
import th.co.entronica.state.W_SEARCH_DNS_PARTNER;
import th.co.entronica.state.W_SEARCH_ERROR_PARTNER;
import th.co.entronica.state.W_SERVICE_CREDENTIAL;


public class Entronica2018StateManager extends AbstractAFStateManager{
	public Entronica2018StateManager(String state) {
		this.afState = null;
		EEntronica2018State eEntronica2018State=Enum.valueOf(EEntronica2018State.class, state.toUpperCase()); 
		switch(eEntronica2018State){
			case IDLE:
				this.afState=new IDLE();
				break;
			case W_SERVICE_CREDENTIAL:
				this.afState = new W_SERVICE_CREDENTIAL();
				break;
			case W_SEARCH_DNS_PARTNER:
				this.afState = new W_SEARCH_DNS_PARTNER();
				break;
			case W_IBM_API:
				this.afState = new W_IBM_API();
				break;
			case W_SEARCH_ERROR_PARTNER:
				this.afState = new W_SEARCH_ERROR_PARTNER();
				break;

		}
	}
}
