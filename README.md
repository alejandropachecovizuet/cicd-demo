# cti-cicd-demo

ansible-playbook -i ansible/inventory/hosts -b --become-user axis ansible/deploy-cicd-demo.yml -k

minikube delete
minikube start --insecure-registry "dockerhub:5000"
minikube ssh
#sudo -s
#echo "$(ip route show | awk '/default/ {print $3}')  dockerhub" >> /etc/hosts

docker run -p 5000:5000 registry
o
drun registry registry 5000


docker image build --rm -t interware/cicd-demo:0.1.0 -f Dockerfile.cicd .
docker tag interware/cicd-demo:0.1.0 dockerhub/interware/cicd-demo:0.1.0
docker push dockerhub:5000/interware/cicd-demo:0.1.0

kubectl run cicd-demo --image=dockerhub:5000/interware/cicd-demo:0.1.0 --port=8090
kubectl expose deployment/cicd-demo --type="NodePort" --port 8090

kubectl describe pod cicd-demo-7c8cc8d5c-bbs6s
kubectl describe deployment cicd-demo
kubectl describe services/cicd-demo

