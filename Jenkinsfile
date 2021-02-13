pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'docker build -t litiezhu/rubicks-cube .'
      }
    }

    stage('test') {
      steps {
        echo 'testing'
      }
    }

    stage('deploy') {
      steps {
        sh '''docker run \\ 
--rm \\
-d \\
--name cube-service \\
-p 9597:9596 \\
litiezhu/rubicks-cube'''
      }
    }

  }
}