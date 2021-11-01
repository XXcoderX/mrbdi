# Use docker build and then use Docker run

# Docker Build Command
docker build -f dockerfile -t validatejourney:v1 .
# DOcker run command
docker run -it -p 8080:8080 --name=validatejouney validatejourney:v1
#Run the service
URL : localhost:8080/mrbdi/validate_journey
Input format : { "vin": "W1K2062161F0014", "source": "source name", "destination": "destination name" }
