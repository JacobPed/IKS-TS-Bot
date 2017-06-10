package app

import modules.*
import com.natpryce.konfig.*
import java.io.File
//import kotlin.reflect.KClass
//import kotlin.reflect.full.cast
//import kotlin.reflect.full.createInstance
//import kotlin.reflect.full.createType
//import kotlin.reflect.full.primaryConstructor

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
// The type inside should be AbstractModule, but it wont accept it for now. Star works as a workaround.
//private val modules: List<KClass<*>> = listOf(StandardCommands::class)
private var modules: MutableList<AbstractModule> = mutableListOf()

fun main(args: Array<String>) {
    println("Hello, world!")
    // Setup connection and event handler
    val tsConnection = TsConnection(GetConfig())
    val eventHandler = EventHandler(tsConnection)
    tsConnection.setupEventListeners(eventHandler)
    eventHandler.addModule(StandardCommands(tsConnection))
    loadModules(eventHandler, tsConnection)
//    bot.addModule(SimpleMath(bot))
    while (tsConnection.isConnected()) {
        Thread.yield()
    }
    println("No longer connected")
//    Thread.sleep(1000)
    tsConnection.close()
}

fun loadModules(eventHandler: EventHandler, tsConnection: ITsConnection) {
    //This is a shitty solution. But it'll do for now.
    modules.add(StandardCommands(tsConnection))
    modules.forEach { eventHandler.addModule(it) }

//    modules.first().cast(AbstractModule::class)
//    modules.first().constructors.apply { tsConnection }
//    modules.first().createType()

//    println(modules.first().createInstance())
//    val module: AbstractModule = modules.first()::members as AbstractModule
//    if(module != null)
//        println("module: " + module.CommandPrefixes.first())
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
