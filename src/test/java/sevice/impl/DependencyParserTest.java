package sevice.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import model.Dependency;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class DependencyParserTest {

  @DisplayName("검색어와 관련 있는 순서대로 의존성을 5개 가져온다.")
  @Test
  public void getDependencies() throws IOException {
    // given
    DependencyParser parser = new DependencyParser();

    // when
    List<Dependency> dependencies = parser.parseDependencies("lombok");

    // then
    assertThat(dependencies.size()).isEqualTo(5);

    List<String> displayNames = Arrays.asList("lombok(org.projectlombok)",
        "lombok-maven-plugin(org.projectlombok)",
        "lombok(io.mateu)",
        "lombok-maven(org.projectlombok)",
        "lombok-pg(com.github.peichhorn)");

    for (int i = 0; i < 5; i++) {
      assertThat(dependencies.get(i).getDisplayName()).isEqualTo(displayNames.get(i));
    }
  }

  @DisplayName("총 검색 갯수가 5개 미만이면 모두 가져온다.")
  @Test
  public void getAllIfTotalLessThan5() throws IOException {
    // given
    DependencyParser parser = new DependencyParser();

    // when
    List<Dependency> dependencies = parser.parseDependencies("asdf");

    // then
    assertThat(dependencies.size()).isEqualTo(1);
    assertThat(dependencies.get(0).getDisplayName()).isEqualTo("asdf(org.webjars.npm)");
  }
}