import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {


  private Map<String, Aeroport> aeroports = new HashMap<String, Aeroport>(); // IATA, Aeroport
  private HashSet<Vol> vols = new HashSet<Vol>();
  private Map<Aeroport, List<Vol>> volsSortantAeroport = new HashMap<Aeroport, List<Vol>>();

  private Map<Aeroport, Double> etiquettesProvisoires = new HashMap<Aeroport, Double>();
  private Map<Aeroport, Double> etiquettesDefinitives = new HashMap<Aeroport, Double>();

  public Graph(File aeroportsFile, File volsFile) {
    try (
        BufferedReader aeroportsBuffer = new BufferedReader(new FileReader(aeroportsFile));
        BufferedReader volsBuffer = new BufferedReader(new FileReader(volsFile))
    ) {

      String aeroportLine;
      String volLine;

      //Creation des aéroports et ajout dans la structure de données
      while ((aeroportLine = aeroportsBuffer.readLine()) != null) {
        Object[] aeroport = Arrays.stream(aeroportLine.split(",")).toArray();
        Aeroport aeroportTemp = new Aeroport(aeroport[0].toString(), aeroport[1].toString(),
            aeroport[2].toString(), aeroport[3].toString(),
            Double.parseDouble(aeroport[4].toString()), Double.parseDouble(aeroport[5].toString()));
        aeroports.put(aeroportTemp.getIATA(), aeroportTemp);
      }

      //Création des vols et ajout dans la structure de données
      while ((volLine = volsBuffer.readLine()) != null) {
        Object[] vol = Arrays.stream(volLine.split(",")).toArray();
        Vol volTemp = new Vol(vol[0].toString(), vol[1].toString(), vol[2].toString());
        vols.add(volTemp);

        //Ajout du vol dans volsSortantAeroports
        if (volsSortantAeroport.containsKey(aeroports.get(volTemp.getIATASource()))) {
          volsSortantAeroport.get(aeroports.get(volTemp.getIATASource())).add(volTemp);
        } else {
          volsSortantAeroport.put(aeroports.get(volTemp.getIATASource()), new ArrayList<Vol>());
          volsSortantAeroport.get(aeroports.get(volTemp.getIATASource())).add(volTemp);
        }

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination) {
    Aeroport sourceA = aeroports.get(source);
    Aeroport destA = aeroports.get(destination);

    Set<Aeroport> aeroportsDejaPasses = new HashSet<>();
    ArrayDeque<HashMap<Aeroport, Integer>> etiquettesProv = new ArrayDeque<>(); //Aeroport, nombre_de_vol

    int nbFleches = 1;
    for (Vol vol : vols) {
      if (vol.getIATASource().equals(source) && vol.getIATADestination().equals(destination)) {
        return;
      } else {
        HashMap<Aeroport, Integer> destVolNbFleches = new HashMap<>(); //<destVol, nbFleche>
        destVolNbFleches.put(aeroports.get(vol.getIATADestination()), nbFleches);
        etiquettesProv.add(destVolNbFleches);
        aeroportsDejaPasses.add(aeroports.get(vol.getIATASource()));
      }
    }
    //throw new IllegalStateException();

    //etiquettesProvisoires.put(sourceA, (double) -1);

    // On commence par ici
    for (Vol v : volsSortantAeroport.get(sourceA)) {
      HashMap<Aeroport, Integer> destinationANbFleches = new HashMap<>();
      destinationANbFleches.put(aeroports.get(v.getIATADestination()), nbFleches);
      etiquettesProv.add(destinationANbFleches);

      aeroportsDejaPasses.add(aeroports.get(v.getIATASource()));
    }

    HashMap<Aeroport, Integer> premierAeroport = etiquettesProv.poll();
    if (premierAeroport == null) // Si queue est vide
    {
      return;
    }
    Aeroport a = premierAeroport.keySet().stream().findAny().orElse(null);
    if (a == null) // normalement jamais le cas
    {
      return;
    }
    etiquettesDefinitives.put(a, (double) nbFleches);
    //etiquettesDefinitives.put(sourceA, (double) -1);

    nbFleches++;
  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {

  }
}
