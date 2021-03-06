/*
 * This file is part of git-as-svn. It is subject to the license terms
 * in the LICENSE file found in the top-level directory of this distribution
 * and at http://www.gnu.org/licenses/gpl-2.0.html. No part of git-as-svn,
 * including this file, may be copied, modified, propagated, or distributed
 * except according to the terms contained in the LICENSE file.
 */
package svnserver.repository.git;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import svnserver.Loggers;
import svnserver.config.ConfigHelper;
import svnserver.context.Shared;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Git submodules list.
 *
 * @author Artem V. Navrotskiy <bozaro@users.noreply.github.com>
 */
public final class GitSubmodules implements Shared {
  @NotNull
  private static final Logger log = Loggers.git;
  @NotNull
  private final Set<Repository> repositories = new CopyOnWriteArraySet<>();

  GitSubmodules() {
  }

  public GitSubmodules(@NotNull Path basePath, @NotNull Collection<String> paths) throws IOException {
    for (String path : paths) {
      final Path file = ConfigHelper.joinPath(basePath, path);
      if (!Files.exists(file))
        throw new FileNotFoundException(file.toString());

      log.info("Linked repository path: {}", file);
      repositories.add(new FileRepository(file.toFile()));
    }
  }

  @Nullable GitObject<RevCommit> findCommit(@NotNull ObjectId objectId) throws IOException {
    for (Repository repo : repositories)
      if (repo.getObjectDatabase().has(objectId))
        return new GitObject<>(repo, new RevWalk(repo).parseCommit(objectId));

    return null;
  }

  void register(@NotNull Repository repository) {
    repositories.add(repository);
  }

  void unregister(@NotNull Repository repository) {
    repositories.remove(repository);
  }
}
