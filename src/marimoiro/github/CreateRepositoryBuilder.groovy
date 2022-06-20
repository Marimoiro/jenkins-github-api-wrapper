package marimoiro.github

import org.kohsuke.github.GHCreateRepositoryBuilder

/**
 * repository builder wrap https://github-api.kohsuke.org/apidocs/org/kohsuke/github/GHCreateRepositoryBuilder.html
 * This instance cannot jump between stages
 */
class CreateRepositoryBuilder implements Serializable {
    private transient GHCreateRepositoryBuilder builder

    public CreateRepositoryBuilder(GHCreateRepositoryBuilder builder)
    {
        this.builder = builder
    }

    public CreateRepositoryBuilder 	allowRebaseMerge(boolean enabled)
    {
        builder.allowRebaseMerge(enabled)
        return this
    }

    public CreateRepositoryBuilder 	allowMergeCommit(boolean enabled)
    {
        builder.allowMergeCommit(enabled)
        return this
    }

    public CreateRepositoryBuilder allowSquashMerge(boolean enabled)
    {
        builder.allowSquashMerge(enabled)
        return this
    }

    public CreateRepositoryBuilder autoInit(boolean enabled)
    {
        builder.autoInit(enabled)
        return this
    }

    public Repository create() {
        return new Repository(builder.create())
    }

    public CreateRepositoryBuilder defaultBranch(String name)
    {
        builder.defaultBranch(name)
        return this
    }

    public CreateRepositoryBuilder deleteBranchOnMerge(boolean enabled)
    {
        builder.deleteBranchOnMerge(enabled)
        return this
    }

    public CreateRepositoryBuilder description(String description)
    {
        builder.description(description)
        return this
    }

    public CreateRepositoryBuilder downloads(boolean enabled)
    {
        builder.downloads(enabled)
        return this
    }

    public CreateRepositoryBuilder fromTemplateRepository(String templateOwner, String templateRepo){
        builder.fromTemplateRepository(templateOwner,templateRepo)

        return this
    }

    public CreateRepositoryBuilder gitignoreTemplate(String language){
        builder.gitignoreTemplate(language)

        return this
    }

    public CreateRepositoryBuilder homepage(String homepage){
        builder.homepage(homepage)

        return this
    }

    public CreateRepositoryBuilder homepage(URL homepage){
        builder.homepage(homepage)

        return this
    }


    public CreateRepositoryBuilder issues(boolean enabled)
    {
        builder.issues(enabled)
        return this
    }



    public CreateRepositoryBuilder isTemplate(boolean enabled)
    {
        builder.isTemplate(enabled)
        return this
    }

    public CreateRepositoryBuilder licenseTemplate(String license){
        builder.licenseTemplate(license)

        return this
    }


    public CreateRepositoryBuilder owner(String owner){
        builder.owner(owner)

        return this
    }

    public CreateRepositoryBuilder projects(boolean enabled){
        builder.owner(enabled)

        return this
    }

    public CreateRepositoryBuilder wiki(boolean enabled){
        builder.wiki(enabled)

        return this
    }
}
