package svnserver.server.command;

import org.jetbrains.annotations.NotNull;

/**
 * Update between revisions.
 * <pre>
 * diff
 *    params:   ( [ rev:number ] target:string recurse:bool ignore-ancestry:bool
 *    url:string ? text-deltas:bool ? depth:word )
 *    Client switches to report command set.
 *    Upon finish-report, server sends auth-request.
 *    After auth exchange completes, server switches to editor command set.
 *    After edit completes, server sends response.
 *    response: ( )
 * </pre>
 *
 * @author Marat Radchenko <marat@slonopotamus.org>
 */
public final class DiffParams extends DeltaParams {

  public DiffParams(@NotNull int[] rev,
                    @NotNull String target,
                    boolean recurse,
                    boolean ignoreAncestry,
                    @NotNull String url,
                    boolean textDeltas,
                    @NotNull String depth) {
    super(rev, target, url, textDeltas, recurse, depth, false, ignoreAncestry);
  }
}
