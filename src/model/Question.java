/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author corry
 */
public class Question {
    
    private String question;
    private List<Integer> options;
    private int answer;
    
    public Question(Analysis analysis){
        String p = analysis.getRandom();
        this.question = "Quante volte compare la parola " + p + "?";
        this.answer = analysis.frequency(p);
        this.options = generateOptions(answer); 
    }
     private List<Integer> generateOptions(int correctAnswer) {
        
        List<Integer> options = new ArrayList<>();
        Random rand = new Random();
        
        
        options.add(correctAnswer);
        
        
        for (int i = 0; i < 3; i++) {
            int wrongAnswer;
            do {
                wrongAnswer = correctAnswer + rand.nextInt(11) - 5; // range: answer ±5
            } while (wrongAnswer < 0 || options.contains(wrongAnswer));
            
            options.add(wrongAnswer);
        }
        
        
        Collections.shuffle(options);
        
        return options;
    }

       
    }
    

