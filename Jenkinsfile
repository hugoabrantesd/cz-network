node {
    def WORKSPACE = "./"
    def dockerImageTag = "cz_network-deploy${env.BUILD_NUMBER}"

    try{
         stage('Clone Repo') {
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

        throw e
    }finally{

    }
}
