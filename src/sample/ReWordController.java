package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.awt.*;

public class ReWordController {
    @FXML
    private javafx.scene.control.TextField word;

    @FXML
    private javafx.scene.control.TextField meaning;

    @FXML
    public Button back;


    public void replaceWord(){
        if(word.getText().equals("")){
            show_mess(0);
        }
        else if(meaning.getText().equals("")){
            show_mess(3);
        }
        else if(!Controller.myDictionary.containsKey(word.getText())){
            show_mess(1);
        }
        else{
            Controller.myDictionary.put(word.getText(),meaning.getText());
            show_mess(2);
        }
        meaning.clear();
        word.clear();
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
            alert.setContentText("This Word is not in the Dictionary, Add it before replace");
        }
        else if(n==2){
            alert.setTitle("Done");
            alert.setContentText("Completed");
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
