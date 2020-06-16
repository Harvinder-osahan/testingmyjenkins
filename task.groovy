job("JobFromGroovy"){

	steps{
         shell('sleep 10; exit 0')
        }       
}


job("Launching-Kubernetes"){
	description("Launching a deployment on Kubernetes ")
	triggers{
		 upstream{
		 upstreamProjects("JobFromGroovy")
		 threshold("SUCCESS")
	         }
                }
	steps{
         shell('if sudo kubectl get deployments | grep mygamedep ; then  exit 0; else sudo cd /root/DevOpsAL;  sudo kubectl create -f /root/DevOpsAL/deploy.yml;  sudo kubectl expose deployment mygamedep --port=80 --type=NodePort;fi')
        }   
}

job("Rolling-Updates"){
	description("Updating a deployment on Kubernetes ")
	triggers{
		 upstream{
		 upstreamProjects("Launching-Kubernetes")
		 threshold("SUCCESS")
	         }
                }
	steps{
         shell('sudo kubectl set image deploy mygamedep mygame-con=harvinderosahan31/httpd-snake-game:v3 --record=true')
         }
}
