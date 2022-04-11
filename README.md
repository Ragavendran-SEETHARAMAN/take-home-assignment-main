## Task Implemented
1.  Completed the task as per the given requirement document.

2.  Implemented REST service to fetch the User account details by providing
Username & Password.

URL to invoke the REST service:

```http://localhost:8080/marionete/useraccount```.

```
Request:
{
"username": "bla",
"password": "foo"
}

Response:
{
"accountInfo": {
"accountNumber": "12345-3346-3335-4456"
},
"userInfo": {
"name": "John",
"surname": "Doe",
"sex": "male",
"age": 32
}
}
```

3.Service implemented by calling REST service to invoke the gRPC server.

4.**Main Class ->** Main Springboot java java class (SpringBootMain.java) resides under Src/Main 

5.Able to fetch the User and account details by passing the token in the provided USerInfoMock and AccountInfoMock service.

6.Implemented to handle **Sync/Async calls**

7.As mentioned in the requirment document, **Yes implementation is done to
handle the AccountInfoMock service, since AccountInfoMock will fail for first two 
calls. Check(AccountServiceImpl.java)**

8.Implemented **JWT (Json Web Token)** to generate token to pass for other calls 
AccountInfoMock & UserInfoMock (backend module) to fetch Account & user details

9.Implemented **Exception handling**

10.Implemented Logger to track trace the output.

11.Created junIt to test the UserController class and also User & Account Service implementation classes

12.SpringBootMain is the main class resides inside Src/Main folder

##  Framework/Libraries Used:
1.Spring boot (Web)

2.grpc-server-spring-boot-starter

3.Spring Security jar

4.JsonWebToken (To generate Jwt Token)

5.Grpc related jars

6.jackson-dataformat-yaml (Object Mapper to convert jSon string response to required Object)

7.lombok (used for Getter and Setter, Args Contructor etc.,)

8.org.mapstruct (Mapper Used to convert from one object to another Object )

9.jUnit & Mockito dependent jars

10.Log4J App(Latest non vulnerable version)

11.validation-api

## Code files Structure 
![img_3.png](img_3.png)

![img_4.png](img_4.png)

![img_6.png](img_6.png)

