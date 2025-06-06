/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

/**
 *
 * @author pasquy
 */
public interface DaoUser {
    public boolean authentication(String username, String password);
    public boolean registration(String username,String password);
}
