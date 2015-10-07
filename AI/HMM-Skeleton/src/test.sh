javac *.java;
rm player2server
rm server2player
mkfifo player2server server2player
java Main server < player2server | java Main verbose > player2server

