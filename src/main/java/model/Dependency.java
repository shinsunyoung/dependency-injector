package model;

public class Dependency {

  private final DependencyName name;
  private final Version version;

  public Dependency(DependencyName name, Version version) {
    this.name = name;
    this.version = version;
  }

  public static Dependency of(DependencyName name, Version version) {
    return new Dependency(name, version);
  }

  public DependencyName getName() {
    return name;
  }

  public String getMostPopularVersion() {
    return version.getVersion();
  }

  public String getDisplayName() {
    return name.getProjectName() + "(" + name.getPackageName() + ")";
  }
}
