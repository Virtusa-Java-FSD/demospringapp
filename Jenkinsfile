pipeline {
    agent any

    tools{
        jdk "JDK21"
        maven "Maven3"
    }

    environment {

            EC2_HOST = 'ec2-13-126-0-26.ap-south-1.compute.amazonaws.com'  // e.g., ec2-54-123-45-67.compute-1.amazonaws.com
            EC2_USER = 'ec2-user'                   // Default for Amazon Linux
            KEY_FILE = 'C:/Users/ArulanandhaGuru/Downloads/project-key.pem'   // Full path on Jenkins server/agent
            REMOTE_APP_DIR = '/home/ec2-user'
            JAR_NAME = 'ecommerceapp-0.0.1-SNAPSHOT.jar'  // Or use find command below for dynamic name
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

        // NEW: Deploy JAR to EC2
        stage('Deploy to EC2') {

            steps {
                script {
                    // Find the built JAR dynamically (recommended)
                    def jarFile = findFiles(glob: '**/target/*.jar')[0].path

                    // Copy JAR to EC2 using scp
                    bat """
                        scp -i "${KEY_FILE}" -o StrictHostKeyChecking=no ${jarFile} ${EC2_USER}@${EC2_HOST}:${REMOTE_APP_DIR}/app.jar
                    """

                    echo "JAR deployed to EC2: app.jar"
                }
            }
        }

        // NEW: Restart Application on EC2
        stage('Restart Application on EC2') {
         
            steps {
                sshagent(['ec2-ssh-credentials']) {  // Recommended: Use Jenkins credentials (see note below)
                    bat """
                        ssh -i "${KEY_FILE}" -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} "
                            # Stop existing app if running
                            pkill -f 'java -jar.*app.jar' || true

                            # Wait a moment
                            sleep 5

                            # Start new app in background
                            nohup java -jar ${REMOTE_APP_DIR}/app.jar --spring.profiles.active=prod > app.log 2>&1 &

                            echo 'Application restarted successfully'
                        "
                    """
                }
            }
        }
    }


    post{

        always {

            echo "Pipeline Done..."
        }

    }


}