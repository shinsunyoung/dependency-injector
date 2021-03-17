package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import component.FileInfo;
import component.Selector;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import model.BuildType;
import model.UserRequest;
import org.jetbrains.annotations.NotNull;
import component.Loading;

public abstract class ChangeAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Loading loadingComponent = new Loading(e);
    loadingComponent.showPopUp();

    String selectedText = new Selector(e).getSelectedText();
    UserRequest userRequest = new UserRequest(selectedText, new FileInfo(e));

    init(e);

    CompletableFuture.supplyAsync(() -> (selectedText))
        .thenAccept(dependencyText -> {
          loadingComponent.hidePopUp();
          action(userRequest);
        });
  }

  protected abstract void init(AnActionEvent e);

  protected abstract void action(UserRequest userRequest);

}
