package com.spring.springwekabackend.repository.WekaTrainer;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class DataModel {
    private static final String PATH = "D:\\FINAL YEARS\\DEEP LEARNING\\spring-weka-backend\\src\\main\\resources\\static\\";
    private static final String RAW_TEST_ARFF = PATH + "raw_test.arff";
    private static final String RAW_TRAIN_ARFF = PATH + "raw_train.arff";
    private static final String UN_LABEL = PATH + "un_label.arff";
    private static final String UN_LABEL_FORM = PATH + "form.arff";
    private static final String VERIFY = PATH + "verify.arff";

    Instances dataset;
    Instances testSet;
    Instances trainSet;
    String[] data_options;
    String[] model_options;
    ConverterUtils.DataSource dataSource;

    public DataModel() {
    }

    public DataModel(String filename, String model_option, String data_option) throws Exception {
        if (!filename.isEmpty()) {
            this.dataSource = new ConverterUtils.DataSource(filename);
            this.dataset = this.dataSource.getDataSet();
        }
        if (model_option != null) {
            this.model_options = weka.core.Utils.splitOptions(model_option);
        }
        if (data_option != null) {
            this.data_options = weka.core.Utils.splitOptions(data_option);
        }
    }

    public void setTestSet(String fileName) throws Exception {
        ConverterUtils.DataSource testDataSource = new ConverterUtils.DataSource(fileName);
        this.testSet = testDataSource.getDataSet();
    }

    public void setTrainSet(String fileName) throws Exception {
        ConverterUtils.DataSource trainSetSource = new ConverterUtils.DataSource(fileName);
        this.trainSet = trainSetSource.getDataSet();
    }

    public Integer readFile(String fileName, Boolean shouldCountLine) throws Exception {
        Integer totalLine = 0;
        File file = new File(fileName);
        Scanner readFile = new Scanner(file);
        while (readFile.hasNextLine()) {
            String data = readFile.nextLine();
            if (shouldCountLine) {
                totalLine++;
            }
        }

        readFile.close();
        return totalLine;
    }

    public Instances RemovePercentageFilter(Double percent, Boolean isInvert) throws Exception {
        RemovePercentage rmp = new RemovePercentage();
        rmp.setPercentage(percent);
        rmp.setInvertSelection(isInvert);
        rmp.setInputFormat(this.dataset);
        return Filter.useFilter(this.dataset, rmp);
    }

    public void writeFile(String data, String fileName) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.write(data);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void setTestSetV2() throws Exception {
        Instances tempSet = this.RemovePercentageFilter(30.0, false);
        this.writeFile(tempSet.toString(), RAW_TEST_ARFF);
        this.testSet = tempSet;
    }

    public void writeUnLabelFiles(String record) throws Exception {
        FileWriter outputFile = new FileWriter(VERIFY);
        Scanner readFile = new Scanner(new File(UN_LABEL_FORM));
        while (readFile.hasNextLine()) {
            String line = readFile.nextLine();
            outputFile.append(line + "\n");
        }
        outputFile.append(record + "\n");
        readFile.close();
        outputFile.close();
    }

    public void setTrainSetV2(String inputDiagnose) throws Exception {
        System.out.println(inputDiagnose);
        Instances tempSet = this.RemovePercentageFilter(30.0, true);
        this.writeFile(tempSet.toString(), RAW_TRAIN_ARFF);
        this.trainSet = tempSet;
        this.readFile(RAW_TRAIN_ARFF, true);
        this.writeUnLabelFiles(inputDiagnose);
    }
}
