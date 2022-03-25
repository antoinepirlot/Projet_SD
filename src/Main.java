import java.io.File;

public class Main {
	public static void main(String[] args) {
		try {
			File aeroports = new File("aeroports.txt");
			File vols = new File("vols.txt");
			Graph g = new Graph(aeroports,vols);
			g.calculerItineraireMinimisantNombreVol("BRU", "EWR");
			System.out.println("-----------------");
			g.calculerItineraireMiniminantDistance("BRU", "EWR");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
