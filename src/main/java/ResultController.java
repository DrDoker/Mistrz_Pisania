
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;



public class ResultController  {


    @FXML
    private Label speed;

    @FXML
    private Label accuracy;

    @FXML
    private Label timeSpent;


     @FXML
    void goHome(ActionEvent event) {
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
    public void initializeMyData( int errorCount, int pressedСhar, int totalChar, String timeToComplete ){
        Double timeInMin = (Double.parseDouble(timeToComplete.substring(0, 2)) + (Double.parseDouble(timeToComplete.substring(3, 5))/60.0)) ;
        double acc = (double) (100 - (errorCount * 100)/pressedСhar);
        
        speed.setText( String.format("%.0f", (pressedСhar/timeInMin)));
        accuracy.setText(String.format("%.1f", acc));
        timeSpent.setText(timeToComplete);

        System.out.println("errorCount = [" + errorCount + "], pressedСhar = [" + pressedСhar + "], totalChar = [" + totalChar + "], timeToComplete = [" + timeToComplete + "]");

    }

    
}
