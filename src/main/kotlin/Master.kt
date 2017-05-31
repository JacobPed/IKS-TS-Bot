import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent
import java.util.*

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class Master: TeamSpeakBot {
    private var slaves = mutableListOf<ITeamSpeakBot>()
    private val eventQueue = LinkedList<Object>()

    constructor(config: Config): super(config){
        setupEventListeners()

        if(isConnected())
           setupSlaves()
    }

    private fun setupSlaves() {
        val channels = api.channels
        for (c in channels) {
            // TODO: Create a new bot slave for each channel
            val number = if(slaves.size > 0) slaves.size-1 else 0
            val config = Config(this.config.LoginName, this.config.LoginPassword, this.config.NickName + "slave$number", ChannelId = c.id)
            val newSlave = Slave(config, eventQueue)
            slaves.add(newSlave)
        }
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

    override fun close() {
        //TODO: terminate all slave instances
        super.close()
    }
}
