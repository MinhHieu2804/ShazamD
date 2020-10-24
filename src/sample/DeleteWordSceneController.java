package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

public class DeleteWordSceneController {

    @FXML
    private
    TextField deleteWord_word;

    static String deleteW;

    @FXML
    void deleteWord() throws IOException {
        String w=deleteWord_word.getText();
        if(Controller.myDictionary.containsKey(w)){
            Controller.myDictionary.remove(w);
            Controller.allWords.remove(w);
            show_mess(1);
            deleteW=w;
        }
        else show_mess(2);
    }

    public void show_mess(int n){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(n==2){
            alert.setTitle("Failed");
            alert.setContentText("This word is not in the dictionary");
        }
        else{
            alert.setTitle("Done");
            alert.setContentText("Word deleted!");
        }
        alert.showAndWait();
    }

    public void back_f(){
        Controller.st.close();
    }


}

