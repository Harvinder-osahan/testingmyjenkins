job("JobFromGroovy"){

	steps{
         shell('sleep 60')
        }       
}

job("LaunchingDockerContainer"){
	description("Launching a Docker Container")
	
        scm {
              github('Harvinder-osahan/testingmyjenkins', 'k8s2')
             }
        triggers{
                scm("* * * * *")
                }
         
	steps{
         shell('sudo docker run -dit --name webgame -p 1919:80 "$PWD":/usr/local/apache2/htdocs/  httpd')
        }   
 	

}

job("LaunchingDockerContainer"){
	description("Launching a deployment on Kubernetes ")
	       
	steps{
         shell('if sudo kubectl get deployments | grep mygamedep; then  exit 0; 
	         else sudo cd /root/DevOpsAL;  sudo kubectl create -f /root/DevOpsAL/deploy.yml;
                  sudo kubectl expose deployment mygamedep --port=80 --type=NodePort;fi')
        }   
 
}
