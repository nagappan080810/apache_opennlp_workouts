package com.nagappans.apachenlp;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class DocTrainer {
    private static final Logger log = Logger.getLogger(DocTrainer.class.getName());


    public static void main(String[] args) {
        String content[] = new String[] {
            "positive	 I love this. I like this. I really love this product. We like this.",
            "negative	 I hate this. I dislike this. We absolutely hate this. I really hate this product."};

        try {
            new DocTrainer().DocumentCategorizer(content);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void DocumentCategorizer(String[] text) throws IOException {
        File test = new File("en-doccat.bin");
        String classificationModelFilePath = test.getAbsolutePath();
        DocumentCategorizerME classificationME =
                new DocumentCategorizerME( new DoccatModel( new FileInputStream(classificationModelFilePath)));
        String[] documentContent = text;
        double[] classDistribution = classificationME.categorize(documentContent);
        String predictedCategory = classificationME.getBestCategory(classDistribution);
        System.out.println("Model prediction : " + predictedCategory);
    }
}
