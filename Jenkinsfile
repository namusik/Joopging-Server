pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'build'
        sh 'chmod +x gradlew'
        sh './gradlew clean build'
      }
    }

    stage('Upload') {
      steps {
        sh '''withAWS("aws_access_key: AKIASOSAJCMIHLSK34UX", "aws_secret_key": "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")
aws s3 cp build/libs/Joopging-0.0.1-SNAPSHOT.jar s3://elasticbeanstalk-ap-northeast-2-168712278800/Joopging-0.0.1-SNAPSHOT.jar
'''
      }
    }

    stage('Deploy') {
      steps {
        sh 'aws elasticbeanstlak create-application-version --region ap-northeast-2 -- application-name joopging-nodoker --version-label ${BUILD_TAG} --source-bundle S3Bucket="elasticbeanstalk-ap-northeast-2-168712278800",S3Key="Joopging-0.0.1-SNAPSHOT.jar"'
        sh '''aws elasticbeanstalk update-environment --region ap-northeast-2 --environment-name Joopgingnodoker-env --version-label ${BUILD_TAG}
access_key_id: AKIASOSAJCMIHLSK34UX
secret_access_key: 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'''
      }
    }

  }
}