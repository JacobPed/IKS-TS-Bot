package Module

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
abstract class AbstractModule {
    abstract val CommandPrefixes: Array<String>

    open fun OnMessage(message: String) {}
    open fun OnMessage(message: Message) {}
}

data class Message(
        val sender: Object, // No idea what type sender should be atm.
        val message: String
)
