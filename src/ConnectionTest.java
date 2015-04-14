import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
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
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

/**
 * 
 */

/**
 * @author Jacob
 *
 */
public class ConnectionTest {
	
	TsConnection tsConnection;
	private TS3Api api;

	/**
	 * 
	 */
	public ConnectionTest() {
		tsConnection = new TsConnection();
		api = tsConnection.getApi();
		
		clientInfoExample();
	}
	
	public void clientInfoExample() {
		
		api.sendChannelMessage("Greetings. You're beloved " + tsConnection.getNickname() + " is online!");

		/*for (final Client c : api.getClients()) {
			System.out.println(c.getNickname() + " in channel: "
					+ api.getChannelInfo(c.getChannelId()).getName());
		}*/
		
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
						api.sendChannelMessage("Goodby.");
						System.exit(0);
					}
				}
			}

			public void onClientJoin(ClientJoinEvent e) {
				//api.sendChannelMessage("Hey fancy man! I'm a crazy bot that does crazy shit!");
				clientMoveMessages(e.getClientTargetId(), e.getClientId());
			}

			public void onServerEdit(ServerEditedEvent e) {
				System.out.println("Server edited by " + e.getInvokerName());
			}

			public void onClientMoved(ClientMovedEvent e) {
				System.out.println("Client has been moved " + e.getClientId());
				System.out.println("Channel ID: " + e.getClientTargetId());
				
				clientMoveMessages(e.getClientTargetId(), e.getClientId());
				//System.out.println(api.getClientInfo(e.getClientId()).getNickname());
				//System.out.println(api.getClientInfo(e.getClientId()).getUniqueIdentifier());
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
	
	private void clientMoveMessages(int targetId, int clientId) {
		if(targetId == 26) {
			String nickname = api.getClientInfo(clientId).getNickname().toLowerCase();
			if(nickname.contains("ray")) {
				api.sendChannelMessage("Greetings my master, and welcome home.");
			}
			else if(nickname.contains("rena") || nickname.contains("alleice")) {
				api.sendChannelMessage("Greetings o' american visitor from the magical islands");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ConnectionTest();
	}
	
}
