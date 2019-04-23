pipeline {
    agent any
    stages {
        stage("Build") {
            environment {
                ENV = credentials('db-vars')
            }
            steps {
                 sh '''
                 echo $ENV > hello.txt
                 mvn test
                 '''
            }
        }
    }
}
