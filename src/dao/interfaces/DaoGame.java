/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import model.Game;

/**
 *
 * @author simonegrimaldi
 */
public interface DaoGame {
    public void inserisci (Game m) throws Exception;
    public String getPoints (int id) throws Exception;
}
