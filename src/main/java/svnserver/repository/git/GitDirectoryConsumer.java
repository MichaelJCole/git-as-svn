package svnserver.repository.git;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import svnserver.repository.VcsDirectoryConsumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Данные о директории для корректного обновления данных.
 *
 * @author a.navrotskiy
 */
public class GitDirectoryConsumer implements VcsDirectoryConsumer {
  public enum Mode {
    Open,
    Create,
    Modify
  }
  @NotNull
  private final String path;
  @NotNull
  private final Map<String, String> props;
  @Nullable
  private final GitTreeEntry source;
  @NotNull
  private final Mode mode;

  public GitDirectoryConsumer(@NotNull String path, @Nullable GitFile file, @NotNull Mode mode) throws IOException {
    this.path = path;
    this.mode = mode;
    if (file != null) {
      this.props = new HashMap<>(file.getProperties(false));
      this.source = file.getTreeEntry();
    } else {
      this.props = new HashMap<>();
      this.source = null;
    }
  }

  @NotNull
  @Override
  public String getPath() {
    return path;
  }

  @Nullable
  public GitTreeEntry getSource() {
    return source;
  }

  @NotNull
  @Override
  public Map<String, String> getProperties() {
    return props;
  }

  @NotNull
  public Mode getMode() {
    return mode;
  }
}
