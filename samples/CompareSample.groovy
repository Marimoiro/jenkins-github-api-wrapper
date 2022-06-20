import marimoiro.github.Commit
@Library('jenkins-github-api-wrapper')
import marimoiro.github.Ref
import marimoiro.github.Repository
import marimoiro.github.PullRequest
import marimoiro.github.Compare
import net.sf.json.groovy.JsonSlurper

int id = 0
PullRequest pr
Repository repo

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
                
                echo "compare" >> newfile.txt
                git add newfile.txt
                git commit -am "newfile.txt 2"
    
                git push origin :refs/heads/compare-test || true

                git push origin HEAD:refs/heads/compare-test
    
                """



            }

        }
        stage('get repository api') {
            steps {
                script {
                    info =  load "samples/Info.groovy"
                    repo = Repository.find(info.repoUrl)
                }
            }
        }
        stage('create pull request') {
            steps {
                script {
                    pr = repo.createPullRequest('テスト', 'compare-test', 'main', 'テスト中\nテスト中')
                    id = pr.number
                }
            }
        }

        stage('compare pullRequest') {
            steps {
                script {
                    Compare compare = pr.compare
                    compare.compare.getCommits().collect {
                        echo "${it.dump()}"
                    }
                    echo "${Test.CompareText(compare)}"
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

                try {
                    Ref h = pr.headRef
                    if (h != null) {
                        echo h.url
                        h.delete()
                    }
                } catch(e) {}

            }
        }
    }
}

public class Test {
    static def CompareText(Compare compare) {
        String ret = ""
        ret = compare.dump() + "\n"

        def commits = compare.getCommits(true)

        commits.each {
            ret += it.dump() + "\n"
            it.files.each { ret += it.dump() + "\n" }
        }

        return ret

    }
}