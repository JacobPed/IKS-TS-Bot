package app

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent

/**
 * Created by jacob on 2017-06-09 (YYYY-MM-DD).
 */
interface ISender {
    val uniqueId: String
}

interface ICommand {
    val sender: ISender
    val prefix: String
    val suffix: String
}

interface IMessage {
    val sender: ISender
    val message: String
}

interface IClientJoined {
    val clientId: Int
    val channelId: Int
}

interface IMoved {
    val channelIdMovedTo: Int
    val clientId: Int
}

interface IClient { //This might be missing relations to channel
    val id: Int // The clients current instance id.
    val uniqueId: String
    val databaseId: Int
    val nickName: String
    val channelGroupId: Int
//    val type: //The framework value is Int, which doesn't make any sense.
    val isInputMuted: Boolean
    val isOutputMuted: Boolean
    val isOutputOnlyMuted: Boolean
    val isUsingHardwareInput: Boolean
    val isUsingHardwareOutput: Boolean
    val isRecording: Boolean
    val isTalking: Boolean
    val isPrioritySpeaker: Boolean
    val isChannelCommander: Boolean
    val isAway: Boolean
    val awayMessage: String
    val amountOfServerGroups: Int
    val ServerGroups: MutableCollection<IServerGroup>
    val avatarId: String
    val talkPower: Int
    val country: String
}

interface IServerGroup {

}

enum class ClientType {
    query,
    user
}

class InheritedClient(event: ClientJoinEvent): IClient {
    override val uniqueId: String = event.uniqueClientIdentifier
    override val databaseId: Int = event.clientDatabaseId
    override val id: Int = event.clientId
    override val nickName: String = event.clientNickname
    override val channelGroupId: Int = event.clientChannelGroupId
    //    val type: //The framework value is Int, which doesn't make any sense.
    override val isInputMuted: Boolean = event.isClientInputMuted
    override val isOutputMuted: Boolean = event.isClientOutputMuted
    override val isOutputOnlyMuted: Boolean = event.isClientOutputOnlyMuted
    override val isUsingHardwareInput: Boolean = event.isClientUsingHardwareInput
    override val isUsingHardwareOutput: Boolean = event.isClientUsingHardwareOutput
    override val isRecording: Boolean = event.isClientRecording
    override val isTalking: Boolean = event.isClientTalking
    override val isPrioritySpeaker: Boolean = event.isClientPrioritySpeaker
    override val isChannelCommander: Boolean = event.isClientChannelCommander
    override val isAway: Boolean = event.isClientAway
    override val awayMessage: String = event.clientAwayMessage
    override val amountOfServerGroups: Int = event.amountOfServerGroups
    override val ServerGroups: MutableCollection<IServerGroup> = arrayListOf() //TODO: Define and add Servergroups
    override val avatarId: String = event.clientFlagAvatarId
    override val talkPower: Int = event.clientTalkPower
    override val country: String = event.clientCountry
}
