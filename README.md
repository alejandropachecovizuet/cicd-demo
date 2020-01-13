# cti-cicd-demo

mvn clean compile
mvn clean test
mvn clean package
ansible-playbook -i ansible/inventory/hosts -b --become-user apacheco ansible/deploy-cicd-demo-docker.yml -k