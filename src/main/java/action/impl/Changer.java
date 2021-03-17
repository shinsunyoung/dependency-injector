package action.impl;

import action.ChangeAction;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import component.FileInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Dependency;
import model.UserRequest;
import service.CodeParser;
import service.impl.DependencyParser;

public class Changer extends ChangeAction {

  private Editor editor;

  @Override
  protected void init(AnActionEvent e) {
    editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
  }

  @Override
  protected void action(UserRequest request) {
    LookupManager lookupManager = LookupManager.getInstance(Objects.requireNonNull(editor.getProject()));
    ApplicationManager.getApplication().invokeLater(() -> {
      try {
        lookupManager.showLookup(editor, getDependencies(request.getFileInfo(), request.getSelectedText()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public LookupElement[] getDependencies(FileInfo fileInfo, String keyword) throws IOException {
    DependencyParser dependencyParser = new DependencyParser();
    CodeParser codeParser = fileInfo.getBuild().getCodeParser();

    List<Dependency> dependencies = dependencyParser.parseDependencies(keyword);
    List<LookupElement> lookupElements = new ArrayList<>();

    for (Dependency dependency : dependencies) {
      LookupElement element = LookupElementBuilder
          .create(codeParser.getDependencyCode(dependency))
          .withPresentableText(dependency.getDisplayName()); // key

      lookupElements.add(element);
    }

    return lookupElements.stream()
        .toArray(LookupElement[]::new);
  }
}
