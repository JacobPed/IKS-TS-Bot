/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */

fun main(args: Array<String>) {
    println("Hello, world!")
    // Create Master
    val m = Master(GetConfig())
//    Thread.sleep(1000)
//    m.close()
}

fun GetConfig(): Config {
    val LoginName = "BotUserName"
    val LoginPassword = "SomePassword"
//    val config = Config(LoginName, LoginPassword)
    val config = Config("IKSBot", "gS1uFlRu") // Test login. TODO: Config should be derived from a file.
    config.setHost("localhost")
    config.NickName = "TsBot"
    return config
}
