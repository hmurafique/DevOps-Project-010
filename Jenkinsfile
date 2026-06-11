pipeline {
    agent { label 'Jenkins-Agent' }
    
    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }
    
    environment {
        APP_NAME = "register-app"
        RELEASE = "1.0.0"
        DOCKER_USER = "hmurafique93"
        DOCKER_PASS = 'dockerhub-token'
        IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
        SONARQUBE_SERVER = 'SonarQube-Server'
    }
    
    stages {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Checkout from SCM') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-token',
                    url: 'https://github.com/hmurafique/DevOps-Project-010'
            }
        }
        
        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Test Application') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: false
            }
        }
        
        stage('Build Docker Image') {
            steps {
                docker.withRegistry('', DOCKER_PASS) {
                    docker_image = docker.build("${IMAGE_NAME}")
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                docker.withRegistry('', DOCKER_PASS) {
                    docker_image.push("${IMAGE_TAG}")
                    docker_image.push('latest')
                }
            }
        }
        
        stage('Update GitOps Repo') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'github-token',
                    usernameVariable: 'GIT_USER',
                    passwordVariable: 'GIT_PASS')]) {
                    sh """
                        git clone https://${GIT_USER}:${GIT_PASS}@github.com/hmurafique/DevOps-Project-010-gitops
                        cd DevOps-Project-010-gitops
                        sed -i 's|${IMAGE_NAME}:.*|${IMAGE_NAME}:${IMAGE_TAG}|g' deployment.yaml
                        git config user.email "ci@jenkins.com"
                        git config user.name "Jenkins"
                        git add .
                        git commit -m "Update image tag to ${IMAGE_TAG}"
                        git push https://${GIT_USER}:${GIT_PASS}@github.com/hmurafique/DevOps-Project-010-gitops main
                    """
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
