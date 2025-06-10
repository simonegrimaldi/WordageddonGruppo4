package model; //quiz

import IOOperation.IOTextsImpl;
import java.util.List;

/**
 * Rappresenta un quiz contenente una lista di testi e una lista di domande.
 */
public class Quiz {
    private List<String> testi;
    private List<Question> domande;
    private final String username;
    private IOTextsImpl text;
    
    public Quiz(List<String> testi, List<Question> domande, String username) {
        this.testi = testi;
        this.domande = domande;
        this.username = username;
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

    public void mostra() {
        for (Question q : domande) {
            System.out.println(q.getQuestionText());
        }
    }
}