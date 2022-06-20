package marimoiro.github

import groovy.transform.InheritConstructors
import org.kohsuke.github.GHCommitState
import org.kohsuke.github.GHCommitStatus

@InheritConstructors
class CommitStatus extends Raw {

    public GHCommitStatus getStatus(){
        return raw
    }

    public String getContext(){
        return status.context
    }

    public User getCreator(){
        return new User(status.creator)
    }

    public String getDescription(){
        return status.description
    }

    public GHCommitState getState(){
        return status.state
    }
}
