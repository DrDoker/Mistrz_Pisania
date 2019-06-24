
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;



public class LessonResultController {

    @FXML
    private Label speedKPM;

    @FXML
    private Label trueAccuracy;

    @FXML
    private Label timeSpent;

    @FXML
    private Label accuracy;
    
    private int currentLessonChoice;

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

    @FXML
    void nextLesson(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/Tutorial.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            theStage.setScene(scene);
            theStage.show();
            
            TutorialController controller = loader.getController();
            controller.initializeLessonChoiceAndBegin( ++currentLessonChoice );
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void redoLesson(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/Tutorial.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            theStage.setScene(scene);
            theStage.show();
            
            TutorialController controller = loader.getController();
            controller.initializeLessonChoiceAndBegin( currentLessonChoice );               
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void initializeMyData( int totalChar,  int errorCountWithBackspace, int errorCountWithoutBackspace, String timeToComplete,  int currentLessonChoice){
        try{
        Double timeInMin = (Double.parseDouble(timeToComplete.substring(0, 2)) + (Double.parseDouble(timeToComplete.substring(3, 5))/60.0));
            System.out.println(timeInMin);

        double tacc = (double) (100 - (errorCountWithoutBackspace * 100)/totalChar);
        double acc = (double) (100 - (errorCountWithBackspace * 100)/totalChar);

        System.out.println("totalChar = [" + totalChar + "], errorCountWithBackspace = [" + errorCountWithBackspace + "], errorCountWithoutBackspace = [" + errorCountWithoutBackspace + "], timeToComplete = [" + timeToComplete + "], currentLessonChoice = [" + currentLessonChoice + "]");

        speedKPM.setText( String.format("%.0f", (totalChar/timeInMin)));
        trueAccuracy.setText(String.format("%.1f", tacc));
        accuracy.setText(String.format("%.1f", acc) + "%");
        timeSpent.setText(timeToComplete);

        
        this.currentLessonChoice = currentLessonChoice;
        }catch(RuntimeException ex){
            ex.printStackTrace();
        }
    }

    
}
