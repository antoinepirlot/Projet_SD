import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<String, Aeroport> aeroports = new HashMap<String, Aeroport>(); // IATA, Aeroport
  private HashSet<Vol> vols = new HashSet<Vol>();
  private Map<Aeroport, List<Vol>> volsSortantAeroport = new HashMap<Aeroport, List<Vol>>();

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
          volsSortantAeroport.put(aeroports.get(volTemp.getIATASource()), new ArrayList<>());
          volsSortantAeroport.get(aeroports.get(volTemp.getIATASource())).add(volTemp);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination) {
    Aeroport aeroportSource = aeroports.get(source);
    Aeroport aeroportDestination = aeroports.get(destination);
    Map<Aeroport, Vol> sourceAeroport = new HashMap<>();
    Deque<Aeroport> file = new ArrayDeque<>();
    file.addLast(aeroportSource);

    while (!file.isEmpty()) {
      Aeroport aeroportActuel = file.removeFirst();
      for (Vol vol : this.volsSortantAeroport.get(aeroportActuel)) {
        Aeroport aeroport = this.aeroports.get(vol.getIATADestination());
        if (!sourceAeroport.containsKey(aeroport)) {
          file.addLast(aeroport);
          sourceAeroport.put(aeroport, vol);
        }
        if (aeroport.equals(aeroportDestination)) {
          affichageCheminVol(sourceAeroport, aeroportDestination, aeroportSource);
          return;
        }
      }
    }
  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {
    Aeroport aeroportSource = aeroports.get(source);
    Aeroport aeroportDestination = aeroports.get(destination);
    Map<Aeroport, Double> etiquettesProvisoires = new HashMap<>();
    Map<Aeroport, Double> etiquettesDefinitives = new HashMap<>();
    Map<Aeroport, Vol> sourceAeroport = new HashMap<>();

    // Initialisation des etiquettes provisoires
    for (Vol vol : volsSortantAeroport.get(aeroportSource)) {
      Aeroport aeroportDesti = aeroports.get(vol.getIATADestination());
      etiquettesProvisoires.put(aeroportDesti, Util.distance(aeroportSource.getLatitude(),
          aeroportSource.getLongitude(), aeroportDesti.getLatitude(), aeroportDesti.getLongitude()));
      sourceAeroport.put(aeroportDesti, vol);
    }

    // Initialisation des étiquettes définitives
    while (etiquettesDefinitives.size() != etiquettesProvisoires.size()) {
      Aeroport aeroportDistanceMinimal = etiquettesProvisoires.keySet().stream()
          .filter(a -> !etiquettesDefinitives.containsKey(a)).
          min(Comparator.comparing(etiquettesProvisoires::get)).orElse(null);
      if (aeroportDistanceMinimal == null) break;

      double distanceSourceDestination = etiquettesProvisoires.get(aeroportDistanceMinimal);

      etiquettesDefinitives.put(aeroportDistanceMinimal, distanceSourceDestination);
      if (aeroportDistanceMinimal.equals(aeroportDestination)) break;

      List<Vol> listVolsAeroportDestination = volsSortantAeroport.get(aeroportDistanceMinimal);
      if (listVolsAeroportDestination == null) continue;
      for (Vol vol : listVolsAeroportDestination) {
        Aeroport aeroportVol = aeroports.get(vol.getIATADestination());
        Double distance = distanceSourceDestination + Util.distance(aeroportDistanceMinimal.getLatitude(),
            aeroportDistanceMinimal.getLongitude(), aeroportVol.getLatitude(), aeroportVol.getLongitude());

        Double ancienneDistance = etiquettesProvisoires.get(aeroportVol);
        if (ancienneDistance == null || ancienneDistance > distance) {
          etiquettesProvisoires.put(aeroportVol, distance);
          sourceAeroport.put(aeroportVol, vol);
        }
      }
    }
    affichageCheminVol(sourceAeroport, aeroportDestination, aeroportSource);
  }

  public void affichageCheminVol(Map<Aeroport, Vol> sourceAeroport, Aeroport aeroportDestination, Aeroport aeroportSource) {
    double distance = 0;
    List<String> listDisplay = new ArrayList<>();
    Vol volle = sourceAeroport.get(aeroportDestination);
    while (true) {
      Aeroport volAeroportSource = aeroports.get(volle.getIATASource());
      Aeroport volAeroportDestination = aeroports.get(volle.getIATADestination());

      double actualDistance = Util.distance(volAeroportSource.getLatitude(), volAeroportSource.getLongitude(),
          volAeroportDestination.getLatitude(), volAeroportDestination.getLongitude());

      listDisplay.add("Vol [source=" + volAeroportSource.getNom() + ", "
          + "destination=" + volAeroportDestination.getNom() + ", "
          + "airline=" + volle.getNomCompanie() + ", "
          + "distance=" + actualDistance + "]");
      distance += actualDistance;
      if (volle.getIATASource().equals(aeroportSource.getIATA())) break;
      volle = sourceAeroport.get(aeroports.get(volle.getIATASource()));
    }
    System.out.println("Distance : " + distance);
    for (int i = listDisplay.size() - 1; i >= 0; i--) {
      System.out.println(listDisplay.get(i));
    }
  }
}
