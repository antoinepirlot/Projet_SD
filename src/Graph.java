import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

  private HashMap<String, List<String>> aeroports = new HashMap<>();
  private Map<String, List<String>> inital = new HashMap<>();
  private Map<String, List<String>> f = new HashMap<>();

  public Graph(File aeroportsFile, File volsFile) {
    try (
        BufferedReader aeroportsBuffer = new BufferedReader(new FileReader(aeroportsFile));
        BufferedReader volsBuffer = new BufferedReader(new FileReader(volsFile))
    ) {

      String aeroportLine = "";
      String volLine = "";
      while (aeroportLine != null) {
        aeroportLine = aeroportsBuffer.readLine();
        if (aeroportLine != null) {
          List<String> aeroport = Arrays.stream(aeroportLine.split(",")).toList();
          this.aeroports.put(aeroport.get(0), aeroport);
          //aeroports.add(aeroportLine);

        }
      }
      System.out.println(this.aeroports);
      while (volLine != null) {
        volLine = volsBuffer.readLine();
        if (volLine != null) {

        }
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
