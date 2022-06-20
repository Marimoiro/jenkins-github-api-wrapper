@Library('jenkins-github-api-wrapper')

import hudson.markup.EscapedMarkupFormatter
import marimoiro.github.Ref


import marimoiro.github.Repository
import marimoiro.github.Issue
import marimoiro.github.TagObject

Repository repo
TagObject tag

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

        stage('create tag') {
            steps {
                script {
                    echo env.GIT_COMMIT
                    def ref = repo.createTagRef("test",env.GIT_COMMIT)
                    echo "${ref.url}"
                    // create tag object
                    tag = repo.createTag('test', 'this is test tag',env.GIT_COMMIT, 'commit')

                    echo "${tag.raw.verification.reason}"
                }
            }
        }

        stage('tags info') {
            steps {
                script {
                    echo "path:${tag.owner.htmlUrl.toString()}"
                    echo tag.url
                    echo tag.tag
                    echo tag.sha

                    def tags = repo.listTags().each { echo "Tags: ${it.name}"}
                }
            }
        }

    }


    post {
        always {
            script {
                // tear down
                if(tag != null) {
                    Ref ref = repo.getTagRef(tag.tag)
                    ref.delete()
                }
            }
        }
    }
}