pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                 sh '''
                 source credentials('db-vars')
                 mvn test
                 '''
            }
        }
    }
}
