package model;

import java.io.IOException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Dependency {
//  private String group;
//  private String name;
  private String version;
//  private String build;
  private int popular;


  public void setVersion(String version) {
    this.version = version;
  }

  public void setPopular(int popular) {
    this.popular = popular;
  }

  public String getVersion() {
    return version;
  }

  public int getPopular() {
    return popular;
  }

  public Dependency() {
  }

  public String getSource(String path, String fileType) throws IOException {
    Document doc = Jsoup.connect(path + "/" + this.version).get();
    Elements content;

    if(fileType.equals("maven")){
      content = doc.select("#maven-div textarea");
    } else {
      content = doc.select("#gradle-div textarea");
    }

    if(content.size() == 0) return "not found!";

    String text = fileType.equals("gradle") ? content.get(0).text().replaceAll("\n", "\n\t") : content.get(0).text();

    return text;

  }

}
