package action.impl;

import action.ChangeAction;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Changer extends ChangeAction {

  private static final String REQUEST_URL = "https://mvnrepository.com/search?q={}&sort=popular";

  private Editor editor;

  @Override
  protected void init(AnActionEvent e) {
    editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
  }

  @Override
  protected void action(String text, String fileType, String dependencyText) {
    if(editor != null){
      LookupManager lookupManager = LookupManager.getInstance(editor.getProject());
      ApplicationManager
          .getApplication().invokeLater(() -> lookupManager.showLookup(editor, getProposeList(dependencyText, fileType)));
    }
  }


  public LookupElement[] getProposeList(@NotNull String text, @NotNull String fileType) {

    List<String> listl = new ArrayList<>();

    String url = REQUEST_URL.replace("{}", text);

    listl.add(text + "1");
    listl.add(fileType + "//");
    listl.add(url);

    return listl.stream()
        .map(LookupElementBuilder::create)
        .toArray(LookupElement[]::new);
  }
}
