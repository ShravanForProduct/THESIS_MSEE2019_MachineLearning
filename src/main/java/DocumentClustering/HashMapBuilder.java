package DocumentClustering;

import TFIDF.Article;
import TFIDF.TfIdfCalculator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toMap;

/**
 * @date 16/08/2016
 * @author Debashish Chakraborty
 *
 * Influenced by UnigramBuilder by Scott Sanner
 *
 * Creates a dictionary of file as keys to a dictionary of term with its frequencies
 */
public class HashMapBuilder {

    public static Map<String, Term> termMap;

    // Build dictionary of {File : {Term: Frequency}}
//    public static Map<String, Map> hashMapBuilder (String directory, boolean ignore_stop_words){
//        // Map of terms and their frequencies in each file
//        HashMap<String, Map> fileTermHashMap = new HashMap<>();
//        termMap = new HashMap<>();
//
//
//        // Get list of data files
//        ArrayList<File> files = FileFinder.GetAllFiles(directory, "txt", true);
//        System.out.println("Building HashMap representation of documents with terms and term frequency...\n");
//        System.out.println("Found " + files.size() + " documents.");
//
//        int file_count = 0;
//        for (File f : files) {
//            String file_content = ModDocUtils.ReadFile(f);
//            List<String> tokenList = ModDocUtils.tokenize(file_content, ignore_stop_words);
//
//            Map<String, Integer> termFreqMap;
//            termFreqMap = tokenList.parallelStream().flatMap(s -> Arrays.stream(s.split(" "))).
//                    collect(toConcurrentMap(String::toLowerCase, (String w) -> 1, Integer::sum));
//
//            // take the top 150
//            termFreqMap.entrySet().stream()
//                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//                    .limit(150);
//
//            fileTermHashMap.put(f.getName(), termFreqMap);
//
//            // Only make a set of the top x number of words
//            Set<String> tokenSet = termFreqMap.keySet();
//            for (String token : tokenSet) {
//                Term termToken = new Term(token);
//                if (!termMap.containsKey(token))
//                    termMap.put(token, termToken);
//                termMap.get(token).freqInDoc += 1;
//            }
//
//        }
//
//        System.out.println("\nHashMap representation built.\n");
//        return fileTermHashMap;
//    }


    // Build dictionary of {File : {Term: Frequency}}
    public static Map<String, Map> hashMapBuilder (List<Article> docs, boolean ignore_stop_words){
        // Map of terms and their frequencies in each file
        HashMap<String, Map> docTermHashMap = new HashMap<>();
        termMap = new HashMap<>();


        // Get list of data docs
//        ArrayList<File> docs = FileFinder.GetAllFiles(directory, "txt", true);
        System.out.println("Building HashMap representation of documents with terms and term frequency...\n");
        System.out.println("Found " + docs.size() + " documents.");

        int file_count = 0;
        for (Article article : docs) {
            String doc_content = ModDocUtils.ReadDocument(article);
            List<String> tokenList = ModDocUtils.tokenize(doc_content, ignore_stop_words);

            Map<String, Integer> termFreqMap;
            termFreqMap = tokenList.parallelStream().flatMap(s -> Arrays.stream(s.split(" "))).
                    collect(toConcurrentMap(String::toLowerCase, (String w) -> 1, Integer::sum));

            // take the top 150
            termFreqMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(150);


            List<String> topWords = termFreqMap .entrySet() .stream() .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(4)
                    .map(pair -> pair.getKey())
                    .collect(Collectors.toList());


            docTermHashMap.put(topWords.toString(), termFreqMap);

            // Only make a set of the top x number of words
            Set<String> tokenSet = termFreqMap.keySet();
            for (String token : tokenSet) {
                Term termToken = new Term(token);
                if (!termMap.containsKey(token))
                    termMap.put(token, termToken);
                termMap.get(token).freqInDoc += 1;
            }

        }

        System.out.println("\nHashMap representation built.\n");
        return docTermHashMap;
    }


//    public static void main(String[] args) {
//        String path = "data/blog_data_test/";
//        Map<String, Map> hash = hashMapBuilder(path, true);
//        System.out.println(hash.size());
//    }


}
