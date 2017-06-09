package modules

import app.IMessage
import app.ITeamSpeakBot

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
class SimpleMath(bot: ITeamSpeakBot) : AbstractModule(bot) {
    override val CommandPrefixes = arrayOf("math", "stuff")

    override fun onMessage(message: IMessage) {
        println(message.message)
    }
}
