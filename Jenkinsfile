pipeline {
    agent any
    stages {
        stage("Build") {
            environment {
                ENV = credentials('db-vars')
            }
            steps {
                 sh '''
                 echo $ENV
                 mvn test
                 '''
            }
        }
    }
}
