import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FXApplication extends Application {
private static Controller controller;

    @Override
    public void stop() throws Exception {
        System.out.println("Shutting donw!");
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        controller = new Controller();
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("AarChat");
        primaryStage.setScene(new Scene(root, 650, 500));
        primaryStage.show();


    }


    public static void main(String[] args) {

        launch(args);
    }
    public static void updateDebug(String newtxt){
       /* if(controller == null){
            System.out.println("controller null");
            return;
        }*/
        controller.log(newtxt);
    }
    public static void displayAlert(String text){
        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, text, ButtonType.OK).show());
    }
}
