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

    @FXML
    void deleteWord() throws IOException {
        String t=deleteWord_word.getText();
        if(!t.equals("")){
            if(Controller.myDb.has(t)){
                Controller.myDb.deleteWord(t);
                Controller.allWords.clear();
                Controller.allWords=Controller.myDb.getAllWords();
                show_mess(1);
                Controller.my_hint.clear();
                Controller.my_hint.addAll(Controller.allWords);
            }
            else show_mess(2);
        }
        else{
            show_mess(3);
        }

    }

    public void show_mess(int n){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if(n==2){
            alert.setTitle("Failed");
            alert.setContentText("This word is not in the dictionary");
        }
        else if(n==3){
            alert.setTitle("Failed");
            alert.setContentText("Please enter word");
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

