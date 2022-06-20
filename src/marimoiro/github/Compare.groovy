package marimoiro.github

import org.kohsuke.github.GHCompare
import org.kohsuke.github.GHObject
import org.kohsuke.github.GHTag

import marimoiro.github.Commit
import org.kohsuke.github.PagedIterable

class Compare implements Serializable {
    private GHCompare compare


    GHCompare compare

    public GHCompare getCompare(){
        return compare
    }

    public GHCompare getRaw() {
        return compare
    }

    public Compare(GHCompare compare) {
        this.compare = compare
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public URL getUrl(){
        return compare.url
    }

    /**
     * Gets html url.
     *
     * @return the html url
     */
    public URL getHtmlUrl(){
        return compare.htmlUrl
    }

    /**
     * Gets permalink url.
     *
     * @return the permalink url
     */
    public URL getPermalinkUrl() {
        return compare.permalinkUrl
    }

    /**
     * Gets diff url.
     *
     * @return the diff url
     */
    public URL getDiffUrl() {
        return compare.diffUrl
    }

    /**
     * Gets patch url.
     *
     * @return the patch url
     */
    public URL getPatchUrl() {
        return compare.patchUrl;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public GHCompare.Status getStatus() {
        return compare.status;
    }

    /**
     * Gets ahead by.
     *
     * @return the ahead by
     */
    public int getAheadBy() {
        return compare.aheadBy
    }

    /**
     * Gets behind by.
     *
     * @return the behind by
     */
    public int getBehindBy() {
        return compare.behindBy
    }

    /**
     * Gets total commits.
     *
     * @return the total commits
     */
    public int getTotalCommits() {
        return compare.totalCommits
    }


    public List<Commit> getCommits(boolean includeFileInfo = false) {
        return compare.commits.collect {new Commit(it,includeFileInfo) }
    }

    public List<CommitFile> getFiles()
    {
        return compare.files.collect { new CommitFile(it) }
    }



    private void writeObject(java.io.ObjectOutputStream out)
    {
        GitHubClient.write(out,compare)
    }

    private void readObject(java.io.ObjectInputStream inStream)
    {
        compare = GitHubClient.read(inStream, GHCompare.class)
    }
}


