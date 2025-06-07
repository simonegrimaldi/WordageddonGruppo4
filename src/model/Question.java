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
    private String question;
    private List<T> options;
    private T answer;

    public String getQuestionText() {
        return question;
    }

    public List<T> getOptions() {
        return options;
    }

    public T getAnswer() {
        return answer;
    }
}
    

