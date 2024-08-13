## API 명세서

### schedule 삭제
- request
  - method: delete
  - uri: /api/schedule/{scheduleId}
  - parameters: (path) scheduleId
  - request body:
    ```
    {
      "password": "0000"
    }
    ```
- response
  - status code: 200
  - response body:
    ```
    "ok"
    ```
- 비밀번호를 틀렸거나 존재하지 않는 일정 Id를 입력했을 경우
  - status code: 400 
  - response body:
    ```
    {
      "code": "INVALID_PARAMETER",
      "message": "Invalid parameter included"
    }
    ```
### schedule 단건 조회
- request
  - method: get
  - uri: /api/schedule/{schedulId}
  - parameters: (path) scheduleId
- response
  - status code: 200
  - response body:
    ```
    {
      "id": 0,
      "content": "string",
      "createAt": "2024-08-13T11:36:19",
      "updateAt": "2024-08-13T11:36:19",
      "manager": {
        "id": 0,
        "name": "string",
        "email": "string",
        "createAt": "2024-08-13T04:31:35",
        "updateAt": "2024-08-13T04:31:35"
      }
    }
    ```
- 존재하지 않는 일정 Id를 조회했을 경우
  - status code: 400 
  - response body:
    ```
    {
      "code": "INVALID_PARAMETER",
      "message": "Invalid parameter included"
    }
    ```
### schedule 목록 조회
- request
  - method: get
  - uri: /api/schedule
  - parameters:
    - (query) updateDate: 조회할 일정의 수정일 `ex) 2024-08-13`
    - (query) managerId: 조회할 일정의 담당자 ID
    - (query) pageNum: 페이지 번호 (required)
    - (query) pageSize: 페이지 크기 (required)
- response
  - status code: 200
  - response body:
    ```
    [
      {
        "id": 0,
        "content": "string",
        "createAt": "2024-08-13T04:35:12.849Z",
        "updateAt": "2024-08-13T04:35:12.849Z",
        "manager": {
          "id": 0,
          "name": "string",
          "email": "string",
          "createAt": "2024-08-13T04:35:12.849Z",
          "updateAt": "2024-08-13T04:35:12.849Z"
        }
      },
      ...
    ]
    ```
### schedule 등록
- request
  - method: post
  - uri: /api/schedule
  - request body:
    ```
    {
      "content": "string",
      "name": "string",
      "email": "aaa@bbb.ccc",
      "password": "string"
    }
    ```
- response
  - status code: 200
  - response body:
    ```
    {
      "id": 0,
      "content": "string",
      "createAt": "2024-08-13T04:45:08",
      "updateAt": "2024-08-13T04:45:08",
      "manager": {
        "id": 0,
        "name": "string",
        "email": "string",
        "createAt": "2024-08-13T04:45:08",
        "updateAt": "2024-08-13T04:45:08"
      }
    }
    ```
### schedule 수정
- request
  - method: put
  - uri: /api/schedule/{scheduleId}
  - parameters: (path) scheduleId
  - request body:
    ```
    {
      "content": "string",
      "name": "string",
      "password": "string"
    }
    ```
- response
  - status code: 200
  - response body:
    ```
    {
      "id": 0,
      "content": "string",
      "createAt": "2024-08-13T04:45:08",
      "updateAt": "2024-08-13T04:45:08",
      "manager": {
        "id": 0,
        "name": "string",
        "email": "string",
        "createAt": "2024-08-13T04:45:08",
        "updateAt": "2024-08-13T04:45:08"
      }
    }
    ```
    
### ERD
![image](https://github.com/user-attachments/assets/34215918-eeac-4fce-9ea5-3ffe696e585b)

 ### schedule table
 
|column name|type|description|기본값|null 허용|
|---|---|---|---|---|
|id|BIGINT|일정 ID (PK)|auto_increment|X|
|content|VARCHAR(200)|일정 내용|X|X|
|manager_id|BIGINT|담당자 ID (FK)|X|X|
|password|VARCHAR(10)|비밀번호|X|X|
|create_at|DATETIME|일정 등록일|CURRENT_TIMESTAMP|X|
|update_at|DATETIME|일정 수정일|CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP|X|

### manager table

|column name|type|description|기본값|null 허용|
|---|---|---|---|---|
|id|BIGINT|담당자 ID (PK)|auto_increment|X|
|name|VARCHAR(200)|담당자 이름|X|X|
|email|VARCHAR(200)|담당자 email|X|O|
|create_at|DATETIME|담당자 등록일|CURRENT_TIMESTAMP|X|
|update_at|DATETIME|담당자 수정일|CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP|X|
