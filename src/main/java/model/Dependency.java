package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dependency {
  private String groupId;
  private String name;
  private String version;
  private String build;
}
