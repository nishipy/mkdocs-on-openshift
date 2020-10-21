# Sample MkDocs on OpenShift
![](images/sampledoc.jpg)

## Versions
```
$ oc version
Client Version: 4.4.8
Server Version: 4.4.8
Kubernetes Version: v1.17.1+3f6f40d
```

## Execute commands one by one
```
$ oc new-project mkdocs-dev
$ oc run mkdocs --image=nishipy/mkdocs:sha-512b185
$ oc expose deploymentconfigs.apps.openshift.io mkdocs --port 8000
$ oc expose service mkdocs
$ oc get route mkdocs
```

## Test
- Test by using Selenium
  ![](images/selenuim_test.gif)