/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.Random;

/**
 *
 * @author corry
 */
public class DocumentQuestion extends Question<String>{
    /*Quale parola compare più spesso nel
n-esimo documento?*/
    String question;
    String answer;
    List<String> options;
    
    public DocumentQuestion(List<Analysis> analyses){
            int numero = new Random().nextInt(4);
            this.question = "Qual è la parola più frequente nel documento numero " + (numero + 1);

    }
}
