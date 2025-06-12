package IOOperation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @class IOTextsImpl
 *
 * @brief Implementazione concreta per l'interfaccio 'IOTexts' per la ricerca
 * dei testi in base alla difficoltà.
 *
 * La classe fornisce un meccanismo per il recupero di un certo numero di testi
 * a seconda della difficoltà seleszionata, a partire da una cartella specifica.
 * In particolare la selezione dei testi avviene da una cartella che corrisponde
 * alla difficoltà selezionata dal giocatore.
 *
 */
public class IOTextsImpl implements IOTexts {

    /**
     * @brief Restituisce una lista di testi scelti casualmente dalla cartella
     * associata alla difficoltà.
     *
     * A seconda della difficoltà scelta, il metodo restituisce un numero di
     * testi differenti, prelevati dalla cartella corrispondente alla
     * difficoltà. Nel caso in cui non sia possibile riconoscere la difficoltà
     * verrà stampato un messaggio di errore. Nel caso in cui la cartella
     * corrispondente alla difficoltà non esiste verrà restituito null.
     *
     * @param difficulty la difficoltà secondo la quale recuperare i
     * testi(facile, medio, difficile)
     * @return Una lista di stringhe che contiene i percorsi associati ai file
     * che sono stati scelti.
     */
    @Override
    public List<String> cercaTesti(String difficulty) {
        int numTesti = 0;
        switch (difficulty.toLowerCase()) {
            case "easy":
                numTesti = 1;
                break;
            case "medium":
                numTesti = 2;
                break;
            case "hard":
                numTesti = 3;
                break;
            default:
                return null;
        }

        String path = Paths.get(System.getProperty("user.dir"), "testi", difficulty.toLowerCase()).toString();
        if (!Files.exists(Paths.get(path))) {
            return null;
        }

        File folder = new File(path);

        List<String> fileNames = Arrays.stream(folder.list())
                .filter(name -> name.endsWith(".txt"))
                .collect(Collectors.toList());
        if (fileNames.isEmpty() || fileNames.size() < numTesti) {
            return null;
        }
        List<String> risultati = new ArrayList<>();
        if (fileNames != null) {
            for (String name : fileNames) {
                risultati.add(path + "/" + name);
            }
        }
        Collections.shuffle(risultati);
        return risultati.subList(0, Math.min(numTesti, risultati.size()));
    }

}
