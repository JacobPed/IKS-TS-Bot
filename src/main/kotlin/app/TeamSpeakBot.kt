package app

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent
import modules.AbstractModule

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class TeamSpeakBot: ITeamSpeakBot {
    private val eventQueue = java.util.LinkedList<Object>()
    // Public properties
    var channelId: Int

    // Fields
    private val config: Config
    private val query: com.github.theholywaffle.teamspeak3.TS3Query
    private val api: com.github.theholywaffle.teamspeak3.TS3Api
    private val serverName: String
    private val modules: MutableCollection<modules.AbstractModule>
    private val commandPrefixes: kotlin.collections.HashMap<String, AbstractModule>

    //It doesn't seem feasible to make it possible to perform Unit testing for the methods, as the used framework doesn't allow dependency injection.
    //Perhaps using containers for dependency injection is the way forward here.
    constructor(config: Config) { //The framework requires config to be given as part of constructor..
        this.config = config
        this.query = com.github.theholywaffle.teamspeak3.TS3Query(config)
        query.connect()
        this.api  = query.api
        api.login(config.LoginName, config.LoginPassword)
        api.selectVirtualServerById(1)
        serverName = api.serverInfo.name
        api.setNickname(config.NickName)
        this.channelId = api.whoAmI().channelId
        api.sendChannelMessage("${config.NickName} is online!")
        modules = mutableListOf<modules.AbstractModule>()
        commandPrefixes = hashMapOf<String, modules.AbstractModule>()
        setupEventListeners()

    }

    private fun setupEventListeners() {
        api.registerAllEvents()
        api.addTS3Listeners(object: com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter() {
            override fun onTextMessage(e: TextMessageEvent) {
                println("Message received: ${e.message}")
//                else if(e.message == "ping")
//                    sendMessage("pong")
                val sender = (object: ISender{
                    override val uniqueId: String = e.invokerUniqueId
                })
                if(e.message.first() == '!') { // if !, then it's a command
                    val message = e.message.trimStart('!')//.trimEnd(' ')
                    if(message == "exit") close()
                    else {
                        val prefix =
                                if(message.contains(' '))
                                    message.substring(0, message.indexOf(' '))
                                else
                                    message
                        val suffix =
                                if(message.length != prefix.length) {
                                    message.substring(prefix.length+1, message.length).trim(' ')
                                }
                                else ""
                        println("Prefix: $prefix")
                        println("Suffix: $suffix")
                        val command = (object: ICommand {
                            override val prefix: String = prefix
                            override val suffix: String = suffix
                            override val sender: ISender = sender
                        })
//                        val message = Message(Sender(e.invokerUniqueId), prefix, suffix)
//                        val message: Message = e as Message
                        commandPrefixes.get(prefix)?.OnCommand(command)
                    }
                }
                else {
                    val message = (object: IMessage {
                        override val message: String = e.message
                        override val sender: ISender = sender
                    })
                    modules.forEach { module -> module.OnMessage(message) }
                }
            }
        })
    }

    fun addModule(module: modules.AbstractModule) {
        modules.add(module)
        module.CommandPrefixes.forEach { commandPrefixes.put(it, module) }
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
