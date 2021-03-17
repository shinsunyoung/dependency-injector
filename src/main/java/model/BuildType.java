package model;

import com.intellij.openapi.components.ServiceManager;
import java.util.Arrays;
import service.CodeParser;
import service.GradleCodeParser;
import service.MavenCodeParser;
import service.impl.GradleCodeParserImpl;
import service.impl.MavenCodeParserImpl;

public enum BuildType {
  GRADLE(ServiceManager.getService(GradleCodeParser.class), "gradle"),
  MAVEN(ServiceManager.getService(MavenCodeParser.class), "xml");

  private CodeParser codeParser;
  private String fileExtension;

  public CodeParser getCodeParser() {
    return codeParser;
  }

  BuildType(CodeParser codeParser, String fileExtension) {
    this.codeParser = codeParser;
    this.fileExtension = fileExtension;
  }

  public static BuildType findByExtension(String fileName) {
    return Arrays.stream(BuildType.values())
        .filter(e -> fileName.contains(e.fileExtension))
        .findAny()
        .orElse(BuildType.GRADLE);
  }
}
