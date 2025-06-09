package model; //quiz

import java.util.List;

/**
 * Rappresenta un quiz contenente una lista di testi e una lista di domande.
 */
public class Quiz {
    private List<String> testi;
    private List<Question> domande;

    public Quiz(List<String> testi, List<Question> domande) {
        this.testi = testi;
        this.domande = domande;
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
