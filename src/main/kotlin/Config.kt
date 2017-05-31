import com.github.theholywaffle.teamspeak3.TS3Config
import com.natpryce.konfig.PropertyGroup
import com.natpryce.konfig.getValue
import com.natpryce.konfig.stringType

/**
 * Created by jacob on 2017-05-27 (YYYY-MM-DD).
 */
class Config(
        val LoginName:String,
        val LoginPassword:String,
        var NickName:String = "TsBot",
        val ChannelId:Int = 0)
    : TS3Config()

object server : PropertyGroup() {
    val host by stringType
    val loginName by stringType
    val loginPassword by stringType
    val nickName by stringType
}
