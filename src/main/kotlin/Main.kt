import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import java.io.File
import java.util.*

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */

fun main(args: Array<String>) {
    println("Hello, world!")
    // Create Master
    val bot = TeamSpeakBot(GetConfig())
    while (bot.isConnected()) {
        Thread.yield()
    }
    println("No longer connected")
//    Thread.sleep(1000)
    bot.close()
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
