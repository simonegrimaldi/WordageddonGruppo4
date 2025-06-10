package IOOperation;

import java.util.List;

public interface IOTexts {
    /**
     * Restituisce una lista di testi scelti casualmente dalla cartella associata alla difficoltà.
     * - "facile": 1 testo
     * - "media": 2 testi
     * - "difficile": 3 testi
     */
    List<String> cercaTesti(String difficolta);
}
