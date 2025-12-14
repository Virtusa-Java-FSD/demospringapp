pipeline {
    agent any

    tools{
        jdk "JDK21"
        maven "Maven3"
    }

    environment {
            JAR_NAME = 'ecommerceapp-0.0.1-SNAPSHOT.jar'  // Exact name from target
            REMOTE_DIR = '/home/ec2-user'
            APP_PORT = '8080'
    }

    options {
        skipStagesAfterUnstable()
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
        stage('Deploy to EC2') {
            steps {
                script {
                    sshagent(['ec2-deploy-key']) {
                        bat """
                            scp -o StrictHostKeyChecking=no target/${JAR_NAME} ec2-user@ec2-13-126-0-26.ap-south-1.compute.amazonaws.com:${REMOTE_DIR}/
                        """
                    }
                }
            }
        }

        stage('Restart Application on EC2') {
            steps {
                script {
                    sshagent(['ec2-deploy-key']) {
                        bat """
                            ssh -o StrictHostKeyChecking=no ec2-user@ec2-13-126-0-26.ap-south-1.compute.amazonaws.com "pkill -f ecommerceapp || true && sleep 3 && cd /home/ec2-user && nohup java -jar ecommerceapp-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > app.log 2>&1 &"
                        """
                    }
                }
            }
        }
//
//         stage('Deploy to EC2') {
//             steps {
//                 sshPublisher(
//                     publishers: [
//                         sshPublisherDesc(
//                             configName: 'ec2-server',  // Must match the Name you set
//                             verbose: true,
//                             transfers: [
//                                 sshTransfer(
//                                     sourceFiles: "target/${JAR_NAME}",
//                                     removePrefix: "target/",
//                                     remoteDirectory: "${REMOTE_DIR}",
//                                     flatten: true,  // Overwrites app.jar directly
//                                     execCommand: ''  // No command here
//                                 )
//                             ]
//                         )
//                     ]
//                 )
//                 echo "JAR deployed to EC2 as ${REMOTE_DIR}/${JAR_NAME}"
//             }
//         }
//
//         stage('Restart Application on EC2') {
//             steps {
//                 sshPublisher(
//                     publishers: [
//                         sshPublisherDesc(
//                             configName: 'ec2-server',
//                             verbose: true,
//                             transfers: [
//                                 sshTransfer(
//                                     execCommand: """
//                                         #!/bin/bash
//                                         pkill -f "ecommerceapp-0.0.1-SNAPSHOT.jar" || true
//                                         sleep 3
//                                         cd /home/ec2-user
//                                         nohup java -jar ecommerceapp-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > app.log 2>&1 < /dev/null &
//                                         disown
//                                         sleep 2
//                                         exit 0
//                                     """.stripIndent()
//                                 )
//                             ]
//                         )
//                     ]
//                 )
//             }
//         }
    }


    post{

        always {

            echo "Pipeline Done..."
        }

    }


}