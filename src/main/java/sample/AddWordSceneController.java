package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddWordSceneController {
    @FXML
    private TextField addWord_word;

    @FXML
    private TextArea addWord_meaning;

    @FXML
    public Button back;

    public void addWord(){
        if(addWord_word.getText().equals("")){
            show_mess(0);
        }
        else if(addWord_meaning.getText().equals("")){
            show_mess(3);
        }
        else if(!Controller.myDb.has(addWord_word.getText())){
            Controller.myDb.addWord(addWord_word.getText(),addWord_meaning.getText());
            Controller.allWords.clear();
            Controller.allWords = Controller.myDb.getAllWords();
            show_mess(1);
        }
        else{
            show_mess(2);
        }
        addWord_meaning.clear();
        addWord_word.clear();
    }

    public void show_mess(int n){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(n==0){
            alert.setTitle("Failed");
            alert.setContentText("Please enter word");
        }
        else if(n==1){
            alert.setTitle("Done");
            alert.setContentText("Word added!");
        }
        else if(n==2){
            alert.setTitle("Done");
            alert.setContentText("This word is already exisited");
        }
        else {
            alert.setTitle("Failed");
            alert.setContentText("Please enter word's explanation");
        }
        alert.showAndWait();
    }

    public void back_f(){
        Controller.st.close();
    }
}

