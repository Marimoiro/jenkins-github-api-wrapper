package marimoiro.github

import org.kohsuke.github.GHCompare
import org.kohsuke.github.GHCompare.InnerCommit

/**
 * The type InnerCommit.
 */
public class GitCommit implements Serializable {
    private String url, sha, message;
    private org.kohsuke.github.GitUser author, committer;
    private GHCompare.Tree tree;


    public GitCommit(InnerCommit commit)
    {
        this.url = commit.url
        this.sha = commit.sha
        this.message = commit.message
        this.author = commit.author
        this.committer = commit.author

        this.tree = commit.tree
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets sha.
     *
     * @return the sha
     */
    public String getSha() {
        return sha;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public GitUser getAuthor() {
        return author;
    }

    /**
     * Gets committer.
     *
     * @return the committer
     */
    public GitUser getCommitter() {
        return committer;
    }

    /**
     * Gets tree.
     *
     * @return the tree
     */
    public GHCompare.Tree getTree() {
        return tree;
    }
}