pipeline {
    agent any
    environment {
            AWS_ACCESS_KEY_ID     = 'AKIASOSAJCMIHLSK34UX'
            AWS_SECRET_ACCESS_KEY = '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'
        }
    stages {
        // Jar 파일로 빌드 (테스트 부분 스킵..)
        stage('Build Jar') {
            steps {
                echo 'build'
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        // zip 파일 구성
        stage('zip') {
            steps{
                echo 'zip'
                sh 'cd ${JOB_NAME}/build/libs'
                sh 'cp -r ../../.ebextensions .ebextensions'
                sh 'mv *.jar application.jar'
                sh 'zip -r ${JOB_NAME}.zip application.jar .ebextensions'
            }
        }
        // S3에 먼저 업로드 후 Deploy 진행
        stage('Upload S3') {
            steps {
                echo 'Uploading'
                sh 'aws s3 cp ${JOB_NAME}.zip s3://elasticbeanstalk-ap-northeast-2-168712278800/${JOB_NAME}-${GIT_BRANCH}-${BUILD_NUMBER}.zip \
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
                    --source-bundle S3Bucket="elasticbeanstalk-ap-northeast-2-168712278800",S3Key="${JOB_NAME}-${GIT_BRANCH}-${BUILD_NUMBER}.zip"'
                sh 'aws elasticbeanstalk update-environment \
                    --region ap-northeast-2 \
                    --environment-name 	Joopgingnodoker-env \
                    --version-label ${PROJECT_NAME}-${BUILD_NUMBER}'
        }
    }
}
    post {
        always {
            echo '결과는...'
        }
        // 성공 시 슬랙 #tickets 채널에 성공 메세지 보내기
        success {
            echo '성공!'
        }
        // 실패 시 슬랙 #tickets 채널에 성공 메세지 보내기
        failure {
            echo '실패..'
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