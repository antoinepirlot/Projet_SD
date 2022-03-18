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

  private Map<Aeroport, Double> etiquettesDefinitives = new HashMap<Aeroport, Double>();

  public Graph(File aeroportsFile, File volsFile) {
    try (
        BufferedReader aeroportsBuffer = new BufferedReader(new FileReader(aeroportsFile));
        BufferedReader volsBuffer = new BufferedReader(new FileReader(volsFile))
    ) {

      String aeroportLine;
      String volLine;

      while((aeroportLine = aeroportsBuffer.readLine()) != null){
        Object[] aeroport = Arrays.stream(aeroportLine.split(",")).toArray();
        Aeroport aeroportTemp = new Aeroport(aeroport[0].toString(), aeroport[1].toString(),
            aeroport[2].toString(), aeroport[3].toString(),
            Double.parseDouble(aeroport[4].toString()), Double.parseDouble(aeroport[5].toString()));
        aeroports.put(aeroportTemp.getIATA(), aeroportTemp);
      }

      while ((volLine = volsBuffer.readLine()) != null) {
        Object[] vol = Arrays.stream(volLine.split(",")).toArray();
        Vol volTemp = new Vol(vol[0].toString(), vol[1].toString(), vol[2].toString());
        vols.add(volTemp);

        //Ajout du vol dans volsSortantAeroports
        if(!volsSortantAeroport.containsKey(aeroports.get(volTemp.getIATASource())))
          volsSortantAeroport.put(aeroports.get(volTemp.getIATASource()), new ArrayList<Vol>());
        volsSortantAeroport.get(aeroports.get(volTemp.getIATASource())).add(volTemp);

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

    etiquettesDefinitives.put(sourceA, (double)-1); // car sourceA a besoin de 0 vols pour aller à sourceA

    Set<Aeroport> aeroportsDejaPasses = new HashSet<>();

    double cpt = 1;
    Aeroport aeroportPourBoucleFor = sourceA;
    ArrayDeque<Aeroport> listeAeroport = new ArrayDeque<>();
    while(!etiquettesDefinitives.containsKey(destA)){ // Risque de boucle infinie
      // On commence par ici

      for(Vol v : volsSortantAeroport.get(aeroportPourBoucleFor)){

        if(aeroportsDejaPasses.contains(aeroports.get(v.getIATADestination())))
          continue;

        Aeroport tempAeroport = aeroports.get(v.getIATADestination());
        listeAeroport.add(tempAeroport);
        etiquettesDefinitives.put(tempAeroport, (double) cpt);
        aeroportsDejaPasses.add(tempAeroport);


        if (tempAeroport.equals(destA)) { //On a trouvé le chemin le plus cours pour de sourceA vers destA
          System.out.println("nombre de vols: " + etiquettesDefinitives.get(tempAeroport));
          return; //inutile de continuer
        }

        if (volsSortantAeroport.get(tempAeroport) == null) {
          System.out.println("nulleeeee");
          etiquettesDefinitives.put(tempAeroport, (double) -1); // cet aeroport ne correspond à aucun aeroportSource
        }

      }

      //cpt++;
      aeroportPourBoucleFor = listeAeroport.poll();
      cpt = etiquettesDefinitives.get(aeroportPourBoucleFor) + 1;

    }
  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {

  }
}
