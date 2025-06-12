package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import model.Analysis;
import model.AnalysisImpl;

/**
 * @class FrequencyQuestion
 * @brief Domanda del quiz che chiede "Quante volte compare una certa parola in
 * un documento?".
 *
 * Questa classe genera una domanda di tipo frequenza, in cui viene chiesto
 * quante volte una parola specifica appare in un dato documento. La risposta
 * corretta è la frequenza di quella parola, mentre le opzioni di risposta
 * includono la risposta corretta più 3 risposte plausibili ma errate.
 *
 */
public class FrequencyQuestion extends Question<Integer> {

    /**
     * @brief Costruisce una domanda di frequenza basata su un'analisi.
     *
     * Questo costruttore seleziona una parola casuale dal documento analizzato,
     * quindi costruisce una domanda chiedendo quante volte questa parola appare
     * nel documento. La risposta corretta è la frequenza di quella parola nel testo.
     * Inoltre, vengono generate 3 opzioni errate ma plausibili.
     *
     * @param analysis L'analisi del documento da cui verrà estratta la parola e la frequenza. 
     */
    public FrequencyQuestion(List<AnalysisImpl> analyses) {
        int numero = new Random().nextInt(analyses.size());
        AnalysisImpl selected = analyses.get(numero);
        String p = analyses.get(numero).getRandom();
        super.question = "Quante volte compare la parola \"" + p + "\" nel testo numero " + (numero+1) + "?";
        super.answer = selected.frequency(p);
        super.options = generateOptions(answer);
    }


    /**
     * @brief Genera le opzioni di risposta per la domanda.
     *
     * Questa funzione genera 4 opzioni di risposta, una delle quali è corretta
     * (la frequenza della parola), e le altre 3 sono errate ma plausibili, 
     * scelte casualmente in un intervallo attorno alla risposta corretta.
     *
     * @param correctAnswer La risposta corretta, che è la frequenza della
     * parola nel documento.
     * @return Una lista di 4 opzioni di risposta, una corretta e tre errate. 
     */
    private List<Integer> generateOptions(int correctAnswer) {
        List<Integer> options = new ArrayList<>();
        Random rand = new Random();

        options.add(correctAnswer);
        System.out.println("numero:" + options.size());
        while (options.size() < 4) {
            int wrongAnswer = correctAnswer + rand.nextInt(11) - 5; // ±5
            if (wrongAnswer >= 0 && !options.contains(wrongAnswer)) {
                options.add(wrongAnswer);
            }
        }

        Collections.shuffle(options);
        return options;
    }

    /**
     * @brief Valuta la risposta dell'utente.
     *
     * Questo metodo confronta la risposta selezionata dall'utente con la 
     * risposta corretta. Se la risposta è corretta, l'utente guadagna 10 punti,
     * altrimenti perde 3 punti.
     *
     * @param s La risposta selezionata dall'utente come stringa.
     * @return Il punteggio dell'utente (10 per risposta corretta, -3 per risposta errata, 0 se la risposta è nulla).
     * @post Restituisce il punteggio in base alla risposta dell'utente.
     */
    @Override
    public int valuta(String s) {
        if (s == "") {
            return 0;
        }
        Integer selectedAnswer = Integer.parseInt(s);

        return answerQuestion(selectedAnswer) ? 10 : -3;
    }

}
