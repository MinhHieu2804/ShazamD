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

    @FXML
    ListView<String> hint;

    @FXML
    Button speak;

    static Stage st;
    static ArrayList<String> allWords;
    static ArrayList<String> my_hint;
    static DbController myDb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InitAll();
    }

    public void InitAll(){
        history.setVisible(false);
        myDb=new DbController();
        allWords =new ArrayList<>();
        my_hint=new ArrayList<>();
        allWords=myDb.getAllWords();
        hint.getItems().addAll(allWords);
    }

    public void search_Word(){
        hide_history();
        WebEngine webEngine = meaning.getEngine();
        String w = searchWord.getText();
        if (!w.equals("")) {
            String tmp=myDb.searchWord(w);
            webEngine.loadContent(tmp);
            if(!tmp.equals("<html>NO WORD<br>add word to the dictionary if you want</html>")){
                history.getItems().remove(w);
                history.getItems().add(0, w);
            }
        }
        else{
            String t="<html>Please enter word</html>";
            webEngine.loadContent(t);
        }
    }

    public void show_history(){
        if(!history.getItems().isEmpty()){
            history.setVisible(true);
        }

    }

    public void hide_history(){
        history.setVisible(false);
        show_hint();
        if(searchWord.getText().equals("")){
            show_history();
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
        if(!history.getSelectionModel().isEmpty()){
            String w = history.getSelectionModel().getSelectedItem();
            searchWord.setText(w);
            history.getItems().remove(w);
            history.getItems().add(0, w);
            w= myDb.searchWord(w.toUpperCase());
            webEngine.loadContent(w);
            history.scrollTo(0);
            history.getSelectionModel().clearSelection();
            history.setVisible(false);
        }
    }

    public void hint_f(){
        WebEngine webEngine = meaning.getEngine();
        history.setVisible(false);
        if(!hint.getSelectionModel().isEmpty()){
            String w = hint.getSelectionModel().getSelectedItem();
            searchWord.setText(w);
            history.getItems().remove(w);
            history.getItems().add(0, w);
            w= myDb.searchWord(w);
            webEngine.loadContent(w);
        }
    }

    public void show_hint(){
        hint.scrollTo(0);
        if(searchWord.getText().equals("")){
            hint.getItems().clear();
            hint.getItems().addAll(allWords);
        }
        my_hint=new ArrayList<>();
        String t=searchWord.getText().toUpperCase();
        my_hint=myDb.gethint(t+"%");
        if(my_hint.isEmpty()){
            String w=searchWord.getText().toUpperCase();
            int n=w.length();
            String key="";
            for(int v=0;v<n;v++){
                key=key+w.charAt(v)+'%';
            }
            my_hint=myDb.gethint(key);
        }
            hint.getItems().clear();
            hint.getItems().addAll(my_hint);
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

    public void openReplaceWord() throws IOException {
        st = new Stage();
        st.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/ReWordScene.fxml"));
        st.setTitle("Replace word");
        st.setScene(new Scene(root, 600, 400));
        st.show();
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
