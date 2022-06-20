package marimoiro.github

import org.kohsuke.github.GHPullRequest
import org.kohsuke.github.GHPullRequestReviewEvent

import groovy.transform.InheritConstructors

@InheritConstructors
class PullRequest extends Issue {


    /**
     * Gets raw GHPullRequest
     * @return raw GHPullRequest
     */
    public GHPullRequest getPullRequest() {
        return raw as GHPullRequest
    }

    /**
     * Merges the pull request
     * @param msg message
     * @param sha check sha
     */
    public void merge(String msg = "", String sha = null) {
        if (sha != null) {
            pullRequest.merge(msg, sha)
        } else {
            pullRequest.merge(msg)
        }
    }

    /**
     * Gets the head ref
     * whe you want to delete head.
     * head.ref.delete()
     * @return head ref
     */
    public Ref getHeadRef() {
        return new Ref(pullRequest.repository.getRef("refs/heads/" + pullRequest.head.ref))
    }

    /**
     * Gets the base ref
     * @return base ref
     */
    public Ref getBaseRef() {
        return new Ref(pullRequest.repository.getRef("refs/heads/" + pullRequest.base.ref))
    }

    /**
     * get Compare between [base sha..head sha]
     * @return
     */
    public Compare getCompare() {
        return new Compare(pullRequest.repository.getCompare(pullRequest.base.sha,pullRequest.head.sha))
    }

    /* branch */

    /**
     * Sets the base branch
     * @param branch
     * @return updated pull request
     */
    public PullRequest setBaseBranch(String branch) {
        return new PullRequest(pullRequest.setBaseBranch(branch))
    }

    public void updateBranch() {
        pullRequest.updateBranch()
    }

    /* reviewers */

    /**
     * requests reviewers
     * @param reviewers
     */
    public void requestReviewers(User[] reviewers) {
        requestReviewers(reviewers)
    }

    /**
     * requests reviewers
     * @param reviewers
     */
    public void requestReviewers(Collection<User> reviewers) {
        pullRequest.requestReviewers(reviewers.collect { it.raw })
    }

    /**
     * requests reviewers
     * @param reviewers
     */
    public void requestReviewers(String[] reviewers) {
        def list = reviewers.collect { it.toLowerCase() }
        def users = repository.collaborators.findAll { list.contains(it.name) }

        requestReviewers(users)
    }

    /* comments */

    /**
     * Approves the pull request
     */
    public void approve(String body) {
        pullRequest.createReview().create().submit(body, GHPullRequestReviewEvent.APPROVE)
    }

    /**
     * list review comments
     * @return review comments
     */
    public List<Comment> listReviewComments() {
        return pullRequest.listReviewComments().collect { new PullRequestReviewComment(it) }
    }

}
