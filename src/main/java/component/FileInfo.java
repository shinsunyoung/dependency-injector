package component;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import model.BuildType;

public class FileInfo {

  private String name;
  private BuildType build;

  public FileInfo(AnActionEvent e) {
    VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    this.name = vFile != null ? vFile.getName() : "";
  }

  public BuildType getBuildName() {
    build = name.contains(".xml") ? BuildType.MAVEN : BuildType.GRADLE;
    return build;
  }
}
