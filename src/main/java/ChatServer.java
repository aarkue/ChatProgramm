
/**
 * Created by aarkue on 07.09.17.
 */
public class ChatServer extends Server {


    public ChatServer(int pPortNr) {
        super(pPortNr);
    }

    public void processNewConnection(String pClientIP, int pClientPort) {
        System.out.println("(Server) New Connection from " + pClientIP +":"+pClientPort);
        FXApplication.updateDebug("(Server) New Connection from " + pClientIP +":"+pClientPort);
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        if(pMessage.matches(	"SEND \\S+ .+")){
            System.out.println("SEND DETECTED");
            String newMessage = pMessage.substring(5);
            System.out.println(newMessage.indexOf(" "));
            String username = newMessage.split(" ")[0];
            String message = newMessage.substring(newMessage.indexOf(" ")+1);
            System.out.println(username);
            System.out.println(message);
            FXApplication.updateDebug( "SEND DETECTED:"  + pMessage);
        }
        System.out.println("(Server) New Message from " + pClientIP +":"+pClientPort + ";;" + pMessage);
        FXApplication.updateDebug( pClientIP +":"+pClientPort + ": " + pMessage);

    }

    public void processClosingConnection(String pClientIP, int pClientPort) {
        System.out.println("Close Connection from " + pClientIP +":"+pClientPort);
        FXApplication.updateDebug("Closing Connection from " + pClientIP +":"+pClientPort);
    }
}
