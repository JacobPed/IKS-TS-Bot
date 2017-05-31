import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Query
import java.util.*

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class Slave : TeamSpeakBot {
    constructor(config: Config, eventQueue: Queue<Object>): super(config){
        moveToChannelId(config.ChannelId)
        // TODO: Add some sort of eventQue to report to, which master can process from. Slave should be sender
    }
}
