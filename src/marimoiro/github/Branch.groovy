package marimoiro.github

import org.kohsuke.github.GHBranch

class Branch implements Serializable {

    private GHBranch branch;

    public GHBranch getRaw() { // this library rule
        return branch
    }

    public GHBranch getBranch() {
        return branch
    }

    public Branch(GHBranch branch)
    {
        this.branch = branch
    }

    // todo
    // public BranchProtectionBuilder enableProtection()

    public String getName(){
        return branch.name
    }

    public Repository getRepository() {
        return new Repository(branch.owner)
    }

    public String getSha1(){
        return branch.getSHA1()
    }

    public String getIsProtected() {
        return branch.isProtected()
    }

    public void merge(String head, String commitMessage) {
        branch.merge(head,commitMessage)
    }

    public void merge(Branch head, String commitMessage) {
        branch.merge(head.branch,commitMessage)
    }
}
