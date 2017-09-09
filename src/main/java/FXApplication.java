
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXApplication extends Application {

    @Override
    public void stop() throws Exception {
        System.out.println("Shutting down!");
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("AarChat");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }


    public static void main(String[] args) {

        launch(args);
    }
}
