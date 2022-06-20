@Library('jenkins-github-api-wrapper')

import marimoiro.github.Repository
import marimoiro.github.Issue

Repository repo
Issue issue

def info

pipeline {
    agent any
    stages {
        stage('get repository api') {
            steps {
                script {
                    checkout scm
                    info = load "samples/Info.groovy"

                    repo = marimoiro.github.Repository.find(info.repoUrl)
                }
            }
        }

        stage('create issue') {
            steps {
                script {
                    issue = repo.createIssue("test issue","this is test issue", [info.user], ['bug'])
                }
            }
        }

        stage('issue info') {
            steps {
                script {
                    issue.comment("foo bar")
                    issue.listComments().each { echo it.body }

                    issue.title = "it's issue test"
                    issue.body = "it's ok"

                    echo "number: ${issue.number}"
                    echo "title: ${issue.title}" // not updated
                    echo "body: ${issue.body}" // not updated
                    echo "url: ${issue.htmlUrl.toString()}"

                }
            }
        }

    }


    post {
        always {
            script {
                // tear down
                if(issue != null) {
                    issue.close()
                }
            }
        }
    }
}