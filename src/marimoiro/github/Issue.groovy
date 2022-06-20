package marimoiro.github

import org.kohsuke.github.GHIssue
import org.kohsuke.github.GHLabel
import org.kohsuke.github.GHIssueState

import groovy.transform.InheritConstructors

@InheritConstructors
public class Issue extends Raw {


    /**
     * Gets raw GHIssue object
     * @return raw GHIssue
     */
    public GHIssue getIssue() {
        return raw
    }

    /* Body */

    /**
     * The description of this issue.
     * @return  the body
     */
    public String getBody() {
        return issue.body
    }

    /**
     * Sets body
     * @param body new body
     */
    public void setBody(String body) {
        issue.setBody(body)
    }

    /**
     * Gets title
     * @return the title
     */
    public String getTitle() {
        return issue.title
    }

    /**
     * Sets title
     * @param title new title
     */
    public void setTitle(String title) {
        issue.setTitle(title)
    }

    /**
     * Gets ID
     * @return the id
     */
    public int getNumber() {
        return issue.number
    }

    /* Label */

    /**
     * Gets labels
     * @return the labels
     */
    public List<Label> getLabels() {
        return issue.labels.collect { new Label(it) }
    }

    /**
     * Add labels
     * @param labels
     * @return updated label list
     */
    public List<Label> addLabels(String... labels) {
        return addLabels(Arrays.asList(labels))
    }

    // copy from https://github.com/hub4j/github-api/blob/4e103a6b192ca88c7948457e2b21ea3e6be9de89/src/main/java/org/kohsuke/github/GHIssue.java#L381
    // groovy can't distinguish addLabels(String... names) between addLabels(GHLabel... labels)
    public List<Label> addLabels(Collection<String> labels) {
        return raw.root.createRequest()
                .with("labels", labels.toArray())
                .method("POST")
                .withUrlPath(raw.issuesApiRoute + "/labels") // issuesApiRoute is protected
                .fetch(GHLabel[].class).collect { new Label(it) }

        //return issue.addLabels(labels.flatten()).collect { new Label(it) }
    }

    /**
     * Add labels
     * @param labels
     * @return updated label list
     */
    public Label addLabelsWithLabel(Label[] labels) {
        return addLabelsWithLabel(labels.toList())
    }

    /**
     * Add labels
     * @param labels
     * @return updated label list
     */
    public List<Label> addLabelsWithLabel(Collection<Label> labels) {
        return addLabels(GHLabel.toNames(labels))
    }

    /**
     * Sets labels
     * @param labels
     * @return updated label list
     */
    public List<Label> setLabels(String[] labels) {
        return issue.setLabels(labels).collect { new Label(it) }
    }

    /**
     * Removes a label
     * @param label
     * @return updated label list
     */
    public List<Label> removeLabel(String label) {
        return issue.removeLabel(label).collect { new Label(it) }
    }

    /**
     * Removes labels
     * @param labels
     * @return updated label list
     */
    public List<Label> removeLabels(String[] labels) {
        return issue.removeLabels(labels).collect { new Label(it) }
    }

    /**
     * Removes labels
     * @param labels
     * @return updated label list
     */
    public List<Label> removeLabels(Label[] labels) {
        return issue.removeLabels(labels.collect { it.label }).collect { new Label(it) }
    }

    /**
     * Removes labels
     * @param labels
     * @return updated label list
     */
    public List<Label> removeLabelsWithLabel(Collection<Label> labels) {
        return issue.removeLabels(labels.collect { it.label }).collect { new Label(it) }
    }

    /* Assinee */

    /* ReOpen/Close */

    /**
     * Reopens the issue
     */
    public void reopen() {
        issue.reopen()
    }

    /**
     * Closes the issue
     */
    public void close() {
        issue.close()
    }


    public Date getClosedAt() {
        return issue.closedAt
    }

    public boolean getIsClosed() {
        return issue.state == GHIssueState.CLOSED
    }

    /* comment */

    /**
     * Posts comment
     * @param message
     */
    public void comment(String message) {
        issue.comment(message)
    }

    /**
     * Repository to which the issue belongs
     * @return
     */
    public Repository getRepository() {
        return new Repository(issue.repository)
    }

    /* 	assignees */

    /**
     * Gets Assignees
     * @return
     */
    public List<User> getAssignees() {
        return issue.assignees.collect { new User(it) }
    }

    /**
     * Add assignees
     * @param assignees
     */
    public void addAssignees(User... assignees) {
        addAssignees(assignees.toList())
    }

    /**
     * Add assignees
     * @param assignees
     */
    public void addAssignees(Collection<User> assignees) {
        issue.addAssignees(assignees.collect { it.raw })
    }

    /**
     * Add assignees
     * @param assignees
     */
    public void addAssignees(String[] assignees) {
        def list = assignees.collect { it.toLowerCase() }
        def users = repository.collaborators.findAll { list.contains(it.name) }

        addAssignees(users)
    }

    /**
     * list issue comments
     * @return
     */
    public List<Comment> listComments() {
        return issue.listComments().collect { new Comment(it) }
    }


}
