package IOOperation;

import java.util.List;

/**
 * @interface IOTexts
 *
 * @brief Interfaccia per la gestione delle operazioni di recupero dei testi.
 *
 * L'interfaccia definisce un metodo per cercare e restituire una lista di testi
 * associati ad una specifica difficoltà (facile, media, difficile). La scelta
 * dei testi all'interno della cartella corrispondende alla difficoltà sarà
 * completamente casuale
 */
public interface IOTexts {

    /**
     * @brief Restituisce una lista di testi scelti casualmente dalla cartella
     * associata alla difficoltà. Il numero di testi scelti dipende dalla
     * difficoltà: - "facile": 1 testo - "media": 2 testi - "difficile": 3 testi
     *
     * I testi verranno letti dalla cartella corrispondente alla difficoltà e
     * restituiti sotto forma di stringa concatenata.
     *
     * @param difficolta la difficoltà secondo la quale recuperare i
     * testi(facile, medio, difficile)
     * @return Una lista di stringhe che contiene i percorsi associati ai file
     * che sono stati scelti.
     */
    public List<String> cercaTesti(String difficolta);
}
