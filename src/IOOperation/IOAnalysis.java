package IOOperation;

import model.Analysis;


public interface IOAnalysis {

    Analysis loadAnalysis(String filename);

    void saveAnalysis(Analysis a, String filename);
}
