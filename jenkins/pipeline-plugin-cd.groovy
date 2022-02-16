library 'utils@master'
env.REPOSITORY_URL = "https://git.homecreditcfc.cn/it-dev/development-office/jenkins/${PLUGIN_NAME}.git"

pipeline {
    agent {
        kubernetes {
            cloud 'sit-buildtime'
            namespace 'buildtime'
            defaultContainer "java-buildtime"
            // yaml templateManager.getJavaBuildTimeAgentTemplate('sit', [BUILDTIME_IMAGE_TAG: "adoptopenjdk-1.8.0_252-slim--alpine-3.11"])
            yaml templateManager.getJavaBuildTimeAgentTemplate('sit',['BUILDTIME_IMAGE_TAG':  "adoptopenjdk-1.8.0_252-slim--alpine-3.11",CERT_PATH:pipelineConstants.CERT_PATH,DEPLOYMENT_ENV: "sit"])


        }
    }

    environment {
        CURRENT_ENV = "sit"
    }

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    stages {
        stage("Check out Code") {
            steps {
                container("java-buildtime") {
                    script {
                        pipelineJavaCI.checkoutCodes(null, "${REPOSITORY_URL}", "master")
                    }
                }
            }
        }
        stage("Build") {
            steps {
                container("java-buildtime") {
                    script {
                        def now = new Date()
                        def mArtifactId = "${PLUGIN_NAME}".toLowerCase()
                        def mVersion = now.format("yyyyMMdd", TimeZone.getTimeZone('GMT+08:00'))
                        def mClassifier = now.format("HHmmss", TimeZone.getTimeZone('GMT+08:00'))+"+${GIT_SHORT_COMMIT}"
                        def mFile = "target/${mArtifactId}.hpi"
                        def mType = "hpi"
                        manager.addShortText(mArtifactId+"\n"+mVersion+"-"+mClassifier)

                        sh 'mvn package'
                        archiveArtifacts artifacts: mFile

                        nexusArtifactUploader(
                            artifacts: [[ \
                                artifactId: mArtifactId, \
                                classifier: mClassifier, \
                                file: mFile, \
                                type: mType \
                            ]], \
                            credentialsId: pipelineConstants.NEXUS_DEPLOYMENT_CREDENTIAL, \
                            groupId: "jenkins.plugins", \
                            nexusUrl: pipelineConstants.NEXUS_REPO_URL-"https://", \
                            nexusVersion: 'nexus3', \
                            protocol: 'http', \
                            repository: "maven2-hosted-3rd-parties", \
                            version: mVersion)
                            env.downloadUrl = "http://alm.homecredit.cn/nexus/repository/maven2-hosted-3rd-parties/jenkins-plugins/${PLUGIN_NAME}/${mVersion}/${PLUGIN_NAME}-${mVersion}-${mClassifier}.${mType}"
                            println("downloadUrl -> $downloadUrl")
                    }
                }
            }
        }
    }
}
/*node{
    stage("deploy"){
        withCredentials([usernamePassword(credentialsId: pipelineConstants.JENKINS_ADMIN_CREDENTIAL, passwordVariable: 'password', usernameVariable: 'username')]) {
            wrap([$class: 'BuildUser']) {
                def password = '1184f536195c802ef5daa19d74c21bfa57'
                def username = 'hulk.yang'
                def jenkinsHome = "https://jenkins.homecreditcfc.cn"
                sh "wget ${jenkinsHome}/jnlpJars/jenkins-cli.jar -O jenkins-cli.jar"
                sh "java -jar jenkins-cli.jar -s ${jenkinsHome} -webSocket -auth ${username}:${password} install-plugin ${downloadUrl}" + ("$RESTART"=="true"?" -restart":"")
            }
        }
    }
}*/

