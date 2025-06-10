package IOOperation;

import model.Analysis;


public interface IOAnalysis {

    public Analysis loadAnalysis(String filename);

    public void saveAnalysis(Analysis a, String filename);
}
