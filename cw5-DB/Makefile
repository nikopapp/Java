

compile:
	javac -g GUI.java
run:
	java GUI
compileConsole:
	javac -g DB.java
runConsole: 
	java DB
jar:
	jar -cvfm DB.jar Manifest.txt *.class logo/logo.png
runJar:
	java -jar DB.jar
test:
	./test.sh
push:
	git commit -a -m $$(date +%d-%m-%Y/%H:%M)
	git push
pull:
	git pull

