package model;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Dependency {

  private DependencyName name;
  private String version;

  public Dependency(DependencyName name) {
    this.name = name;
  }

  public DependencyName getName() {
    return name;
  }

  public String getDisplayName() {
    return name.getProjectName() + "(" + name.getPackageName() + ")";
  }

  public String getSource(String path, BuildType buildType) throws IOException {

    Document doc = Jsoup.connect(path + "/" + this.version).get();
    Elements content;

    if (buildType == BuildType.MAVEN) {
      content = doc.select("#maven-div textarea");
    } else {
      content = doc.select("#gradle-div textarea");
    }

    if (content.size() == 0) {
      return "not found!";
    }

    return content.get(0).text().replaceAll("\n", "\n\t");

  }

}
