package marimoiro.github

import org.kohsuke.github.GHTag

class Tag implements Serializable{

    private GHTag tag

    public Tag(GHTag tag)
    {
        this.tag = tag
    }

    String getName(){
        return tag.name
    }

    Repository getOwner(){
        return new Repository(tag.owner)
    }

    private void writeObject(java.io.ObjectOutputStream out)
    {
        GitHubClient.write(out,tag)
    }

    private void readObject(java.io.ObjectInputStream inStream)
    {
        tag = GitHubClient.read(inStream,GHTag.class)
    }
}
