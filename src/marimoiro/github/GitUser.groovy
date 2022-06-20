package marimoiro.github

class GitUser implements Serializable{
    private Date date
    private String email
    private String name
    private String username

    public GitUser(org.kohsuke.github.GitUser user)
    {
        date = user.date
        email = user.email
        name = user.name
        username = user.username
    }

    public Date getDate() {
        return date
    }

    public String getEmail(){
        return email
    }

    public String getName() {
        return name
    }

    public String getUsername(){
        return username
    }

}
