package sample;

import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class ApiController {
    @FXML
    TextField word;

    @FXML
    WebView target;

    public boolean network(){
        try{
            final URL url= new URL("https://www.google.com");
            final URLConnection conn =url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            return false;
        }
    }

    public void trans() throws IOException {
        if(network()){
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
        else {
            show_mess();
        }
    }

    public void show_mess(){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Failed");
        alert.setContentText("No internet connection");
        alert.showAndWait();
    }

    public void close(){
        Controller.st.close();
    }
}
