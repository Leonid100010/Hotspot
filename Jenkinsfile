pipeline {
    agent any
    stages {
        stage('test') {
             steps{
		        sh 'mvn test'
             }
        }
        stage('build') {
            steps {
                sh 'mvn clean package'
                sh 'docker build -t hotspot:latest .'
	        }
        }
        stage('deploy') {
            steps {
                sh 'docker rm -f hotspot'
                sh 'docker run -d --name hotspot -p 8086:8086 --net=sw3 hotspot:latest'
	        }
        }
    }
}
