package modules

import app.IMessage
import app.ITsConnection

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
class SimpleMath(tsConnection: ITsConnection) : AbstractModule(tsConnection) {
    override val CommandPrefixes = arrayOf("math", "stuff")

    override fun onMessage(message: IMessage) {
        println(message.message)
    }
}
