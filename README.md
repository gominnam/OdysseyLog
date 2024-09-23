# OdysseyLog

# 1. 프로젝트 목적 
이 프로젝트는 여행 일지를 기록하고 공유하는 App(Flutter)과 App Server를 구현한 프로젝트입니다.</br>
**[Flutter 레포지토리 GitHub Link](https://github.com/gominnam/OdysseyLogApp)**</br>
App Server의 주요 기능은 다음과 같습니다

- **Presigned URL**: App에서 이미지 업로드를 위한 Presigned URL을 제공.
- **Image Upload**: App에서 업로드한 이미지를 AWS S3 Storage에 저장.
- **Image Resize**: AWS Lambda를 이용하여 이미지 리사이징.
- **App Server**: App에서 사용하는 API를 Spring Boot로 구현.
</br></br>


# 2. 개발 환경 

- Language: Kotlin, Dart
- Framework: Spring Boot, Flutter
- Database: MySQL
- AWS S3 Storage, Lambda(python 3.10)
</br></br>


# 3. 구조

- 설계 이미지 </br>
   <img src="src/main/resources/static/architecture.png" alt="structure" width="600"/></br>

- Moblie로부터 GET, POST 요청을 처리
- AWS S3 Storage에 이미지가 업로드
  - 이미지 업로드 완료가 Event Trigger로 AWS Lambda 호출
    - AWS Lambda를 통해 이미지 리사이징 
      - 리사이징이 완료되면 Spring 서버로 notification api를 호출하여 isCompressed 컬럼 상태를 업데이트

</br></br>


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

</br></br>


# 5. Reference

- [AWS S3 Presigned URL](https://docs.aws.amazon.com/AmazonS3/latest/userguide/PresignedUrlUploadObject.html)
- [AWS LABMDA](https://docs.aws.amazon.com/lambda/latest/dg/welcome.html)