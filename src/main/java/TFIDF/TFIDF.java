package TFIDF;

import DocumentClustering.Kmeans;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TFIDF {

    private static String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=46c53b2bbf084eb398c15da461db139d";
    private static LocalDate date = LocalDate.now();
    private static String USER_AGENT = "Mozilla/5.0";


    StringTokenizer tokenizer = new StringTokenizer();


    public static void main(String[] args) throws Exception {

        int count = 200;


//        getandProcessData();

        KMeans();

//        calculateTfIdf(count);
    }


    public static void KMeans() throws Exception{

//        TFIDF tfidf = new TFIDF();

        // get data from NewsApi.org
//        String data = tfidf.sendGet(url);

        // convert the raw data to Objects
//        News newsData = tfidf.convertToPojo(data);

        DataBaseOperations dbops = new DataBaseOperations();
        ArrayList<Article> articles = dbops.getDataFromDB();

        Kmeans.startKMeans(articles, 60,
                100, 5);

    }





    // this function gets current data from NewsApi.org
    // Processes it
    // Stores it in database
    public static void getandProcessData() throws Exception {
        TFIDF tfidf = new TFIDF();

        // get data from NewsApi.org
        String data = tfidf.sendGet(url);

        // convert the raw data to Objects
        News newsData = tfidf.convertToPojo(data);

        // process the data - remove stop words, punctuations, etc
        newsData = tfidf.processNewsData(newsData);

        calculateTfIdf( newsData.getArticles(), 200);



        // save the processed data to database
//        DataBaseOperations dbops = new DataBaseOperations();
//        dbops.writeToDB(newsData);
    }

    public static void calculateTfIdf(List<Article> articles, int count) throws Exception {

        // get data from db and store it as objects
//        DataBaseOperations dbops = new DataBaseOperations();
//        ArrayList<Article> articles = dbops.getDataFromDB();

        // calculate Tf-Idf
        TfIdfCalculator calc = new TfIdfCalculator();
        calc.startCalculation(articles, count);
    }


    ////////////////////////////// Methods related to Data Processing ///////////////////////////////////

    // converts string to pojo
    public News convertToPojo(String data) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(data, News.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Remove stop symbols from Title, Decsription and content - to store it in DB
    // token - contains all of the above with stopwords removed from them
    public News processNewsData(News newsData) {

        for (Article article : newsData.getArticles()) {

            article.setTitle(StringTokenizer.removeStopSymbols(article.getTitle()));
            article.setDescription(StringTokenizer.removeStopSymbols(article.getDescription()));
            article.setContent(StringTokenizer.removeStopSymbols(article.getContent()));

            String token =
                    StringTokenizer.removeStopWords(article.getTitle()) +
            StringTokenizer.removeStopWords(article.getDescription()) +
            StringTokenizer.removeStopWords(article.getContent());
            article.setTokens(token);

        }

        return newsData;
    }

    ////////////////////////////// API function - GET ///////////////////////////////////

    // HTTP GET request
    private String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        return response.toString();

    }


}
