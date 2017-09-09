
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    private Server server;
    private ChatClient client;
    public Controller(){
    }
    @FXML
    private void senddebug(ActionEvent event)
    {
        System.out.println(client +""+ cmdtxt);
       client.send(cmdtxt.getText());
}

    @FXML
    private void login(ActionEvent event)
    {
        if(client != null){
            client.close();
            client =  null;
        }
        client = new ChatClient(serveripfield.getText(),Integer.parseInt(serverportfield.getText()),this);

        if(client.isConnected()){
            log("Trying to connect as " + namefield.getText() + "...");
            System.out.println("test");
            client.send("LOGIN " + namefield.getText());
        }
    }
    @FXML
    private void createServer(ActionEvent event){
        if(server == null){
            server = new ChatServer(Integer.parseInt(cserverport.getText()));
            System.out.println("Created server "+server.isOpen());
        }
    }

    public void updateDebug(Text[] newtxt) {
        debugtxt.getChildren().addAll(newtxt);
    }


    public void connectClient(ChatClient client) {
        this.client = client;
    }
    public void log(String toLog){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj) + ":  "+ toLog);
        Text[] text = {new Text(df.format(dateobj)), new Text(": " + toLog + System.getProperty("line.separator"))};
        text[0].setStyle("-fx-font-weight: bold");
        updateDebug( text);
    }
}
