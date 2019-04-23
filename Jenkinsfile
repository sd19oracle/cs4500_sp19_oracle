pipeline {
    stages {
        stage('Example stage 1') {
            environment {
                source credentials('db-vars')
            }
            steps {
                echo $DBSCHEMA 
            }
        }
        stage('Example stage 2') {
            steps {
                // 
            }
        }
    }
}
