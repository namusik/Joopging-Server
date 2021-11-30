#!/bin/sh
#build 한위치
cd /var/lib/jenkins/workspace/JoopgingServer/build/libs

#현재 구동중인 어플리케이션 종료후 실행
CURRENT_PID=$(pgrep -f Joopging-0.0.1-SNAPSHOT.jar)
if [ -z "$CURRENT_PID"]; then
        echo "> 현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi
echo "> 새 어플리케이션 배포"
nohup java -jar Joopging-0.0.1-SNAPSHOT.jar &
 