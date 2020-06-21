package com.nagappans.apachenlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.*;
import opennlp.tools.util.Span;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;



public class TokenizerDetector {

    @Test
    public void givenEnglishModel_whenTokenize_thenTokensAreDetected()
            throws Exception {

        InputStream inputStream = getClass()
                .getResourceAsStream("/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(inputStream);
        TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize("Its my first attempt to learn apache nlp tutorial.");

        String expected[] = {"Its", "my", "first", "attempt", "to", "learn", "apache", "nlp", "tutorial", "."};
        assertArrayEquals(tokens, expected);
    }

    @Test
    public void givenWhitespaceTokenizer_whenTokenize_thenTokensAreDetected()
            throws Exception {

        WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("It is my first attempt, trying to learn apache opennlp.");

        String expected[] = {"It", "is", "my", "first", "attempt,", "trying", "to", "learn", "apache", "opennlp."};
        assertArrayEquals(tokens, expected);
    }

    @Test
    public void givenSimpleTokenizer_whenTokenize_thenTokensAreDetected()
            throws Exception {

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("It is my first attempt, trying to learn apache opennlp.");

        String expected[] = {"It", "is", "my", "first", "attempt", ",", "trying", "to", "learn", "apache", "opennlp", "."};
        assertArrayEquals(tokens, expected);
    }


    @Test
    public void givenEnglishPersonModel_whenNER_thenPersonsAreDetected()
            throws Exception {

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        InputStream tokenModelIn = getClass().getResourceAsStream("/models/en-token.bin");
        TokenizerModel tokenizerModel = new TokenizerModel(tokenModelIn);
        TokenizerME tokenizerModal = new TokenizerME(tokenizerModel);
        String[] tokens = tokenizerModal
                .tokenize("Legends of the game, masters of their art –" +
                        " Muttiah Muralitharan, Imran Khan and Shane Warne are the three leading wicket-takers in " +
                        "Tests");

        InputStream inputStreamNameFinder = getClass()
                .getResourceAsStream("/models/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(
                inputStreamNameFinder);
        NameFinderME nameFinderME = new NameFinderME(model);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
        List<String> spanlist = new ArrayList<>();
        for (Span span: spans) {
            spanlist.add(span.toString());
            System.out.println(span.getType() + "  " + span.toString() + " " + span.getProb());
        }
//        String expected[] = {"[0..1) person", "[13..14) person", "[20..21) person"};
//        assertArrayEquals(spanlist.toArray(), expected);
    }

    @Test
    public void givenPOSModel_whenPOSTagging_thenPOSAreDetected()
            throws Exception {

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize("John has a sister named Penny.");

        InputStream inputStreamPOSTagger = getClass()
                .getResourceAsStream("/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(tokens);
        String expected[] = {"NNP", "VBZ", "DT", "NN", "VBN", "NNP", "."};
        assertArrayEquals(tags, expected);
    }

    @Test
    public void parts_of_speech_tagger() throws Exception {
        String sentence = "";
        InputStream tokenModelIn = getClass().getResourceAsStream("/models/en-token.bin");
        TokenizerModel tokenizerModel = new TokenizerModel(tokenModelIn);
        Tokenizer tokenizer = new TokenizerME(tokenizerModel);
        String tokens[] = tokenizer.tokenize("I am trying to tag the tokens");
        InputStream posModelIn = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(posModelIn);
        POSTaggerME posTaggerME = new POSTaggerME(posModel);
        String tags[] = posTaggerME.tag(tokens);

        double[] probs = posTaggerME.probs();

        System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
        for(int i=0;i<tokens.length;i++){
            System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]  );
        }

    }
}
