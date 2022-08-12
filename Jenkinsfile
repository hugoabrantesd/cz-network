node {
    def WORKSPACE = "/var/lib/jenkins/workspace/cz_network-deploy"
    def dockerImageTag = "cz_network-deploy${env.BUILD_NUMBER}"

    try{
//           notifyBuild('STARTED')
         stage('Clone Repo') {
            // for display purposes
            // Get some code from a GitHub repository
            git url: 'https://github.com/hugoabrantesd/cz-network.git',
                credentialsId: 'cz-network',
                branch: 'develop'
         }
          stage('Build docker') {
                 dockerImage = docker.build("cz_network-deploy:${env.BUILD_NUMBER}")
          }

          stage('Deploy docker'){
                  echo "Docker Image Tag Name: ${dockerImageTag}"
                  sh "docker stop cz_network-deploy || true && docker rm cz_network-deploy || true"
                  sh "docker run --name cz_network-deploy -d -p 8081:8080 cz_network-deploy:${env.BUILD_NUMBER}"
          }
    }catch(e){
//         currentBuild.result = "FAILED"
        throw e
    }finally{
//         notifyBuild(currentBuild.result)
    }
}
