/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

/**
 *
 * @author simonegrimaldi
 */
public interface ChangeView {
    public void goHome(String username);
    public void goAdminPanel(String superUsername);
    public void goSignUp();
    public void goLogIn();
    public void goReading(String username);
    public void goQuestion(String username);
}
