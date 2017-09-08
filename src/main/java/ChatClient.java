/**
 * Created by aarkue on 07.09.17.
 */
public class ChatClient extends Client{
    public ChatClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);
    }

    public void processMessage(String pMessage) {
        System.out.println("CLIENT Message: " + pMessage);
    }




}
