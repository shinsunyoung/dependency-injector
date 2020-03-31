package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import component.FileInfo;
import component.Selector;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public abstract class ChangeAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    String selectedText = new Selector(e).getSelectedText();
    String fileType = new FileInfo(e).getBuildName();

    // 초기값 세팅
    init(e);

    // 비동기 Action 실행
    CompletableFuture.supplyAsync(() -> (selectedText))
        .thenAccept(dependencyText -> {
          action(selectedText, fileType, dependencyText);
        });

  }

  protected abstract void init(AnActionEvent e);

  protected abstract void action(String text, String fileName, String dependencyText);

}
