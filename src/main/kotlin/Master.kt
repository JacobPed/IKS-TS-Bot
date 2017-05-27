import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class Master: AutoCloseable {
    val config: Config
    val query: TS3Query
    val api: TS3Api
    val serverName: String

    constructor(config: Config){
        this.config = config
        this.query = TS3Query(config)
        query.connect()
        this.api  = query.api

        api.login(config.LoginName, config.LoginPassword)
//        val virtualServer = api.virtualServers.first()
//        serverName = virtualServer.name
        api.selectVirtualServerById(1)
        serverName = api.serverInfo.name
        api.setNickname(config.NickName)
        setupEventListeners()
        api.sendChannelMessage("${config.NickName} is online!")
    }

    fun setupEventListeners() {

    }

    override fun close() {
        //TODO: terminate all slave instances
        query.exit()
        println("Master terminated - Server: $serverName")
    }
}
