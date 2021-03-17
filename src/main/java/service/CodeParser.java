package service;

import java.io.IOException;
import model.Dependency;
import org.jsoup.select.Elements;

public interface CodeParser extends Parser {
  String getDependencyCode(Dependency dependency) throws IOException;
  Elements getSourceCode(Dependency dependency) throws IOException;
}
