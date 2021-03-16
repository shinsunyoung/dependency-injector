package sevice.impl;

import static util.ListConfig.LIST_MAXIMUM_SIZE;
import static util.UrlConfig.DEPENDENCY_REQUEST_URL;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Dependency;
import model.DependencyName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sevice.Parser;

public class DependencyParser implements Parser {
  public LookupElement[] getDependencies(String keyword) throws IOException {
    List<Dependency> dependencies = parseDependencies(keyword);
    List<LookupElement> lookupElements = new ArrayList<>();

    for (Dependency dependency : dependencies) {
      LookupElement element = LookupElementBuilder
          .create("value")
          .withPresentableText(dependency.getDisplayName()); // key

      lookupElements.add(element);
    }

    return lookupElements.stream()
        .toArray(LookupElement[]::new);
  }

  private List<Dependency> parseDependencies(String keyword) throws IOException {
    List<Dependency> dependencies = new ArrayList<>();
    Elements names = getNameList(keyword);

    for (Element name : names) {
      DependencyName dependencyName = getDependencyName(name);
      dependencies.add(new Dependency(dependencyName));

      if (listFull(dependencies.size())) {
        break;
      }
    }

    return dependencies;
  }

  private Elements getNameList(String keyword) throws IOException {
    return Jsoup
        .connect(DEPENDENCY_REQUEST_URL + keyword)
        .get()
        .select(".im-subtitle");
  }

  private DependencyName getDependencyName(Element name) {
    Elements nameInfo = name.select("a");

    String projectName = nameInfo.get(1).text();
    String packageName = nameInfo.get(0).text();

    return new DependencyName(projectName, packageName);
  }

  private boolean listFull(int listSize) {
    return listSize == LIST_MAXIMUM_SIZE;
  }
}
