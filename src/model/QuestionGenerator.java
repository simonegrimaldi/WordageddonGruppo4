/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author corry
 */
public class QuestionGenerator {
    private Analysis analysis;
    private String question;
    private int answer;
    
    public QuestionGenerator(Analysis analysis){
        this.analysis = analysis;
    }
    public String createQuestion(int type){
        return "ciao";
    }
    
}
