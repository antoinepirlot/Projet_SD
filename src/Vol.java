public class Vol {

  private final String nomCompanie;
  private final Aeroport aeroportSource;
  private final Aeroport aeroportDestination;

  public Vol(String nomCompanie, Aeroport aeroportSource, Aeroport aeroportDestination) {
    this.nomCompanie = nomCompanie;
    this.aeroportSource = aeroportSource;
    this.aeroportDestination = aeroportDestination;
  }

  public Aeroport getAeroportSource() {
    return aeroportSource;
  }

  public Aeroport getAeroportDestination() {
    return aeroportDestination;
  }

  public double getDistance() {
    return Util.distance(aeroportSource.getLatitude(), aeroportSource.getLongitude(),
        aeroportDestination.getLatitude(), aeroportDestination.getLongitude());
  }

  @Override
  public String toString() {
    return "Vol [source=" + this.aeroportSource.getNom()
        + ", destination=" + this.aeroportDestination.getNom()
        + ", airline=" + this.nomCompanie
        + ", distance=" + getDistance() + "]";
  }
}
