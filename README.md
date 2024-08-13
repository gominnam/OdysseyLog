
 앱을 사용하는 유저들이 자신만의 경험한 여행경로나 산책경로들을 사진과 텍스트로 기록하고 서로 공유하고 소통하는 목적으로 프로젝트를 구상했다.

# 1. Project OdysseyLog

- AWS S3 Storage를 이용하기 위해 Presigned URL을 사용하여 이미지 GET, POST를 구현.
- `Odyssey`는  트로이 전쟁 영웅 오디세우스(Odysseus)의 10년간에 걸친 귀향 모험담을 말합니다.</br>
  사용자가 자신만의 여행 경로나 산책 경로를 사진과 텍스트로 기록하고, 이를 다른 사용자와 공유하며 소통할 수 있는 플랫폼을 만들고자 하였습니다.


# 2. Development Environment

- Language: Kotlin, Dart
- Framework: Spring Boot, Flutter(https://github.com/gominnam/OdysseyLogApp)
- Database: MySQL
- AWS S3 Storage

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


