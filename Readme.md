Spring Framework 3.3.3
Spring Cloud version 2023.0.3
Java 17

POST http://localhost:8005/auth/signup
{
"email": "test@gmail.com.com",
"password":"test",
"fullName":"Naveen"
}

POST http://localhost:8005/auth/login
{
"email": "test@gmail.com.com",
"password":"test"
}

GET http://localhost:8005/users/me
    