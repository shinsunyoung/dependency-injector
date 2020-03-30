package action.impl;

import action.ChangeAction;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Changer extends ChangeAction {

  private Editor editor;

  @Override
  protected void init(AnActionEvent e) {
    editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
  }

  @Override
  protected void action(String text, String dependencyText) {
    if(editor != null){
      LookupManager lookupManager = LookupManager.getInstance(editor.getProject());
      ApplicationManager
          .getApplication().invokeLater(() -> lookupManager.showLookup(editor, getProposeList(dependencyText)));
    }
  }


  public LookupElement[] getProposeList(@NotNull String text) {

    List<String> listl = new ArrayList<>();

    listl.add("안녕?");
    listl.add("뽀로로");
    listl.add("크롱");

    return listl.stream()
        .map(LookupElementBuilder::create)
        .toArray(LookupElement[]::new);
  }
}
