package IOOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;

/**
 * @class IOFileImpl
 *
 * @brief Implementazione concreta per l'interfaccia 'IOFile' per il salvataggio
 * e il caricamento dei file
 *
 * La classe fornisce le operazioni concrete per il contenuto di un file di
 * testo e per salvare un file in una destinazione specificata.
 *
 */
public class IOFileImpl implements IOFile {

    /**
     * @brief Carica il contenuto di uno o più file di testo
     *
     * Il metodo apre ogni file presente nell'elenco 'filePaths', legge il
     * contenuto di ogni file e restituisce il risultato complessivo concatenato
     * in una stringa.
     *
     * Nel caso in cui uno dei file non viene trovato, verrà sollevata un
     * eccezione e stampato un messaggio che ci avvisa che il file non è stato
     * trovato.
     *
     * @param filename lista dei file da caricare
     * @return Il contenuto del file selezionato sotto forma di unica Stringa
     */
    @Override
    public String loadFile(List<String> filePaths) {
        System.out.println(filePaths);
        StringBuilder contenutoComplessivo = new StringBuilder();

        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            contenutoComplessivo.append("Testo ").append(i + 1).append(": ").append(file.getName()).append("\n");

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    contenutoComplessivo.append(scanner.nextLine()).append("\n");
                }
            } catch (FileNotFoundException e) {
                System.err.println(" File non trovato: " + file.getAbsolutePath());
            }

            contenutoComplessivo.append("\n\n");
        }

        return contenutoComplessivo.toString();
    }

    /**
     * @brief Salva il contenuto di un file selezionato in uno specifico file di
     * destinazione
     *
     * In particolare, il metodo copia il contenuto del 'selectedFile' nel file
     * di destinazione 'destFile'. Nel caso in cui il file di destinazione
     * esista già, allora questo verrà sovrascritto.
     *
     * @param destFile il file di destinazione dove il contenuto verrà copiato
     * @param selectedFile il file di origine da cui copiare il contenuto
     * @return 'true' se il file è stato salvato con successo, altrimenti
     * 'false'
     */
    @Override
    public boolean saveFile(File destFile, File selectedFile) {
        try {
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
