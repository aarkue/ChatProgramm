import javafx.application.Platform;

/**
 * Created by aarkue on 07.09.17.
 */
public class ChatClient extends Client{
    private Controller controller;
    public ChatClient(String pServerIP, int pServerPort,Controller controller) {
        super(pServerIP, pServerPort);
        this.controller = controller;
    }

    public void processMessage(String pMessage) {
        System.out.println("CLIENT Message: " + pMessage);
        Platform.runLater(() -> controller.log(pMessage));
    }




}
