package service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import model.Dependency;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import service.impl.DependencyParser;

public class DependencyParserTest {

  @DisplayName("검색어와 관련 있는 순서대로 의존성을 3개 가져온다. - 이름")
  @Test
  public void getDependencies() throws IOException {
    // given
    DependencyParser parser = new DependencyParser();

    // when
    List<Dependency> dependencies = parser.parseDependencies("lombok");

    // then
    assertThat(dependencies.size()).isEqualTo(3);

    List<String> displayNames = Arrays.asList("lombok(org.projectlombok)",
        "lombok-maven-plugin(org.projectlombok)",
        "lombok(io.mateu)");

    for (int i = 0; i < 3; i++) {
      assertThat(dependencies.get(i).getDisplayName()).isEqualTo(displayNames.get(i));
    }
  }

  @DisplayName("의존성은 가장 인기있는 버전을 가져온다.")
  @Test
  public void getMostFamousDependency() throws IOException {
    // given
    DependencyParser parser = new DependencyParser();

    // when
    List<Dependency> dependencies = parser.parseDependencies("lombok");

    // then
    assertThat(dependencies.get(0).getMostPopularVersion()).isEqualTo("1.18.12");
  }

  @DisplayName("총 검색 갯수가 3개 미만이면 모두 가져온다.")
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