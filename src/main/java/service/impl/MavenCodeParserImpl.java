package service.impl;

import java.io.IOException;
import model.Dependency;
import org.jsoup.select.Elements;
import service.CodeParser;

public class MavenCodeParserImpl implements CodeParser {

  @Override
  public String getDependencyCode(Dependency dependency) {
    return "maven";
  }

  @Override
  public Elements getSourceCode(Dependency dependency) throws IOException {
    return null;
  }
}
