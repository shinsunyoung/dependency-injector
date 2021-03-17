package service.impl;

import model.Dependency;
import service.CodeParser;

public class GradleCodeParserImpl implements CodeParser {

  @Override
  public String getDependencyCode(Dependency dependency) {
    return "gradle";
  }
}
