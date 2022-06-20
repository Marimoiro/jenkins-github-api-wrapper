package marimoiro.github;

import groovy.transform.InheritConstructors
import org.kohsuke.github.GHPullRequestReviewComment

@InheritConstructors
class PullRequestReviewComment extends Comment
{

    public  GHPullRequestReviewComment getReviewComment()
    {
        return raw
    }
    @Override
    public Issue getParent()
    {
        return new PullRequest(reviewComment.getParent())
    }

    public int getPosition()
    {
        return reviewComment.position
    }

    public String getPath()
    {
        return reviewComment.path
    }

    public int getOriginalPosition()
    {
        return reviewComment.originalPosition
    }

    public PullRequestReviewComment reply(String body)
    {
        return reviewComment.reply(body)
    }
}