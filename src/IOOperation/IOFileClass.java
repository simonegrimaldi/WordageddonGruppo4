/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOOperation;

import IOoperation.IOFile;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Carica il contenuto di un file di testo.
 *
 * Questo metodo apre un file di testo, legge ogni sua riga e restituisce il
 * contenuto come una stringa. Se il file non viene trovato, restituisce un
 * messaggio di errore.
 *
 * @param filename Il nome del file da caricare (senza estensione .txt).
 * @return Il contenuto del file come una stringa, o un messaggio di errore se
 * il file non è stato trovato.
 * @throws FileNotFoundException Se il file non viene trovato nel percorso
 * specificato.
 */
public class IOFileClass implements IOFile {

    @Override
    public String loadFile(String filename) {
        String contenutoFile = null;
        try (Scanner scanner = new Scanner(new FileReader(filename + ".txt"))) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            contenutoFile = sb.toString();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("ERRORE IN LOADFILE");
        }
        return contenutoFile;
    }
}
