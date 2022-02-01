pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Run Gradle WrapperMaven on a Unix agent.
                sh " ./gradlew clean test assemble"
            }

            post {
                // If gradle was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit 'build/test-results/test/*xml'
                    archiveArtifacts 'build/libs/*.jar'
                    jacoco execPattern: 'build/jacoco/*.exec'
                }
            }
        }
    }
}
