#!/bin/bash
grepParams="EKYC_PLATFOM_xxx"

runPath=$grepParams
logPath=/deploy/log/ibo/ibo-backend.log
today=process`date '+%Y-%m-%d'`
r=`tput setaf 1`
r1=`tput setaf 2`
N=`tput sgr0`
#JVMOPTION="-Xdebug -Xnoagent -server -Xss228k -Xrunjdwp:transport=dt_socket,address=10305,server=y,suspend=n"
JVMOPTION=""
start(){
    t1=`ps -ef |grep $grepParams |grep -v 'grep' |wc -l`
    if [ $t1 -eq 0  ];
    then
    java -cp :./lib/*:./config/:$grepParams $JVMOPTION com.vmg.ibo.IBOApplication &
  # $runPath/run.sh &
    echo $grepParams "start${r1}[ok]${N}"
    fi
    if [ $t1 -eq 1  ]; then
    echo $grepParams "${r1}Started${N}"
    fi
    if (( $t1 > 1  ))
    then
    echo $grepParams "${r1}Started${N} more once instance"
    fi
}

stop(){
    /bin/ps -ef |grep $grepParams |grep -v 'grep' |awk '{print$2}' |xargs kill >/dev/null 2>&1 &
    t2=`ps -ef |grep $grepParams |grep -v 'grep' | wc -l`
    if [ $t2 -eq 0 ]; then
    echo $grepParams "stop${r1}[ok]${N}"
    fi
    if (( $t2 > 0  ))
    then
    STOPTIMEOUT=5
    while [ $STOPTIMEOUT -gt 0 ]; do
         sleep 1
         let STOPTIMEOUT=${STOPTIMEOUT}-1
         done
         if [ $STOPTIMEOUT -eq 0 ]; then
         t3=`ps -ef |grep $grepParams |grep -v 'grep' | wc -l`
         if (( $t3 > 0  ))
         then
         echo $grepParams "stop${r}[fail]${N}"
         echo "Timeout error when try to stop "$runPath", process is still runing..."
         echo "Use shell sh file to kill -9 program" $runPath
        fi
        if [ $t3 -eq 0  ];
        then
         echo $grepParams "stop${r1}[ok]${N}"
        fi
      fi
   fi
}


kill(){
/bin/ps -ef |grep $grepParams |grep -v 'grep' |awk '{print$2}' |xargs kill -9 >/dev/null 2>&1 &
echo "Program "$runPath" is killed -9"
echo $grepParams "kill -9 ${r1}[ok]${N}"
}

restart(){
    stop
        #kill
    start
}

taillog(){
  /usr/bin/tail -F $logPath
  # echo "Method not available..."
}

case "$1" in
  start)
   start
   ;;
  stop)
   stop
   ;;
  status)
    t3=`ps -ef |grep $grepParams |grep -v 'grep' |wc -l`
    if [ $t3 -eq 1 ];
    then
    echo $runPath "${r1}started${N}"
    fi
    if [ $t3 -eq 0 ];
    then
    echo $runPath stopped
    fi
    if (( $t3 > 1  ))
    then
    echo $runPath "processing more once instance, please kill some one..."
    fi
    ;;
  taillog)
  taillog
  ;;
  restart)
   restart
   ;;
  kill)
    kill
   ;;
  *)
    echo $"Usage: $0 {start|stop|status|restart|kill|taillog}"
    exit 1
esac
exit $?