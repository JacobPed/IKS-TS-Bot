package app

/**
 * Created by jacob on 2017-06-09 (YYYY-MM-DD).
 */
interface ISender {
    val uniqueId: String
}

interface ICommand {
    val sender: ISender
    val prefix: String
    val suffix: String
}

interface IMessage {
    val sender: ISender
    val message: String
}

data class Message (
        override val sender: ISender, // No idea what type sender should be atm.
        override val message: String
) : IMessage

data class Sender(
        val uniqueId: String
)
