# My Spring Boot Web Service Template

## Quick Steps
1. Clone it: 
```
git clone https://kcheung00@github.com/kcheung00/spring_ws_template.git
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
curl http://localhost:8080/greeting
curl http://localhost:8080/greeting?name=ABCD
```

## Qucik Note Create SSL Cert
**Create PKCS12**
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