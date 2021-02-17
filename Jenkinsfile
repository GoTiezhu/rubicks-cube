pipeline {
    agent {
        docker {
            image 'maven:3-jdk-11'
            args '--network jenkins'
            args '-v /root/.m2:/root/.m2'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'echo Testing'
            }
        }
        stage('Build Image') {
            steps {
                sh 'docker built -t litiezhu/rubicks-cube:latest .'
            }
        }
    }

}
