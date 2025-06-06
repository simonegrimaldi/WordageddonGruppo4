/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementation;

import dao.connection.DbConnection;
import static dao.connection.DbConnection.getConnection;
import static dao.implementation.PasswordHash.checkPassword;
import static dao.implementation.PasswordHash.hashPassword;
import dao.interfaces.DaoUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pasquy
 */
public class DaoUserImpl implements DaoUser{

   
    @Override
    public String authentication(String username, String password) {
        String password_ricevuta = null;
        String sql ="select password from utente where username=?";
        String tipo="select tipo from utente where username=?";
        try ( Connection conn = getConnection();
               PreparedStatement ps=conn.prepareStatement(sql);
                ){
        ps.setString(1,username);
        
          ResultSet rs = ps.executeQuery();
            password_ricevuta=rs.getString("password");
         } catch (SQLException ex) {
            Logger.getLogger(DaoUserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //se la password è corretta ritorna il tipo di utente in modo da favorire
        //i cambi di view, altrimenti verrà ritornato null
        if(checkPassword(password,password_ricevuta))
            return tipo;
        else
            return null;
    }

    @Override
    public boolean registration(String username, String password) {
        String password_criptata=hashPassword(password);
        boolean esecuzione=false;
        String sql_username="";
        String Username_ricevuto=null;
        try(Connection conn = getConnection();
               PreparedStatement ps=conn.prepareStatement(sql_username);
                ){
            ResultSet rs=ps.executeQuery();
           Username_ricevuto=rs.getString("username").toLowerCase();
        } catch (SQLException ex) {
            Logger.getLogger(DaoUserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(username.toLowerCase().equals(Username_ricevuto)){
             esecuzione=false;
         }else{
             esecuzione=true;
             String sql_insert ="insert into utente(username,password,tipo) values(?,?,?)";
         try ( Connection conn = getConnection();
               PreparedStatement ps=conn.prepareStatement(sql_insert);
                ){
             ps.setString(1,username);
             ps.setString(2,password_criptata);
             ps.setString(3,"User");
             ps.executeUpdate();
    }   catch (SQLException ex) {
            Logger.getLogger(DaoUserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
        return esecuzione;
    }
    
}
