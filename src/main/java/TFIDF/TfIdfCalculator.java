package TFIDF;

import document.processing.Document_reader;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


public class TfIdfCalculator {

    SortedSet<String> wordList = new TreeSet(String.CASE_INSENSITIVE_ORDER);
    TreeMap<String, Integer> globalWordCount = new TreeMap<>();
    ArrayList<TfIdfPair> tdidfValuePair = new ArrayList<>();

    public static <K, V extends Comparable<? super V>>
    SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }



    // calculates term frequency
    // calculates inverse doc frequency
    // calculate tf-idf
    // generates word cloud with the terms with high tf-idf

    public void startCalculation(List<Article> articles, int count) {
        int noOfDocs = articles.size();

        ArticleMaps[] articleMaps = new ArticleMaps[noOfDocs];

        // term frequency
        int i = 0;
        for (Article art : articles) {
            articleMaps[i] = new ArticleMaps();
            HashMap<String, Integer> wordCount = this.wordCounter(art.getTokens());
            List<String> tokens = Document_reader.process(art.getTokens());

            articleMaps[i].setTokensList(tokens);
            art.setTokensList(tokens);

            articleMaps[i].setWordCountMap(wordCount);

            HashMap<String, Double> termFrequency = this.calculateTermFrequency(articleMaps[i].getWordCountMap());
            articleMaps[i].setTermFrequencyMap(termFrequency);
            i++;
        }


        // inverse doc fre
        HashMap<String, Double> inverseDocFreqMap = this.calculateInverseDocFrequency(articleMaps);

        this.caluclateTfIdfValues(articles, inverseDocFreqMap, articleMaps);

        this.generateWordCloud(count);

    }



    public void letsTrytheOtherWay(List<Article> articles) {

    }


    // this method has two maps - counter, globalWordCount
    // count - counts the no of occurences of the word in that document
    // globalWordCount - counts the no of occurences of the word in all documents
    // wordList - it contains all the unique words from all the documents
    public HashMap<String, Integer> wordCounter(String data) {
        HashMap<String, Integer> counter = new HashMap<String, Integer>();


            String[] words = data.split(" ");

            for (String term : words) {

                // skip the term if it is numeric or space
                if (StringUtils.isNumeric(term) || term.length() == 0) {
                    continue;
                }
                wordList.add(term);

                if (counter.containsKey(term)) {
                    counter.put(term, counter.get(term) + 1);
                } else {
                    counter.put(term, 1);
                }
                if (globalWordCount.containsKey(term)) {
                    globalWordCount.put(term, globalWordCount.get(term) + 1);
                } else {
                    globalWordCount.put(term, 1);
                }
            }

        Map<String, Integer> treeMap = new TreeMap<>(counter);
        counter = new HashMap<>(treeMap);
        return counter;
    }


    //this method calculates Term Frequency
    // tf = n/N
    // n = no of times a term appears in document d - inputMap - articleProperties[i].getWordCountMap()
    // N = total no of terms in d - sum(inputMap.values)
    public HashMap<String, Double> calculateTermFrequency(HashMap<String, Integer> inputMap) {
        HashMap<String, Double> termFreqMap = new HashMap<>();
        double sum = 0.0;

        //Get the sum of all elements in hashmap
        for (float val : inputMap.values()) {
            sum += val;
        }

        //create a new hashMap with Tf values in it.
        Iterator it = inputMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            double tf = (Integer) pair.getValue() / sum;
            termFreqMap.put((pair.getKey().toString()), tf);
        }
        return termFreqMap;
    }


    //idf = log(dN/dn)
    // dN = no of documents in dataset -articleMaps.length
    // dn = no of documents that has the term - wordCount
    public HashMap<String, Double> calculateInverseDocFrequency(ArticleMaps[] articleMaps) {

        HashMap<String, Double> InverseDocFreqMap = new HashMap<>();
        int size = articleMaps.length;
        double wordCount;
        for (String word : wordList) {
            wordCount = 0;
            for (int i = 0; i < size; i++) {
                HashMap<String, Integer> tempMap = articleMaps[i].getWordCountMap();
                if (tempMap.containsKey(word)) {
                    wordCount++;
                    continue;
                }
            }
            double temp = size / wordCount;
            double idf = 1 + Math.log(temp);
            InverseDocFreqMap.put(word, idf);
        }
        return InverseDocFreqMap;
    }



    // this method calculates tf-idf
    // tfidf = tf * idf
    public void caluclateTfIdfValues(List<Article> articles, HashMap<String, Double> inverseDocFreqMap, ArticleMaps[] articleMaps) {
        int i = 0;
        for (Article art : articles) {
            double tfIdfValue = 0.0;
            double idfVal = 0.0;
            HashMap<String, Double> tf = articleMaps[i].getTermFrequencyMap();
            Iterator itTF = tf.entrySet().iterator();
            while (itTF.hasNext()) {
                Map.Entry pair = (Map.Entry) itTF.next();
                double tfVal = (Double) pair.getValue();
                String key = pair.getKey().toString();
                if (inverseDocFreqMap.containsKey(key)) {
                    idfVal = inverseDocFreqMap.get(key);
                }
                tfIdfValue = tfVal * idfVal;
                articleMaps[i].setTfIdfValueMap(new HashMap<>());
                articleMaps[i].getTfIdfValueMap().put(key, tfIdfValue);
                tdidfValuePair.add(new TfIdfPair(key, tfIdfValue));
            }
            i++;
        }
    }

    public void generateWordCloud(int count) {
        Collections.sort(tdidfValuePair);
        List<String> field1List = tdidfValuePair.stream().limit(count).map(pair -> pair.getKey()).collect(Collectors.toList());
        WordCloudGenerator wordCloudGenerator = new WordCloudGenerator();
        wordCloudGenerator.generateWordCloud(field1List);

    }
}
