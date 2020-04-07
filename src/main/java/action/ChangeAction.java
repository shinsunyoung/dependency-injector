package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import component.FileInfo;
import component.Selector;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import component.Loading;

public abstract class ChangeAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    Loading loadingComponent = new Loading(e);
    loadingComponent.showPopUp();

    String selectedText = new Selector(e).getSelectedText();
    String fileType = new FileInfo(e).getBuildName();

    // 초기값 세팅
    init(e);

    // 비동기 Action 실행
    CompletableFuture.supplyAsync(() -> (selectedText))
        .thenAccept(dependencyText -> {
          loadingComponent.hidePopUp();
          action(selectedText, fileType, dependencyText);
        });
  }


  protected abstract void init(AnActionEvent e);

  protected abstract void action(String text, String fileName, String dependencyText);

}
