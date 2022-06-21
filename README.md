# Github Api Wrapper for Jenkins
This library is a thin wrapper around the GitHub API for Java for github operations from Jenkins.

# Features

Of the features of the [GitHub API for Java
](https://github-api.kohsuke.org/), this implementation focuses on the features commonly used by Jenkins. If you need more functionality than implemented, please customize the library or get GHObject with the raw property. (You can also send out a PullRequest).

 * Repository
    * Create / delete repositories
    * Create / delete labels
    * Get differences between commits
 * PullRequest
    * Create
    * Merge
    * Comment 
    * Close
  * etc...

Go to [API Document](docs/index.html) for more information.

# System Configuration

## System Configuration 

1. GitHub > GitHub Servers

Add a Github Server.
For more information, see [GitHub Plugin](https://plugins.jenkins.io/github/)

2. Global Pipeline Libraries

Set the URL of this repository or the repository you forked.

3. write a Jenkinsfile
4. add a Star to https://github.com/Marimoiro/jenkins-github-api-wrapper

## About Samples.

The [Info.groovy](samples/Info.groovy) file describes the configuration of the repository you want to operate.
If you want to try it out, please rewrite this to the repository you forked.


# Limitation

## About CPS support

We recommend that all instances be used within a single script block if possible.
You can get over the scope, but testing is not sufficient.

## If functionality is not sufficient

Almost all classes have getRaw member that can be used in the backend [GitHub API for Java
](https://github-api.kohsuke.org/) objects.

Alternatively, this library is so simple to code that you can implement it and send out a PullRequest.

## What this library has access to.
Please note that this library can do everything you can do on GitHub if you have legitimate access rights to github.
Basically, you should not check Load implicitly, but only load it in the jobs that need it.

## License

[LICENSE](LICENSE)

## Star is required.

The license is MIT, but please press Star if you want to use this library.
I will be happy.

## This document has been translated
This document is a translation of the Japanese original.
The exact document is [README-en.md](README-en.md)

Translated with www.DeepL.com/Translator (free version)
