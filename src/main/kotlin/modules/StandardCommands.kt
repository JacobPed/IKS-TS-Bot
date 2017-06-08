package modules

import app.ITeamSpeakBot

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
class StandardCommands(bot: ITeamSpeakBot) : AbstractModule(bot) {
    override val CommandPrefixes = arrayOf("ping")

    override fun OnMessage(message: Message) {
        if(message.prefix == "ping")
            masterBot.sendMessage("pong")
    }
}
