package modelLayer;
import java.util.Date;

/**
 * Created by Jacob on 01-05-2015.
 */
public class Client {

    private String uniqueID;
    private String nickname;
    private String country;
    private String ip;
    private int connectionTime; //In seconds
    private Date firstSeen; //yyyy-mm-dd hh-mm-ss ISO-Standard
    private Date lastSeen; //yyyy-mm-dd hh-mm-ss  ISO-Standard

    public Client(String uniqueID, String nickName) {
        this.uniqueID = uniqueID;
        this.nickname = nickName;
    }

    public Client(String uniqueID, String nickName, String country, String ip, int connectionTime, Date firstSeen, Date lastSeen) {
        this.uniqueID = uniqueID;
        this.nickname = nickName;
        this.country = country;
        this.ip = ip;
        this.connectionTime = connectionTime;
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public void addConnectionTime(int connectionTime) {
        this.connectionTime =+ connectionTime;
    }

    public Date getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(Date firstSeen) {
        this.firstSeen = firstSeen;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
}
