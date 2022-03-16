import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
        if(volsSortantAeroport.containsKey(aeroports.get(volTemp.getIATASource()))){
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

    etiquettesProvisoires.put(sourceA, (double) -1);
    etiquettesDefinitives.put(sourceA, (double) -1);

    for(Vol v : volsSortantAeroport.get(sourceA)){
      System.out.println(v);
      //System.out.println("s");
      etiquettesProvisoires.put(aeroports.get(v.getIATASource()), (double) 1);
    }
    //System.out.println(etiquettesProvisoires);
  }

  public void calculerItineraireMiniminantDistance(String source, String destination) {

  }
}
