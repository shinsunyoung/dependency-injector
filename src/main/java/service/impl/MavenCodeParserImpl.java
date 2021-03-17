package service.impl;

import model.Dependency;
import service.CodeParser;

public class MavenCodeParserImpl implements CodeParser {

  @Override
  public String getDependencyCode(Dependency dependency) {
    return "maven";
  }
}
