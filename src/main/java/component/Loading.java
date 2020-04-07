package component;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.ui.AnimatedIcon;
import java.awt.BorderLayout;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nullable;

public class Loading {

  private AnActionEvent e;
  private Balloon balloon;

  public Loading(@Nullable AnActionEvent e) {
    this.e = e;
  }

  private JComponent createPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    AnimatedIcon loadingIcon = new LoadingIcon();
    loadingIcon.resume();
    panel.add(loadingIcon, BorderLayout.CENTER);
    return panel;
  }

  public void showPopUp(){
    Selector selector = new Selector(e);
    JComponent jComponent = selector.getCurrentComponent();
    Point point = selector.extractPoint();

    if (jComponent != null && point != null) {
      balloon = JBPopupFactory.getInstance()
          .createBalloonBuilder(createPanel())
          .createBalloon();

      balloon.show(new RelativePoint(jComponent, point),
          Balloon.Position.below);
    }
  }

  public void hidePopUp(){
    balloon.hide();
  }

}
