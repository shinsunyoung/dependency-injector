package model;

import com.intellij.openapi.actionSystem.AnActionEvent;
import component.FileInfo;
import component.Selector;

public class UserRequest {

  private String selectedText;
  private FileInfo fileInfo;

  public UserRequest(String selectedText, FileInfo fileInfo) {
    this.selectedText = selectedText;
    this.fileInfo = fileInfo;
  }

  public String getSelectedText() {
    return selectedText;
  }

  public FileInfo getFileInfo() {
    return fileInfo;
  }
}
