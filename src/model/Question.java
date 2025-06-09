/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author corry
 */
public abstract class Question<T>  {
    protected String question;
    protected List<T> options;
    protected T answer;

    public String getQuestionText() {
        return question;
    }

    public List<T> getOptions() {
        return options;
    }

    public T getAnswer() {
        return answer;
    }
    @Override
   public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(question).append("\n");

    char label = 'A';
    if (options==null){System.out.println("ciaoooodi");}
    for (T option : options) {
        sb.append(label).append(". ").append(option).append("\n");
        label++;
    }

    return sb.toString();
}
   /**
 * Verifica se la risposta dell'utente è corretta.
 * @param userAnswer la risposta data dall'utente
 * @return true se è corretta, false altrimenti
 */
public boolean answerQuestion(T userAnswer) {
    return answer.equals(userAnswer);
}


    
}
    

