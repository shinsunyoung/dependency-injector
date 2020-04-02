package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


public class Dependency implements Comparable<Dependency> {
//  private String group;
//  private String name;
  private String version;
//  private String build;
  private String popular;

  public Dependency(String version, String popular) {
    this.version = version;
    this.popular = popular;
  }

  public String getVersion() {
    return version;
  }

  public String getPopular() {
    return popular;
  }

  @Override
  public int compareTo(@NotNull Dependency o) {
    return o.popular.compareTo(this.popular);
  }
}
