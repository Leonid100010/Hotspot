pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
                sh 'docker build -t hotspot:latest .'
	        }
        }
        stage('deploy') {
            steps {
                sh 'docker rm -f hotspot'
                sh 'docker run -d --restart=always --name hotspot -p 8086:8086 --net=sw3 hotspot:latest '
	        }
        }
    }
}
