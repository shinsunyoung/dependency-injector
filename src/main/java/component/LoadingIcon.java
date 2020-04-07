package component;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.ui.AnimatedIcon;
import javax.swing.Icon;

public class LoadingIcon extends AnimatedIcon {

  private static final int CYCLE_LENGTH = 300;

  private static final Icon[] ICONS = {
      IconLoader.getIcon("/process/step_1.png"),
      IconLoader.getIcon("/process/step_5.png"),
      IconLoader.getIcon("/process/step_7.png"),
      IconLoader.getIcon("/process/step_12.png")
  };

  private static final Icon STEP_PASSIVE = IconLoader.getIcon("/process/step_1.png");

  public LoadingIcon() {
    super("Loading", ICONS, STEP_PASSIVE, CYCLE_LENGTH);
  }

}
