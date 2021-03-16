package model;

public class DependencyName {
  private final String projectName;
  private final String packageName;

  public DependencyName(String projectName, String packageName) {
    this.projectName = projectName;
    this.packageName = packageName;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getPackageName() {
    return packageName;
  }
}
