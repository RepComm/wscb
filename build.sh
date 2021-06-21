
# compile java classes
echo "[JAVAC] Compiling classes"
javac -cp '.:Java-WebSocket-1.5.2.jar:slf4j-api-1.7.25.jar' -verbose -d ./bin ./src/comm/rep/*.java
echo "[JAVAC] Done"

# make into jar
echo "[JAR] Creating jar file"
cd ./bin
jar --create --file ../wscb.jar --main-class=comm.rep.Test --verbose ./*
echo "[JAR] Done"
