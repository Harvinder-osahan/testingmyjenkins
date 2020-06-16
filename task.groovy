job("JobFromGroovy"){
	description("Launching a Docker Container")
	
        scm {
              github('Harvinder-osahan/testingmyjenkins', 'Groovy')
             }
        triggers{
                scm("* * * * *")
                }
	steps{
         shell('sudo docker run -dit --name webgame -p 1919:80 -v  "$PWD":/usr/local/apache2/htdocs/   httpd')
        }       
}
