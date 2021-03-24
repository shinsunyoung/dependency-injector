package service.impl;

import static util.ListConfig.LIST_MAXIMUM_SIZE;
import static util.ParserConfig.ALL_DEPENDENCY_NAME_LIST_SELECTOR;
import static util.ParserConfig.ALL_VERSION_SELECTOR;
import static util.ParserConfig.DEPENDENCY_NAME_SELECTOR;
import static util.ParserConfig.DEPENDENCY_REQUEST_URL;
import static util.ParserConfig.USED_VERSION_SELECTOR;
import static util.ParserConfig.VERSION_NAME_LIST_SELECTOR;
import static util.ParserConfig.VERSION_REQUEST_URL;
import static util.ParserConfig.VERSION_USAGES_LIST_SELECTOR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import model.Dependency;
import model.DependencyName;
import model.Version;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.Parser;

public class DependencyParser implements Parser {
  public List<Dependency> parseDependencies(String keyword) throws IOException {
    List<DependencyName> dependencyNames = getDependencyNames(keyword);
    List<CompletableFuture<Dependency>> dependencies = getDependenciesByName(dependencyNames);

    return dependencies.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
  }

  private List<DependencyName> getDependencyNames(String keyword) throws IOException {
    Elements dependencyNameElements = getDependencyNameElements(keyword);
    List<DependencyName> dependencyNames = new ArrayList<>();

    for (Element dependencyName : dependencyNameElements) {
      dependencyNames.add(parseDependencyName(dependencyName));

      if (listFull(dependencyNames.size())) {
        break;
      }
    }

    return dependencyNames;
  }

  private Elements getDependencyNameElements(String keyword) throws IOException {
    return Jsoup
        .connect(DEPENDENCY_REQUEST_URL + keyword)
        .get()
        .select(ALL_DEPENDENCY_NAME_LIST_SELECTOR);
  }

  private DependencyName parseDependencyName(Element name) {
    Elements nameInfo = name.select(DEPENDENCY_NAME_SELECTOR);

    String projectName = nameInfo.get(1).text();
    String packageName = nameInfo.get(0).text();

    return new DependencyName(projectName, packageName);
  }

  private boolean listFull(int listSize) {
    return listSize == LIST_MAXIMUM_SIZE;
  }

  private List<CompletableFuture<Dependency>> getDependenciesByName(List<DependencyName> names) {
    return names.stream()
        .map(name -> CompletableFuture.supplyAsync(() -> {
          try {
            return Dependency.of(name, getMostPopularVersion(name));
          } catch (IOException e) {
            e.printStackTrace();
          }
          return Dependency.of(name, Version.unknown());
        }))
        .collect(Collectors.toList());
  }

  private Version getMostPopularVersion(DependencyName name) throws IOException {
    Elements versions = getAllVersions(name);

    Elements versionList = parseVersionList(versions);
    Elements usagesList = parseUsagesList(versions);

    return new Version(getMostPopularVersion(versionList, usagesList));
  }

  private Elements getAllVersions(DependencyName name) throws IOException {
    String subUrl = VERSION_REQUEST_URL
        .replace("{package}", name.getPackageName())
        .replace("{project}", name.getProjectName());

    return Jsoup.connect(subUrl).get().select(ALL_VERSION_SELECTOR);
  }

  private Elements parseVersionList(Elements versions) {
    return versions.select(VERSION_NAME_LIST_SELECTOR);
  }

  private Elements parseUsagesList(Elements versions) {
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
}
