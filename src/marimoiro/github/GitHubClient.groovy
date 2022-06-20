package marimoiro.github



import org.kohsuke.github.GitHub
import org.jenkinsci.plugins.github.GitHubPlugin
import org.jenkinsci.plugins.github.config.GitHubServerConfig

import static org.jenkinsci.plugins.github.util.FluentIterableWrapper.from
import static org.jenkinsci.plugins.github.config.GitHubServerConfig.withHost
import static org.jenkinsci.plugins.github.config.GitHubServerConfig.loginToGithub

import com.google.common.base.Optional
import com.google.common.base.Predicate;


class GitHubClient implements Serializable
{
  transient GitHub rawObject
  private String host;


  /**
   * Finds raw GitHub object by host & configure name
   * @param host github host
   * @param name configure name
   * @return GitHub Object
   */
  public static GitHub findRaw(String host,String name = "")
  {
    def loginFunction = loginToGithub();
    if (Objects.isNull(loginFunction)) {
        return null
    }

    def wHost = withHost(host)

    def configs = GitHubPlugin.configuration().configs
                .findAll({ wHost.apply(it) && (name == null || name == "" || it.name == name)})
                .toArray()
    
    if(configs.size() == 0)
    {
      throw new IllegalArgumentException("${host}${name} is not found.");
    }

    return loginFunction.apply(configs.first())
  }

  /**
   * Finds a GithubClient by host
   * @param host github host. default value = api.github.com
   * @return a GithubClient
   */
  private static GitHubClient find(String host = "api.github.com")
  {
    
    return new GitHubClient(findRaw(host))
  }

  /**
   * Finds a GithubClient by host & name
   * @param host github host. default value = api.github.com
   * @param name configure name
   * @return a GitHubClient
   */
  private static GitHubClient find(String host ,String name)
  {
    
    return new GitHubClient(findRaw(host,name))
  }
  
  public GitHubClient(GitHub rawObject)
  {
    this.rawObject = rawObject
    host = rawObject.apiUrl
  }

  /**
   * Gets host
   * @return the host
   */
  public String getHost()
  {
    return host
  }

  /**
   * Gets raw Github Object
   * @return the raw object
   */
  public GitHub getRaw()
  {
    if(rawObject == null)
    {
      rawObject = findRaw(host)
    }
    return rawObject
  }

  /**
   * Gets Api Url
   * @return api url
   */
  String getApiUrl()
  {
    return raw.apiUrl
  }

  /* Repository */

  /**
   *
   Gets the repository object from 'owner/repo' string that GitHub calls as "repository name"
   * @param name owner/repo name
   * @return repository
   */
  Repository getRepository(String name)
  {
    return new Repository(raw.getRepository(name))
  }

  /**
   *
   * @param name
   * @return CreateRepositoryBuilder
   */
  CreateRepositoryBuilder createRepository(String name)
  {
    return new CreateRepositoryBuilder(raw.createRepository(name))
  }

  /* User */
  public User getUser(String login)
  {
    return new User(raw.getUser(login))
  }

  public static void write(java.io.ObjectOutputStream out, Object value)
  {
    GitHub.getMappingObjectWriter().writeValue(out,value)
  }
  
  public static Object read(java.io.ObjectInputStream inStream,java.lang.Class clazz)
  {
    return GitHub.getMappingObjectReader().forType(clazz).readValue(inStream)
  }


}
