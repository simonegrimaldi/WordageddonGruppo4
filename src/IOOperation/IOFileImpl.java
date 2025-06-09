/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
public class IOFileImpl implements IOFile {

    @Override
    public String loadFile(String difficulty) {

        String basePath = Paths.get(System.getProperty("user.dir"), "testi", difficulty.toLowerCase()).toString();

        File folder = new File(basePath);
        System.out.println("📁 Cerco nella cartella: " + folder.getAbsolutePath());

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.err.println("❌ Nessun file trovato nella cartella: " + basePath);
            return "Nessun contenuto trovato!";
        }

        int numTesti;
        switch (difficulty.toLowerCase()) {
            case "facile":
                numTesti = 1;
                break;
            case "media":
                numTesti = 2;
                break;
            case "difficile":
                numTesti = 3;
                break;
            default:
                System.err.println("Difficoltà non riconosciuta: " + difficulty);
                return "Errore: difficoltà non valida.";
        }

        // Mischia e seleziona
        List<File> fileList = new ArrayList<>(Arrays.asList(files));
        Collections.shuffle(fileList);

        StringBuilder contenutoComplessivo = new StringBuilder();
        for (int i = 0; i < Math.min(numTesti, fileList.size()); i++) {
            File file = fileList.get(i);
            try (Scanner scanner = new Scanner(file)) {
                contenutoComplessivo.append("Testo ").append(i + 1).append("\n");
                while (scanner.hasNextLine()) {
                    contenutoComplessivo.append(scanner.nextLine()).append("\n");
                }
                contenutoComplessivo.append("\n").append("\n");
            } catch (FileNotFoundException e) {
                System.err.println(" File non trovato: " + file.getAbsolutePath());
            }
        }

        return contenutoComplessivo.toString();
    }

    @Override
    public boolean saveFile(File destFile,File selectedFile) {
         try {
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
         return true;
    }
}
