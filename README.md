

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

Table of Contents
=================

   * [e-ink-display-server (under dev)](#e-ink-display-server-under-dev)
   * [API specifications:](#api-specifications)
      * [Message related APIs](#message-related-apis)
      * [User related APIs](#user-related-apis)
      * [e-Ink display related APIs](#e-ink-display-related-apis)
      * [API Details](#api-details)
         * [POST /api/messages: Add a message](#post-apimessages-add-a-message)
         * [GET /api/messages: List messages](#get-apimessages-list-messages)
         * [GET /api/messages/{messageId}: Identify the message](#get-apimessagesmessageid-identify-the-message)
         * [PUT /api/messages/{messageId}: Replace the message](#put-apimessagesmessageid-replace-the-message)
         * [POST /api/users: add a user](#post-apiusers-add-a-user)
         * [GET /api/users/{id}: Identify the user](#get-apiusersid-identify-the-user)
         * [PUT /api/users/{id}: Replace the user](#put-apiusersid-replace-the-user)
         * [GET /api/users/{userId}/messages: List messages related to the user](#get-apiusersuseridmessages-list-messages-related-to-the-user)
         * [POST /api/displays: Add a display](#post-apidisplays-add-a-display)
         * [GET /api/displays: List displays](#get-apidisplays-list-displays)
         * [GET /api/displays/{id}: Identify the displays](#get-apidisplaysid-identify-the-displays)
         * [PUT /api/displays/{id}: Replace the displays](#put-apidisplaysid-replace-the-displays)
         * [GET /api/displays/{displayId}/messages: List messages related to the displays](#get-apidisplaysdisplayidmessages-list-messages-related-to-the-displays)
   * [How to run this project](#how-to-run-this-project)

---

# API specifications:

Most APIs are [`RESTful`](https://en.wikipedia.org/wiki/Representational_state_transfer) APIs like this `http://[host]:[port]/{service name}/{resource}`.

## Message related APIs

| Resources                   | POST          | GET                  | PUT                 | DELETE             |
| --------------------------- | ------------- | -------------------- | ------------------- | ------------------ |
| `/api/messages`             | Add a message | List messages        | -                   | -                  |
| `/api/messages/{messageId}` | -             | Identify the message | Replace the message | Delete the message |

## User related APIs

| Resources                      | POST       | GET                               | PUT              | DELETE          |
| ------------------------------ | ---------- | --------------------------------- | ---------------- | --------------- |
| `/api/users`                   | Add a user | -                                 | -                | -               |
| `/api/users/{id}`              | -          | Identify the user                 | Replace the user | Delete the user |
| `/api/users/{userId}/messages` | -          | List messages related to the user | -                | -               |

## e-Ink display related APIs

| Resources                             | POST              | GET                               | PUT                 | DELETE             |
| ------------------------------------- | ----------------- | --------------------------------- | ------------------- | ------------------ |
| `/api/displays`                       | Add a new display | List displays                     | -                   | -                  |
| `/api/displays/{id}`                  | -                 | Identify the display              | Replace the display | Delete the display |
| `/api/displays/{displayId}/messsages` | -                 | List messages related the display | -                   | -                  |

## API Details

### `POST /api/messages`: Add a message

- Request body is in JSON

- Required fields in the request body:

    | KEY           | TYPE   | DESCRIPTION                                                  |
    | ------------- | ------ | ------------------------------------------------------------ |
    | message       | String | The message to be delivered                                  |
    | time          | String | Submission time in the format of "yyyy-MM-dd HH:mm:ss". Example: "2012-02-01 12:01:02". *(This field can be empty)* |
    | userId        | Number | Identification of the user                                   |
    | eInkDisplayId | Number | Identification of the display                                |

- An example of request body:

    ```json
    {
      "message": "Hello, world!",
  "time": "2021-01-01 00:01:01",
      "userId": "1",
      "eInkDisplayId": "1"
    }
  ```
  
- An example of successful response:

    ```json
    {
      "id": 1,
      "message": "Hello, world!",
      "time": "2021-01-01T00:01:01",
      "user": {
        "id": 1,
        "name": "无名氏"
      },
      "einkDisplay": {
        "id": 1,
        "name": "Sample Display",
        "latitude": 40.156,
        "longitude": 116.283
      },
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/messages/1"
        },
        "messagesFromUser": {
          "href": "http://localhost:8080/api/users/1/messages?n=0"
        },
        "messagesFromDisplay": {
          "href": "http://localhost:8080/api/displays/1/messsages"
        },
        "messages": {
          "href": "http://localhost:8080/api/messages"
        }
      }
    }
    ```

    

### `GET /api/messages`: List messages

- No additonal fields required

- An example of successful response:

    ```json
    {
      "_embedded": {
        "messageList": [
          {
            "id": 1,
            "message": "Hello, world!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "无名氏"
            },
            "einkDisplay": {
              "id": 1,
              "name": "Sample Display",
              "latitude": 40.156,
              "longitude": 116.283
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/1"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/1/messsages"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          },
          {
            "id": 2,
            "message": "Hello, world! Again!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 2,
              "name": "老王"
            },
            "einkDisplay": {
              "id": 1,
              "name": "Sample Display",
              "latitude": 40.156,
              "longitude": 116.283
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/2"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/2/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/1/messsages"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/messages"
        }
      }
    }
    ```



### `GET /api/messages/{messageId}`: Identify the message

- No additonal fields required
- Successful response is the same as `POST /api/messages`

### `PUT /api/messages/{messageId}`: Replace the message

- Refer to `POST /api/messages`. Data fields are the same.

### `POST /api/users`: add a user

- Request body is in JSON

- Required fields in the request body:

    | KEY  | TYPE   | DESCRIPTION |
    | ---- | ------ | ----------- |
    | id   | Number | User ID     |
    | name | String | User name   |

- An example of request body:

    ```json
    {
      "id": "1",
      "name": "无名氏"
    }
    ```

- An example of successful response:

    ```json
    {
      "id": 1,
      "name": "无名氏",
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/users/1"
        }
      }
    }
    ```



### `GET /api/users/{id}`: Identify the user

- No additonal fields required
- Successful response is the same as `POST /api/users`

### `PUT /api/users/{id}`: Replace the user

- Refer to `POST /api/users`. Data fields are the same.

### `GET /api/users/{userId}/messages`: List messages related to the user

- No additonal fields required

- An example of successful response:

    ```json
    {
      "_embedded": {
        "messageList": [
          {
            "id": 3,
            "message": "Hello, world! Once more!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 2,
              "name": "老王"
            },
            "einkDisplay": {
              "id": 2,
              "name": "Another Sample Display",
              "latitude": 40.35,
              "longitude": 116.65
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/3"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/2/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/2/messsages"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          },
          {
            "id": 2,
            "message": "Hello, world! Again!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 2,
              "name": "老王"
            },
            "einkDisplay": {
              "id": 1,
              "name": "Sample Display",
              "latitude": 40.156,
              "longitude": 116.283
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/2"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/2/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/1/messsages"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          }
        ]
      }
    }
    ```

### `POST /api/displays`: Add a display

- Request body is in JSON

- Required fields in the request body:

    | KEY       | TYPE   | DESCRIPTION           |
    | --------- | ------ | --------------------- |
    | id        | Number | Display serial number |
    | name      | String | Display description   |
    | latitude  | Number | Geographic location   |
    | longitude | Number | Geographic location   |

- Request body example:

    ```json
    {
      "id": "1",
      "name": "Sample Display",
      "latitude": "40.156",
      "longitude": "116.283"
    }
    ```

- Response example:

    ```json
    {
      "id": 1,
      "name": "Sample Display",
      "latitude": 40.156,
      "longitude": 116.283,
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/displays/1"
        },
        "displays": {
          "href": "http://localhost:8080/api/displays"
        }
      }
    }
    ```

    

### `GET /api/displays`: List displays

- No additonal fields required

- An example of successful response:

    ```json
    {
      "_embedded": {
        "eInkDisplayList": [
          {
            "id": 0,
            "name": "General Display",
            "latitude": 0.0,
            "longitude": 0.0,
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/displays/0"
              },
              "displays": {
                "href": "http://localhost:8080/api/displays"
              }
            }
          },
          {
            "id": 1,
            "name": "Sample Display",
            "latitude": 40.156,
            "longitude": 116.283,
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/displays/1"
              },
              "displays": {
                "href": "http://localhost:8080/api/displays"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/displays"
        }
      }
    }
    ```

    

### `GET /api/displays/{id}`: Identify the displays

- No additonal fields required
- Successful response is the same as `POST /api/displays`

### `PUT /api/displays/{id}`: Replace the displays

- Refer to `POST /api/displays`. Data fields are the same.

### `GET /api/displays/{displayId}/messages`: List messages related to the displays

- No additonal fields required

- An example of successful response:

    ```json
    {
      "_embedded": {
        "messageList": [
          {
            "id": 2,
            "message": "Hello, world! Again!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 2,
              "name": "老王"
            },
            "einkDisplay": {
              "id": 1,
              "name": "Sample Display",
              "latitude": 40.156,
              "longitude": 116.283
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/2"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/2/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/1/messsages"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          },
          {
            "id": 1,
            "message": "Hello, world!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "无名氏"
            },
            "einkDisplay": {
              "id": 1,
              "name": "Sample Display",
              "latitude": 40.156,
              "longitude": 116.283
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/1"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/1/messsages"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          }
        ]
      },
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/displays/1/messsages"
        }
      }
    }
    ```

    

# How to run this project

1. Clone this repository

   `shell`:

   ```shell
   git clone --depth=1 https://github.com/charlie0129/e-ink-display-server.git
   ```

2. Install `maven`, `mysql` and `jdk` on your computer

3. Login to MySQL and create a database

   `shell`:

   ```shell
   mysql -uroot
   ```
   
   `SQL`:

   ```mysql
   create database eink_server_db;
   create user 'eink_server_admin'@'%' identified by 'your_password';
   grant all on eink_server_db.* to 'eink_server_admin'@'%';
   exit;
   ```
   
4. Run this project

   `shell`:

   ```shell
   cd e-ink-display-server
   mvn spring-boot:run
   ```

   The server will start after all the dependencies are installed (a proxy is recommended during dependency installation in China mainland). You can test `HTTP` requests at port `8080` using the APIs above.

   Use `control-c` to stop the server.

5. Notes:

    - Create a display (`POST /api/displays`) and a user (`POST /api/users`) before adding any messages because you need `displayId` and `userId` when creating the message.
