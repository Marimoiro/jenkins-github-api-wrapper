package marimoiro.github

import org.kohsuke.github.GHTag
import org.kohsuke.github.GHTagObject

class TagObject implements Serializable {

    private GHTagObject tag
    public TagObject(GHTagObject tag)
    {
        this.tag = tag
    }

    public GHTagObject getRaw(){
        return tag
    }

    public String getMessage(){
        return message
    }

    public Repository getOwner(){
        return new Repository(tag.owner)
    }

    public String getSha(){
        return tag.sha
    }

    public String getTag() {
        return tag.tag
    }

    public String getUrl() {
        return tag.url
    }

    private void writeObject(java.io.ObjectOutputStream out)
    {
        GitHubClient.write(out,tag)
    }

    private void readObject(java.io.ObjectInputStream inStream)
    {
        label = GitHubClient.read(inStream,GHTagObject.class)
    }
}
