package marimoiro.github

import org.kohsuke.github.GHCommit
import org.kohsuke.github.GHCompare

class Commit implements Serializable {
    private ShortInfo shortInfo

    private URL url
    private URL htmlUrl

    private int linesAdded
    private int linesChanged
    private int linesDeleted

    private String sha1

    private List<CommitFile> files

    private GitCommit gitCommit


    /**
     *
     * @param commit raw commit
     * @param includeFileInfo If true, this instance has the value of file
     */
    public Commit(GHCompare.Commit commit,boolean includeFileInfo) {

        if(shortInfo != null) {
            this.shortInfo = new ShortInfo(commit.getCommitShortInfo())
        }

        this.url = commit.url
        this.htmlUrl = commit.htmlUrl
        this.linesAdded = commit.linesAdded
        this.linesChanged = commit.linesChanged
        this.linesDeleted = commit.linesDeleted
        this.sha1 = commit.getSHA1()
        this.gitCommit = new GitCommit(commit.commit)
        if(includeFileInfo) {
            this.files = commit.files.collect {new CommitFile(it)}
        }

    }

    ShortInfo getShortInfo() {
        return shortInfo
    }

    User getAuthor() {
        return author
    }

    Date getAuthoredDate() {
        return authoredDate
    }

    Date getCommitDate() {
        return commitDate
    }

    User getCommitter() {
        return committer
    }

    URL getUrl() {
        return url
    }

    URL getHtmlUrl() {
        return htmlUrl
    }

    int getLinesAdded() {
        return linesAdded
    }

    int getLinesChanged() {
        return linesChanged
    }

    int getLinesDeleted() {
        return linesDeleted
    }

    String getSha1() {
        return sha1
    }

    List<File> getFiles() {
        return files
    }

    class ShortInfo implements Serializable {
        private GitUser author
        private Date authoredDate
        private int commentCount
        private Date commitDate
        private GitUser committer
        private String message

        ShortInfo(org.kohsuke.github.GHCommit.ShortInfo info) {
            this.author = info.author
            this.authoredDate = info.authoredDate
            this.commentCount = info.commentCount
            this.commitDate = info.commitDate
            this.committer = info.committer
            this.message = info.message
        }

        GitUser getAuthor() {
            return author
        }

        Date getAuthoredDate() {
            return authoredDate
        }

        int getCommentCount() {
            return commentCount
        }

        Date getCommitDate() {
            return commitDate
        }

        GitUser getCommitter() {
            return committer
        }

        String getMessage() {
            return message
        }
    }




}
