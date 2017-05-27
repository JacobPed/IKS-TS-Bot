import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Query
import kotlin.concurrent.thread

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
abstract class TeamSpeakBot: ITeamSpeakBot {
    // Public properties
    var channelId: Int

    // Protected properties
    protected val config: Config
    protected val query: TS3Query
    protected val api: TS3Api
    protected val serverName: String

    constructor(config: Config) {
        this.config = config
        this.query = TS3Query(config)
        query.connect()
        this.api  = query.api
        api.login(config.LoginName, config.LoginPassword)
        api.selectVirtualServerById(1)
        serverName = api.serverInfo.name
        api.setNickname(config.NickName)
        this.channelId = api.whoAmI().channelId
        api.sendChannelMessage("${config.NickName} is online!")
    }

    override fun isConnected(): Boolean {
        try {
//            api.connectionInfo.ping
            api.serverInfo.ip
        }
        catch(e: Exception) {
//            println("Somethings is wrong with connection. Error: " + e)
            return false
        }
        return true
    }

    override fun moveToChannelId(channelId: Int) {
        api.moveClient(api.whoAmI().id, channelId)
    }

    override fun sendMessage(message: String) {
        sendMessageToChannel(this.channelId, message)
    }

    override fun sendMessageToChannel(channel: Int, message: String) {
        if(channel == channelId) {
            api.sendChannelMessage(message)
        }
        else {
            api.sendChannelMessage(channel, message)
        }
    }

    override fun sendMessageServer(message: String) {
        api.sendServerMessage(message)
    }

    override fun close() {
        sendMessage("Goodbye.")
        query.exit()
        println("${config.NickName} terminated from Server: $serverName")
    }


}