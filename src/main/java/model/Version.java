package model;

public class Version {
  private final String version;

  public String getVersion() {
    return version;
  }

  public Version(String version) {
    this.version = version;
  }

  public static Version unknown() {
    return new Version("unknown");
  }
}
