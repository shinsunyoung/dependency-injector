package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dependency {
  private String group;
  private String name;
  private String version;
  private String build;
}
