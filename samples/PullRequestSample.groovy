@Library('jenkins-github-api-wrapper')
import marimoiro.github.GitHubClient
import marimoiro.github.Ref
import marimoiro.github.Repository
import marimoiro.github.PullRequest

int id = 0
PullRequest pr
Repository repo
Ref base
Ref head

def info

pipeline {
    agent any
    stages {
        stage('git commit') {
            steps {
                checkout scm

                pwsh """

                git config --global user.email "you@example.com"
                git config --global user.name "Your Name"
    
                echo "new ${env.BUILD_NUMBER}" > newfile.txt
                git add newfile.txt
                git commit -am "newfile.txt"
    
                git push origin :refs/heads/pull-request-test || true
                git push origin :refs/heads/pr-test || true
    
                git push origin HEAD:refs/heads/pull-request-test
    
                """



            }

        }
        stage('get repository api') {
            steps {
                script {
                    info = load "samples/Info.groovy"
                    repo = Repository.find(info.repoUrl)
                }
            }
        }
        stage('create pull request') {
            steps {
                script {
                    base = repo.createRef('refs/heads/pr-test', env.GIT_COMMIT)
                    echo base.url

                    head = repo.getBranchRef('pull-request-test')

                    pr = repo.createPullRequest('テスト', 'pull-request-test', 'pr-test', 'テスト中\nテスト中')
                    id = pr.number

                    echo pr.dump()
                }
            }
        }

        stage('edit pull request') {
            steps {
                script {

                    pr.title = 'PR Testing...'
                    pr.body = 'Testing \nテスト中 \n被测 \nsous test'
                    pr.comment('LGTM')
                    /* switch  */

                    pr.addLabels('documentation', 'bug', 'good first issue')
                    pr.removeLabels('good first issue', 'no label')
                    /* */

                    pr.addAssignees(info.user)

                    pr.requestReviewers(info.other)

                    // commented by other user
                    try {
                        def other = GitHubClient.find('api.github.com', 'other')
                        def orepo = other.getRepository(info.repoName)
                        def apr = orepo.getPullRequest(id)

                        apr.approve('LGTM');
                    }
                    catch (Exception e) {
                        echo 'error approve'
                    }

                    /* gets comments */

                    echo 'comments'
                    pr.listComments().each { echo it.body }

                    echo 'pull request comments'
                    pr.listReviewComments().each { echo it.body }

                    repo.createCommitStatus(head.sha, "success", env.BUILD_URL, "test ok")

                    /* merge this pull request */
                    pr.merge()

                }
            }
        }

        stage('PullRequests') {
            steps {
                script {
                    repo.getPullRequests().each {
                        echo it.dump()
                    }
                }
            }
        }
    }

    post {
        always {
            script {

                // tear down
                if (pr != null) {
                    if (!pr.isClosed) {
                        pr.close()
                    }
                }

                if (base != null) {
                    echo base.url
                    base.delete()
                }

                if (head != null) {
                    echo head.url
                    head.delete()
                }

            }
        }
    }
}
