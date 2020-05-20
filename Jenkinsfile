
pipeline {
  environment {
    registryHost = 'dockerhub'
    imageName = "interware/cicd-demo"
    imageVersion = "0.1.0"
    dockerImage = ''
  }
  agent any
  tools {
    maven 'apache-maven-3'
  }

  stages {
    stage('Checkout') {
      steps {
        echo 'Checkout'
      }
    }

    stage('Compile') {
      steps {
        echo 'Clean Compile'
        sh 'mvn clean compile'
      }
    }
    stage('UnitTest') {
      steps {
        echo 'Testing'
        sh 'mvn clean test'
      }
    }
    stage('JaCoCo') {
      steps {
        echo 'Code Coverage'
        jacoco()
      }
    }
    stage('Sonar') {
      steps {
        echo 'Sonar Scanner'
         	//def scannerHome = tool 'SonarQube Scanner 3.0'
        withSonarQubeEnv('sonarqube') {
          sh 'mvn clean package sonar:sonar'
        }
      }
    }
    stage('Package') {
      steps {
        echo 'Packaging'
        sh 'mvn package -DskipTests'
      }
    }

    stage('Publish') {
      steps {
        echo 'Publishing'
        nexusPublisher nexusInstanceId: 'nexus-devops-release', \
          nexusRepositoryId: 'devops-release', \
          tagName: 'cicd-demo',
          packages: [[$class: 'MavenPackage', \
                mavenAssetList: [[classifier: '', \
                          extension: '', \
                          filePath: 'target/cicd-demo-0.1.0.jar']], \
                mavenCoordinate: [artifactId: 'cicd-demo', \
                          groupId: 'mx.interware', \
                          packaging: 'jar', \
                          version: '0.1.0']]]
      }
    }

    stage('Building docker image') {
      steps{
        script {
          imageTag = registryHost + ":5000/" + imageName + ":latest" 
          echo "imageTag : " + imageTag
          dockerImage = docker.build imageTag
        }
      }
    }
    stage('Registering docker Image') {
      steps{
        script {
          docker.withRegistry("http://" + registryHost + ":5000") {
            echo "Pushing docker image : " + imageTag
            dockerImage.push()
          }
        }
      }
    }

    stage('Deploy') {
      steps {
        println 'Deploying microservice'
        ansiblePlaybook become: true, \
                becomeUser: 'apacheco', \
                credentialsId: 'ssh-host', \
                hostKeyChecking: false, \
                installation: 'ansible-2.7.10', \
                inventory: 'ansible/inventory/hosts', \
                playbook: 'ansible/deploy-cicd-demo-docker.yml'
      }
    }

  }

  post {
    always {
      echo 'JENKINS PIPELINE EXECUTION'
    }
    success {
      echo 'JENKINS PIPELINE HAS BEEN EXECUTED SUCCESSFULLY'
    }
    failure {
      echo 'JENKINS PIPELINE HAS BEEN FAILED'
    }
    unstable {
      echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
    }
    changed {
      echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
    }
  }
}