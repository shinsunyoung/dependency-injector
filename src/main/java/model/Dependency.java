package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
public class Dependency implements Comparable<Dependency> {
//  private String group;
//  private String name;
  private String version;
//  private String build;
  private Integer popular;


  @Override
  public int compareTo(@NotNull Dependency o) {
    if (this.popular > o.popular) {
      return 1;
    } else if (this.popular < o.popular) {
      return -1;
    }
    return 0;
  }
}
