package marimoiro.github

import org.kohsuke.github.GitHub
import org.kohsuke.github.GHRef
import org.kohsuke.github.GHRef.GHObject
import org.kohsuke.github.GHPullRequestReviewEvent

class Ref implements Serializable {

  private String ref, url
  RefObject refObject

  class RefObject implements Serializable
  {
    private String sha
    private String type
    private URL url

    public RefObject(GHRef.GHObject ref)
    {
      sha = ref.sha
      type = ref.type
      url = ref.url
    }

    public String getSha()
    {
      return sha
    }

    public String getType()
    {
      return type
    }

    public URL getUrl()
    {
      return url
    }

  }
  
  private String rootHost
  private transient GitHub rootObject;

  Ref(GHRef ref)
  {
    this.ref = ref.ref
    this.url = ref.url
    rootHost = ref.root.apiUrl
    rootObject = ref.root

    refObject = new RefObject(ref.object)
  }

  private GitHub getRoot()
  {
    if(rootObject == null) // TODO repair by github configure name
    {
      rootObject = GitHubClient.findRaw(rootHost)
    }

    return rootObject
  }

  // copy from https://github.com/hub4j/github-api/blob/main/src/main/java/org/kohsuke/github/GHRef.java#L82
  void delete() throws IOException 
  {
    root.createRequest().method("DELETE").withUrlPath(url).send();
  }

  // copy from 
  /**
    * Updates this ref to the specified commit.
    *
    * @param sha
    *            The SHA1 value to set this reference to
    * @param force
    *            Whether or not to force this ref update.
    * @throws IOException
    *             the io exception
    */
  public void updateTo(String sha, Boolean force) throws IOException {
      root.createRequest()
              .method("PATCH")
              .with("sha", sha)
              .with("force", force)
              .withUrlPath(url)
              .fetch(GHRef);
  }
  
  String getRef()
  {
    return ref
  }
  
  String getUrl()
  {
    return url.toString()
  }

  String getSha()
  {
    return refObject.sha
  }

  RefObject getRefObject()
  {
    return refObject
  }

}
