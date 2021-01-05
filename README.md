# e-ink-display-server (under dev)

Back end for BUPT ChuYan project "e-ink Display".

*All APIs are for test purposes.*

**This project is still in its alpha stage,  under development. There is no guarantee that the program can run without problems.**

 TODO:

- [x] Relay messages using HTTP GET

- [x] Commit messages using HTTP POST

- [x] Link different messages to different users

- [x] Retrieve a list of messages using HTTP GET

- [x] Store users and messages using MySQL database

- [x] Link messages to users using many-to-one model

- [ ] Require user identification when committing and retrieving messages

- [ ] Security improvements

  ......



## HTTP request examples (only for test purposes):

1. Add a user.

   - Use `HTTP POST` method at `/add-user`

     |  KEY  | VALUE |       DESCRIPTION        |
     | :---: | :---: | :----------------------: |
     | name  |       |     The user's name.     |
     | phone |       | The user's phone number. |
   
   - The user will be assigned an ID. It is returned as the response.
   
2. Add a message

   - Use `HTTP POST` method at `/add-message`

     |   KEY   | VALUE |              DESCRIPTION               |
     | :-----: | :---: | :------------------------------------: |
     | userid  |       | The user that this message belongs to. |
     | message |       |          The message it self.          |

   - The message will be assigned an ID. It is returned as the response.

3. Retrieve a message list
   - Use `HTTP GET` method at `/get-message`
   
     |  KEY   |       VALUE       |                         DESCRIPTION                          |
     | :----: | :---------------: | :----------------------------------------------------------: |
     | userid | (defaults to "0") | Only get the messages that are relevant to the user ID. But a user ID of `0` means getting messages from any user. |
     
   - The required messages will be returned in JSON format. You can use a JSON parser to parse the messages and display them on the e-ink display.
     - Example: 
     
         ```json
         [
           {
             "id": 9,
             "message": "hello world once more",
             "time": "2021-01-06T00:00:17.838585",
             "user": { "id": 1, "name": "charlie", "phone": 133 }
           },
           {
             "id": 8,
             "message": "hello world again",
             "time": "2021-01-06T00:00:07.464932",
             "user": { "id": 1, "name": "charlie", "phone": 133 }
           },
           {
             "id": 7,
             "message": "hello world",
             "time": "2021-01-05T23:59:54.655655",
             "user": { "id": 1, "name": "charlie", "phone": 133 }
           }
         ]
         
         ```
     
         

## How to run this project

1. Clone this repository

   ```shell
   git clone https://github.com/charlie0129/e-ink-display-server.git
   ```

2. Install `maven`, `mysql` and `jdk`

3. Login to MySQL and create a database

   ```shell
   # shell command
   mysql -uroot
   ```

   ```mysql
   -- SQL statements
   create database db_example;
   create user 'administrator'@'%' identified by 'pswd';
   grant all on db_example.* to 'administrator'@'%';
   ```

4. Run this project

   ```shell
   cd e-ink-display-server
   mvn spring-boot:run
   ```
   
   The server will start after all the dependencies are installed. You can test `HTTP` requests at port `8080` using the APIs above.
   
   Use `control-c` to stop the server.
   
5. (Optional) Create a user, add a message and retrieve it.

    ```shell
    # Create a user named Jonny Appleseed with a phone number of 0109234
    ID=("$(curl -X POST "http://localhost:8080/add-user" -d "name=Johnny%20Appleseed&phone=0109234")")
    
    # Add a message "Hello, world" to the user just created
    curl -X POST "http://localhost:8080/add-message" -d "userid=${ID}&message=Hello,%20world"
    
    # Retrieve messages from the server and display them through 'stdout'
    curl -X GET "http://localhost:8080/get-message?userid=${ID}" | python -m json.tool
    ```

    
