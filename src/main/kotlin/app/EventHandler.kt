package app

import com.github.theholywaffle.teamspeak3.api.event.*
import modules.AbstractModule

/**
 * Created by jacob on 2017-06-10 (YYYY-MM-DD).
 */
class EventHandler: TS3Listener {

    private val tsConnection: ITsConnection
    private val modules: MutableCollection<modules.AbstractModule>
    private val commandPrefixes: kotlin.collections.HashMap<String, AbstractModule>

    constructor(tsConnection: ITsConnection): super() {
        modules = mutableListOf<modules.AbstractModule>()
        commandPrefixes = hashMapOf<String, modules.AbstractModule>()
        this.tsConnection = tsConnection
    }

    fun addModule(module: modules.AbstractModule) {
        modules.add(module)
        module.CommandPrefixes.forEach { commandPrefixes.put(it, module) }
    }

    override fun onTextMessage(e: TextMessageEvent) {
        println("Message received: ${e.message}")
//                else if(e.message == "ping")
//                    sendMessage("pong")
        val sender = (object: ISender{
            override val uniqueId: String = e.invokerUniqueId
        })
        if(e.message.first() == '!') { // if !, then it's a command
            val message = e.message.trimStart('!')//.trimEnd(' ')
            if(message == "exit") tsConnection.close()
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
                commandPrefixes[prefix]?.onCommand(command)
            }
        }
        else {
            val message = (object: IMessage {
                override val message: String = e.message
                override val sender: ISender = sender
            })
            modules.forEach { module -> module.onMessage(message) }
        }
    }

    override fun onClientJoin(e: ClientJoinEvent) {
        val joined = (object: IClientJoined {
            override val channelId: Int = e.clientTargetId
            override val clientId: Int = e.clientId
        })
        modules.forEach { module -> module.onClientJoin(joined)}
    }

    override fun onClientMoved(e: ClientMovedEvent) {
        TODO("not implemented")
    }

    override fun onClientLeave(e: ClientLeaveEvent?) {
        TODO("not implemented")
    }

    override fun onChannelDeleted(e: ChannelDeletedEvent?) {
        TODO("not implemented")
    }

    override fun onChannelMoved(e: ChannelMovedEvent?) {
        TODO("not implemented")
    }

    override fun onPrivilegeKeyUsed(e: PrivilegeKeyUsedEvent?) {
        TODO("not implemented")
    }

    override fun onChannelEdit(e: ChannelEditedEvent?) {
        TODO("not implemented")
    }

    override fun onChannelDescriptionChanged(e: ChannelDescriptionEditedEvent?) {
        TODO("not implemented")
    }

    override fun onServerEdit(e: ServerEditedEvent?) {
        TODO("not implemented")
    }

    override fun onChannelCreate(e: ChannelCreateEvent?) {
        TODO("not implemented")
    }

    override fun onChannelPasswordChanged(e: ChannelPasswordChangedEvent?) {
        TODO("not implemented")
    }
}