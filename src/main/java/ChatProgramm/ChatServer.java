package ChatProgramm;

import abiturklassen.List;
import abiturklassen.Server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aarkue on 07.09.17.
 */
public class ChatServer extends Server {
private final List<String> names = new List<>();
private final List<String> ips = new List<>();
private final List<Integer> ports = new List<>();
    public ChatServer(int pPortNr) {
        super(pPortNr);
    }

    private void printDebug(String text){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj)+" SERVER: "+text);
    }

    public void processNewConnection(String pClientIP, int pClientPort) {
        printDebug("New abiturklassen.Connection from " + pClientIP +":"+pClientPort);
    }

    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        printDebug("Message received (From "+pClientIP+":"+pClientIP+"): "+ pMessage);
        if(pMessage.matches(	"SEND \\S+ .+")){
            String newMessage = pMessage.substring(5);
            String username = newMessage.split(" ")[0];
            String message = newMessage.substring(newMessage.indexOf(" ")+1);
            String[] ipandport = getIPandPort(username.toLowerCase());
            if(ipandport == null){
                printDebug("-ERR: SEND failed. User "+username.toLowerCase() + " is not available.");
                send(pClientIP,pClientPort,"-ERR Could not send message! "+username +" is not available!");
            }else{
                String name = getName(pClientIP,pClientPort);
                if(name == null){
                    send(pClientIP,pClientPort,"-ERR Could not send message! You are not logged in.");
                    printDebug("-ERR: SEND failed. Sending user is not logged in.");
                }else{
                send(ipandport[0],Integer.parseInt(ipandport[1]),name+": "+message);
                send(pClientIP,pClientPort,"+OK " + name+": "+message);
                printDebug("Sent message to both clients.");
                }
            }

        }else if(pMessage.matches("LOGIN \\S+")) {
            String name = pMessage.substring(6);
           if(isNameAvailable(name)){
               names.append(name.toLowerCase());
               ips.append(pClientIP);
               ports.append(pClientPort);
               printDebug("User "+name.toLowerCase()+" logged in successfully");
               send(pClientIP,pClientPort,"+OK Username "+name.toLowerCase()+" successfully logged in!");
           }else {
               printDebug("Login failed: Username "+name.toLowerCase()+" is already taken");
               send(pClientIP, pClientPort, "-ERR Login using  " + name.toLowerCase() + "  failed! Try another username.");
           }
        }else if(pMessage.equals("LIST")) {
            printDebug("Listing online users.");
            names.toFirst();
            StringBuilder online = new StringBuilder("+OK Online Users: ");
            while(names.hasAccess() && names.getContent() != null){
                online.append(names.getContent()).append(", ");
                names.next();
            }
            sendToAll(online.toString());

        }else{
            printDebug("-ERR Unrecognized Command: "+pMessage);
            send(pClientIP,pClientPort,"-ERR Unrecognized Command: "+pMessage);
        }
    }

    public void processClosingConnection(String pClientIP, int pClientPort) {
        printDebug("abiturklassen.Client " + deleteUser(pClientIP,pClientPort) + " ("+pClientIP +":"+pClientPort+") disconnected.");
    }

    private String deleteUser(String ip, int port) {
        names.toFirst();
        ips.toFirst();
        ports.toFirst();
        while(ips.hasAccess() && ips.getContent() != null && ports.hasAccess() && ports.getContent() != null){
            if(ips.getContent().equals(ip) && ports.getContent().equals(port)){
                String username = names.getContent();
                names.remove();
                ips.remove();
                ports.remove();
                return username;
            }else{
                names.next();
                ips.next();
                ports.next();
            }
        }
        return "User not found";

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
        names.toFirst();
        ips.toFirst();
        ports.toFirst();
        while(names.hasAccess() && names.getContent() != null){
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
        names.toFirst();
        ips.toFirst();
        ports.toFirst();
        while(ips.hasAccess() && ips.getContent() != null && ports.hasAccess() && ports.getContent() != null){
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
