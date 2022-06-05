package com.spring.springwekabackend.repository.WekaTrainer;

public class Trainer {
    private final static String PATH = "D:\\FINAL YEARS\\DEEP LEARNING\\spring-weka-backend\\src\\main\\resources\\static\\";
    private final static String PREDICT_PATH = PATH + "diabetes_unlabel.arff";
    private final static String OUTPUT_PATH = PATH + "predict_diabetes_knn_temp.arff";
    private final static String FILE_PATH = "D:\\Weka-3-8-6\\data\\diabetes.arff";
    private static final String VERIFY = PATH + "verify.arff";
    KnnModel model;

    public String trainDataSet(String inputDiagnose) {
        KnnModel model;
        try {
            model = new KnnModel(FILE_PATH, null, null);
            model.buildKNN(inputDiagnose);
            model.evaluationKNN();
            return model.predictLabel(VERIFY, OUTPUT_PATH);
        } catch (Exception ex) {
            return "Unknown";
        }
    }
}
