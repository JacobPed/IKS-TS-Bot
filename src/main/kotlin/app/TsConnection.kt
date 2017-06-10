package app

import com.github.theholywaffle.teamspeak3.TS3Query
import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener
import modules.AbstractModule

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class TsConnection : ITsConnection {
    // Public properties
    var channelId: Int

    // Fields
    private val config: Config
    private val query: TS3Query
    private val api: TS3Api
    private val serverName: String

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
    }

    fun setupEventListeners(eventHandler: TS3Listener) {
        api.registerAllEvents()
        api.addTS3Listeners(eventHandler)
        api.sendChannelMessage("${config.NickName} is now listening.")
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

    override fun sendMessageToChannel(channelId: Int, message: String) {
        if(channelId == this.channelId) {
            api.sendChannelMessage(message)
        }
        else {
            api.sendChannelMessage(channelId, message)
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
