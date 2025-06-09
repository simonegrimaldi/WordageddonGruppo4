/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author pasquy
 */
public class IOTextsImpl implements IOTexts {

    @Override
    public List<String> cercaTesti(String difficulty) {
        int numTesti = 0;
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
                return null;
        }
        
        String path = Paths.get(System.getProperty("user.dir"), "testi", difficulty.toLowerCase()).toString();
        File folder = new File(path);
        String[] fileNames = folder.list();

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