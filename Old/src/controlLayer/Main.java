package controlLayer;

import modelLayer.TsConnection;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;


/**
 * 
 */

/**
 * @author Jacob
 *
 */
public class Main {

	private TS3Api api;
	ClientController clientController;
	ComplaintsCheck complaintsCheck;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main(args);
	}

	/**
	 * 
	 */
	private Main(String[] input) {
		TsConnection tsConnection = new TsConnection();
		api = tsConnection.getApi();
		clientController = new ClientController(api);
		//complaintsCheck = new ComplaintsCheck(api);
		//System.out.println("Client type is: " + api.getClientByUId(api.getDatabaseClientInfo(867).getUniqueIdentifier()).getType());
		api.sendChannelMessage("Greetings. Your beloved " + tsConnection.getNickname() + " is online!");
		
		for(String s : input) {
			api.sendChannelMessage(s);
		}
		actionListeners();
	}

	public void actionListeners() {
		api.registerAllEvents();
		api.addTS3Listeners(new TS3Listener() {

			public void onTextMessage(TextMessageEvent e) {
				if (e.getTargetMode() == TextMessageTargetMode.CHANNEL) {// Only
					// react
					// to
					// channel
					// messages
					if(e.getMessage().equals("ray")) {
						api.sendPrivateMessage(api.getClientByNameExact("Ray Cooper", true).getId(), "Jeg er en fluffy bot, og jeg har ingen idé om hvordan jeg svare dig hvis du skriver til mig D:");
					}
					if (e.getMessage().equals("ping")) {
						api.sendChannelMessage("pong");
					}
					if (e.getMessage().toLowerCase().contains("hello")) {
						api.sendChannelMessage("Greetings " + e.getInvokerName());
					}
					if(e.getMessage().toLowerCase().contains("!exit")) {
						api.sendChannelMessage("Goodbye.");
						System.exit(0);
					}
				}
			}

			public void onClientJoin(ClientJoinEvent e) {
				int clientId = e.getClientId();
				if(e.getClientTargetId() == 26) {
					funnyMessagesForChannel(e.getClientId());
				}
				if(clientController.isClientServerGroupOwner(clientId)) {
					clientWelcomeMessage(clientId);
				}
			}

			public void onServerEdit(ServerEditedEvent e) {
				System.out.println("Server edited by " + e.getInvokerName());
			}

			public void onClientMoved(ClientMovedEvent e) {
				int clientId = e.getClientId();
				int targetId = e.getClientTargetId();
				System.out.println("Client has moved " + clientId);
				System.out.println("Channel ID: " + targetId);

				if(targetId == 26) {
					funnyMessagesForChannel(clientId);
				}
				else if(targetId == 30 && clientController.isClientServerGroupOwner(clientId)) {
					clientWelcomeMessage(clientId);
				}
			}

			public void onClientLeave(ClientLeaveEvent e) {
				// ...

			}

			public void onChannelEdit(ChannelEditedEvent e) {
				// ...

			}

			public void onChannelDescriptionChanged(
					ChannelDescriptionEditedEvent e) {
				// ...

			}

			public void onChannelCreate(ChannelCreateEvent e) {

			}

			public void onChannelDeleted(ChannelDeletedEvent e) {

			}

			public void onChannelMoved(ChannelMovedEvent e) {

			}

			public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {

			}
		});
	}

	private void funnyMessagesForChannel(int clientId) {
		String nickname = api.getClientInfo(clientId).getNickname().toLowerCase();
		if(nickname.contains("ray")) {
			api.sendChannelMessage("Greetings my master, and welcome home.");
		}
		else if(nickname.contains("steve")) {
			api.sendChannelMessage("Greetings you silly, silly (almost) danish talking gentleman..");
		}
		else if(nickname.contains("rena") || nickname.contains("alleice")) {
			api.sendChannelMessage("Greetings, o' american visitor from the magical islands");
		}
		else if(nickname.contains("chifilly")) {
			api.sendChannelMessage("Greetings, silly script kid!");
		}
		else if(nickname.contains("q11")) {
			api.sendChannelMessage("*sigh* It's that guy again..");
		}
	}

	private void clientWelcomeMessage(int clientId) {
		api.sendPrivateMessage(clientId, "Do you see me? I can't do much more than this atm. But it's okay to love me anyway..");
	}

	public static void reportBotError(String message) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				//TODO send message to all online owners
				//TODO Send email to bot administrator
			}
		});
		thread.start();
	}

}
