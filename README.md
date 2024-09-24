Installation
1. Clone the repository https://github.com/nnnnnnnnnnnnni-1/RESTful-Service-VK/
2. Load Gradle buildings in build.gradle
3. Run the file with Spring Boot config Application
4. Go to http://localhost:8080/api/v1/user-info in Postman or another service for create requests and send POST method with
   your VK token in the header of request.

Swagger OpenAPI definition по ссылке: http://localhost:8080/swagger-ui/index.html

Вид JSON в теле HTTP POST запроса:
{
    "user_id" : 269866241,
    "group_id" : 168131127
}
