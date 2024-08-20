
 앱을 사용하는 유저들이 자신만의 경험한 여행경로나 산책경로들을 사진과 텍스트로 기록하고 서로 공유하고 소통하는 목적으로 프로젝트를 구상했다.

# 1. Project OdysseyLog

- AWS S3 Storage를 이용하기 위해 Presigned URL을 사용하여 이미지 GET, POST를 구현.
- AWS Lambda를 이용하여 이미지 리사이징을 구현. 
- App에서 사용하는 API를 Spring Boot로 구현.

# 2. Development Environment

- Language: Kotlin, Dart
- Framework: Spring Boot, Flutter(https://github.com/gominnam/OdysseyLogApp)
- Database: MySQL
- AWS S3 Storage, Lambda(python 3.10)

# 3. Structure

- Archetecture </br>
   <img src="src/main/resources/static/architecture.png" alt="structure" width="600"/></br>


# 4. Presigned URL

- Why Presigend URL ?</br></br>

    - 보안: 클라이언트가 직접 AWS S3에 접근하는 것이 아니라 서버를 통해 제한된 시간 동안</br>
           유효한 URL을 제공받아 접근하므로 보안이 강화된다.</br></br>
  
    - 성능: 서버에서 이미지를 업로드하고 다운로드하는 것이 아니라 클라이언트가 직접</br>
           S3에 접근하므로 서버의 부하를 줄일 수 있다.</br></br>

    - 비용: 서버에서 데이터 전송 비용이 줄어들어 운영 비용을 절감할 수 있다.</br></br>

- Presigned URL GET, POST 흐름도</br></br>

     - POST</br>
       <img src="src/main/resources/static/presigned_url_post.png" alt="structure" width="600"/></br></br>

     - GET</br>
       <img src="src/main/resources/static/presigned_url_get.png" alt="structure" width="600"/></br></br>

       