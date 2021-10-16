# Spring Boot Jasypt API

Jasypt Encryption Implementation using Spring Boot

## Setup Locally

```bash
git clone https://github.com/residwi/spring-boot-jasypt.git
mvn spring-boot:run
```

Access swagger docs in http://localhost:8080/swagger-ui.html, or you can access [here](https://spring-boot-jasypt.herokuapp.com/) for demo.

## API

### Encryption

```http request
POST /api/encrypt
Content-Type: application/json
```

Request body for encryption **ONE_WAY**

```json
{
  "plainText": "string",
  "type": "ONE_WAY"
}
```

Request body for encryption **TWO_WAY**

```json
{
  "plainText": "string",
  "type": "TWO_WAY",
  "secretKey": "string"
}
```

### Decryption

```http request
POST /api/decrypt
Content-Type: application/json
```

Request body for decryption **ONE_WAY**

```json
{
  "encryptedText": "string",
  "type": "ONE_WAY",
  "matchText": "string"
}
```

Request body for decryption **TWO_WAY**

```json
{
  "encryptedText": "string",
  "type": "TWO_WAY",
  "secretKey": "string"
}
```








