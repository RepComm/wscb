
# compile java classes
echo "[JAVAC] Compiling classes"
javac -verbose -d ./bin ./src/comm/rep/Main.java
echo "[JAVAC] Done"

# make into jar
echo "[JAR] Creating jar file"
cd ./bin
jar --create --file ./wscb.jar --main-class=comm.rep.Main --verbose ./*
echo "[JAR] Done"
