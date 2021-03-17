package service;

import model.Dependency;

public interface MavenCodeParser extends CodeParser {
  String getDependencyCode(Dependency dependency);
}
