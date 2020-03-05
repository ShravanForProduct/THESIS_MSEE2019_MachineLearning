
package document.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *
 * @author umang_borad
 */
public class DocumentProcessing {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, Exception {
        
        System.out.println("Please enter the Value of K:");
        Scanner s=new Scanner(System.in);
        int K_cardinality=s.nextInt();
        ArrayList<Documents> listOfDocuments=new ArrayList<>();
        

        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.showOpenDialog(j);

        //File dir = new File("C:\\Users\\tirth_parikh\\Desktop\\Test");
        File dir = new File(j.getSelectedFile().getAbsolutePath());
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : files) {
	
                    listOfDocuments.add(new Documents(file));
		}
                
//        KmeansCluster k=new KmeansCluster(listOfDocuments);
//                            k.createCluster(K_cardinality);


    }

}
