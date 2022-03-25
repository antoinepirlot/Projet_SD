import java.util.Objects;

public class Aeroport {
  private final String IATA;
  private final String nom;
  private final String ville;
  private final String pays;
  private final double longitude;
  private final double latitude;

  public Aeroport(String IATA, String nom, String ville, String pays, double longitude,
      double latitude) {
    this.IATA = IATA;
    this.nom = nom;
    this.ville = ville;
    this.pays = pays;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public String getIATA() {
    return IATA;
  }

  public String getNom() {
    return nom;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Aeroport aeroport = (Aeroport) o;
    return Objects.equals(IATA, aeroport.IATA);
  }

  @Override
  public int hashCode() {
    return Objects.hash(IATA);
  }
}
