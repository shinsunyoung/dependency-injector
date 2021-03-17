package service.impl;

import static util.ParserConfig.CODE_REQUEST_URL;
import static util.ParserConfig.MAVEN_SOURCE_SELECTOR;

import java.io.IOException;
import model.Dependency;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import service.CodeParser;

public class MavenCodeParserImpl implements CodeParser {

  @Override
  public String getDependencyCode(Dependency dependency) throws IOException {
    Elements sourceCode = getSourceCode(dependency);
    return sourceNotFound(sourceCode) ? "not found!" : getSourceCode(sourceCode);
  }

  @Override
  public Elements getSourceCode(Dependency dependency) throws IOException {
    String url = CODE_REQUEST_URL
        .replace("{package}", dependency.getName().getPackageName())
        .replace("{project}", dependency.getName().getProjectName())
        .replace("{version}", dependency.getMostPopularVersion());

    return Jsoup.connect(url).get().select(MAVEN_SOURCE_SELECTOR);
  }

  private boolean sourceNotFound(Elements sourceCode) {
    return sourceCode.size() == 0;
  }

  @NotNull
  private String getSourceCode(Elements sourceCode) {
    return sourceCode.get(0).text().replaceAll("\n", "\n\t");
  }
}
