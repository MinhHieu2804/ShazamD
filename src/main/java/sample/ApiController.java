package sample;

import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;


public class ApiController {
    @FXML
    TextField word;

    @FXML
    WebView target;

    public void trans() throws IOException {
        String w=word.getText();
        WebEngine e=target.getEngine();
        if(!w.equals("")){
            String v= GoogleTranslate.translate("vi",w);
            e.loadContent(v);
        }
        else {
            e.loadContent("");
        }
    }

    public void close(){
        Controller.st.close();
    }
}
