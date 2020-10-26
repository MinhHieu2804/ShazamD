package sample;

import com.google.api.GoogleAPI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;


public class ApiController {
    @FXML
    TextField word;

    @FXML
    WebView target;

    public void trans(){
//        String w=word.getText();
//        String v= GoogleTranslate.translate("vi",w);
    }

    public void close(){
        Controller.st.close();
    }
}
