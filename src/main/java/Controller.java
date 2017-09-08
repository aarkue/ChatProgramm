
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private Button sendenbtn;
    @FXML
    private TextField cmdtxt;
    @FXML
    private TextArea debugtxt;

    private ChatServer server;
    private ChatClient client;
    public Controller(ChatServer server, ChatClient client){
        this.server = server;
        this.client = client;
    }
    @FXML
    private void senddebug(ActionEvent event)
    {
        System.out.println(client +""+ cmdtxt);
       client.send(cmdtxt.getText());
}

    public void updateDebug(String newtxt) {
        debugtxt.appendText(System.getProperty("line.separator") + newtxt);
    }
}
