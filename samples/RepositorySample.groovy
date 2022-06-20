@Library('jenkins-github-api-wrapper')
import marimoiro.github.GitHubClient
import marimoiro.github.Repository
import marimoiro.github.Color

Repository repo;

Info info

pipeline {
    agent any
    stages {
        stage('find repo') {
            steps {
                script {

                    checkout scm
                    info = load "samples/Info.groovy"

                    // or repo = Repository.find(info.repoUrl)
                    def github = GitHubClient.find();
                    echo github.apiUrl;
                    repo = github.getRepository(info.repoName)
                    echo repo.dump()



                }
            }
        }

        stage('labels')
                {
                    steps {
                        script {
                            // sample colors
                            echo Color.randomPastelRgbCode()
                            echo Color.randomVividRgbCode()


                            try {
                                // create labels
                                repo.createLabel("label")
                                def blue = repo.createLabel("blue", "0000dd", "blue label")
                                echo "${blue.name} ${blue.color}"

                                def labels = repo.labels
                                echo " labels count = ${labels.size()}"
                                labels.each { echo "${it.name} ${it.color}" }
                            }
                            catch (e) {
                                if (!e.message.contains("already_exists")) {
                                    throw e
                                }
                            }

                            sleep(60)


                        }
                    }
                }
    }

    post
            {
                always {
                    //tear down
                    script {
                        repo.getLabel("label").delete()
                        repo.getLabel("blue").delete()
                    }
                }
            }
}
