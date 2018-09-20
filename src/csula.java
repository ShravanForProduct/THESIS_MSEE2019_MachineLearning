//Libraries for Selenium
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//Libraries for Java
import java.io.FileWriter;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class csula
{	



	public static void main(String args[]) throws Exception
    {
//create the first object
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the URL (Copy and Paste it from the browser) : ");
        String url = in.nextLine();

//Google Chrome has its own .exe file for selenium automation testing
//navigate to that file
        System.setProperty("webdriver.chrome.driver","C:\\Users\\shravan\\Desktop\\Thesis Workspace\\THESIS_MSEE2019-master\\chromedriver.exe");
        WebDriver  driver = new ChromeDriver(); 	//create a new object 'driver' with ChromeDriver() class
        driver.get(url);
        
        List <WebElement> tags = driver.findElements(By.tagName("a"));
        int count1 = tags.size();
//Get the links        
        Set <String> links = new HashSet<String>();
        int[] statusCode = new int[count1];
          
        for(WebElement a : tags)
        	{
        		if(a.getAttribute("href")!=null)
        		{
        		links.add(a.getAttribute("href"));
        		}
        	}            
        System.out.println("List of links Available in HomePage & Condition: ");
        
        String[] linksHome = new String[count1];
        FileWriter writer= new FileWriter("CsulaLinks.csv");
        writer.write("Links,Level,Type,Status Code\n");

        int k = 0;
        for(String link : links)	//iterates through each Link (iteration without index)
        	{
                  linksHome[k] = link;
                  writer.write(link+",1,Parallel,\n");
                  k++;
        	}

              writer.flush();
              writer.close();
         
        
        int j = 0;
        for (int i = 0; i < count1; i++)
              {		
                  driver.navigate().to(linksHome[i]);
                  try
                  {		
                      Thread.sleep(3000);  //wait for 3 seconds for each page to fully load
                      System.out.println(linksHome[i]);
                  		URL url1  = new URL(linksHome[i]);
                  		//HttpURLConnection.setFollowRedirects(false);
                  		HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                  		connection.connect();
                  	
                  		statusCode[j] = connection.getResponseCode();
                  		connection.disconnect();
                  		if(statusCode[j] != 0)
                  			{
                  				System.out.println(statusCode[j]);
                  				j++;
                  			}
                  			
                  	}    
                      
                  catch(InterruptedException e)
                  {
                      System.out.println("Program got interrupted!");
                  }
              }       
    }
	
}
        
  