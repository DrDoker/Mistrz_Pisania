
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class InfoPageController {



    @FXML
    void goToMainPage(ActionEvent event) {
        try{
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/LandingPage.fxml"));
            Scene scene = new Scene(root);
            theStage.setScene(scene);
            theStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }


}
