package model; //quiz

import util.Question;
import IOOperation.IOTextsImpl;
import java.util.List;

/**
 * Rappresenta un quiz contenente una lista di testi e una lista di domande.
 */
public class Quiz {

    private List<String> testi;
    private List<Question> domande;
    private int points=0;
    private static int id=0;
    private final String username;
    private IOTextsImpl text;
    

    public Quiz(List<String> testi, List<Question> domande, String username) {
        this.testi = testi;
        this.domande = domande;
        this.username = username;
        this.id++;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getTesti() {
        return testi;
    }

    public List<Question> getDomande() {
        return domande;
    }
   

    

    public void setPoints(List<String> selectedAnswers) {
        

        for (int i = 0; i < domande.size(); i++) {
            Question q = domande.get(i);
            String selectedAnswer = selectedAnswers.get(i);

            this.points += q.valuta(selectedAnswer);
        }

        
    }
    public int getPoints(){
        return this.points;
    }
}

