package service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import model.Dependency;
import model.DependencyName;
import model.Version;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class MavenCodeParserTest {
  @DisplayName("Maven 소스를 가져온다.")
  @Test
  public void getGradleSource() throws IOException {
    // given
    MavenCodeParserImpl parser = new MavenCodeParserImpl();

    DependencyName dependencyName = new DependencyName("lombok", "org.projectlombok");
    Version version = new Version("1.18.14");

    // when
    String gradleSource = parser.getDependencyCode(new Dependency(dependencyName, version));

    // then
    assertThat(gradleSource).contains("<groupId>org.projectlombok</groupId>");
    assertThat(gradleSource).contains("<artifactId>lombok</artifactId>");
    assertThat(gradleSource).contains("<version>1.18.14</version>");
  }
}