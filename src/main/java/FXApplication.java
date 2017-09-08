import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXApplication extends Application {
private static Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));

        ChatServer server = new ChatServer(3333);
        ChatClient client = new ChatClient("localhost", 3333);
        System.out.println(server.isOpen());
        System.out.println(client.isConnected());
        controller = new Controller(server,client);

        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("AarChat");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
    public static void updateDebug(String newtxt){
        if(controller == null){
            System.out.println("controller null");
            return;
        }
        controller.updateDebug(newtxt);
    }
}
