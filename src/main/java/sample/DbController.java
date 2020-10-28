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
            ResultSet rs=st.executeQuery("SELECT DISTINCT word FROM 'av' ORDER BY word ");//ASC
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
            ResultSet rs=st.executeQuery("SELECT * FROM 'av' WHERE UPPER(word)=\""+w.toUpperCase()+"\" LIMIT 1");
            if(rs.next()){
                tmp=rs.getString(3);
                conn.close();
                return tmp;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "<html>NO WORD<br>add word to the dictionary if you want</html>";
    }

    public boolean has(String w){
        Connection conn =DbConnection.getConnection();
        String tmp="";
        Statement st= null;
        try {
            st = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet rs=st.executeQuery("SELECT * FROM 'av' WHERE UPPER(word)=\""+w.toUpperCase()+"\" LIMIT 1");
            if(rs.next()){
                conn.close();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void addWord(String a,String b){
        Connection conn =DbConnection.getConnection();
        try {
            Statement st=conn.createStatement();
            st.execute("INSERT INTO 'av'(word,html) VALUES(\'"+a+"\',\'"+b+"\')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void replaceWord(String a,String b){
//        Connection conn =DbConnection.getConnection();
//        try {
//            Statement st=conn.createStatement();
//            st.execute("UPDATE 'av' SET word=\'"+a+"\',html=\'"+b+"\' WHERE UPPER(word)=\'"+a.toUpperCase()+"\'");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        deleteWord(a);
        addWord(a,b);
    }

    public void deleteWord(String a){
        Connection conn =DbConnection.getConnection();
        try {
            Statement st=conn.createStatement();
            st.execute("DELETE FROM 'av' WHERE UPPER(word)=\'"+a.toUpperCase()+"\' OR word=\'"+a+"\'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
