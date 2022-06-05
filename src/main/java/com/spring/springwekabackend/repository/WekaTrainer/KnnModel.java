package com.spring.springwekabackend.repository.WekaTrainer;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class KnnModel extends DataModel {
    IBk iBk;
    Evaluation evaluation;

    public KnnModel(String filename, String model_option, String data_option) throws Exception {
        super(filename, model_option, data_option);
    }

    public void buildKNN(String inputDiagnose) throws Exception {
//        setTrainSet(fileName);
        this.setTrainSetV2(inputDiagnose);
        this.trainSet.setClassIndex(this.trainSet.numAttributes() - 1);
        this.iBk = new IBk(8);
        iBk.setOptions(model_options);
        iBk.buildClassifier(trainSet);
    }

    public void evaluationKNN() throws Exception {
        this.setTestSetV2();
        this.testSet.setClassIndex(this.testSet.numAttributes() - 1);
        Debug.Random rd = new Debug.Random();
        int folds = 10;
        this.evaluation = new Evaluation(this.trainSet);
        this.evaluation.crossValidateModel(this.iBk, this.testSet, folds, rd);
//        System.out.println(this.evaluation.toSummaryString("Kết quả đánh giá\n", false));
//        System.out.println(this.evaluation.toClassDetailsString());
//        System.out.println(this.evaluation.toMatrixString());
    }

    public String predictLabel(String fileName, String fileOut) throws Exception {
        ConverterUtils.DataSource dataSource1 = new ConverterUtils.DataSource(fileName);
        Instances instances = dataSource1.getDataSet();
        String result = "";
        instances.setClassIndex(instances.numAttributes() - 1);
        for (int i = 0; i < instances.numInstances(); i++) {
            double pre = iBk.classifyInstance(instances.instance(i));
            instances.instance(i).setClassValue(pre);
            switch ((int) pre) {
                case 0:
                    System.out.println("Instance " + i + ": negative");
                    result = "Negative";
                    break;
                case 1:
                    System.out.println("Instance " + i + ": positive");
                    result = "Positive";
                    break;
                default:
                    System.out.println("Instance " + i + ": unknown");
                    result = "Unknown";
                    break;
            }
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut));
        bufferedWriter.write(instances.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();

        return result;
    }
}
