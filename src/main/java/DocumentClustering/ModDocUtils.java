package DocumentClustering;

import DocumentClustering.filters.SimpleTokenizer;
import DocumentClustering.filters.SnowballStemmer;
import DocumentClustering.filters.StopWordChecker;
import TFIDF.Article;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModDocUtils {

    public static final SimpleTokenizer ST = new SimpleTokenizer();

    // Example file for the blog data
    public static final String BLOG_FILE = "data/blog_data/file_1.txt";

    public static final String SPLIT_TOKENS = "[!\"#$%&'()*+,./:;<=>?\\[\\]^`{|}~\\s]"; // missing: [_-@]

    public final static DecimalFormat DF2 = new DecimalFormat("#.##");
    public final static DecimalFormat DF3 = new DecimalFormat("#.###");

    public static String ReadFile(File file) {
        return ReadFile(file, false);
    }

    public static String ReadFile(File file, boolean keep_newline) {
        try {
            StringBuilder sb = new StringBuilder();
            java.io.BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "ISO-8859-1"));
            String line;
            if ((line = br.readLine()) != null) {
                do {
                    //System.out.println(line);
                    sb.append((sb.length() > 0 ? (keep_newline ? "\n" : " ") : "") + line);
                } while ((line = br.readLine()) != null);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            return null;
        }
    }

    /*
    Modified tokenize from original DocUtils
    */
    public static List<String> tokenize(String sent, Boolean ignore_stop_words) {
        StopWordChecker stopWordChecker = new StopWordChecker();
        List<String> result = new ArrayList<>();
        String tokens[] = sent.split(SPLIT_TOKENS);
        for (String token : tokens) {
            token = standardiseToken(token);
            if (token.length() == 0 || (ignore_stop_words && stopWordChecker.isStopWord(token))) // like UnigramBuilder
                continue;
            result.add(token);
        }
        return result;
    }

    // Modified convertToMap into Map of Object with Integer
    public static Map<Object,Integer> convertToMap(String sent) {
        Map<Object,Integer> map = new HashMap<>();
        String tokens[] = sent.split(SPLIT_TOKENS);
        for (String token : tokens) {
            token = standardiseToken(token);
            if (token.length() == 0)
                continue;
            if (map.containsKey(token))
                map.put(token, map.get(token) + 1);
            else
                map.put(token, 1);
        }
        return map;
    }

    // standardise token by stemming and replacing � character
    public static String standardiseToken (String token){
        // The SnowBall stemmer is not that good since it stems weirdly "issuing" -> "issu"
        SnowballStemmer snowballStemmer = new SnowballStemmer();
        String newToken = token.trim().toLowerCase();
        newToken = snowballStemmer.stem(newToken);
        // this � character needed to be replaced, maybe wrong encoding
        newToken = newToken.replaceAll("�", "");
        return newToken;
    }

    public static void main(String[] args) throws IOException {
        String file = "data/blog_data/file_1.txt";
        byte[] bytes = Files.readAllBytes(Paths.get(file));
        String content = new String(bytes);
        Map<Object, Integer> tokenMap = convertToMap(content);
        System.out.println(tokenMap);



    }




    ///////////////// New version ///////////////

    public static String ReadDocument(Article article) {

        StringBuilder sb = new StringBuilder();
        sb.append((article.getTitle() != null && article.getTitle().length() > 0) ? article.getTitle() : "");
        sb.append(" ");

        sb.append((article.getDescription() != null && article.getDescription().length() > 0) ? article.getDescription() : "");
        sb.append(" ");

        sb.append((article.getContent() != null && article.getContent().length() > 0) ? article.getContent() : "");
        sb.append(" ");

        return sb.toString();

    }




}
