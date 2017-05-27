import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class Master: TeamSpeakBot {

    constructor(config: Config): super(config){
        setupEventListeners()
        setupSlaves()
    }

    private fun setupSlaves() {
        val channels = api.channels
        for (c in channels) {
            // TODO: Create a new bot slave for each channel
        }
    }

    private fun setupEventListeners() {
        api.registerAllEvents()
        api.addTS3Listeners(object: TS3EventAdapter() {
            override fun onTextMessage(e: TextMessageEvent) {
                println("Message received: ${e.message}")
                if (e.message == "exit")
                    close()
            }
        })
    }

    override fun close() {
        //TODO: terminate all slave instances
        super.close()
    }
}
