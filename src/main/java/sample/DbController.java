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
            ResultSet rs=st.executeQuery("SELECT word FROM 'av' ORDER BY word");//ASC
            while(rs.next()){
                String e=rs.getString(1);
                myList.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myList;
    }

    public ArrayList<String> gethint(String key){
        ArrayList<String> myList =new ArrayList<>();
        Connection conn =DbConnection.getConnection();
        try {
            Statement st= conn.createStatement();
            ResultSet rs=st.executeQuery("SELECT DISTINCT word FROM 'av' " +
                    "WHERE UPPER(word) LIKE \""+key+"\"");//ASC
            while(rs.next()){
                String e=rs.getString(1);
                myList.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            conn.close();
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
            if(!w.toUpperCase().equals("WORD")){
                ResultSet rs=st.executeQuery("SELECT html FROM 'av' WHERE UPPER(word)=\""+w.toUpperCase()+"\" LIMIT 1");
                if(rs.next()){
                    tmp=rs.getString(1);
                    conn.close();
                    return tmp;
                }
            }
            ResultSet rs=st.executeQuery("SELECT html FROM 'av' WHERE UPPER(word)=\'"+w.toUpperCase()+"\' LIMIT 1");
            if(rs.next()){
                tmp=rs.getString(1);
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
        if(w.toUpperCase().equals("DESCRIPTION")
            || w.toUpperCase().equals("ID")
                || w.toUpperCase().equals("WORD")
                || w.toUpperCase().equals("HTML")
                || w.toUpperCase().equals("PRONOUNCE")){
            return true;
        }
        Connection conn =DbConnection.getConnection();
        String tmp="";
        Statement st= null;
        try {
            st = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet rs=st.executeQuery("SELECT word FROM 'av' WHERE UPPER(word)=\'"+w.toUpperCase()+"\' LIMIT 1");
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
