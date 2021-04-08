

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
   * [Table of Contents](#table-of-contents)
   * [API specifications:](#api-specifications)
      * [Message related APIs](#message-related-apis)
      * [User related APIs](#user-related-apis)
      * [e-Ink display related APIs](#e-ink-display-related-apis)
      * [Image related APIs](#image-related-apis)
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
         * [GET /api/images/{imageId}: Get a certain image](#get-apiimagesimageid-get-a-certain-image)
         * [POST /api/messages/{messageId}/images: Add an image to a certain message](#post-apimessagesmessageidimages-add-an-image-to-a-certain-message)
   * [How to run this project](#how-to-run-this-project)

---

# API specifications:

Most APIs are [`RESTful`](https://en.wikipedia.org/wiki/Representational_state_transfer) APIs like this `http://[host]:[port]/{service name}/{resource}`.

## Message related APIs

| Resources                          | POST                              | GET                                      | PUT                 | DELETE                                    |
| ---------------------------------- | --------------------------------- | ---------------------------------------- | ------------------- | ----------------------------------------- |
| `/api/messages`                    | Add a message                     | List messages                            | -                   | -                                         |
| `/api/messages/{messageId}`        | -                                 | Identify the message                     | Replace the message | Delete the message and the related images |
| `/api/messages/{messageId}/images` | Add an image to a certain message | List images related to a certain message |                     |                                           |

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

## Image related APIs

| Resources                          | POST                              | GET                  | PUT  | DELETE |
| ---------------------------------- | --------------------------------- | -------------------- | ---- | ------ |
| `/api/images`                      |                                   | List all image files |      |        |
| `/api/images/{imageId}`            |                                   | Get a certain image  |      |        |
| `/api/messages/{messageId}/images` | Add an image to a certain message |                      |      |        |



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
      "eInkDisplayId": "0"
    }
  ```
  
- An example of successful response:

    ```json
    {
      "id": "0a901ec0-d11b-46e5-9f87-5a58f6f5f023",
      "message": "Hello, world!",
      "time": "2021-01-01T00:01:01",
      "user": {
        "id": 1,
        "name": "test user"
      },
      "einkDisplay": {
        "id": 0,
        "name": "General Display",
        "latitude": 0.0,
        "longitude": 0.0
      },
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023"
        },
        "images": {
          "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023/images"
        },
        "messagesFromUser": {
          "href": "http://localhost:8080/api/users/1/messages?n=0"
        },
        "messagesFromDisplay": {
          "href": "http://localhost:8080/api/displays/0/messages?n=0"
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
            "id": "0a901ec0-d11b-46e5-9f87-5a58f6f5f023",
            "message": "Hello, world!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "test user"
            },
            "einkDisplay": {
              "id": 0,
              "name": "General Display",
              "latitude": 0.0,
              "longitude": 0.0
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023"
              },
              "images": {
                "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023/images"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/0/messages?n=0"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          },
          {
            "id": "04bf9f20-bfb7-4b7c-b702-38c4ec72fca2",
            "message": "Hello, world! Again!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "test user"
            },
            "einkDisplay": {
              "id": 0,
              "name": "General Display",
              "latitude": 0.0,
              "longitude": 0.0
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/04bf9f20-bfb7-4b7c-b702-38c4ec72fca2"
              },
              "images": {
                "href": "http://localhost:8080/api/messages/04bf9f20-bfb7-4b7c-b702-38c4ec72fca2/images"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/0/messages?n=0"
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
      "name": "test user"
    }
    ```

- An example of successful response:

    ```json
    {
      "id": 1,
      "name": "test user",
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

- Required fields in the URL parameters:

    | KEY  | TYPE   | DESCRIPTION                |
    | ---- | ------ | -------------------------- |
    | n    | Number | Number of entries you want |

- An example of successful response:

    ```json
    {
      "_embedded": {
        "messageList": [
          {
            "id": "0a901ec0-d11b-46e5-9f87-5a58f6f5f023",
            "message": "Hello, world!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "test user"
            },
            "einkDisplay": {
              "id": 0,
              "name": "General Display",
              "latitude": 0.0,
              "longitude": 0.0
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023"
              },
              "images": {
                "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023/images"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/0/messages?n=0"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          },
          {
            "id": "04bf9f20-bfb7-4b7c-b702-38c4ec72fca2",
            "message": "Hello, world! Again!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "test user"
            },
            "einkDisplay": {
              "id": 0,
              "name": "General Display",
              "latitude": 0.0,
              "longitude": 0.0
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/04bf9f20-bfb7-4b7c-b702-38c4ec72fca2"
              },
              "images": {
                "href": "http://localhost:8080/api/messages/04bf9f20-bfb7-4b7c-b702-38c4ec72fca2/images"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/0/messages?n=0"
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
          "href": "http://localhost:8080/api/users/1/messages?n=0"
        }
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

- Required fields in the URL parameters:

    | KEY  | TYPE   | DESCRIPTION                |
    | ---- | ------ | -------------------------- |
    | n    | Number | Number of entries you want |

- An example of successful response:

    ```json
    {
      "_embedded": {
        "messageList": [
          {
            "id": "0a901ec0-d11b-46e5-9f87-5a58f6f5f023",
            "message": "Hello, world!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "test user"
            },
            "einkDisplay": {
              "id": 0,
              "name": "General Display",
              "latitude": 0.0,
              "longitude": 0.0
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023"
              },
              "images": {
                "href": "http://localhost:8080/api/messages/0a901ec0-d11b-46e5-9f87-5a58f6f5f023/images"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/0/messages?n=0"
              },
              "messages": {
                "href": "http://localhost:8080/api/messages"
              }
            }
          },
          {
            "id": "04bf9f20-bfb7-4b7c-b702-38c4ec72fca2",
            "message": "Hello, world! Again!",
            "time": "2021-01-01T00:01:01",
            "user": {
              "id": 1,
              "name": "test user"
            },
            "einkDisplay": {
              "id": 0,
              "name": "General Display",
              "latitude": 0.0,
              "longitude": 0.0
            },
            "_links": {
              "self": {
                "href": "http://localhost:8080/api/messages/04bf9f20-bfb7-4b7c-b702-38c4ec72fca2"
              },
              "images": {
                "href": "http://localhost:8080/api/messages/04bf9f20-bfb7-4b7c-b702-38c4ec72fca2/images"
              },
              "messagesFromUser": {
                "href": "http://localhost:8080/api/users/1/messages?n=0"
              },
              "messagesFromDisplay": {
                "href": "http://localhost:8080/api/displays/0/messages?n=0"
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
          "href": "http://localhost:8080/api/displays/0/messages?n=0"
        }
      }
    }
    ```
    
### `GET /api/images/{imageId}`: Get a certain image

- Required fields in the URL parameters:

    | KEY     | TYPE   | DESCRIPTION        |
    | ------- | ------ | ------------------ |
    | imageId | String | UUID for the image |

    **Note:** A list of `imageId`s of a certain message can be obtained from **GET** `/api/messages/{messageId}/images`.

- You can directly shart downloading the image.

### `POST /api/messages/{messageId}/images`: Add an image to a certain message

- Required fields in the URL parameters:

    | KEY       | TYPE   | DESCRIPTION          |
    | --------- | ------ | -------------------- |
    | messageId | String | UUID for the message |

- Use HTTP multipart request to send data:

    | Part Name | Value                |
    | --------- | -------------------- |
    | file      | (image file content) |

- An example of successful response:

    ```json
    {
      "id": "e5b22ad4-5b29-4a86-9986-7ce79898fdc3",
      "name": "IMG_0312.jpg",
      "type": "image/jpeg",
      "_links": {
        "self": {
          "href": "http://localhost:8080/api/images/e5b22ad4-5b29-4a86-9986-7ce79898fdc3"
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
