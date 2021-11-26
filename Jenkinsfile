pipeline {
    agent any
    environment {
        // Slack configuration
        SLACK_COLOR_DANGER  = '#E01563'
        SLACK_COLOR_INFO    = '#6ECADC'
        SLACK_COLOR_WARNING = '#FFC300'
        SLACK_COLOR_GOOD    = '#3EB991'
    } // environment
    stages {
        // WAR 파일로 빌드 (테스트 부분 스킵..)
        stage('Build Jar') {
            steps {
                echo 'build'
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        // S3에 먼저 업로드 후 Deploy 진행
        stage('Upload S3') {
            steps {
                echo 'Uploading'
                sh 'aws s3 cp /var/lib/jenkins/workspace/joopgging/target/joopgging-0.0.1.SNAPSHOT.jar s3://elasticbeanstalk-ap-northeast-2-168712278800/${JOB_NAME}-${GIT_BRANCH}-${BUILD_NUMBER}.jar \
                    --acl public-read-write \
                    --region ap-northeast-2' //서울리전
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying'
                sh 'aws elasticbeanstalk create-application-version \
                    --region ap-northeast-2 \
                    --application-name joopging-nodoker \
                    --version-label ${JOB_NAME}-${BUILD_NUMBER} \
                    --description ${BUILD_TAG} \
                    --source-bundle S3Bucket="S3버킷이름",S3Key="${JOB_NAME}-${GIT_BRANCH}-${BUILD_NUMBER}.jar"'
                sh 'aws elasticbeanstalk update-environment \
                    --region ap-northeast-2 \
                    --environment-name 	Joopgingnodoker-env \
                    --version-label ${JOB_NAME}-${BUILD_NUMBER}'
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        // 성공 시 슬랙 #tickets 채널에 성공 메세지 보내기
        success {
            echo 'This will run only if successful'
            echo 'Pushing EBS'
            slackSend (color: "${env.SLACK_COLOR_GOOD}",
                channel: "#tickets",
                message: "*SUCCESS:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} by ${env.USER_ID}\n More info at: ${env.BUILD_URL}")

        }
        // 실패 시 슬랙 #tickets 채널에 성공 메세지 보내기
        failure {
            echo 'This will run only if failed'
            //슬랙 #tickets 채널에 실패 메세지 보내기
            slackSend (color: "${env.SLACK_COLOR_DANGER}",
                channel: "#tickets",
                message: "*FAILED:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} by ${env.USER_ID}\n More info at: ${env.BUILD_URL}")
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}  