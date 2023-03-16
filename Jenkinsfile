pipeline{
    agent any
    
    stages{
        
        stage("Build"){
            steps{
                echo("Build project")
            }
        }
        
        stage("Deploy to dev"){
            steps{
                echo("Deploy to dev environment")
            }
        }
        
        stage("Deploy to qa"){
            steps{
                echo("Deploy to qa environment")
            }
        }
       
        
        stage("Run regression automation test cases"){
            steps{
                echo("Run regression automation test cases")
            }
        }
        
        stage("Deploy to stage"){
            steps{
                echo("Deploy to stage environment")
            }
        }
        
         
        stage("Run sanity automation test cases"){
            steps{
                echo("Run sanity automation test cases")
            }
        }
        
         stage("Deploy to prod"){
            steps{
                echo("Deploy to prod environment")
            }
        }
        
        
    }
    
}