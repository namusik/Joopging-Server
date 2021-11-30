pipeline {
    agent any
    stages {
        // Jar 파일로 빌드 (테스트 부분 스킵..)
        stage('Build Jar') {
            steps {
                echo 'build'
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying'
                sh 'cd /home/ubuntu'
                sh 'chmod +x deploy.sh'
                sh './deploy.sh'
        }
    }
}
    post {
        always {
            echo '결과는...........'
        }
        // 성공 시 슬랙 #tickets 채널에 성공 메세지 보내기
        success {
            echo '성공!!!'
        }
        // 실패 시 슬랙 #tickets 채널에 성공 메세지 보내기
        failure {
            echo '실패..'
        }
        unstable {
            echo '실행이 불안정합니다!'
        }
        changed {
            echo '파이프 라인이 변경되었습니다!'
            echo '예를들어 이전에는 실패했지만 지금은 성공한 경우'
        }
    }
}