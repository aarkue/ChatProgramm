import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by aarkue on 07.09.17.
 */
public class ChatServer extends Server {
private List<String> names = new List<>();
private List<String> ips = new List<>();
private List<Integer> ports = new List<>();
    public ChatServer(int pPortNr) {
        super(pPortNr);
    }

    public void processNewConnection(String pClientIP, int pClientPort) {
        System.out.println("(Server) New Connection from " + pClientIP +":"+pClientPort);
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        System.out.println(pMessage);
        if(pMessage.matches(	"SEND \\S+ .+")){
            System.out.println("SEND DETECTED");
            String newMessage = pMessage.substring(5);
            String username = newMessage.split(" ")[0];
            String message = newMessage.substring(newMessage.indexOf(" ")+1);
            String[] ipandport = getIPandPort(username.toLowerCase());
            if(ipandport == null){
                send(pClientIP,pClientPort,"-ERR Could not send message!");
            }else{
                String name = getName(pClientIP,pClientPort);
                if(name == null){
                    send(pClientIP,pClientPort,"-ERR Could not send message! Username error");
                }else{
                send(ipandport[0],Integer.parseInt(ipandport[1]),name+": "+message);
                send(pClientIP,pClientPort,"+OK " + name+": "+message);
                System.out.println("DONE " +message);
                }
            }

        }else if(pMessage.matches("LOGIN \\S+")) {
            String name = pMessage.substring(6);
            System.out.println("testing name " + name);
           if(isNameAvailable(name)){
               names.append(name.toLowerCase());
               ips.append(pClientIP);
               ports.append(pClientPort);
               System.out.println("Name " + name.toLowerCase() + " logged in!");
               send(pClientIP,pClientPort,"+OK Username "+name.toLowerCase()+" succesfully logged in!");
           }else {
               System.out.println("Could NOT log in " + name.toLowerCase() + "!");
               send(pClientIP, pClientPort, "-ERR Login using  " + name.toLowerCase() + "  failed!");
           }


        }else if(pMessage.equals("LIST")) {
            names.toFirst();
            String online = "Online Users: ";
            while(names.hasAccess() && names.getContent() != null){
                online += names.getContent()+", ";
            }
            sendToAll(online);

        }else{

            System.out.println("(Server) New Message from " + pClientIP + ":" + pClientPort + ";;" + pMessage);
           send(pClientIP,pClientPort,"-ERR Unrecognized Commend: "+pMessage);
        }
    }

    public void processClosingConnection(String pClientIP, int pClientPort) {
        System.out.println("Close Connection from " + pClientIP +":"+pClientPort);
     // FXApplication.updateDebug("Closing Connection from " + pClientIP +":"+pClientPort);
    }
    private boolean isNameAvailable(String name){
        names.toFirst();
        while(names.hasAccess() && names.getContent() != null){
            if(names.getContent().equals(name.toLowerCase())){
                return false;
            }
            names.next();
        }
        return true;
    }
    private String[] getIPandPort(String name){
        System.out.println(names.hasAccess());
        names.toFirst();
        ips.toFirst();
        ports.toFirst();
        while(names.hasAccess() && names.getContent() != null){
            System.out.println("Name: " + names.getContent()+ " IP: " +ips.getContent()+ ":"+ports.getContent());
            if(names.getContent().equals(name)){
                return new String[]{ips.getContent(),""+ports.getContent()};
            }else{
                names.next();
                ips.next();
                ports.next();
            }
        }
        return null;
    }

    private String getName(String ip, Integer port){
        System.out.println("2nd " +ips.hasAccess()+ " ," + ports.hasAccess()+ip+":"+port);
        names.toFirst();
        ips.toFirst();
        ports.toFirst();
        while(ips.hasAccess() && ips.getContent() != null && ports.hasAccess() && ports.getContent() != null){
            System.out.println("2nd Name: " + names.getContent()+ " IP: " +ips.getContent()+ ":"+ports.getContent());
            if(ips.getContent().equals(ip) && ports.getContent().equals(port)){
                return names.getContent();
            }else{
                names.next();
                ips.next();
                ports.next();
            }
        }
        return null;
    }
}
