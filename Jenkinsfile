pipeline {
  // ref: https://github.com/mosuke5/openshift-pipeline-practice-java/blob/master/Jenkinsfile
  // pipelineを実行するagentの設定。yamlファイルで設定を渡せる
  // 可能な限りJenkinsfileにagentの設定をもたせたほうが自動化とGit管理が進むためおすすめ。
  agent {
    kubernetes {
      cloud 'openshift'
      yamlFile 'manifests/jenkins-agent-deploy.yaml'
    }
  }

  environment {
    deploy_branch = "origin/main"
    deploy_project = "mkdocs-dev"
    app_name = 'mkdocs'
    app_image = "image-registry.openshift-image-registry.svc:5000/${deploy_project}/${app_name}"
  }

  stages {
    stage('Setup') {
      steps {
        echo "Setting up..."
      }
    }

    stage('Application test') {
      parallel {
        stage('Code analysis') {
          steps {
            echo "Executing static analysis..."
          }
        }

        stage('Unit test') {
          steps {
            echo "Executing unit test..."
          }
        }
      }
    }

    stage('Build and Tag OpenShift Image') {
      when {
        expression {
          return env.GIT_BRANCH == "${deploy_branch}" || params.FORCE_FULL_BUILD
        }
      }

      steps {
        echo "Building OpenShift container image"
        script {
          openshift.withCluster() {
            openshift.withProject("${deploy_project}") {
              //oc apply -f manifests/mkdocs-build.yaml
              openshift.apply('-f', 'manifests/mkdocs-build.yaml')

              //oc start-build mkdocs
              openshift.selector("bc", "${app_name}").startBuild("--wait=true")
              //openshift.tag("${app_name}:latest", "${app_name}:${env.GIT_COMMIT}")
            }
          }
        }
      }
    }

    stage('Deploy app on OpenShift') {
      when {
        expression {
          return env.GIT_BRANCH == "${deploy_branch}" || params.FORCE_FULL_BUILD
        }
      }

      steps {
        echo "Deploying app on OpenShift"
        script {
          openshift.withCluster() {
            openshift.withProject("${deploy_project}") {
              // oc apply -f mkdocs-deploy.yaml
              openshift.apply('-f', 'manifests/mkdocs-deploy.yaml')

              // Wait for application to be deployed
              def dc = openshift.selector("dc", "${app_name}").object()
              def dc_version = dc.status.latestVersion
              def rc = openshift.selector("rc", "${app_name}-${dc_version}").object()

              echo "Waiting for ReplicationController ${app_name}-${dc_version} to be ready"
              while (rc.spec.replicas != rc.status.readyReplicas) {
                sleep 5
                rc = openshift.selector("rc", "${app_name}-${dc_version}").object()
              }
            }
          }
        }
      }
    }

    stage('Integration test') {
      when {
        expression {
          return env.GIT_BRANCH == "${deploy_branch}" || params.FORCE_FULL_BUILD
        }
      }

      steps {
        echo "Test by curl and selenium"
        script {
          openshift.withCluster() {
            openshift.withProject("${deploy_project}") {
              def dc = openshift.selector("route", "${app_name}").object()
              def url = dc.spec.host
              echo "${url}"
              while (true) {
                def app_status = sh(returnStdout: true, script: "curl ${url} -o /dev/null -w '%{http_code}' -s").trim()
                if(app_status == "200") {
                  break;
                }
                sleep 5
              }
              // selenium test
              sh "python test/selenium-sample.py http://${url}"
            }
          }
        }
      }
    }
  }
}