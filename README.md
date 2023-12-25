# DropletMS
DropletMS: A compact and powerful microservice designed for efficient cloud droplet creation and destruction.

## HOW TO CONNECT TO A DROPLET?
ssh -i <ssh_public_key_path> root@<Droplet_IP>
> Example: ssh -i C:\Users\nicol\Desktop\DropletMS\src\main\java\com\nicogtzl\SSHKeys\id_rsa root@188.166.41.118

## DIGITALOCEAN
DROPLET CREATION: Main <token> create <name> <region> <size> <image>
> Example: ./gradlew run --args="<token> create 1 ams3 s-1vcpu-512mb-10gb ubuntu-23-10-x64"

DROPLET DESTRUCTION: Main <token> destroy <dropletId>
> Example: ./gradlew run --args="<token> destroy 392171886"

## GOOGLE CLOUD
INSTANCE CREATION: ./gradlew run --args="googlecloud <token> create <project_id> <instance_name> <region> <zone> <machine_type> <image>"
> Example: ./gradlew run --args="googlecloud <token> create stellar-operand-409204 mi-instancia us-central1 us-central1-a e2-micro projects/ubuntu-os-cloud/global/images/ubuntu-2310-mantic-amd64-v20231215"

DESTROY AN INSTANCE: ./gradlew run --args="googlecloud <token> destroy <project_id> <zone> <instance_name>"
> Example: ./gradlew run --args="googlecloud <token> destroy stellar-operand-409204 us-central1-a mi-instancia"
