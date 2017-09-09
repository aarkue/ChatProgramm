package ChatProgramm;

import abiturklassen.Client;
import gui.Controller;
import javafx.application.Platform;

/**
 * Created by aarkue on 07.09.17.
 */
@SuppressWarnings("SpellCheckingInspection")
public class ChatClient extends Client {

    private final Controller controller;

    public ChatClient(String pServerIP, int pServerPort,Controller controller) {
        super(pServerIP, pServerPort);
        this.controller = controller;
    }

    public void processMessage(String pMessage) {
        System.out.println("CLIENT Message: " + pMessage);
        Platform.runLater(() -> controller.log(pMessage));
    }




}
