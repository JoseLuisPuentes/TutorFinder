#!/bin/bash

java -Djavax.net.ssl.keyStore=/usr/share/TutorFinderServer/ServerKeyStore.jks \
-Djavax.net.ssl.keyStorePassword=tutorfinder \
-jar /usr/share/TutorFinderServer/TutorFinder.jar server start -ssl basic
