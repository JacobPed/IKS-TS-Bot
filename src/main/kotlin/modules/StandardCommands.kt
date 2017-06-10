package modules

import app.ICommand
import app.ITsConnection

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
class StandardCommands(tsConnection: ITsConnection) : AbstractModule(tsConnection) {
    override val CommandPrefixes = listOf("ping")

    override fun onCommand(command: ICommand) {
        if(command.prefix == "ping")
            tsConnection.sendMessage("pong")
    }
}
