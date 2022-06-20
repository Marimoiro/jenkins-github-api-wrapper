package marimoiro.github;

import java.net.URL
import org.kohsuke.github.GHPullRequest
import org.kohsuke.github.GHPullRequestReviewEvent

import groovy.transform.InheritConstructors

/**
* issue comment class 
*/
@InheritConstructors
class Comment extends Raw
{
    /**
    *  delete this comment
    */
    public void delete()
    {
        raw.delete()
    }

    /**
     *
     * @return Gets  body
     */
    public String getBody()
    {
        return raw.body
    }

    /**
     *
     * @return Gets parent issue
     */
    public Issue getParent()
    {
        return new Issue(raw.getParent())
    }

    /**
     * Gets the issue to which this comment is associated.
     * @return user
     */
    public User getUser()
    {
        return new User(raw.user)
    }

    /**
     * Updates the body of the issue comment.
     * @param body
     */
    public void update(String body)
    {
        return raw.update(body)
    }

}