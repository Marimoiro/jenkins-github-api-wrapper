@Library('jenkins-github-api-wrapper')

import marimoiro.github.GitHubClient
import marimoiro.github.Repository

Repository repo


pipeline {
    agent any
    stages {
        stage('get repository api') {
            steps {
                script {

                    def github = GitHubClient.find()
                    repo = github.createRepository("create-test")
                            .licenseTemplate("mit")
                            .gitignoreTemplate("Java")
                            .wiki(false).create()
                }
            }
        }

        stage('repository info') {
            steps {
                script {
                    echo "url:${repo.url} htmlUrl:${repo.htmlUrl}"
                }
            }
        }

        stage('wait a minutes') {
            steps{
                sleep(60)
            }
        }
    }



    post {
        always {
            script {
                if(repo != null)
                {
                    // need delete_repo permission
                    repo.delete()
                }
            }
        }
    }
}