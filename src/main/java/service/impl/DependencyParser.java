package service.impl;

import static util.ListConfig.LIST_MAXIMUM_SIZE;
import static util.ParserConfig.ALL_DEPENDENCY_NAME_LIST_SELECTOR;
import static util.ParserConfig.ALL_VERSION_SELECTOR;
import static util.ParserConfig.DEPENDENCY_NAME_SELECTOR;
import static util.ParserConfig.DEPENDENCY_REQUEST_URL;
import static util.ParserConfig.VERSION_REQUEST_URL;
import static util.ParserConfig.USED_VERSION_SELECTOR;
import static util.ParserConfig.VERSION_NAME_LIST_SELECTOR;
import static util.ParserConfig.VERSION_USAGES_LIST_SELECTOR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Dependency;
import model.DependencyName;
import model.Version;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.Parser;

public class DependencyParser implements Parser {
  public List<Dependency> parseDependencies(String keyword) throws IOException {
    List<Dependency> dependencies = new ArrayList<>();

    for (Element name : getDependencyNames(keyword)) {
      DependencyName dependencyName = getDependencyName(name);
      Version version = getMostPopularVersion(dependencyName);

      dependencies.add(new Dependency(dependencyName, version));

      if (listFull(dependencies.size())) {
        break;
      }
    }

    return dependencies;
  }

  private Elements getDependencyNames(String keyword) throws IOException {
    return Jsoup
        .connect(DEPENDENCY_REQUEST_URL + keyword)
        .get()
        .select(ALL_DEPENDENCY_NAME_LIST_SELECTOR);
  }

  private DependencyName getDependencyName(Element name) {
    Elements nameInfo = name.select(DEPENDENCY_NAME_SELECTOR);

    String projectName = nameInfo.get(1).text();
    String packageName = nameInfo.get(0).text();

    return new DependencyName(projectName, packageName);
  }

  private Version getMostPopularVersion(DependencyName name) throws IOException {
    Elements versions = getAllVersions(name);

    Elements versionList = getVersionList(versions);
    Elements usagesList = getUsagesList(versions);

    String mostPopularVersion = getMostPopularVersion(versionList, usagesList);

    return new Version(mostPopularVersion);
  }

  private Elements getAllVersions(DependencyName name) throws IOException {
    String subUrl = VERSION_REQUEST_URL
        .replace("{package}", name.getPackageName())
        .replace("{project}", name.getProjectName());

    return Jsoup.connect(subUrl).get().select(ALL_VERSION_SELECTOR);
  }

  private Elements getVersionList(Elements versions) {
    return versions.select(VERSION_NAME_LIST_SELECTOR);
  }

  private Elements getUsagesList(Elements versions) {
    return versions.select(VERSION_USAGES_LIST_SELECTOR);
  }

  private String getMostPopularVersion(Elements versionList, Elements usagesList) {
    String mostPopularVersion = "";
    int mostUsages = 0;

    for (int i = 0; i < versionList.size(); i++) {
      int usages = getUsage(usagesList.get(i));

        if (mostUsages < usages) {
          mostUsages = usages;
          mostPopularVersion = versionList.get(i).text();
        }
    }

    return mostPopularVersion;
  }

  private int getUsage(Element usage) {
    Elements usagesValueList = usage.select(USED_VERSION_SELECTOR);
    return usagesValueList.isEmpty() ? 0 : Integer.parseInt(usagesValueList.first().text().replace(",", ""));
  }

  private boolean listFull(int listSize) {
    return listSize == LIST_MAXIMUM_SIZE;
  }
}
