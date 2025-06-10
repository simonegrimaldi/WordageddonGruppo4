package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @class FrequencyQuestion
 * @brief Domanda: "Quante volte compare una certa parola in un documento?"
 */
public class FrequencyQuestion extends Question<Integer> {
    

    /**
     * Costruisce una domanda di frequenza basata su un'analisi.
     * @param analysis L'analisi da cui estrarre la parola e la frequenza.
     */
    public FrequencyQuestion(AnalysisImpl analysis) {
        String p = analysis.getRandom();

        this.question = "Quante volte compare la parola \"" + p + "\"?";
        this.answer = analysis.frequency(p);
        this.options = generateOptions(answer);
    }

    /**
     * Genera opzioni multiple per la risposta.
     * Include la risposta corretta + 3 risposte plausibili errate.
     *
     * @param correctAnswer La risposta corretta.
     * @return Lista di 4 opzioni (una corretta e tre errate).
     */
    private List<Integer> generateOptions(int correctAnswer) {
        List<Integer> options = new ArrayList<>();
        Random rand = new Random();

        options.add(correctAnswer);
        System.out.println("numero:"+options.size());
        while (options.size() < 4) {
            int wrongAnswer = correctAnswer + rand.nextInt(11) - 5; // ±5
            if (wrongAnswer >= 0 && !options.contains(wrongAnswer)) {
                options.add(wrongAnswer);
            }
        }

        Collections.shuffle(options);
        return options;
    }
    
    
}
