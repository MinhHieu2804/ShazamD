package sample;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    Button searchButton;

    @FXML
    Button allWordButton;

    @FXML
    TextField searchWord;

    @FXML
    WebView meaning;

    @FXML
    ListView<String> history;

    @FXML
    Button speakButton;
    
    @FXML
    ImageView speakerIcon;

    @FXML
    ListView<String> hint;

    public void viewAllWorld(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Scene s = new Scene(root, 600, 400);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(s);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void speakWord(ActionEvent event){
        InputStream sound=null;
        Audio audio=Audio.getInstance();
        try {
            sound=audio.getAudio("hello",Language.ENGLISH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            audio.play(sound);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        speakButton.setVisible(false);
        speakerIcon.setVisible(false);
        hint.setVisible(false);
        WebEngine webEngine = meaning.getEngine();
        Map<String, String> myDictionary = new HashMap<>();
        FileInputStream inputStream = null;
        Scanner sc = null;

        //read txt;
        try {
            inputStream = new FileInputStream("E_V.txt");
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                int i = 0;
                String tmp;
                tmp = "";
                for (String w : line.split("<", 2)) {
                    if (i == 0) {
                        tmp = w;
                        hint.getItems().add(tmp);
                        i++;
                    } else {
                        myDictionary.put(tmp, '<' + w);
                    }
                }
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }

        searchWord.setOnMouseClicked(event -> {
            hint.setVisible(true);


        });

        hint.setOnMouseClicked(mouseEvent -> {
            String w = hint.getSelectionModel().getSelectedItem();
            webEngine.loadContent(myDictionary.get(w));
            searchWord.setText(w);
            history.getItems().remove(w);
            history.getItems().add(0, w);
            hint.setVisible(false);
        });

        searchButton.setOnAction(event -> {
            hint.setVisible(false);
            String w = searchWord.getText();
            w = w.toLowerCase();
            w = w.trim();
            if (!w.equals("")) {
                if (!myDictionary.containsKey(w)) {
                    speakButton.setVisible(false);
                    speakerIcon.setVisible(false);
                    String t = "<html>NO WORD<br>add word to dictionary if you want</html>";
                    webEngine.loadContent(t);
                } else {
                    speakButton.setVisible(true);
                    speakerIcon.setVisible(true);
                    webEngine.loadContent(myDictionary.get(w));
                    history.getItems().remove(w);
                    history.getItems().add(0, w);
                }

            }
        });

        history.setOnMouseClicked(event -> {
            hint.setVisible(false);
            speakButton.setVisible(true);
            speakerIcon.setVisible(true);
            String w = history.getSelectionModel().getSelectedItem();
            webEngine.loadContent(myDictionary.get(w));
            searchWord.setText(w);
        });

    }
}
