package marimoiro.github


import org.kohsuke.github.GHCommitState
import org.kohsuke.github.GHIssueBuilder
import org.kohsuke.github.GHLabel
import org.kohsuke.github.GHTagObject
import org.kohsuke.github.GHUser
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GHIssueState

import com.cloudbees.jenkins.GitHubRepositoryName 

import groovy.transform.InheritConstructors

@InheritConstructors
class Repository extends Raw 
{

  public String getFullName()
  {
    return raw.fullName
  }
  
  public GHRepository getRepository()
  {
    return raw as GHRepository
  }

  /**
   * Forked repositories have a 'parent' attribute that specifies the repository this repository is directly forked from.
   * @return
   */
  public Repository getParent()
  {
    def p = repository.parent
    if(p == null) return null
    return new Repository(p)
  }

  /**
   * Forked repositories have a 'parent' attribute that specifies the repository this repository is directly forked from.
   * @return
   */
  public Repository getSource()
  {
    def p = repository.source
    if(p == null) return null
    return new Repository(p)
  }

  /* Issues */

  /**
   * Creates a issue
   * @param title
   * @param body
   * @param assignees
   * @param labels
   * @return
   */
  public Issue createIssue(String title, String body,List assignees = null,List labels = null)
  {
    GHIssueBuilder builder = repository.createIssue(title).body(body)

    if (assignees != null) {
      assignees.each {
        if (it instanceof String) {
          builder.assignee((String)it)
        } else if (it instanceof User) {
          builder.assignee((GHUser)(it.raw))
        } else if (it instanceof GHUser) {
          builder.assignee((GHUser)it)
        }
      }
    }

    if(labels != null){
      labels.each {
        if (it instanceof String) {
          builder.label((String)it)
        } else if (it instanceof Label) {
          builder.label((GHLabel)(it.label))
        } else if (it instanceof GHLabel) {
          builder.label((GHLabel)it)
        }
      }
    }

    return  new Issue(builder.create())

  }
  public int getOpenIssueCount()
  {
    return  repository.getOpenIssueCount()
  }

  /* PullRequest */

  /**
   * create a pull request
   * @param title
   * @param head
   * @param base
   * @param body
   * @param maintainerCanModify
   * @param draft
   * @return
   */
  public PullRequest createPullRequest(String title, String head, String base, String body, boolean maintainerCanModify = true, boolean draft = false)
  {
    def req = repository.createPullRequest(title,head,base,body,maintainerCanModify,draft)
    return new PullRequest(req)
  }

  /**
   * Gets a pull request from number
   * @param number
   * @return
   */
  public PullRequest getPullRequest(int number)
  {
    return new PullRequest(repository.getPullRequest(number))
  }

  /**
   * Gets pull requests
   * @param state
   * @return
   */
  public PullRequest getPullRequests(GHIssueState state = GHIssueState.OPEN)
  {
    return repository.getPullRequests(state).collect{ new PullRequest(it) }
  }

  /**
   * Get pull requests
   * @param state
   * @return
   */
  public PullRequest getPullRequests(String state)
  {
    switch (state.toLowerCase())
    {
      case "all":
        return getPullRequests(GHIssueState.ALL);
      case "open":
        return getPullRequests(GHIssueState.OPEN);
      case "closed":
        return getPullRequests(GHIssueState.CLOSED);
      default:
        throw new IllegalArgumentException("allowed type is all,open or closed")
 
    }
    
  }

  /* Labels */

  /**
   * creates a label
   * @param name
   * @return
   */
  public Label createLabel(String name)
  {
    return new Label(repository.createLabel(name, Color.randomPastelRgbCode()))
  }

  /**
   * creates a label
   * @param name
   * @param color
   * @param description
   * @return
   */
  public Label createLabel(String name, String color, String description = "")
  {
    return new Label(repository.createLabel(name, color, description))
  }

  /**
   * gets a label
   * @param name
   * @return
   */
  public Label getLabel(String name)
  {
    return new Label(repository.getLabel(name))
  }

  /**
   * get labels in the repository
   * @return
   */
  public List<Label> getLabels()
  {
    return repository.listLabels().collect { new Label(it) }
  }

  /* branches */

  /***
   * get branch
   * @param name
   * @return
   */
  public Branch getBranch(String name) {
    return new Branch(repository.getBranch(name))
  }

  public Ref getBranchRef(String name)
  {
    return getRef("refs/heads/${name}")
  }

