import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Query
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent
import java.util.*

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class TeamSpeakBot: ITeamSpeakBot {
    private val eventQueue = LinkedList<Object>()
    // Public properties
    var channelId: Int

    // Protected properties
    private val config: Config
    private val query: TS3Query
    private val api: TS3Api
    private val serverName: String

    //It doesn't seem feasible to make it possible to perform Unit testing for the methods, as the used framework doesn't allow dependency injection.
    //Perhaps using containers for dependency injection is the way forward here.
    constructor(config: Config) { //The framework requires config to be given as part of constructor..
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
        setupEventListeners()

    }

    private fun setupEventListeners() {
        api.registerAllEvents()
        api.addTS3Listeners(object: TS3EventAdapter() {
            override fun onTextMessage(e: TextMessageEvent) {
                println("Message received: ${e.message}")
                if (e.message == "exit")
                    close()
                else if(e.message == "ping")
                    sendMessage("pong")
            }
        })
    }

//    override fun connect() {
//
//    }

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