import com.github.theholywaffle.teamspeak3.TS3Config

/**
* Created by jacob on 2017-05-27 (YYYY-MM-DD).
*/
class Config(
        val LoginName:String,
        val LoginPassword:String,
        var NickName:String = "TsBot")
    : TS3Config() {

}
