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
	final TS3Api api;
    //TODO Get server login information from config file.
	private String serverUserName = "";
	private String serverUserPassword = "";
	private String nickname = "PutPotBot";
	private String nickname2 = "PotPutBot";

	public TsConnection() {
		final TS3Config config = new TS3Config();
		config.setHost(serverIp);
		config.setDebugLevel(Level.ALL);
		config.setLoginCredentials(serverUserName, serverUserPassword);

		final TS3Query query = new TS3Query(config);
		query.connect();

		api = query.getApi();
		api.selectVirtualServerById(1);
		//api.setNickname("PutOBot");
		Client cStart = api.getClientByNameExact(nickname, false);
		if(cStart != null) {
			nickname = nickname2;
		}
		api.setNickname(nickname);
		api.moveClient(26);
	}
	
	public TS3Api getApi() {
		return api;
	}
	
	public String getNickname() {
		return nickname;
	}
}
