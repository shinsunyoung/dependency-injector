package model;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Dependency {

  private String version;
  private int popular;

  public Dependency(String version, int popular) {
    this.version = version;
    this.popular = popular;
  }

  public String getSource(String path, String fileType) throws IOException {
    Document doc = Jsoup.connect(path + "/" + this.version).get();
    Elements content;

    if (fileType.equals("maven")) {
      content = doc.select("#maven-div textarea");
    } else {
      content = doc.select("#gradle-div textarea");
    }

    if (content.size() == 0) {
      return "not found!";
    }

    String text = fileType.equals("gradle") ? content.get(0).text().replaceAll("\n", "\n\t")
        : content.get(0).text();

    return text;

  }

}
