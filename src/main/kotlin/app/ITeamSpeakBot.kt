package app

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
interface ITeamSpeakBot: AutoCloseable {
//    fun connect()
    fun isConnected() : Boolean
    fun moveToChannelId(channelId: Int)
    fun sendMessage(message: String)
    fun sendMessageToChannel(channel: Int, message: String)
    fun sendMessageServer(message: String)
}
