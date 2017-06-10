package modules

import app.*

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
abstract class AbstractModule {
    val tsConnection: ITsConnection
    abstract val CommandPrefixes: Array<String>

    constructor(tsConnection: ITsConnection) {
        this.tsConnection = tsConnection
    }

    open fun onMessage(message: IMessage) {}

    open fun onCommand(command: ICommand) {}

    open fun onClientJoin(joined: IClientJoined) {}
}
