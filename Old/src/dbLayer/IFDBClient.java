package dbLayer;

import modelLayer.Client;

import java.util.ArrayList;

/**
 * Created by Jacob on 01-05-2015.
 */
public interface IFDBClient {

    // get all Clients
    public ArrayList<Client> getAllClients();

    // Check if client exist
    public boolean existClient(String uniqueID);

    // find one Client by UniqueID
    public Client findClientByID(String uniqueID);

    // find all Clients by given NickName
    public ArrayList<Client> findClientsByNickName(String nickName);

    // find one Client by exact NickName
    public Client findClientByExactNickName(String exactNickName);

    // insert a new Client
    public void insertCLient(Client client) throws Exception;

    // update Client information
    public void updateClient(Client client) throws Exception;

    // Remove Client by given UniqueID
    public void delete(String uniqueID) throws Exception;
}
