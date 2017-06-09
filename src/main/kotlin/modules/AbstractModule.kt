package modules

import app.IClient
import app.ICommand
import app.IMessage
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

    open fun onMessage(message: IMessage) {}

    open fun onCommand(command: ICommand) {}

    open fun onClientJoin(client: IClient) {}
}
