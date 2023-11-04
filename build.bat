aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 584941907545.dkr.ecr.us-east-1.amazonaws.com
docker build -t techchallenger -f Dockerfile .
docker tag techchallenger:latest 584941907545.dkr.ecr.us-east-1.amazonaws.com/techchallenger:latest
docker push 584941907545.dkr.ecr.us-east-1.amazonaws.com/techchallenger:latest