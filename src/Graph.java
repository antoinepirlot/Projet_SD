import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Graph {

  public Graph(File aeroports, File vols) {
    try {
      BufferedReader aeroportsBuffer = new BufferedReader(new FileReader(aeroports));
      BufferedReader volsBuffer = new BufferedReader(new FileReader(vols));
      String line = "";
      while (line != null) {
        line = aeroportsBuffer.readLine();
        System.out.println(line);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination) {

  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {

  }
}
