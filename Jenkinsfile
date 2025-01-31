//전역변수 설정
def mainDir="Joopging-Server"                                           //Jenkinsfile 메인위치 지정.
def ecrLoginHelper="docker-credential-ecr-login"                        //jib으로 만든 도커이미지를 ECR에 push하기전에 인증문제없도록 도와주는 helper
def region="ap-northeast-2"                                             //Aws 지역
def ecrUrl="962689718920.dkr.ecr.ap-northeast-2.amazonaws.com"          //ECR 경로
// def nexusUrl="ec2-3-35-22-214.ap-northeast-2.compute.amazonaws.com:5000" //Nexus 경로
def repository="joopging"                                                   //ECR repository 이름
def jenkinsHost="13.209.89.121"                                           //Jenkins 서버 ipv4
def deployHost="13.125.150.144"                                            //배포 서버 ipv4. 젠킨스서버와 배포서버가 같은 vpc안에 있으면 private IPv4 쓰면 되는데 지금은 달라서 public씀
def containerName="joopging"                                                //배포 서버 도커 container 이름
def tagName="awsecr"

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
        stage('Build Codes by Gradle & SonarQube') { //코드를 가져와서, web Application을 Build해서 jar 파일 만들어줌.
            steps {
                sh """
                ./gradlew clean build
                ./gradlew jacocoTestCoverageVerification --info
                ./gradlew jacocoTestReport --info
                ./gradlew sonarqube --info
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
//                 sh """
//                     docker login -u test -p 'test' ${nexusUrl}
//                     ./gradlew jib -Djib.to.image=${nexusUrl}/${repository}:${tagName} -DsendCredentialsOverHttp=true -Djib.console='plain'
//                 """
//                 //nexus id pw로 nexus registry 도커 로그인
//                 //-DsendCredentialsOverHttp=true : HTTP로 연결하기 때문에 연결 허용
            }
        }
//         stage('Scan Security CVE at Clair Scanner') {
//             steps {
//                 script {
//                     try {
//                         jenkins_ip = sh(script: "docker inspect -f '{{ .NetworkSettings.IPAddress }}' jenkins", returnStdout: true).trim()
//                         clair_ip = sh(script: "docker inspect -f '{{ .NetworkSettings.IPAddress }}' clair", returnStdout: true).trim()
//                         sh """
//                             apt update
//                             apt install -y wget
//                             docker pull ${nexusUrl}/${repository}:${tagName}
//                             wget https://github.com/arminc/clair-scanner/releases/download/v12/clair-scanner_linux_amd64
//                             chmod +x clair-scanner_linux_amd64
//                             mv clair-scanner_linux_amd64 /usr/local/bin/clair-scanner
//                         """
//                         //nexus registry에 있는 이미지 pull 땡겨서
//                         //wget으로 clair 설치 후, 검사
//                         sh "clair-scanner --ip ${jenkins_ip} --clair='http://${clair_ip}:6060' --log='clair.log' \
//                                 --report='report.txt' ${nexusUrl}/${repository}:${tagName}"
//                     } catch (err) {
//                         echo err.getMessage()
//                     }
//                 }
//                 echo currentBuild.result
//             }
//         }
        stage('Deploy to AWS EC2 VM'){ //CD 작업 시작. 베포서버에 이미지 배포후 컨테이너 run
            steps{
                sshagent(credentials : ["cicd-test"]) { //jenkins에서 ec2에 접속을 해야하기 때문에 등록해준 deploy-key 사용. ec2 pem key
                    //| xargs --no-run-if-empty : docker ps -a -q의 결과가 비었을 때, 다음 커맨드 실행 안됨.
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
                     'docker ps -a -q -f name=${containerName} | xargs --no-run-if-empty docker rm -f; \
                      docker images ${ecrUrl}/${repository} -q | xargs --no-run-if-empty docker rmi; \
                      aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}/${repository}; \
                      sleep 3; \
                      docker run -d --name ${containerName} -p 80:8080 -t ${ecrUrl}/${repository}:${currentBuild.number} ;'"
                      //ssh로 deployhost에 젠킨스서버에서 접속
                      //aws ecr get-login-password : ecr에 로그인
                      //sleep 3 : 3초 쉬어줌.
                      //가져온 이미지 기반으로 docker run 해줌. host port 80. image는 ecr에서 태그도 붙여서.
                }
            }
        }
//         stage('Deploy container to AWS EC2 VM'){
//             steps{
//                 sshagent(credentials : ["cicd-test"]) {
//                     sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
//                      'docker login -u test -p 'test' ${nexusUrl}; \
//                       docker run -d -p 80:8080 -t ${nexusUrl}/${repository}:${tagName};'"
//                 }
//             }
//         }
    }
}
