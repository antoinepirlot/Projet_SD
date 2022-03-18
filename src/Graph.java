import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

  private Map<String, Aeroport> aeroports = new HashMap<>(); //IATA, AEROPORT
  private Map<Aeroport, Double> etiquettesProvisoire = new HashMap<>();
  private Map<Aeroport, Double> etiquettesDefinitive = new HashMap<>();
  private Map<Aeroport, Vol> sourceAeroport = new HashMap<>();


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
        Aeroport aeroport;
        if (aeroportLine != null) {
          List<String> aeroportString = Arrays.stream(aeroportLine.split(",")).toList();
          aeroport = new Aeroport(aeroportString.get(0), aeroportString.get(1), aeroportString.get(2),
              aeroportString.get(3),Double.parseDouble(aeroportString.get(4)),
              Double.parseDouble(aeroportString.get(5)));
          this.aeroports.put(aeroport.getIATA(), aeroport);
          //aeroports.add(aeroportLine);

        }
      }
      System.out.println(this.aeroports);
      while (volLine != null) {
        volLine = volsBuffer.readLine();
        if (volLine != null) {
          List<String> volString = Arrays.stream(volLine.split(",")).toList();
          Aeroport aeroportSource = this.aeroports.get(volString.get(1));
          Aeroport aeroportDestination = this.aeroports.get(volString.get(2));
          Vol vol = new Vol(volString.get(0), aeroportSource, aeroportDestination);
          aeroportSource.addVolSortant(vol);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination) {
    Aeroport aeroportSource = this.aeroports.get(source);
    Aeroport aeroportDestination = this.aeroports.get(destination);
    this.etiquettesProvisoire.clear();
    this.etiquettesDefinitive.clear();
    this.sourceAeroport.clear();

    //Initalisation des etiquettes provisoires
    Set<Vol> volsSortant = aeroportSource.getVolsSortant();
    for(Vol vol : volsSortant) {
      Aeroport aeroportVolDestination = this.aeroports.get(vol.getAeroportDestination());
      this.etiquettesProvisoire.put(aeroportVolDestination, (double) 1);
    }
  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {

  }
}
