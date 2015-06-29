/**
 * 
 */
package controlLayer;

import java.util.ArrayList;
import java.util.List;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import modelLayer.ServerGroup;

/**
 * @author Jacob
 *
 */
public class ClientController {
	private TS3Api api;
	public ClientController(TS3Api api) {
		this.api = api;
	}
	
	public void sendPrivateMessageToClient(int clientId, String message) {
		System.out.println("Client type is: " + api.getClientInfo(clientId).getType());
		api.sendPrivateMessage(clientId, message);
	}
	
	/**
	 * Send a given message to all Clients of the given group that's online.
	 * @param servergroup
	 * @param message
	 */
	public void sendMessageToClientsOfGroup(ServerGroup servergroup, String message) {
		if(servergroup.equals(ServerGroup.owner)) {
			List<Client> owners = getClientOwnersOnline();
			if(owners != null) {
				for(int i = 0; i < owners.size(); i++) {
					sendPrivateMessageToClient(owners.get(i).getId(), message);
				}
			}
			
		}
	}

	public List<Client> getClientOwnersOnline() {
		List<Client> clients = api.getClients();
		List<Client> owners = new ArrayList<Client>();
		for(int i = 0; i < clients.size(); i++)  {
			Client client = clients.get(i);
			if(isClientServerGroupOwner(client.getId())) {
				if(client.getType() == 0) {
				owners.add(clients.get(i));
			}}
		}
		if(!owners.isEmpty()) {
			return owners;
		}
		return null;
	}
	
	/**
	 * Checks if the given user is member of the server group owner
	 * @return
	 */
	public boolean isClientServerGroupOwner(int clientId) {
		int[] clientGroups = api.getClientInfo(clientId).getServerGroups();
		for(int i = 0; i < clientGroups.length; i++) {
			if(clientGroups[i] == ServerGroup.owner.getIndex()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isQuery(int clientId) {
		//Client client = api.getClientInfo(clientId).getType();
		return false;
		
	}
}
