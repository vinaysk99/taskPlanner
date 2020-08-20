Task Planner:
* sample java springboot project to plan tasks:
* clone the source code : `git clone https://github.com/vinaysk99/taskPlanner.git`

Build and Run Locally:
* Build with gradle : `./gradlew build`
* update dependencies : `mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)`
* Run : `./gradlew bootRun` and verify `http://localhost:8080/v1/plans`

Dockerise and run:
* build image `docker build -t vinaysk99/sample-planner-docker .`
* run : `docker run -p 8080:8080 -t vinaysk99/sample-planner-docker`
* explore the apis : `http://localhost:8080/v1/plans`

Kube and run:
* download and install kubectl : `brew install kubectl`
* download and install minikube : `brew install minikube`
* start: `minikube start`
* check cluster is created and running : `kubectl cluster-info`
* create deployment(specify dockerImage) : 
    * ex: `kubectl create deployment {deploymentName} --image={dockerImage} --dry-run -o=yaml > deployment.yaml`
    * ->  `kubectl create deployment demo --image=vinaysk99/sample-planner-docker --dry-run -o=yaml > deployment.yaml`
* add : `echo --- >> deployment.yaml`
* create service : 
    * `kubectl create service clusterip {deploymentName} --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml`
    * `kubectl create service clusterip demo --tcp=8080:8080 --dry-run -o=yaml >> deployment.yaml`
* run deployment.yaml on kube(skip prev steps if updating): `kubectl apply -f deployment.yaml`
* verify the services/pods are running : `kubectl get all`
* create an SSH tunnel to connect to services : `kubectl port-forward svc/demo 8080:8080`
