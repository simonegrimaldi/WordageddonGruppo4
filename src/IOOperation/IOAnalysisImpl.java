/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOOperation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Analysis;
import model.AnalysisImpl;

/**
 *
 * @author simonegrimaldi
 */
public class IOAnalysisImpl implements IOAnalysis{
    public void saveAnalysis (Analysis a,String filename) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
        oos.writeObject(a);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public AnalysisImpl loadAnalysis (String filename){
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (AnalysisImpl) ois.readObject();
        } catch (ClassNotFoundException | IOException ex){
                ex.printStackTrace();
                }
       return null; 
    }
}