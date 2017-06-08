import Module.AbstractModule
import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Query
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class TeamSpeakBot: ITeamSpeakBot {
    private val eventQueue = LinkedList<Object>()
    // Public properties
    var channelId: Int

    // Fields
    private val config: Config
    private val query: TS3Query
    private val api: TS3Api
    private val serverName: String
    private val modules: MutableCollection<AbstractModule>
    private val commandPrefixes: HashMap<String, AbstractModule>

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
        modules = mutableListOf<AbstractModule>()
        commandPrefixes = hashMapOf<String, AbstractModule>()
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
                else if(e.message.first() == '!') { // if !, then it's a command
                    val message = e.message.trimStart('!').trimEnd(' ')
                    val prefix = message.substring(0, message.indexOf(' ')) // TODO: This will fail if there's no space
                    val suffix = message.substring(prefix.length+1, message.length).trim(' ')
                    println("Prefix: $prefix")
                    println("Suffix: $suffix")
                    commandPrefixes.get(prefix)?.OnMessage(suffix)
                }
            }
        })
    }

    fun addModule(module: AbstractModule) {
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