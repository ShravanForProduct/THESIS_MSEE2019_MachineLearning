package TFIDF;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StringTokenizer {

    private static String[] stopWords =
            {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards",
                    "again", "against", "ain't", "all", "allow", "allows", "almost", "alone", "along", "already", "also",
                    "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow",
                    "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate",
                    "are", "aren't", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away",
                    "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand",
                    "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both",
                    "brief", "but", "by", "c'mon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain",
                    "certainly", "changes", "chars", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider",
                    "considering", "contain", "containing", "contains", "corresponding", "could", "couldn't", "course",
                    "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt",
                    "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else",
                    "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody",
                    "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff",
                    "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth",
                    "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes",
                    "going", "gone", "got", "gotten", "greetings", "had", "hadn't", "happens", "hardly", "has", "hasn't",
                    "have", "haven't", "having", "he", "hes", "hello", "help", "hence", "her", "here", "here's", "hereafter",
                    "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully",
                    "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in",
                    "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into",
                    "inward", "is", "isn't", "it", "itd", "it'll", "its", "its", "itself", "just", "keep", "keeps", "kept",
                    "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest",
                    "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many",
                    "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly",
                    "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs",
                    "neither", "never", "nevertheless", "new", "next", "news", "nine", "no", "nobody", "non", "none", "no-one", "nor",
                    "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok",
                    "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought",
                    "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per",
                    "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite",
                    "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively",
                    "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see",
                    "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious",
                    "seriously", "seven", "several", "shall", "she", "should", "shouldn't", "since", "six", "so", "some",
                    "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon",
                    "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take",
                    "taken", "tell", "tends", "th", "than", "thank", "thanks", "that", "that's", "that's", "the",
                    "their", "theirs", "them", "themselves", "then", "thence", "there", "there's", "thereafter", "thereby",
                    "therefore", "therein", "there's", "thereupon", "these", "they", "they'd", "they'll", "they're", "they've",
                    "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout",
                    "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try",
                    "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up",
                    "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via",
                    "viz", "vs", "want", "wants", "was", "wasn't", "way", "we", "wed", "well", "were", "we've", "welcome",
                    "well", "went", "were", "weren't", "what", "whats", "whatever", "when", "whence", "whenever", "where",
                    "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which",
                    "while", "whither", "who", "who's", "whoever", "whole", "whom", "whose", "why", "will", "willing",
                    "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldn't", "yes", "yet",
                    "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "zero"};

    private static String[] stopSymbols = {"\\.", "-", "\'", ";", ":", "!", "@", "#", "%", "^", "&", "\\*", "\\(", "\\)", ",", "\\+", "\"", "-", "’", "”",
    "“", "…", "‘", "\\?", "\\{", "\\}", "=", "/", "\\[", "\\]"};
    private static Set<String> stopWordsSet = new HashSet(Arrays.asList(stopWords));
    private static Set<String> stopSymbolsSet = new HashSet(Arrays.asList(stopSymbols));

    public HashMap<String, Long> counter = new HashMap<String, Long>();

    public static String removeStopWords(String s) {

        if (s == null) {
            return null;
        }

        StringBuilder output = new StringBuilder();
        String[] words = s.split(" ");

        for (String str : words) {
            if (str.trim() != "" && !stopWordsSet.contains(str.trim().toLowerCase())) {
                output.append(str.trim().toLowerCase() + " ");
            }
        }
        return output.toString();
    }

    public static String removeStopSymbols(String s) {
        if (s == null) {
            return null;
        }
        for (String c : stopSymbols) {
            s = s.replaceAll(c, " ");
        }
        return s;
    }

    public static void main(String[] args) {
        StringTokenizer tokenizer = new StringTokenizer();
        tokenizer.wordCounter(stopWords.toString());
    }

    public void wordCounter(String data) {
        if (data == null) {
            return;
        }
        String[] words = data.split(" ");
        java.util.Map<String, Long> nameCount = Arrays.asList(words).stream().collect(Collectors.groupingBy(string -> string, Collectors.counting()));
        nameCount.forEach((name, count) -> {
            if (name.length()>0 && counter.containsKey(name)) {
                counter.put(name, count + counter.get(name));
            } else if(name.length()>0) {
                counter.put(name, count);
            }
        });

    }

}
