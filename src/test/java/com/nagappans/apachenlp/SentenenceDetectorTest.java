package com.nagappans.apachenlp;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;


public class SentenenceDetectorTest {
    @Test
    public void givenEnglishModel_whenDetect_thenSentencesAreDetected()
            throws Exception {

        String paragraph = "This is a statement. This is another statement."
                + " Now is an abstract word for time, "
                + "that is always flying. And my email address is google@gmail.com.";

        InputStream is = getClass().getResourceAsStream("/models/en-sent.bin");
        SentenceModel model = new SentenceModel(is);

        SentenceDetector sdetector = new SentenceDetectorME(model);

        String sentences[] = sdetector.sentDetect(paragraph);


        Assert.assertArrayEquals("Sentences detected successfully", sentences, new String[]{
                "This is a statement.",
                "This is another statement.",
                "Now is an abstract word for time, that is always flying.",
                "And my email address is google@gmail.com."});
    }
}
