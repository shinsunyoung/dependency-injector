package sevice;

import java.io.IOException;
import java.util.List;
import model.Dependency;

public interface Parser {

  List<Dependency> getDependencies(String keyword) throws IOException;
}
