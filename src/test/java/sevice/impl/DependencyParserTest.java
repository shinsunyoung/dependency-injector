package sevice.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import model.Dependency;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class DependencyParserTest {

  @DisplayName("검색어와 관련 있는 순서대로 의존성을 5개 가져온다.")
  @Test
  public void getDependencies() {
    // given
    DependencyParser parser = new DependencyParser();

    // when
    List<Dependency> dependencies = parser.getDependencies("lombok");

    // then
    assertThat(dependencies.size()).isEqualTo(5);

    List<String> projectNames = Arrays.asList("lombok", "lombok-maven-plugin", "lombok", "lombok-maven", "lombok-pg");
    for (int i = 0; i < 5; i++) {
      assertThat(dependencies.get(i).getName().getProjectName()).isEqualTo(projectNames.get(i));
    }
  }

  @DisplayName("총 검색 갯수가 5개 미만이면 모두 가져온다.")
  @Test
  public void getAllIfTotalLessThan5() {
    // given
    DependencyParser parser = new DependencyParser();

    // when
    List<Dependency> dependencies = parser.getDependencies("asdf");

    // then
    assertThat(dependencies.size()).isEqualTo(1);
    assertThat(dependencies.get(0).getName().getPackageName()).isEqualTo("org.webjars.npm");
    assertThat(dependencies.get(0).getName().getProjectName()).isEqualTo("asdf");
  }
}