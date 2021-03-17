package service;

import model.Dependency;

public interface CodeParser extends Parser {
  String getDependencyCode(Dependency dependency);
}
