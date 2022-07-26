//전역변수 설정
def mainDir="Joopging-Server"                                           //Jenkinsfile 메인위치 지정.
def ecrLoginHelper="docker-credential-ecr-login"                        //jib으로 만든 도커이미지를 ECR에 push하기전에 인증문제없도록 도와주는 helper
def region="ap-northeast-2"                                             //Aws 지역
def ecrUrl="382240023058.dkr.ecr.ap-northeast-2.amazonaws.com"          //ECR 경로
def repository="test"                                                   //ECR repository 이름
def deployHost="3.39.21.179"                                            //배포 서버 ipv4. 젠킨스서버와 배포서버가 같은 vpc안에 있으면 private IPv4 쓰면 되는데 지금은 달라서 public씀

pipeline { //pipleling stage별로 명시
   agent any
   //agent는 이런 pipleline이 수행을 할 떄, 실제 job을 실행에 서버가 필요함. 젠킨스서버 자체를 사용하든지, 젠킨스가 master/slave 구조로 되어있으면 master/slave 모두에 job이 실행.
   //현재는 도커 기반으로 EC2 단일 VM에 올라가 있기 때문에, any로 설정을 주면 해당 VM에 컨테이너에서 젠킨스 job이 구동됨.

    stages { //각각의 job task를 stage별로 나눔. pipleline 전체가 job이면 각각의 stage는 task
        stage('Pull Codes from Github'){ //깃헙 repository에서 코드를 가져옴.
            steps{
                checkout scm //pipeline설젱에 있는 브랜치에서 코드를 checkout 해옴.
            }
        }
        stage('Build Codes by Gradle') { //코드를 가져와서, web Application을 Build해서 jar 파일 만들어줌.
            steps {
                sh """
                ./gradlew clean build
                """
                //gradle wrapper 파일 이용. 기존꺼 지우기 위해 clean 사용. 그 후 build.
            }
        }
        stage('Build Docker Image by Jib & Push to AWS ECR Repository') { //jib으로 도커 이미지를 빌드해서 ecr로 push
            steps {
                withAWS(region:"${region}", credentials:"aws-key") { //aws ecr에 로그인 해줘야 함. 젠킨스에 등록한 aws access key 사용.
                    ecrLogin() //ecr 로그인
                    sh """
                        curl -O https://amazon-ecr-credential-helper-releases.s3.us-east-2.amazonaws.com/0.4.0/linux-amd64/${ecrLoginHelper}
                        chmod +x ${ecrLoginHelper}
                        mv ${ecrLoginHelper} /usr/local/bin/
                        ./gradlew jib -Djib.to.image=${ecrUrl}/${repository}:${currentBuild.number} -Djib.console='plain'
                    """
                    //curl : ecr-credential 다운로드
                    //chmod 권한 부여
                    // usr/local/bin으로 옮김. 해당 helper 어디서든지 사용가능해짐
                    // helper jib에서 사용. image ecr로 push:태그(현재 젠킨스 job을 진행하는 번호), jib.console=plain 옵션 추가. jib로그를 출력할 때 뜨게 하려고
                    //jib.to.image 경로를 동적으로 구성하기 위해 pipeline 스크립트에 넣어줌.
                    //build.gradle을 보면 그래서 jib 코드에 to{} 부분이 빠져있다.
                }
            }
        }
        stage('Deploy to AWS EC2 VM'){ //CD 작업 시작. 베포서버에 이미지 배포후 컨테이너 run
            steps{
                sshagent(credentials : ["jenkins-deploy"]) { //jenkins에서 ec2에 접속을 해야하기 때문에 등록해준 deploy-key 사용. ec2 pem key
                    sh "docker rm -f $(docker ps -a -q -f name=jenkinsTest)"
                    sh "docker rmi -f ${ecrUrl}/${repository}"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
                     'aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}/${repository}; \
                      sleep 3; \
                      docker run -d --name jenkinsTest -p 80:8080 -t ${ecrUrl}/${repository}:${currentBuild.number} ;'"
                      //ssh로 deployhost에 젠킨스서버에서 접속
                      //aws ecr get-login-password : ecr에 로그인
                      //sleep 3 : 3초 쉬어줌.
                      //가져온 이미지 기반으로 docker run 해줌. host port 80. image는 ecr에서 태그도 붙여서.
                }
            }
        }
    }
}
