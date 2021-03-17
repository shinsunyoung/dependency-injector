package component;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import model.BuildType;

public class FileInfo {

  private final String name;
  private final BuildType build;

  public FileInfo(AnActionEvent e) {
    VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    this.name = vFile != null ? vFile.getName() : "";
    this.build = BuildType.findByExtension(name);
  }

  public String getName() {
    return name;
  }

  public BuildType getBuild() {
    return build;
  }
}
