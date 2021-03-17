package service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import model.Dependency;
import model.DependencyName;
import model.Version;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class GradleCodeParserTest {
  @DisplayName("Gradle 소스를 가져온다.")
  @Test
  public void getGradleSource() throws IOException {
    // given
    GradleCodeParserImpl parser = new GradleCodeParserImpl();

    DependencyName dependencyName = new DependencyName("lombok", "org.projectlombok");
    Version version = new Version("1.18.14");

    // when
    String gradleSource = parser.getDependencyCode(new Dependency(dependencyName, version));

    // then
    assertThat(gradleSource).contains("compileOnly group: 'org.projectlombok', name: 'lombok', version: '1.18.14'");
  }
}