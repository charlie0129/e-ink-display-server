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

- [ ] Link messages to users using many-to-one model

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
   
     |  KEY   |       VALUE        |                         DESCRIPTION                          |
     | :----: | :----------------: | :----------------------------------------------------------: |
     | which  | (defaults to "-1") | How many messages you want? A negative number returns the messages in descending order sorted by submission time and vice versa. |
     | userid | (defaults to "0")  | Only return the messages that are related to the specified user. ("0" means all messages) |
   
   - The required messages will be returned in JSON format. You can use a JSON parser to parse the messages and display them on the e-ink display.
     - Example: `/get-message?which=-4`
     - Returns: `[{"id":4,"message":"1message","time":"2020-11-10T12:28:40.056608","userID":2},{"id":3,"message":"2message","time":"2020-11-10T12:28:37.933348","userID":2},{"id":2,"message":"2message","time":"2020-11-10T12:28:34.644941","userID":1},{"id":1,"message":"1stmessage","time":"2020-11-10T12:28:28.029652","userID":1}]`



## How to run this project

1. Clone this repository

   `git clone https://github.com/charlie0129/e-ink-display-server.git`

2. Create a MySQL database

   ```mssql
   create database db_example;
   create user 'administrator'@'%' identified by 'pswd';
   grant all on db_example.* to 'administrator'@'%';
   ```

3. Run this project

   `mvn spring-boot:run`

   Test the HTTP requests at port `8080`.
