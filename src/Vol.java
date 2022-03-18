public class Vol {

  private String nomCompanie;
  private Aeroport aeroportSource;
  private Aeroport aeroportDestination;

  public Vol(String nomCompanie, Aeroport aeroportSource, Aeroport aeroportDestination) {
    this.nomCompanie = nomCompanie;
    this.aeroportSource = aeroportSource;
    this.aeroportDestination = aeroportDestination;
  }

  public String getNomCompanie() {
    return nomCompanie;
  }

  public void setNomCompanie(String nomCompanie) {
    this.nomCompanie = nomCompanie;
  }

  public Aeroport getAeroportSource() {
    return aeroportSource;
  }

  public void setAeroportSource(Aeroport aeroportSource) {
    this.aeroportSource = aeroportSource;
  }

  public Aeroport getAeroportDestination() {
    return aeroportDestination;
  }

  public void setAeroportDestination(Aeroport aeroportDestination) {
    this.aeroportDestination = aeroportDestination;
  }

  @Override
  public String toString() {
    return "Vol{" +
        "nomCompanie='" + nomCompanie + '\'' +
        ", IATASource='" + aeroportSource + '\'' +
        ", IATADestination='" + aeroportDestination + '\'' +
        '}';
  }
}