package service;

import model.Dependency;

public interface GradleCodeParser extends CodeParser {
  String getDependencyCode(Dependency dependency);
}
