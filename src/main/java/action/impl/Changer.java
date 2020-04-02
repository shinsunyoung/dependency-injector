package action.impl;

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
import java.util.PriorityQueue;
import model.Dependency;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Changer extends ChangeAction {

  private static final String REQUEST_URL = "https://mvnrepository.com/search?q={}&sort=popular";

  private Editor editor;

  @Override
  protected void init(AnActionEvent e) {
    editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
  }

  @Override
  protected void action(String text, String fileType, String dependencyText) {


    if (editor != null) {
      LookupManager lookupManager = LookupManager.getInstance(editor.getProject());
      ApplicationManager
          .getApplication().invokeLater(
          () -> lookupManager.showLookup(editor, getProposeList(dependencyText, fileType)));
    }
  }


  public LookupElement[] getProposeList(@NotNull String text, @NotNull String fileType) {

    List<LookupElement> lookupElements = new ArrayList<>();

    String url = REQUEST_URL.replace("{}", text);

    try {
      Document doc = Jsoup.connect(url).get();
      Elements contents = doc.select(".im-subtitle a");

      int count = 0;

      for(int i=0; i<contents.size(); i+=2){

        if(count >= 3){
          break;
        }

        Document subDocs = Jsoup.connect("https://mvnrepository.com/artifact/"+contents.get(i).text()+"/"+contents.get(i+1).text()).get();
        Elements subContents = subDocs.select("tbody tr td a");

        PriorityQueue<Dependency> dependencies = new PriorityQueue<>();

        for(int j=1; j<subContents.size()-3; j+=3){
          dependencies.add(new Dependency(subContents.get(j).text(), subContents.get(j+2).text()));
        }

        LookupElement element = LookupElementBuilder
            .create(dependencies.peek().getVersion()) // value. 값이 중복되면 하나만 나옴
            .withPresentableText(contents.get(i+1).text() + "(" + contents.get(i).text() + ")"); // key
        lookupElements.add(element);

        count++;
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return lookupElements.stream()
        .toArray(LookupElement[]::new);
  }
}
