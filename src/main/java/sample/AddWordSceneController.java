package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddWordSceneController {
    @FXML
    private TextField addWord_word;

    @FXML
    private TextField addWord_meaning;

    @FXML
    public Button back;

    public void addWord(){
        if(addWord_word.getText().equals("")){
            show_mess(0);
        }
        else if(addWord_meaning.getText().equals("")){
            show_mess(3);
        }
        else if(!Controller.myDictionary.containsKey(addWord_word.getText())){
            Controller.myDictionary.put(addWord_word.getText(),addWord_meaning.getText());
            Controller.allWords.add(addWord_word.getText());
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

