package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbController {
    public ArrayList<String> getAllWords(){
        ArrayList<String> myList =new ArrayList<>();
        Connection conn =DbConnection.getConnection();
        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery("SELECT word FROM 'av'");
            while(rs.next()){
                String e=rs.getString(1);
                myList.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myList;
    }

    public String searchWord(String w){
        Connection conn =DbConnection.getConnection();
        String tmp="";
        Statement st= null;
        try {
            st = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet rs=st.executeQuery("SELECT * FROM 'av' WHERE word=\""+w+"\"");
            tmp=rs.getString(3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tmp;
    }
}
