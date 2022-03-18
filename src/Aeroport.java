import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Aeroport {
  private Set<Vol> volsSortant;

  private String IATA;
  private String nom;
  private String ville;
  private String pays;
  private double longitude;
  private double latitude;

  public Aeroport() {

  }
  public Aeroport(String IATA, String nom,
      String ville, String pays, double longitude, double latitude) {
    this.volsSortant = new HashSet<>();
    this.IATA = IATA;
    this.nom = nom;
    this.ville = ville;
    this.pays = pays;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Set<Vol> getVolsSortant() {
    return volsSortant;
  }

  public void setVolsSortant(Set<Vol> volsSortant) {
    this.volsSortant = volsSortant;
  }

  public String getIATA() {
    return IATA;
  }

  public void setIATA(String IATA) {
    this.IATA = IATA;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void addVolSortant(Vol vol) {
    this.volsSortant.add(vol);
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

  @Override
  public String toString() {
    return "Aeroport{" +
        ", volsSortant=" + volsSortant +
        ", IATA='" + IATA + '\'' +
        ", nom='" + nom + '\'' +
        ", ville='" + ville + '\'' +
        ", pays='" + pays + '\'' +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        '}';
  }
}