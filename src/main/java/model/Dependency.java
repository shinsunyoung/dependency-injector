package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


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
}
