package util;

public class ParserConfig {
  public static final String DEPENDENCY_REQUEST_URL = "https://mvnrepository.com/search?q=";
  public static final String VERSION_REQUEST_URL = "https://mvnrepository.com/artifact/{package}/{project}";
  public static final String CODE_REQUEST_URL = "https://mvnrepository.com/artifact/{package}/{project}/{version}";

  public static final String ALL_VERSION_SELECTOR = "tbody tr";
  public static final String ALL_DEPENDENCY_NAME_LIST_SELECTOR = ".im-subtitle";
  public static final String VERSION_NAME_LIST_SELECTOR = "td a.vbtn.release";
  public static final String VERSION_USAGES_LIST_SELECTOR = "td:nth-last-child(2)";
  public static final String USED_VERSION_SELECTOR = "a";
  public static final String GRADLE_SOURCE_SELECTOR = "#gradle-div textarea";
  public static final String MAVEN_SOURCE_SELECTOR = "#maven-div textarea";

}
