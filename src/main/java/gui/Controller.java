package gui;
import ChatProgramm.ChatClient;
import ChatProgramm.ChatServer;
import abiturklassen.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Controller {
    @FXML
    private Button sendenbtn;
    @FXML
    private TextField cmdtxt;
    @FXML
    private TextFlow debugtxt;
    @FXML
    private TextField namefield;
    @FXML
    private TextField serveripfield;
    @FXML
    private TextField serverportfield;
    @FXML
    private TextField cserverport;
    @FXML
    private Label iplabel;
    @FXML
    private ScrollPane scrollPane;

    private Server server;
    private ChatClient client;


    @FXML
    private void senddebug(ActionEvent event)
    {
       client.send(cmdtxt.getText());
}

    @FXML
    private void login(ActionEvent event) {
        if(client != null){
            client.close();
            client =  null;
        }
        client = new ChatClient(serveripfield.getText(),Integer.parseInt(serverportfield.getText()),this);
        if(client.isConnected()){
            log("Trying to connect as " + namefield.getText() + "...");
            client.send("LOGIN " + namefield.getText());
        }
    }

    @FXML
    private void createServer(ActionEvent event){
        if(server == null){
            server = new ChatServer(Integer.parseInt(cserverport.getText()));
            try {
                InetAddress i = InetAddress.getLocalHost();
                iplabel.setText("Your local IP is: " +i.getHostAddress());
                serveripfield.setText(i.getHostAddress());
                serverportfield.setText(cserverport.getText());
            } catch (Exception e) {
                e.printStackTrace();
        }

            System.out.println("Created server "+server.isOpen());
        }
    }

    private void updateDebug(Text[] newtxt) {
        debugtxt.getChildren().addAll(newtxt);
    }

    public void log(String toLog){
        scrollPane.setVvalue(scrollPane.getVmax());
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj) + ":  "+ toLog);
        Text[] text = {new Text(df.format(dateobj)), new Text(": " + toLog + System.getProperty("line.separator"))};
        text[0].setStyle("-fx-font-weight: bold");
        updateDebug( text);
    }
}
