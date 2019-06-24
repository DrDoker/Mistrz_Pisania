import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class LandingPageController {


    @FXML
    void goToLessons(ActionEvent event) {
        try{
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Fxml/Tutorial.fxml"));
            Scene scene = new Scene(root);

            theStage.setScene(scene);
            theStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void goToTest(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Fxml/QuickTest.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            theStage.setScene(scene);
            theStage.show();
            
            QuickTestController controller = loader.getController();
            controller.loadTest();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void goToInfo(ActionEvent event) {
        try{
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Fxml/Info.fxml"));
            Scene scene = new Scene(root);

            theStage.setScene(scene);
            theStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    
}
