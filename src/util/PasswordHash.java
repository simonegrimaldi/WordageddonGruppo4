package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @class PasswordHash
 * @brief Classe per la gestione della cifratura delle password.
 *
 * La classe fornisce metodi per hashare una password utilizzando l'algoritmo
 * SHA-256 e per verificare se una password inserita corrisponde a una password
 * hashata precedentemente salvata.
 */
public class PasswordHash {

    /**
     * @brief Cifra la password utilizzando l'algoritmo SHA-256.
     *
     * Questo metodo prende una password in chiaro e restituisce una versione
     * hashata della stessa utilizzando l'algoritmo di cifratura SHA-256.
     *
     * @param password La password in chiaro da cifrare.
     * @return La password cifrata in formato esadecimale, o null se si verifica
     * un errore.
     * @throws NoSuchAlgorithmException Se l'algoritmo di hashing richiesto non
     * è disponibile.
     * @post La password viene convertita in un formato sicuro (hash).
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @brief Verifica se la password inserita corrisponde alla password
     * hashata.
     *
     * Questo metodo confronta la password fornita con la versione hashata per
     * determinare se sono uguali.
     *
     * @param password La password inserita dall'utente.
     * @param hashedPassword La password precedentemente hashata da confrontare.
     * @return true se la password fornita corrisponde alla versione hashata,
     * false altrimenti.
     * @post Restituisce true se le password corrispondono, false altrimenti.
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
