import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TutorialController implements Initializable {

    @FXML
    private ChoiceBox<String> lessonChoiceBox;

    @FXML
    private CheckBox soundCheckBox;

    @FXML
    private Label ETLabel;

    @FXML
    private Label displayArea;

    @FXML
    private JFXTextArea textInputArea;

    @FXML
    private Button timerButton;

    @FXML
    private JFXButton key1;

    @FXML
    private JFXButton key2;

    @FXML
    private JFXButton key3;

    @FXML
    private JFXButton key4;

    @FXML
    private JFXButton key5;

    @FXML
    private JFXButton key6;

    @FXML
    private JFXButton key7;

    @FXML
    private JFXButton key8;

    @FXML
    private JFXButton key9;

    @FXML
    private JFXButton key0;

    @FXML
    private JFXButton keyminus;

    @FXML
    private JFXButton keyplus;

    @FXML
    private JFXButton backspace;

    @FXML
    private JFXButton q;

    @FXML
    private JFXButton w;

    @FXML
    private JFXButton e;

    @FXML
    private JFXButton r;

    @FXML
    private JFXButton t;

    @FXML
    private JFXButton y;

    @FXML
    private JFXButton u;

    @FXML
    private JFXButton i;

    @FXML
    private JFXButton o;

    @FXML
    private JFXButton p;

    @FXML
    private JFXButton keyBoxBracketL;

    @FXML
    private JFXButton keyBoxBracketR;

    @FXML
    private JFXButton keyBackslash;

    @FXML
    private JFXButton a;

    @FXML
    private JFXButton s;

    @FXML
    private JFXButton d;

    @FXML
    private JFXButton f;

    @FXML
    private JFXButton g;

    @FXML
    private JFXButton h;

    @FXML
    private JFXButton j;

    @FXML
    private JFXButton k;

    @FXML
    private JFXButton l;

    @FXML
    private JFXButton keySemicolon;

    @FXML
    private JFXButton keyAphostrophe;

    @FXML
    private JFXButton shiftl;

    @FXML
    private JFXButton z;

    @FXML
    private JFXButton x;

    @FXML
    private JFXButton c;

    @FXML
    private JFXButton v;

    @FXML
    private JFXButton b;

    @FXML
    private JFXButton n;

    @FXML
    private JFXButton m;

    @FXML
    private JFXButton keyComma;

    @FXML
    private JFXButton keyDot;

    @FXML
    private JFXButton keyForwardslash;

    @FXML
    private JFXButton shiftr;

    @FXML
    private JFXButton space;

    @FXML
    private JFXButton goButton;

    @FXML
    private Button goToResultButton;

    // для звуков
    private static final AudioClip ALERT_AUDIOCLIP = new AudioClip(TutorialController.class.getResource("/Audio/alert.mp3").toString());
    private static final AudioClip TYPING_AUDIOCLIP = new AudioClip(TutorialController.class.getResource("/Audio/typing.wav").toString());

    // прогресс отслеживания переменных

    private int errorCountWithoutBackspace;
    private int errorCountWithBackspace;
    private int totalChar;
    private char expectedKey;

    String timeToComplete;

    private char typedKey;

    int indexOfLine = 0;

    String line;

    //Timer variables
    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;

    boolean sos = true;
    boolean timerStarted = false;


    @FXML
    void goHome(ActionEvent event) {
        try {
            Stage theStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/LandingPage.fxml"));
            Scene scene = new Scene(root);
            theStage.setScene(scene);
            theStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    void onPressGo(ActionEvent event) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("files\\" + lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt")));

            errorCountWithBackspace = 0;
            errorCountWithoutBackspace = 0;
            totalChar = 0;
            indexOfLine = 0;
            displayArea.setText("");// очищает область отображения
            textInputArea.setText(""); // очищает inputTextArea.
            textInputArea.requestFocus();// фокусируется на textInputArea.

            TutorialController.ALERT_AUDIOCLIP.setRate(2.0);
            TutorialController.TYPING_AUDIOCLIP.setVolume(1.0);


            textInputArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    textInputArea.setId("normal");
                    expectedKey = line.charAt(indexOfLine);
                    typedKey = event.getCharacter().charAt(0);

                    System.out.println("expected : " + expectedKey);
                    System.out.println("typed : " + typedKey);

                    // проверяем, запущен ли таймер, и запускаем, если нет.
                    if (!timerStarted) {
                        timerStarted = true;
                        timerButton.fire();
                    }

                    // если набрана последняя буква показанной строки, читать следующую строку урока
                    if (indexOfLine == line.length() - 1) {


                        // проверяем последний символ
                        if (typedKey != expectedKey) {

                            if (soundCheckBox.isSelected())
                                TutorialController.ALERT_AUDIOCLIP.play();
                            textInputArea.setId("warn");// предупреждает пользователя, устанавливая красный фон.

                            errorCountWithBackspace++;
                            errorCountWithoutBackspace++;
                            // System.out.println("false");
                        } else {
                            if (soundCheckBox.isSelected())
                                TutorialController.TYPING_AUDIOCLIP.play();
                        }
                        try {
                            // если файл не полностью прочитан
                            if ((line = reader.readLine()) != null) {
                                displayArea.setText(line);
                                totalChar += line.length();
                                textInputArea.setText("");
                            }
                            // достигнут конец файла
                            else {
                                timerButton.fire(); // таймер приостановлен.
                                timeToComplete = ETLabel.getText(); // окончательное время чтения и конец урока.

                                // закрываем файл урока.
                                reader.close();
                                // урок закончен - переключить сцену на страницу результатов.
                                goToResultButton.fire();
                            }
                            // В любом из вышеперечисленных случаев устанавливаем индекс строки на ноль - для следующей строки или следующего урока.
                            indexOfLine = 0;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    // если не последний символ и если нажатая клавиша не является клавишей возврата
                    else if (!(event.getCharacter().equals("\u0008"))) {
//     System.out.println("typed : " + typedKey);
                        if (typedKey != expectedKey) {

                            if (soundCheckBox.isSelected())
                                TutorialController.ALERT_AUDIOCLIP.play();
                            textInputArea.setId("warn");

                            errorCountWithBackspace++;
                            errorCountWithoutBackspace++;
//                       System.out.println("false");
                        } else {
                            if (soundCheckBox.isSelected())
                                TutorialController.TYPING_AUDIOCLIP.play();
                        }
//                    System.out.println("true");
                        indexOfLine++;
                    }
                    // если нажат бекспейс, уменьшается indexOfLine и только errorCountWithBackspace.
                    else if (event.getCharacter().equals("\u0008") && textInputArea.getText() != null) {
                        indexOfLine--;
                        if (errorCountWithBackspace > 0)
                            errorCountWithBackspace--;
                    }
                    // вызываем метод showKeyPressed, чтобы показать нажатие клавиши на клавиатуре GUI.
                    showKeyPressed(event.getCharacter());
                }
            });

            // Первая строка отображается.
            if ((line = reader.readLine()) != null) {
                displayArea.setText(line);
                totalChar += line.length();

// System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Этот метод выбирает и показывает клавишу, которая нажата на клавиатуре GUI.
    public void showKeyPressed(String key) {
        if (Character.isLetter(key.charAt(0)))
            key = key.toLowerCase();
        switch (key) {
            case "q":
                q.arm(); q.disarm();
                break;
            case "w":
                w.arm(); w.disarm();
                break;
            case "e":
                e.arm(); e.disarm();
                break;
            case "r":
                r.arm(); r.disarm();
                break;
            case "t":
                t.arm(); t.disarm();
                break;
            case "y":
                y.arm(); y.disarm();
                break;
            case "u":
                u.arm(); u.disarm();
                break;
            case "i":
                i.arm(); i.disarm();
                break;
            case "o":
                o.arm(); o.disarm();
                break;
            case "p":
                p.arm(); p.disarm();
                break;
            case "a":
                a.arm(); a.disarm();
                break;
            case "s":
                s.arm(); s.disarm();
                break;
            case "d":
                d.arm(); d.disarm();
                break;
            case "f":
                f.arm(); f.disarm();
                break;
            case "g":
                g.arm(); g.disarm();
                break;
            case "h":
                h.arm(); h.disarm();
                break;
            case "j":
                j.arm(); j.disarm();
                break;
            case "k":
                k.arm(); k.disarm();
                break;
            case "l":
                l.arm(); l.disarm();
                break;
            case "z":
                z.arm(); z.disarm();
                break;
            case "x":
                x.arm(); x.disarm();
                break;
            case "c":
                c.arm(); c.disarm();
                break;
            case "v":
                v.arm(); v.disarm();
                break;
            case "b":
                b.arm(); b.disarm();
                break;
            case "n":
                n.arm(); n.disarm();
                break;
            case "m":
                m.arm(); m.disarm();
                break;
            case "1":
            case "!":
                key1.arm(); key1.disarm();
                break;
            case "2":
            case "@":
                key2.arm(); key2.disarm();
                break;
            case "3":
            case "#":
                key3.arm(); key3.disarm();
                break;
            case "4":
            case "$":
                key4.arm(); key4.disarm();
                break;
            case "5":
            case "%":
                key5.arm(); key5.disarm();
                break;
            case "6":
            case "^":
                key6.arm(); key6.disarm();
                break;
            case "7":
            case "&":
                key7.arm(); key7.disarm();
                break;
            case "8":
            case "*":
                key8.arm(); key8.disarm();
                break;
            case "9":
            case "(":
                key9.arm(); key9.disarm();
                break;
            case "0":
            case ")":
                key0.arm(); key0.disarm();
                break;
            case "-":
            case "_":
                keyminus.arm(); keyminus.disarm();
                break;
            case "+":
            case "=":
                keyplus.arm(); keyplus.disarm();
                break;
            case "{":
            case "[":
                keyBoxBracketL.arm(); keyBoxBracketL.disarm();
                break;
            case "}":
            case "]":
                keyBoxBracketR.arm(); keyBoxBracketR.disarm();
                break;
            case "|":
            case "\\":
                keyBackslash.arm(); keyBackslash.disarm();
                break;
            case ";":
            case ":":
                keySemicolon.arm(); keySemicolon.disarm();
                break;
            case "\"":
            case "'":
                keyAphostrophe.arm(); keyAphostrophe.disarm();
                break;
            case ",":
            case "<":
                keyComma.arm(); keyComma.disarm();
                break;
            case ">":
            case ".":
                keyDot.arm(); keyDot.disarm();
                break;
            case "?":
            case "/":
                keyForwardslash.arm(); keyForwardslash.disarm();
                break;
            case "\u21E7":
                shiftl.arm(); shiftr.arm();
                shiftl.disarm(); shiftr.disarm();
                break;
            case " ":
                space.arm(); space.disarm();
                break;
            case "\u0008": backspace.arm(); backspace.disarm();
                break;
        }

    }


    // метод для изменения текста метки прошедшего времени.
    void change() {
        if (millis == 1000) {
            secs++;
            millis = 0;
        }
        if (secs == 60) {
            mins++;
            secs = 0;
        }
        ETLabel.setText((((mins / 10) == 0) ? "0" : "") + mins + ":" + (((secs / 10) == 0) ? "0" : "") + secs);
        millis++;
    }

    @FXML
    void switchSceneToResultPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Fxml/LessonResult.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            LessonResultController controller = loader.getController();


            int currentLessonChoice = lessonChoiceBox.getSelectionModel().getSelectedIndex();

            controller.initializeMyData(totalChar, errorCountWithBackspace, errorCountWithoutBackspace, timeToComplete, currentLessonChoice);

            Stage theStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            theStage.setScene(scene);
            theStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // загружает тот же или следующий урок.
    void initializeLessonChoiceAndBegin(int choice) {
        if (choice <= 16 && 0 <= choice)
            lessonChoiceBox.getSelectionModel().select(choice);
        else if (choice == 17)
            lessonChoiceBox.getSelectionModel().select(16);
        goButton.fire();
    }

    // Запускается, когда сцена загружена.
    public void initialize(URL url, ResourceBundle rb) {

        // Инициализирует окно выбора с параметрами урока.
        lessonChoiceBox.getItems().addAll("Demo", "Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5", "Lesson 6", "Lesson 7", "Lesson 8", "Lesson 9", "Lesson 10", "Lesson 11", "Lesson 12", "Lesson 13", "Lesson 14", "Lesson 15", "Common Words");
        lessonChoiceBox.getSelectionModel().select("Demo");

        ETLabel.setText("00:00");

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
                if (sos) {
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