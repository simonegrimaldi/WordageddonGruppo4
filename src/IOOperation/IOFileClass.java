/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
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
    public String loadFile(String difficulty) {
        Random random = new Random();
        String basePath = "../../" + difficulty;
        int numFiles = countFilesInFolder(basePath);
        int numTesti;

        // Determina quanti testi servono
        switch (difficulty.toLowerCase()) {
            case "Facile":
                numTesti = 1;
                break;
            case "Media":
                numTesti = 2;
                break;
            case "Difficile":
                numTesti = 3;
                break;
            default:
                return null;
        }

        // Genera indici unici casuali
        Set<Integer> chosenIndices = new HashSet<>();
        while (chosenIndices.size() < numTesti && numFiles > 0) {
            chosenIndices.add(random.nextInt(numFiles));
        }

        // Carica i file selezionati
        StringBuilder contenutoComplessivo = new StringBuilder();
        for (int index : chosenIndices) {
            String filePath = basePath + "/Testo" + index + ".txt";
            try (Scanner scanner = new Scanner(new FileReader(filePath))) {
                contenutoComplessivo.append("Testo n." + index + "\n");
                while (scanner.hasNextLine()) {
                    contenutoComplessivo.append(scanner.nextLine()).append("\n");
                }
                contenutoComplessivo.append("\n"); // separatore tra testi
            } catch (FileNotFoundException e) {
                System.err.println("File non trovato: " + filePath);
            }
        }

        return contenutoComplessivo.toString();
    }

    private int countFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        int fileCount = 0;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileCount++;
                }
            }
        }
        return fileCount;
    }

    @Override
    public boolean saveFile(String difficulty) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
