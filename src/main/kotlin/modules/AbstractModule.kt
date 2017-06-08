package modules

import app.ITeamSpeakBot

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
abstract class AbstractModule {
    val masterBot: ITeamSpeakBot
    abstract val CommandPrefixes: Array<String>

    constructor(bot: ITeamSpeakBot) {
        masterBot = bot
    }

    open fun OnMessage(message: Message) {}
}

data class Message(
        val sender: Sender, // No idea what type sender should be atm.
        val prefix: String,
        val suffix: String
)

data class Sender(
        val uniqueId: String
)
