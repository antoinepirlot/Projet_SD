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
import java.util.List;
import java.util.Map;

public class Graph {
  private final Map<String, Aeroport> aeroports = new HashMap<>();
  private final Map<Aeroport, List<Vol>> volsSortantAeroport = new HashMap<>();

  public Graph(File aeroportsFile, File volsFile) {
    try (
        BufferedReader aeroportsBuffer = new BufferedReader(new FileReader(aeroportsFile));
        BufferedReader volsBuffer = new BufferedReader(new FileReader(volsFile))
    ) {
      String aeroportLine;
      while ((aeroportLine = aeroportsBuffer.readLine()) != null) {
        Object[] aeroport = Arrays.stream(aeroportLine.split(",")).toArray();
        Aeroport aeroportTemp = new Aeroport(aeroport[0].toString(), aeroport[1].toString(),
            aeroport[2].toString(), aeroport[3].toString(),
            Double.parseDouble(aeroport[4].toString()), Double.parseDouble(aeroport[5].toString()));
        this.aeroports.put(aeroportTemp.getIATA(), aeroportTemp);
      }
      String volLine;
      while ((volLine = volsBuffer.readLine()) != null) {
        Object[] vol = Arrays.stream(volLine.split(",")).toArray();
        Vol volTemp = new Vol(vol[0].toString(), aeroports.get(vol[1].toString()), aeroports.get(vol[2].toString()));
        if (this.volsSortantAeroport.containsKey(volTemp.getAeroportSource())) {
          this.volsSortantAeroport.get(volTemp.getAeroportSource()).add(volTemp);
        } else {
          List<Vol> listVol = new ArrayList<>();
          listVol.add(volTemp);
          this.volsSortantAeroport.put(volTemp.getAeroportSource(), listVol);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreVol(String source, String destination) {
    Aeroport aeroportSource = this.aeroports.get(source);
    Aeroport aeroportDestination = this.aeroports.get(destination);
    Map<Aeroport, Vol> sourceAeroports = new HashMap<>();
    Deque<Aeroport> file = new ArrayDeque<>();
    file.addLast(aeroportSource);

    while (!file.isEmpty()) {
      Aeroport aeroportActuel = file.removeFirst();
      List<Vol> listVols = this.volsSortantAeroport.get(aeroportActuel);
      if (listVols == null) throw new IllegalStateException("Aucun vol");
      for (Vol vol : listVols) {
        Aeroport aeroport = vol.getAeroportDestination();
        if (!sourceAeroports.containsKey(aeroport)) {
          file.addLast(aeroport);
          sourceAeroports.put(aeroport, vol);
        }
        if (aeroport.equals(aeroportDestination)) {
          affichageCheminVol(sourceAeroports, aeroportDestination, aeroportSource);
          return;
        }
      }
    }
  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {
    Aeroport aeroportSource = this.aeroports.get(source);
    Aeroport aeroportDestination = this.aeroports.get(destination);
    Map<Aeroport, Double> etiquettesProvisoires = new HashMap<>();
    Map<Aeroport, Double> etiquettesDefinitives = new HashMap<>();
    Map<Aeroport, Vol> sourceAeroports = new HashMap<>();

    List<Vol> listVols = this.volsSortantAeroport.get(aeroportSource);
    if (listVols == null) throw new IllegalStateException("Aucun vol");
    for (Vol vol : listVols) {
      etiquettesProvisoires.put(vol.getAeroportDestination(), vol.getDistance());
      sourceAeroports.put(vol.getAeroportDestination(), vol);
    }

    while (etiquettesDefinitives.size() != etiquettesProvisoires.size()) {
      Aeroport aeroportDistanceMinimal = etiquettesProvisoires.keySet().stream()
          .filter(a -> !etiquettesDefinitives.containsKey(a)).
          min(Comparator.comparing(etiquettesProvisoires::get)).orElse(null);
      if (aeroportDistanceMinimal == null) break;

      double distanceSourceDestination = etiquettesProvisoires.get(aeroportDistanceMinimal);

      etiquettesDefinitives.put(aeroportDistanceMinimal, distanceSourceDestination);
      if (aeroportDistanceMinimal.equals(aeroportDestination)) break;

      List<Vol> listVolsAeroportDestination = this.volsSortantAeroport.get(aeroportDistanceMinimal);
      if (listVolsAeroportDestination == null) continue;
      for (Vol vol : listVolsAeroportDestination) {
        Aeroport aeroportVol = vol.getAeroportDestination();
        Double distance = distanceSourceDestination + Util.distance(aeroportDistanceMinimal.getLatitude(),
            aeroportDistanceMinimal.getLongitude(), aeroportVol.getLatitude(), aeroportVol.getLongitude());

        Double ancienneDistance = etiquettesProvisoires.get(aeroportVol);
        if (ancienneDistance == null || ancienneDistance > distance) {
          etiquettesProvisoires.put(aeroportVol, distance);
          sourceAeroports.put(aeroportVol, vol);
        }
      }
    }
    affichageCheminVol(sourceAeroports, aeroportDestination, aeroportSource);
  }

  private void affichageCheminVol(Map<Aeroport, Vol> sourcesAeroport, Aeroport aeroportDestination, Aeroport aeroportSource) {
    double distance = 0;
    List<String> listDisplay = new ArrayList<>();
    Vol vol = sourcesAeroport.get(aeroportDestination);
    while (true) {
      listDisplay.add(vol.toString());
      distance += vol.getDistance();
      if (vol.getAeroportSource().equals(aeroportSource)) break;
      vol = sourcesAeroport.get(vol.getAeroportSource());
    }
    System.out.println("Distance : " + distance);
    for (int i = listDisplay.size() - 1; i >= 0; i--) {
      System.out.println(listDisplay.get(i));
    }
  }
}