  /**
   * get branches
   * @return
   */
  public Map<String,Branch> getBranches() {
    return repository.branches.collectEntries { k,b ->
      new Branch(b)
    }
  }


  /* Refs */

  /**
   * Gets ref
   * @param refName
   * @return
   */
  public Ref getRef(String refName)
  {
    return new Ref(repository.getRef(refName))
  }

  /**
   * Creates a ref
   * @param refName
   * @param sha
   * @return
   */
  public Ref createRef(String refName,String sha)
  {
    return new Ref(repository.createRef(refName,sha))
  }

  /**
   * Create Branch
   * @param branchName
   * @param sha
   * @return
   */
  public Ref createBranch(String branchName, String sha)
  {
    return createRef("refs/heads/" + refName,sha)
  }

  /**
   * Create a branch
   * @param branchName new branch name
   * @param fromBranch base branch name
   * @return
   */
  public Ref createBranchFromBranch(String branchName, String fromBranch)
  {
    return createRef("refs/heads/" + refName, repository.getRef("refs/heads/" + fromBranch).ref)
  }
  
  public Ref createRefFromRef(String refName,String fromRef)
  {
    return createRef( refName, repository.getRef(fromRef).ref )
  }

  /**
   * gets refs
   * @param type
   * @return
   */
  public List<Ref> getRefs(String type = "all")
  {
    
    switch(type.toLowerCase())
    {
      case "all":
        return repository.refs.collect { new Ref(it) }
      case "commits":
      case "tags":
        return repository.getRefs(type).collect { new Ref(it) }
      default:
        throw new IllegalArgumentException("allowed type is all,commits or tags")
    }
    
  }

  /**
   * creates a tag
   * @param tag
   * @param message
   * @param object
   * @param type
   * @return
   */
  public TagObject createTag(String tag, String message, String object, String type = "commit") {

    return new TagObject(repository.createTag(tag,message,object,type))
  }

  /**
   * get a tag
   * NOTE: when you want to delete tag. use getTagRef
   * @param sha
   * @return
   */
  public TagObject getTagObject(String sha)
  {
    return new TagObject(repository.getTagObject(sha))
  }

  /**
   * gets a ref by tag name
   * @param name
   * @return
   */
  public Ref getTagRef(String name)
  {
    return getRef("refs/tags/${name}")
  }

  /**
   * create a ref by tag name
   * @param name
   * @param sha
   * @return
   */
  public Ref createTagRef(String name,String sha)
  {
    return createRef("refs/tags/${name}",sha)
  }

  /**
   * list tags
   * @return
   */
  public List<Tag> listTags() {
    return repository.listTags().collect { new Tag(it) }
  }
  
  /* Collaborators */

  /**
   * list collaborators
   * @return
   */
  public List<User>	getCollaborators()
  {
    return repository.collaborators.toArray().toList().collect { new User(it) }
  }

  /* commit */

  public Compare getCompare(String id1,String id2)
  {
    return new Compare( repository.getCompare(id1,id2))
  }

  public Compare getCompare(Ref ref1,Ref ref2)
  {
    return getCompare(ref1.sha,ref2.sha)
  }




  /*  commit status */

  /**
   * create commit status
   * @param sha1
   * @param state
   * @param targetUrl
   * @param description
   * @param context
   * @return
   */
  CommitStatus createCommitStatus(String sha1, GHCommitState state, String targetUrl, String description, String context = null)
  {
    return  repository.createCommitStatus(sha1,state,targetUrl,description,context)
  }

  /**
   * create commit status
   * @param sha1
   * @param state
   * @param targetUrl
   * @param description
   * @param context
   * @return
   */
  CommitStatus createCommitStatus(String sha1, String state, String targetUrl, String description, String context = null)
  {
    return  repository.createCommitStatus(sha1,GHCommitState.valueOf(state.toUpperCase()),targetUrl,description,context)
  }

  /* utils */

  /**
   * find repository api from url
   * @param uri
   * @return
   */
  public static List<Repository> findAll(String uri)
  {
    return GitHubRepositoryName.create(uri).resolve().collect { new Repository(it) }
  }

  /**
   * find first repository api from url
   * @param uri
   * @return
   */
  public static Repository find(String uri)
  {
    return new Repository(GitHubRepositoryName.create(uri).resolveOne())
  }

  /**
   * delete the repository
   */
  public void delete()
  {
    repository.delete()
  }

  public void archive() {
    repository.archive()
  }

}
