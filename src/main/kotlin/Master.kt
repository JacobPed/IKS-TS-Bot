import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray
import java.lang.Thread.yield
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class Master: TeamSpeakBot {
//    private var slaves = mutableListOf<ITeamSpeakBot>()
    private var slaves = mutableListOf<Thread>()
    private val eventQueue = LinkedList<Object>()

    constructor(config: Config): super(config){
        setupEventListeners()

        if(isConnected())
           setupSlaves()
    }

    private fun setupSlaves(): Unit {
        val channels = api.channels
//        for (c in channels) {
////        for (c in intArrayOf(1,2)) {
            // TODO: Create a new bot slave for each channel
//            val number = if(slaves.size > 0) slaves.size-1 else 0
            val number = 0
//            val config = Config(this.config.LoginName, this.config.LoginPassword, this.config.NickName + "slave$number", ChannelId = 0)
        try {


            val config = Config("eoijwetoiohiewoih", "7lVlLwK4", this.config.NickName + "slave$number", ChannelId = 0)
            val newSlave = thread() { Slave(config, eventQueue) }
            slaves.add(newSlave)
        }
        catch (e: Exception){ println("slave creation exception: \n" + e)}
            println("slave: $number")
//        }
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
