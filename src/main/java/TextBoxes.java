import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by devsun on 15. 1. 6..
 */
public class TextBoxes extends AnAction {
  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    Project project = anActionEvent.getProject();

    Messages.showMessageDialog(project, "Hello fresh", "Greet", Messages.getInformationIcon());
  }
}
