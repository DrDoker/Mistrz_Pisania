import com.jfoenix.controls.JFXTextArea;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuickTestController implements Initializable {

    @FXML
    private Label timeLabel;

    @FXML
    private JFXTextArea textDisplay;
    
    @FXML
    private Button resultButton;

    @FXML
    private Button timerButton;

    private static final AudioClip ALERT_AUDIOCLIP = new AudioClip(TutorialController.class.getResource("/Audio/alert.mp3").toString());
    private static final AudioClip TYPING_AUDIOCLIP = new AudioClip(TutorialController.class.getResource("/Audio/typing.wav").toString());

    private int errorCount;
    private int totalChar;
    private int pressedСhar;
    private char expectedKey;
    private char typedKey;
    int indexOfLine=0;
    
    String arr="";

    String timeToComplete;
    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;
        
    boolean sos = true;
    boolean timerStarted = false;
   
   
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
    
    @FXML
    void switchSceneToResult(ActionEvent event) {
        try{ 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/Result.fxml"));
            Parent root = loader.load();
        
            Scene scene = new Scene(root);
            ResultController controller = loader.getController();

            controller.initializeMyData(errorCount,  pressedСhar, totalChar, timeToComplete);
        
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            theStage.setScene(scene);
            theStage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    @FXML
    void restartTest(MouseEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/QuickTest.fxml"));
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
            
    

    void loadTest(){
        try{
            BufferedReader reader = new BufferedReader( new FileReader( new File("files\\Test.txt") ) ); 
            String line;
            while((line = reader.readLine()) != null){
                textDisplay.setText( textDisplay.getText() + line);
                arr += line;
            }
            reader.close();
            textDisplay.requestFocus();
            pressedСhar = 0; errorCount =0; indexOfLine=0; totalChar = arr.length();
            
            QuickTestController.ALERT_AUDIOCLIP.setRate(2.0);
            QuickTestController.TYPING_AUDIOCLIP.setVolume(1.0);
            
            textDisplay.setStyle("-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
            textDisplay.selectRange(indexOfLine, indexOfLine+1);
            
            textDisplay.setOnKeyTyped( new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(!timerStarted){
                        timerStarted = true;
                        timerButton.fire();
                    }
                    
                    expectedKey = arr.charAt(indexOfLine);
                    //System.out.println(expectedKey);
                    typedKey = event.getCharacter().charAt(0);
                    if(indexOfLine < arr.length()){
                        if(typedKey != expectedKey){
                            QuickTestController.ALERT_AUDIOCLIP.play();
                            errorCount++;
                            indexOfLine++;
                            textDisplay.setStyle("-fx-background-color: #ffcdd2;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                            textDisplay.selectRange(indexOfLine, indexOfLine+1);
                        } else {
                            QuickTestController.TYPING_AUDIOCLIP.play();
                            indexOfLine++;
                            textDisplay.setStyle("-fx-background-color: white;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                            textDisplay.selectRange(indexOfLine, indexOfLine+1);
                        }

                        pressedСhar++;
                        
                    }
                    if(indexOfLine == arr.length()){
                        timerButton.fire();
                        timeToComplete = timeLabel.getText();
                        resultButton.fire();

                    }
                }
            });
            
        }catch( IOException ex){
            ex.printStackTrace();
        }
    }
    // метод для изменения текста метки прошедшего времени.
    void change(){
		if(millis == 1000) {
			secs++;
			millis = 0;

		}
		if(secs == 60) {
			mins++;
			secs = 0;
		}

		timeLabel.setText((((mins/10) == 0) ? "0" : "") + mins + ":" + (((secs/10) == 0) ? "0" : "") + secs);
                millis++;
                if(timeLabel.getText().equals("01:00")){
                        timerButton.fire();
                        timeToComplete = timeLabel.getText();
                        resultButton.fire();
                }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Первоначально истекшее время установлено в 0.
        timeLabel.setText("00:00");
        
        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
	@Override
            public void handle(ActionEvent event) {
                change();
            }
	}));
        
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.setAutoReverse(false);

        // Действие, выполняемое кнопкой таймера
	timerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(sos) {
            		timeline.play();
            		sos = false;
            		timerButton.setText("Stop");
            	} else {
            		timeline.pause();
            		sos = true;
            		timerButton.setText("Start");
            	}
            }
        });
        
    }    
    
}
