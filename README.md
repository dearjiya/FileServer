# FileServer
## 환경
* java version: 1.8.0

## 로컬 환경 설정
1. clientconfig.properties를 설정한다. 연결할 서버의 주소와 포트번호를 적는다.
2. ${SERVER_NAME}_config.properties를 설정한다. 서버가 listen 하게될 port, 원격서버의 IP/port, 파일 저장소 경로, 암호화 여부를 적는다.

## Server 기동 방법
* 첫 번째 파일 서버 기동
	* CMD> FileServer [SERVER_NAME]. ex) FileServer server1
		* 이 때 server1_config.properties 파일이 반드시 존재하여야 한다.
* 두 번째 파일 서버 기동
	* CMD> FileServer [SERVER_NAME]. ex) FileServer server2
		* 이 때 server2_config.properties 파일이 반드시 존재하여야 한다.
* 클라이언트 실행
	* CMD> FileServerClient
		* 마찬가지로 clientconfig.properties 파일이 반드시 존재하여야 한다.

## 클라이언트 명령어
* CONNECT
	* config 파일에 기재된 파라미터 RemoteIpPort에 접속하도록 서버에 요청
* LIST
	* config 파일에 기재된 파라미터 FileRepository 경로에 존재하는 파일의 리스트를 출력
* PUSH [file_name]
	* remote server에 파일 전송
* PULL [file_name]
	* remote server에 있는 파일 전송을 요청
* STOP
	* 서버 종료를 요청
