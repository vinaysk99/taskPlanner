sample project to plan tasks:
* git clone
* ./gradlew build
* mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
* docker build -t vinaysk99/sample-planner-docker .
* docker run -p 8080:8080 -t vinaysk99/sample-planner-docker
* http://localhost:8080/v1/plans