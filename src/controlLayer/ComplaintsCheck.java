/**
 * 
 */
package controlLayer;

import modelLayer.ServerGroup;

import com.github.theholywaffle.teamspeak3.TS3Api;

/**
 * @author Jacob
 *
 */
public class ComplaintsCheck extends Thread {
	
	private TS3Api api;
	private boolean repeat = true;
	private int spamLimit = 0;
	private ClientController clientcontroller;
	
	public ComplaintsCheck(TS3Api api) {
		super();
		this.api = api;
		clientcontroller = new ClientController(api);
	}

	public void run() {
		System.out.println("Before while loop");
		while(repeat) {
			System.out.println("Reruning complaints check.");
			if(spamLimit > 5) {
				repeat = false;
			}
			try {
				Thread.sleep(1000*30*1);
				if(spamLimit > 0) {
					spamLimit--;
				}
			} catch (InterruptedException e) {
				spamLimit++;
				Main.reportBotError("Complaints check pausing of thread failed.");
				e.printStackTrace();
			}
			if(!api.getComplaints().isEmpty()) { //If the complaints list isn't empty. Report it to the owners.
				clientcontroller.sendMessageToClientsOfGroup(ServerGroup.owner, "There's an unread complaint.");
			}

		}
	}
}
