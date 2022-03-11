import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {

  public List<String> aeroports = new ArrayList<>();
  public List<String> vols = new ArrayList<>();

  public Graph(File aeroportsFile, File volsFile) {
    try (
        BufferedReader aeroportsBuffer = new BufferedReader(new FileReader(aeroportsFile));
        BufferedReader volsBuffer = new BufferedReader(new FileReader(volsFile))
    ) {

      String aeroportLine = "";
      String volLine = "";
      while (aeroportLine != null && volLine != null) {

        volLine = volsBuffer.readLine();
        if (aeroportLine != null) {
          aeroportLine = aeroportsBuffer.readLine();
          if (aeroportLine != null) {
            aeroports.add(aeroportLine);
          }
        }

        if (volLine != null) {
          volLine = volsBuffer.readLine();
          if (volLine != null) {
            vols.add(volLine);
          }
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
