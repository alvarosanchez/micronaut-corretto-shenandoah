#!/bin/zsh

ulimit -S -n 10240

source "${HOME}/.sdkman/bin/sdkman-init.sh"
sdk use java $1
java -version

echo "\nStarting application..."
case $2 in
  openjdk)
    java -Xlog:gc:file=$2.log -jar build/libs/micronaut-corretto-shenandoah-0.1-all.jar &
    ;;
  corretto)
    java -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -Xlog:gc:file=$2.log -jar build/libs/micronaut-corretto-shenandoah-0.1-all.jar &
    ;;
esac

export pid=$!

#Wait for application to be running
while ! curl --output /dev/null --silent --head --fail http://localhost:8080/teams/1; do
  sleep 0.1;
done

echo "\nLaunching VisualVM"
/usr/local/visualVM_204/bin/visualvm --jdkhome ${HOME}/.sdkman/candidates/java/$1 --openpid $pid &

sleep 5

echo "\nWarming up"
wrk2 -t10 -c500 -d15s -R10000 http://127.0.0.1:8080/teams/1

echo "\nCooling down 10 seconds"
sleep 10

echo "\nLoad testing"
wrk2 -t10 -c500 -d60s -R50000 -L http://127.0.0.1:8080/teams/1 &> $2.out

echo "\nShutting down application..."
kill $pid