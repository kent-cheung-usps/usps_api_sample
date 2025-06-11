## Disclaimer
This document has been compiled based on personal notes and experiences. While intended for personal use, it is shared here for the benefit of others who may find it useful. Users are advised to adapt and expand these test steps to suit their specific needs and project requirements. No responsibility or liability is assumed for any outcomes or consequences resulting from the use of these notes.

## Quick Started
  
  1. Clone it: 
  ```
  git clone https://github.com/kent-cheung-usps/usps_api_sample.git
  ```
  2. Build by Maven
  ```
  mvn clean install
  ```
  3. Execute
  ```
  mvn spring-boot:run
  ```
  4. Verification
  ```
  curl -X POST http://localhost:8080/address/validate -H "Content-Type: application/json" -d '{"streetAddress":"3120%20M%20St", "secondaryAddress":"NM", "city":"Washington","state":"DC"}'
  ```
  ```
  curl http://localhost:8080/greeting
  ```
  ```
  curl http://localhost:8080/greeting?name=ABCD
  ```
  ```
  curl -X POST http://localhost:8080/PostGreeting -H "Content-Type: application/json" -d "{\"name\":\"John\"}"
  ```
  ```
  curl -X POST http://localhost:8080/PostParam -H "Content-Type: application/x-www-form-urlencoded" -d "param1=John&param2=GoForIt"
  ```
  #### Quick HTML UI
  ```
  http://localhost:8080/index.html
  ```
  #### Spring Actuator
  ```
  http://localhost:8080/actuator/health
  ```
  #### Swagger
  ```
  http://localhost:8080/swagger-ui/index.html
  ```
## Local Deployment
Deployment with Docker & Kubernetes
    
  Ensure Dockerfile in the repo, then build the image.
  ```
  docker build -t usps_api_sample:local .
  ```
  Run the USPS API Sample locally with Docker.
  ```
  docker run -p 8080:8080 usps_api_sample:local
  ```
  Confirm the usps_api_sample app run.  
  `http://localhost:8080`

  Push the Docker Image to a Registry (ex. Docker Hub).
  ```
  docker tag usps-api-sample:local <your-dockerhub-username>/usps-api-sample:latest
  docker push <your-dockerhub-username>/usps-api-sample:latest
  ```
  Apply the Kubernetes manifests files locally.
  ```
  kubectl apply -f deployment.yaml
  kubectl apply -f service.yaml
  ```
  Confirm pods are running.
  ```
  kubectl get pods
  ```
  Verify the usps_api_sample is running healthy<br>
  - `kubectl get svc`<br>
  - `kubectl describe pod usps-api-sample`<br>
  - `kubectl logs usps-api-sample`<br>
  
  Test Access to the sample app.
  ```
  kubectl port-forward svc/usps-api-sample 8080:80
  ```
  Navigate to: `http://localhost:8080` to confirm the app responds as expected.

<details>
  <summary><h2>Create SSL Cert<h2></summary>
    
   Create PKCS12   
   ```
   keytool -genkeypair -alias springboot -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore springboot.p12 -validity 3650 -storepass myLocalStorePass
   ```
    
   - genkeypair: generates a key pair;
   - alias: the alias name for the item we are generating;
   - keyalg: the cryptographic algorithm to generate the key pair;
   - keysize: the size of the key;
   - storetype: the type of keystore;
   - keystore: the name of the keystore;
   - validity: validity number of days;
   - storepass: a password for the keystore.
    
    **Verify**
    ```
    keytool -list -v -keystore springboot.p12
    (Password == myLocalStorePass)
    ```
</details>


Quick Note:
```
http://56.94.76.203:8080/index.html
```
