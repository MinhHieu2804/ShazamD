package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class Controller implements Initializable {

    @FXML
    Button searchButton;

    @FXML
    TextField searchWord;

    @FXML
    WebView meaning;

    @FXML
    ListView<String> history;

    static Map<String, String> myDictionary;

    @FXML
    ListView<String> hint;

    @FXML
    Button speak;

    static Stage st;
    static Vector<String> allWords;
    static Vector<String> my_hint;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InitAll();
    }


    public void InitAll(){
        history.setVisible(false);
        myDictionary = new HashMap<>();
        allWords =new Vector<>();
        my_hint=new Vector<>();
        readTxt();
        hint.getItems().addAll(allWords);
    }

    public void readTxt(){
        //read txt;
        FileInputStream inputStream = null;
        Scanner sc = null;
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
                        allWords.add(w);
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
    }

    public void search_Word(){
        hide_history();
        WebEngine webEngine = meaning.getEngine();
        String w = searchWord.getText();
        w = w.toLowerCase();
        w = w.trim();
        if (!w.equals("")) {
            if (!myDictionary.containsKey(w)) {
                String t = "<html>NO WORD<br>add word to the dictionary if you want</html>";
                webEngine.loadContent(t);
            } else {
                webEngine.loadContent(myDictionary.get(w));
                history.getItems().remove(w);
                history.getItems().add(0, w);
            }
        }
    }

    public void show_history(){
        delete_handel();
        if(!history.getItems().isEmpty())
        history.setVisible(true);
      //  history.getSelectionModel().sel;
    }

    public void hide_history(){
        history.setVisible(false);
        show_hint();
        if(searchWord.getText().equals("")){
            history.setVisible(true);
        }
    }

    public void hide_history2(){
        history.setVisible(false);
    }

    public void enter_search(KeyEvent event){
        if(event.getCode()== KeyCode.ENTER){
            search_Word();
        }
    }

    public void history_f(){
        WebEngine webEngine = meaning.getEngine();
        String w = history.getSelectionModel().getSelectedItem();
        webEngine.loadContent(myDictionary.get(w));
        searchWord.setText(w);
        history.getItems().remove(w);
        history.getItems().add(0, w);
        history.setVisible(false);
    }

    public void hint_f(){
        WebEngine webEngine = meaning.getEngine();
        history.setVisible(false);
        String w = hint.getSelectionModel().getSelectedItem();
        webEngine.loadContent(myDictionary.get(w));
        searchWord.setText(w);
        history.getItems().remove(w);
        history.getItems().add(0, w);
    }

    public void show_hint(){
        if(searchWord.getText().equals("")){
            hint.getItems().clear();
            hint.getItems().addAll(allWords);
        }
        my_hint=new Vector<>();
        int i=0;
        while(i<allWords.size()){
            String t=allWords.get(i);
            if(t.startsWith(searchWord.getText()) || t.startsWith(searchWord.getText().toLowerCase().trim())){
                my_hint.add(t);
            }
            i++;
        }
        my_hint.remove(DeleteWordSceneController.deleteW);
        hint.getItems().clear();
        hint.getItems().addAll(my_hint);

    }


    public void openAddWord() throws IOException {
        st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/AddWordScene.fxml"));
        st.setTitle("Add new word");
        st.setScene(new Scene(root, 600, 400));
        st.show();
    }

    public void openDeleteWord() throws IOException{
        st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/DeleteWordScene.fxml"));
        st.setTitle("Delete word");
        st.setScene(new Scene(root, 600, 400));
        st.show();
    }

    public void delete_handel(){
        history.getItems().remove(DeleteWordSceneController.deleteW);
    }

    public void openReplaceWord() throws IOException {
        st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/ReWordScene.fxml"));
        st.setTitle("Replace word");
        st.setScene(new Scene(root, 600, 400));
        st.show();
    }

    public void speak_word() throws Exception {
        try {
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            synthesizer.allocate();

            synthesizer.resume();

            synthesizer.speakPlainText(
                    searchWord.getText(), null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openGoogleApi() throws IOException{
        st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/ApiScene.fxml"));
        st.setTitle("Google api");
        st.setScene(new Scene(root, 600, 400));
        st.show();
    }


}
