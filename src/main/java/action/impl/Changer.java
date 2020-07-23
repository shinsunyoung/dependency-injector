package action.impl;

import static util.Config.REQUEST_URL;
import static util.Config.SUB_REQUEST_URL;

import action.ChangeAction;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Dependency;
import org.jetbrains.annotations.NotNull;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Changer extends ChangeAction {

  private Editor editor;

  @Override
  protected void init(AnActionEvent e) {
    editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
  }

  @Override
  protected void action(String text, String fileType, String dependencyText) {

    if (editor != null) {
      LookupManager lookupManager = LookupManager.getInstance(
          Objects.requireNonNull(editor.getProject()));
      ApplicationManager
          .getApplication().invokeLater(
          () -> lookupManager.showLookup(editor, getProposeList(dependencyText, fileType)));
    }
  }

  public LookupElement[] getProposeList(@NotNull String text, @NotNull String fileType) {

    List<LookupElement> lookupElements = new ArrayList<>();
    String url = REQUEST_URL.replace("{search}", text);

    try {
      Document doc = Jsoup.connect(url).get();
      Elements contents = doc.select(".im-subtitle a");

      int count = 0;

      for (int i = 0; i < contents.size(); i += 2) {
        if (count >= 3) { // 리스트 최대 갯수를 3으로 설정
          break;
        }

        String subUrl = SUB_REQUEST_URL.replace("{project}", contents.get(i).text())
            .replace("{version}",  contents.get(i + 1).text());

        Document subDocs = Jsoup.connect(subUrl).get();
        Elements subContents = subDocs.select("tbody tr td a.vbtn.release");
        Elements subPopular = subDocs.select("tbody tr td:nth-last-child(2)");

        int max = 0;
        String version = "";

        for (int j = 0; j < subContents.size(); j++) {

          Elements searchByA = subPopular.get(j).select("a");

          if (!searchByA.isEmpty()) { // size 0 == 0
            int popular = Integer.parseInt(searchByA.get(0).text().replaceAll(",", ""));

            if (max < popular) {
              max = popular;
              version = subContents.get(j).text();
            }
          }
        }

        try {
          LookupElement element = LookupElementBuilder
              .create(
                  new Dependency(version, max).getSource(subUrl, fileType)) // value
              .withPresentableText(
                  contents.get(i + 1).text() + "(" + contents.get(i).text() + ")"); // key
          lookupElements.add(element);
        } catch (HttpStatusException ex) {
          ex.getStackTrace();
        }
        count++;
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return lookupElements.toArray(new LookupElement[0]);
  }
}
