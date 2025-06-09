package IOOperation;

import model.AnalysisImpl;

public interface IOAnalysis {

    AnalysisImpl loadAnalysis(String filename);

    void saveAnalysis(AnalysisImpl a, String filename);
}
