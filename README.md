# IdeaGenius
Something like a pathfinder for your next coding project. Choose which project types you prefer. | API that works with OpenAI's ChatGPT  | Staffbase Internship

## Before running the project
Make sure to insert you OpenAI API Key in [application.properties](src/main/resources/application.properties)
```
openAi.apiKey= <your OpenAI key>
```
You can get your own for free key at [OpenAI](https://platform.openai.com/)


## Endpoints
Use this [link](https://api.postman.com/collections/19754314-fab6546d-a6bf-4ce8-90c6-e00eeb6725e5?access_key=PMAT-01H9JTV2VENY7C6QNPKV4V5W91) to download my postman workspace.

### User
```
GET http://localhost:8080/user?id=<userId>
```
get User by userId
-> returns User

```
PUT http://localhost:8080/user?id=<userId>&surname=<newSurname>&firstname=<newFirstname>&email=<newEmail>
```
update User
(only id is required)
returns updated User

```
POST http://localhost:8080/user?email=<email>&surname=<surname>&firstname=<firstname>
```
create User
(all parameters are required)
-> returns id of new User

```
DELETE http://localhost:8080/user?id=<userId>
```
delete User
-> returns true if acknowledged

### Process
```
POST http://localhost:8080/process/start?id=<userId>&lang=<language>
```
starts a new process
(all parameters are required, language ISO foramt or writen out)
-> returns first question (this object will include processId)

### Process
```
POST http://localhost:8080/process/response?id=<processId>&choice=<your choice>
```
response to previous question
-> returns next question or final idea

Have a look at
- [/dtos](src/main/kotlin/de/jeske/restapiwithopenai/dtos)
- [/controller](src/main/kotlin/de/jeske/restapiwithopenai/controller)
  
to see DTO's and Controller more in detail.
