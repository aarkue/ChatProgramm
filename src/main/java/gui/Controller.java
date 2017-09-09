package gui;
import ChatProgramm.ChatClient;
import ChatProgramm.ChatServer;
import abiturklassen.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    @FXML
    private CheckBox replacebox;

    private Server server;
    private ChatClient client;
    private FileWriter writer;

    public Controller() {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss");
        Date dateobj = new Date();
        try {
            writer = new FileWriter(new File(df.format(dateobj) + "-chat.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj) + ":  "+ toLog);
        try {
            writer.append(df.format(dateobj) + ": " + toLog + System.getProperty("line.separator"));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (replacebox.isSelected()) {
            toLog = toLog.replaceAll(":\\)", "😁")
                    .replaceAll(":\\(", "🙁")
                    .replaceAll(":/", "\uD83D\uDE15")
                    .replaceAll("<3", "❤")
                    .replaceAll(":\\*", "\uD83D\uDE18");
        }
        Text[] text = {new Text(df.format(dateobj)), new Text(": " + toLog + System.getProperty("line.separator"))};
        text[0].setStyle("-fx-font-weight: bold");
        updateDebug(text);
        scrollPane.setVvalue(scrollPane.getVmax());
    }
}
