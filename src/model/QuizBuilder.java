package model;

import util.Question;
import util.RelativeFrequencyQuestion;
import util.DocumentQuestion;
import util.FrequencyQuestion;
import IOOperation.IOTexts;
import IOOperation.IOAnalysis;
import java.util.ArrayList;
import java.util.List;

/**
 * @class QuizBuilder
 * @brief La classe costruisce un quiz in base alla difficoltà selezionata
 * sfruttando i testi e le analisi
 *
 * La classe è responsabile della creazione del quiz. Utilizza l'interfaccio
 * IOTexts per caricare i testi in base alla difficoltà e l'interfaccia
 * IOAnalysis per caricare le analisi persistenti associate ai testi.
 * Successivamente genera una lista di domande basata sulle analisi e crea un
 * oggetto {@code Quiz}
 *
 */
public class QuizBuilder {

    private IOTexts textReader;
    private IOAnalysis analysisReader;
    private String username;

    /**
     * @brief Costruttore della classe QuizBuilder
     *
     * Inizializza l'oggetto QuizBuilder con le interfacce necessarie per
     * leggere i testi e le analisi, insieme al nome utente che sta per avviare
     * il quiz
     *
     * @param textReader Interfaccia per la lettura dei testi
     * @param analysisReader Interfaccia per la lettura delle analisi
     * @param username Il nome utente che crea il quiz
     */
    public QuizBuilder(IOTexts textReader, IOAnalysis analysisReader, String username) {
        this.textReader = textReader;
        this.analysisReader = analysisReader;
        this.username = username;
    }

    /**
     * @brief Crea un quiz in base alla difficoltà selezionata
     *
     * Il metodo carica i testi associati alla difficoltà, carica le relative
     * analisi e genera una lista di domande. Infine crea un oggetto Quiz con i
     * testi e le domande
     *
     * @param difficolta la difficoltà selezionata per il quiz
     * @return un oggetto quiz contenente i testi e le domande generate, oppure
     * null se non ci sono testi
     */
    public Quiz creaQuiz(String difficolta) {
        List<String> testi = textReader.cercaTesti(difficolta);
        if (testi == null) {
            return null;
        }
        List<AnalysisImpl> analisiList = new ArrayList<>();

        for (String nomeTesto : testi) {
            String pathAnalisi = nomeTesto.replace(".txt", "Analysis.bin");
            Analysis a = analysisReader.loadAnalysis(pathAnalisi);
            if (a != null) {
                analisiList.add((AnalysisImpl) a);
            }
        }

        List<Question> domande = genera(analisiList);
        return new Quiz(testi, domande, username);
    }

    /**
     * @brief Genera una lista di domande basate sulle analisi dei testi
     *
     * Questo metodo crea domande di frequenza assoluta, frequenza relatica e
     * domande sui documenti, in base alla disponibilità e al numero di testi
     * caricati
     *
     * @param analisi lista di oggetti di tipo AnalysisImpl contenenti le
     * analisi dei testi
     * @return Una lista di domande generate
     */
    public List<Question> genera(List<AnalysisImpl> analisi) {
        List<Question> domande = new ArrayList<>();

        if (!analisi.isEmpty()) {
            domande.add(new FrequencyQuestion(analisi));
            domande.add(new FrequencyQuestion(analisi));

            if (analisi.size() == 1) {
                domande.add(new FrequencyQuestion(analisi));
                domande.add(new FrequencyQuestion(analisi));
            } else {
                domande.add(new DocumentQuestion(analisi));
                domande.add(new DocumentQuestion(analisi));
            }
            domande.add(new RelativeFrequencyQuestion(analisi));
        }
        return domande;
    }
}
