

---
RestController

### defaultMapping = /user
## 1. endpoint : GET /get/user
---
- Response : 200 OK
```
{
"userId": "user_01", 
"userEmail": "user@email.com", 
"userState": "LOGIN"
}
```
- error - '401 Unauthorized'

## 2. endpoint : GET /get/users
---
- 모든 사용자의 계정 목록을 가져온다.
- Response : 200 OK

```json
[ 
	{ 
		"userId": "user_01", 
		"userEmail": "user@email.com", 
		"userState": "LOGIN" 
	}
		,
   {
      //    
   }
]
```


## 3. endpoint : DELETE /{AccountId}
---
- 사용자 계정을 Id로 삭제.
- Response : 200 OK

``` json
{
	"result" : "OK"
}
```
- error : 400 Bad Request

## 4. endpoint : POST /create
---
- 새로운 사용자 계정을 생성
- Request ->
```json
{ 
	"userId": "new_user", 
	"userPassword": "password123", 
	"userEmail": "new_user@email.com", 
	"userState": "LOGIN" 
}
```

- Response : 201 Created
``` json
{ 
	"userId": "new_user", 
	"userEmail": "new_user@email.com", 
	"userState": "LOGIN" 
}
```

## 5. endpoint : /login
---
- 사용자 로그인
- Request ->
``` json
{ 
	"userId": "user_01", 
	"userPassword": "password123" 
}
```

- Response : 200 OK
```json
{ 
	"userId": "user_01", 
	"userEmail": "user@email.com", 
	"userState": "LOGIN"
}
```

- error : 404 not found

## endpoint : /update
---
- 사용자의 상태 업데이트
- Request ->
```json
{
	"userId" : "user_01",
	"userPassword" : "userPassword",
	"userEmail" : "user@Email.com",
	"userState" : "ACTIVE"
}
```
- Response : 200 OK
```json
{
	"userId" : "user_01",
	"userPassword" : "userPassword",
	"userEmail" : "user@Email.com",
	"userState" : "DISABLED"
}
```

- error : 404 not found