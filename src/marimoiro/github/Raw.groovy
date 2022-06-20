package marimoiro.github

import java.util.Date
import java.net.URL

import org.kohsuke.github.GHObject

/**
 * base class of this library
 */
public abstract class Raw implements Serializable {
  
  protected transient GHObject rawObject



  public Raw(GHObject raw)
  {
    this.rawObject = raw
  }

  /**
   * Gets raw object
   */
  protected final GHObject getRaw()
  {
    return rawObject
  }
  
  public final Date getCreatedAt()
  {
    return rawObject.getCreatedAt()
  }

  /**
   * Gets html url
   * @return
   */
  public final URL getHtmlUrl()
  {
    return rawObject.getHtmlUrl()
  }

  /**
   * Id
   * @return
   */
  public final long getId()
  {
    return rawObject.getId()
  }

  /**
   * node id
   * @return
   */
  public final String getNodeId()
  {
    return rawObject.getNodeId()
  }

  /**
   * Gets updated at
   * @return
   */
  public final Date getUpdatedAt()
  {
    return rawObject.getUpdatedAt
  }

  /**
   * Gets url
   * @return
   */
  public final URL getUrl()
  {
    return rawObject.getUrl()
  }
  
  public String toString()
  {
    return rawObject.toString()
  }
  
  protected java.lang.Class getRawClass()
  {
    return GHObject.class
  }

  private void writeObject(java.io.ObjectOutputStream out)
  {
    GitHubClient.write(out,raw)
  }

  private void readObject(java.io.ObjectInputStream inStream)
  {
    rawObject = GitHubClient.read(inStream,getRawClass())
  }
}
