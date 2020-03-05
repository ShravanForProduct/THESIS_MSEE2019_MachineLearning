package TFIDF;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Article implements Serializable {

    private Source source;

    private String author;

    private String title;

    private String description;

    private String publishedAt;

    private String content;

    private String url;

    private String urlToImage;

    private String tokens;

    private List<String> tokensList;
    public List<String> getTokensList() {
        return tokensList;
    }

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }


    ////////// from the new code

    private ArrayList<Double> docVector=new ArrayList<>();
    private ArrayList<Double> unitVector=new ArrayList<>();


    public int number_of_tokens()
    {
        return tokensList.size();
    }


    public double countOccurences(String get) {
        int count = 0;
        for (int x=0;x<tokensList.size();x++) {
            if (get.equals(tokensList.get(x))) {
                count++;
            }
        }
        return count;
    }

    public void add_component_to_vector(double TF_IDF) {
        docVector.add(TF_IDF);
    }

    public ArrayList<Double> getVector()
    {
        return docVector;
    }

    public void normalized_Vector()
    {
        double squaredSum=0;
        if(!docVector.isEmpty())
        {

            for(int d=0;d<docVector.size();d++)
            {
                squaredSum+=(docVector.get(d)*docVector.get(d));
            }

            squaredSum=Math.sqrt(squaredSum);

            for(int d=0;d<docVector.size();d++)
            {
                unitVector.add(d,docVector.get(d)/squaredSum);
            }
        }

    }

    public ArrayList<Double> getUnitVector() {
        return unitVector;
    }


    public List<String> get_tokens() throws IOException
    {
        return tokensList;
    }



}
