import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

/**
 * 
 */

/**
 * @author Jacob
 *
 */
public class TsConnection {
	//Lovely usefull git command: 
	//Ignore this file: git update-index --assume-unchanged TsConnection.java
	//Undo ignore file: git update-index --no-assume-unchanged TsConnection.java
	final TS3Api api;
    //TODO Get server login information from config file.
	private String serverIp = "";
	private String serverUserName = "";
	private String serverUserPassword = "";
	private int virtualServerId = 1; //Normally 1 if you only have one server instance.
	private int defaultServerChannel = 30;
	private String nickname = "PutPotBot";
	private String nickname2 = "PotPutBot"; //In case nickname already is taken. For example if the bot timed out. TODO make dynamic.

	public TsConnection() {
		final TS3Config config = new TS3Config();
		config.setHost(serverIp);
		config.setDebugLevel(Level.ALL);
		config.setLoginCredentials(serverUserName, serverUserPassword);

		final TS3Query query = new TS3Query(config);
		query.connect();

		api = query.getApi();
		api.selectVirtualServerById(virtualServerId);
		Client cStart = api.getClientByNameExact(nickname, false); //Checks if there's already a bot connected with the specified nickname.
		if(cStart != null) {
			nickname = nickname2; //Switches to the reserve nickname.
		}
		api.setNickname(nickname);
		api.moveClient(defaultServerChannel); //Moves the bot to the specified channel.
	}
	
	public TS3Api getApi() {
		return api;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public int getDefaultServerChannel() {
		return defaultServerChannel;
	}
}
