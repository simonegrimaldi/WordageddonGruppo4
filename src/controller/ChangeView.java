/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

/**
 * L'interfaccia {@code ChangeView} definisce le operazioni di navigazione tra
 * le varie view dell'applicazione JavaFX.
 * <p>
 * È utilizzata per applicare il principio di Inversione delle Dipendenze
 * (Dependency Inversion Principle) permettendo ai controller delle viste di
 * richiedere un'interfaccia anziché una concreta implementazione del controller
 * di navigazione ({@link ChangeViewController}).
 * </p>
 */
public interface ChangeView {

    public void goSignUp();

    public void goLogIn();

    public void goHome(String username);

    public void goAdminPanel(String superUsername);

    public void goReading(String username);

    public void goQuestion(String username);
}
