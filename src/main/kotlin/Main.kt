/**
 * Created by jacob on 5/27/17.
 */
import com.github.theholywaffle.teamspeak3.TS3Config

fun main(args: Array<String>) {
    println("Hello, world!")
    // Create Master
    Master(CreateTs3Config())
}

fun CreateTs3Config(): TS3Config {
    val config = TS3Config()
    config.setHost("localhost")
    return config
}
