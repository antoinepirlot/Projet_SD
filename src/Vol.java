public class Vol {

  private String nomCompanie;
  private String IATASource;
  private String IATADestination;

  public Vol(String nomCompanie, String IATASource, String IATADestination) {
    this.nomCompanie = nomCompanie;
    this.IATASource = IATASource;
    this.IATADestination = IATADestination;
  }

  public String getNomCompanie() {
    return nomCompanie;
  }

  public void setNomCompanie(String nomCompanie) {
    this.nomCompanie = nomCompanie;
  }

  public String getIATASource() {
    return IATASource;
  }

  public void setIATASource(String IATASource) {
    this.IATASource = IATASource;
  }

  public String getIATADestination() {
    return IATADestination;
  }

  public void setIATADestination(String IATADestination) {
    this.IATADestination = IATADestination;
  }

  @Override
  public String toString() {
    return "Vol{" +
        "nomCompanie='" + nomCompanie + '\'' +
        ", IATASource='" + IATASource + '\'' +
        ", IATADestination='" + IATADestination + '\'' +
        '}';
  }
}
