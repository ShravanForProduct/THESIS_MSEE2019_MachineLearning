package TFIDF;

import sun.security.krb5.internal.crypto.Aes128;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseOperations {

    private static LocalDate date = LocalDate.now();
    Connection c = null;
    Statement stmt = null;

    // database connection details initialisation
    public void connectToDb() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/newsdatadb",
                            "postgres", "Cauvery185!");
            c.setAutoCommit(false);
            System.out.println("Database connection successful !!");

            stmt = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToDB(News news) {
        String sql = "";
        try {

            connectToDb();

            for (Article article : news.getArticles()) {
                sql = "INSERT INTO newsdata (date,source_id,source_name,author,title,description,url,urltoimage,publishedat,content,tokens) "
                        + "VALUES (" +
                        "'" + date.toString() + "'," +
                        "'" + article.getSource().getId() + "'," +
                        "'" + article.getSource().getName() + "'," +
                        "'" + article.getAuthor() + "'," +
                        "'" + article.getTitle() + "'," +
                        "'" + article.getDescription() + "'," +
                        "'" + article.getUrl() + "'," +
                        "'" + article.getUrlToImage() + "'," +
                        "'" + article.getPublishedAt() + "'," +
                        "'" + article.getContent() + "'," +
                        "'" + article.getTokens() + "'" +
                        ");";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Article> getDataFromDB() {
        connectToDb();
        ArrayList<Article> result = new ArrayList<Article>();

        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM newsdata;");

            while (rs.next()) {
                Article article = new Article();
                article.setTokens(rs.getString("tokens"));
                article.setTitle(rs.getString("title"));
                article.setDescription(rs.getString("description"));
                article.setContent(rs.getString("content"));
                result.add(article);

            }

            rs.close();
            stmt.close();
            c.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
