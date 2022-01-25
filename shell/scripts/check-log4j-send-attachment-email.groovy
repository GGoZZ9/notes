String fileName=""

pipeline{
    agent any
    stages{
        stage('check log4j and download record file'){
            steps{
                script{
                    def recordFile = sh(script: "curl http://10.29.70.141:8080/check-log4j  -H 'accept: application/json'", returnStdout: true)
                    fileName = recordFile.substring(recordFile.lastIndexOf("/") + 1)
                    println "fileName" + fileName
                    def checkLog4jRecord = sh(script: "curl http://10.29.70.141:8080/downloadResultFile?resultFile=record/${fileName} --output ${WORKSPACE}/${fileName} -H 'accept: application/vnd.ms-excel'", returnStdout: true)

                    sleep 30
                }
            }
        }

        stage('send email with xls record'){
            steps{
                script{
                    emailext(
                            attachmentsPattern: "log4j-check-version-record-*.xls",
                            to: NOTIFICATION_EMAIL_ADDRESSES,
                            mimeType: 'text/html',
                            subject: "Log4j check finished -- on ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                            body:
                                    """
                                    <!DOCTYPE html>
                                    <html>
                                        <body>
                                        <p>Check Apache log4j in maven project finished, please download record file: ${fileName}</p>
                                        <p>This email is sent automatically, do not reply.</p>
                                        </body>
                                    </html>
                                    """
                    )
                }
            }
        }

        stage('clean workspace'){
            steps{
                script{
                    sh(script: "rm -rf *")
                }
            }
        }
    }
}