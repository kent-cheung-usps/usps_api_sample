<details>
  <summary><h2>Quick Started</h2></summary>
  
  1. Clone it: 
  ```
  git clone https://github.com/kcheung00/usps_api_sample.git
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
  ### Quick HTML UI
  ```
  http://localhost:8080/index.html
  ```
  ### Spring Actuator
  ```
  http://localhost:8080/actuator/health
  ```
  ### Swagger
  ```
  http://localhost:8080/swagger-ui/index.html
  ```
</details>

<details>
  <summary><h2>Docker & Kubernetes<h2></summary>
    
  Ensure Dockerfile in the repo
  ```
  docker build -t usps_api_sample:local .
  ```
  Run the USPS API Sample locally with Docker
  ```
  docker run -p 8080:8080 usps_api_sample:local
  ```
  Apply the Kubernetes manifests files locally
  ```
  kubectl apply -f deployment.yaml
  kubectl apply -f service.yaml
  ```
</details>

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
