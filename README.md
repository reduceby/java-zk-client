# ZooKeeper
ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services. All of these kinds of services are used in some form or other by distributed applications.

## Helm Chart
```shell
$ kubectl create namespace zoo
$ helm repo add bitnami https://charts.bitnami.com/bitnami
$ helm install zoo bitnami/zookeeper -n zoo
```

## Connection
```shell
$ kubectl port-forward svc/zoo-zookeeper 2181:2181 --namespace zoo
```