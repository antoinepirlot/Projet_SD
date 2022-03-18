public class Vol {

  private String nomCompanie;
  private Aeroport source;
  private Aeroport destination;

  public Vol(String nomCompanie, Aeroport source, Aeroport destination) {
    this.nomCompanie = nomCompanie;
    this.source = source;
    this.destination = destination;
  }

  public String getNomCompanie() {
    return nomCompanie;
  }

  public void setNomCompanie(String nomCompanie) {
    this.nomCompanie = nomCompanie;
  }

  public Aeroport getSource() {
    return source;
  }

  public void setSource(Aeroport source) {
    this.source = source;
  }

  public Aeroport getDestination() {
    return destination;
  }

  public void setDestination(Aeroport destination) {
    this.destination = destination;
  }


  @Override
  public String toString() {
    return "Vol [" +
        "source=" + source.getNom() + ", " +
        "destination= " + destination.getNom() + ", " +
        "airline=" + nomCompanie + ", " +
        "distance=" + Util.distance(source.getLatitude(), source.getLongitude(),
        destination.getLatitude(), destination.getLongitude()) +
        "]\n";
  }
}
