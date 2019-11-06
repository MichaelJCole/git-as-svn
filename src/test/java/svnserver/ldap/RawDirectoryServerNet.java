/*
 * This file is part of git-as-svn. It is subject to the license terms
 * in the LICENSE file found in the top-level directory of this distribution
 * and at http://www.gnu.org/licenses/gpl-2.0.html. No part of git-as-svn,
 * including this file, may be copied, modified, propagated, or distributed
 * except according to the terms contained in the LICENSE file.
 */
package svnserver.ldap;

import com.unboundid.ldap.listener.InMemoryListenerConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * @author Marat Radchenko <marat@slonopotamus.org>
 */
final class RawDirectoryServerNet implements DirectoryServerNet {

  @NotNull
  static final DirectoryServerNet instance = new RawDirectoryServerNet();

  @NotNull
  @Override
  public InMemoryListenerConfig getListenerConfig() throws Exception {
    return InMemoryListenerConfig.createLDAPConfig(getUrlSchema());
  }

  @NotNull
  @Override
  public String getUrlSchema() {
    return "ldap";
  }

  @Nullable
  @Override
  public Path getCertificatePath() {
    return null;
  }

  @Override
  public String toString() {
    return getUrlSchema();
  }
}
