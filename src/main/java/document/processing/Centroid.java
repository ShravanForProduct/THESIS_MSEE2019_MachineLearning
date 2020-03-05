
package document.processing;

import TFIDF.Article;

import javax.naming.CannotProceedException;
import java.util.ArrayList;

/**
 *
 * @author umang_borad
 */

public class Centroid {
    private ArrayList<Double> points= new ArrayList<>();
    private ArrayList<Article> docs= new ArrayList<>();
    private ArrayList<Double> newpoints= new ArrayList<>();
   
    public Centroid(ArrayList<Double> set_points)
    {
        points.addAll(0,set_points);
        
        newpoints.addAll(0,set_points);
    }

    ArrayList<Double> getpoints() {
        return points;
    }
    
    void AssignDocumentToCentroid(Article d)
    {
        docs.add(d);
    }
    
    void clearlistOfDoc()
    {
        docs =new ArrayList<>();
    }
    
    void setPoints(ArrayList<Double> set_points)
    {
        points=new ArrayList<>();
        points.addAll(0,set_points);
    }
    
    ArrayList<Article> getdocs()
    {
        return docs;
    }

    void setnewPoints(ArrayList<Double> temp) {
        
        newpoints=new ArrayList<>();
        newpoints.addAll(0,temp);
    
    }
    
    boolean compareCentroidPoints() throws Exception
    {   
        int counter=0;
    if(points.size()==newpoints.size())
    {
       for(int z=0;z<points.size();z++)
        {
           if(points.get(z).compareTo(newpoints.get(z))==0)
            counter+=0; 
           else 
               counter++;
        }
    }
    else
    {
//        throw new CannotProceedException("Number of components do not match");
    }
       return counter==0; 
    }
    
    void replaceOldPointsWithNew()
    {
        points=newpoints;
        newpoints=new ArrayList<>();
    }
    
}
