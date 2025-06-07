/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author pasquy
 */
public class PasswordHash {
    // Funzione per generare un hash della password con SHA-256
    public static String hashPassword(String password) {
        try {
            // Crea un'istanza di MessageDigest per SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Converte la password in un array di byte
            byte[] hashedBytes = digest.digest(password.getBytes());
            
            // Converte i byte in una stringa esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Restituisce l'hash come stringa esadecimale
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Funzione per verificare se la password inserita corrisponde all'hash salvato
    public static boolean checkPassword(String password, String hashedPassword) {
        // Genera l'hash della password inserita e confronta con l'hash salvato
        return hashPassword(password).equals(hashedPassword);
    }
}
