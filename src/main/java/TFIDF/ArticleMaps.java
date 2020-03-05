package TFIDF;

import java.util.HashMap;
import java.util.List;

public class ArticleMaps {
    private HashMap<String, Double> termFrequencyMap;
    private HashMap<String, Integer> wordCountMap;
    private HashMap<String, Double> tfIdfValueMap;
    private List<String> tokensList;


    public List<String> getTokensList() {
        return tokensList;
    }

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }


    public HashMap<String, Double> getTermFrequencyMap() {
        return termFrequencyMap;
    }

    public void setTermFrequencyMap(HashMap<String, Double> termFrequencyMap) {
        this.termFrequencyMap = termFrequencyMap;
    }

    public HashMap<String, Integer> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(HashMap<String, Integer> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }


    public HashMap<String, Double> getTfIdfValueMap() {
        return tfIdfValueMap;
    }

    public void setTfIdfValueMap(HashMap<String, Double> tfIdfValueMap) {
        this.tfIdfValueMap = tfIdfValueMap;
    }
}


