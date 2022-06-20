package marimoiro.github

/**
 * a file in a commit
 */
public class CommitFile implements Serializable
{
    private URL blobUrl
    private String fileName

    private int linesAdded
    private int linesChanged
    private int linesDeleted

    private String patch
    private String previousFilename

    URL rawUrl

    String sha
    String status

    URL getBlobUrl() {
        return blobUrl
    }

    String getFileName() {
        return fileName
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

    String getPatch() {
        return patch
    }

    String getPreviousFileName() {
        return previousFilename
    }

    URL getRawUrl() {
        return rawUrl
    }

    String getSha() {
        return sha
    }

    String getStatus() {
        return status
    }

    public CommitFile(org.kohsuke.github.GHCommit.File file) {
        this.blobUrl = file.blobUrl
        this.fileName = file.fileName
        this.linesAdded = file.linesAdded
        this.linesChanged = file.linesChanged
        this.linesDeleted = file.linesDeleted
        this.patch = file.patch
        this.previousFilename = file.previousFilename
        this.rawUrl = file.rawUrl
        this.sha = file.sha
        this.status = file.status
    }
}