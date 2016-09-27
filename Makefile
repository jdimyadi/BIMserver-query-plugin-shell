# Path to BIMserver jars

BIMSERVER_PATH = unmanaged_libs/

BIMSERVER_JARS = "$(BIMSERVER_PATH)org.eclipse.emf.common_2.9.1.v20130827-0309.jar:$(BIMSERVER_PATH)org.eclipse.emf_2.6.0.v20130902-0605.jar:$(BIMSERVER_PATH)org.eclipse.emf.ecore_2.9.1.v20130827-0309.jar:$(BIMSERVER_PATH)bimserver-1.4.0-FINAL-2015-11-04-shared.jar:$(BIMSERVER_PATH)bimserver-1.4.0-FINAL-2015-11-04.jar:$(BIMSERVER_PATH)commons-io-1.4.jar:$(BIMSERVER_PATH)guava-18.0.jar:$(BIMSERVER_PATH)slf4j-api-1.6.2.jar"

BIMserver-query-plugin-shell.jar: src/main/java/nz/ac/auckland/cs/*.java
	mkdir built
	javac -classpath "$(BIMSERVER_JARS)" -sourcepath ./src/main/java -d built src/main/java/nz/ac/auckland/cs/*.java
	cp -R plugin/ built
	cd built && jar cf BIMserver-query-plugin-shell.jar * && cd ..
	mv built/BIMserver-query-plugin-shell.jar BIMserver-query-plugin-shell.jar

all: BIMserver-query-plugin-shell.jar

clean:
	rm -rf built

.DEFAULT_GOAL := all

.PHONY: clean
