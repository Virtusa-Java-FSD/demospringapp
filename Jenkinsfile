pipeline {
    agent any

    tools{
        jdk "JDK21"
        maven "Maven3"
    }

    options {
        skipStagesAfterUnstable()
    }

    environment {
        JAR_NAME = "ecommerceapp-0.0.1-SNAPSHOT.jar"
        REMOTE_DIR = "/home/ec2-user"
        APP_PORT = "8080"
    }

    stages{

        stage('Checkout'){
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps{
                bat 'mvn -B clean compile'
            }
            post {
                success {
                    echo 'Build successful'
                }
                failure {
                    echo 'Build failed'
                }

            }
        }

        stage('Test') {
            steps{
                bat 'mvn -B test'
            }
            post {
                success{
                     echo 'Test successful'
                }
                failure{
                     echo 'Test failed'
                }
            }
        }

        stage('Package') {
            steps{
                bat 'mvn -B package -DskipTests'
            }
            post{
                success{
                    echo "JAR CREATED SUCCESSFULLY"
                }
            }
        }
        stage("Deploy to EC2") {
            steps {
                sshPublisher(
                    publishers:[
                        sshPublisherDesc(
                            configName: "ec2-server",
                            verbose: true,
                            transfers: [
                                sshTransfer(
                                    sourceFiles: "target/${JAR_NAME}",
                                    removePrefix: "target/",
                                    remoteDirectory: "${REMOTE_DIR}",
                                    flatten: true,
                                    execCommand: ''

                                )
                            ]
                        )
                    ]
                )
            }
        }

    }


    post{

        always {

            echo "Pipeline Done..."
        }

    }


}