package app

import modules.*
import com.natpryce.konfig.*
import java.io.File

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */

fun main(args: Array<String>) {
    println("Hello, world!")
    // Setup connection and event handler
    val tsConnection = TsConnection(GetConfig())
    val eventHandler = EventHandler(tsConnection)
    tsConnection.setupEventListeners(eventHandler)
    eventHandler.addModule(StandardCommands(tsConnection))
    loadModules(eventHandler)
//    bot.addModule(SimpleMath(bot))
    while (tsConnection.isConnected()) {
        Thread.yield()
    }
    println("No longer connected")
//    Thread.sleep(1000)
    tsConnection.close()
}

fun loadModules(eventHandler: EventHandler) {
}

fun GetConfig(): Config {
    val file = File("../teamspeakbot.properties")
    val configFile =
            if(file.canRead())
                ConfigurationProperties.fromFile(file)
            else {
                println("Couldn't read config file. Loading default.")
                ConfigurationProperties.fromResource("defaults.properties")
            }

    val loginName = configFile[server.loginName]
    val loginPassword = configFile[server.loginPassword]
    val config = Config(loginName, loginPassword)
    config.setHost(configFile[server.host])
    config.NickName = configFile[server.nickName]
    return config
}
