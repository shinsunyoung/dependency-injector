package component;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

public class FileInfo {

  private String name;
  private String build;

  public FileInfo(AnActionEvent e) {
    VirtualFile vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    this.name = vFile != null ? vFile.getName() : "";
  }

  public String getBuildName() {
    if (this.name.contains(".xml")) {
      this.build = "maven";
    } else {
      this.build = "gradle";
    }
    return this.build;
  }
}
