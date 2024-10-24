# Detalks Back

<div>
<img width="200" alt="image" src="https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/2211464e-faf4-429b-ae0f-6bd3d5be5068">
</div>
<br />

> **새싹(Seoul Software Academy, SeSAC) 청년취업사관학교** <br/> **개발기간: 2024.05.31 ~ 2024.06.21** <br/> **팀명: 이쪽저쪽**

<br/>

## 📬 배포 주소

> **배포 버전** : [http://13.124.92.241/](http://13.124.92.241) <br/>
> **API docs** : [http://13.124.92.241:8080/api-docs](http://13.124.92.241:8080/api-docs) <br/>

## 👨‍👩‍👧‍👦 팀 소개

|      김성민       |         이기혁         |       임학민         |      김예지       |      강혜인       |
|---|---|---|---|---|
|![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/9d9d9e4f-007f-418f-b4d5-ccfec40e7fa3) |![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/7a5c61ee-4996-42eb-8c3a-fb90ff5911e1) | ![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/fb75dbc1-480f-4e0d-a9b1-211590fed587) | ![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/d7ba3172-607f-4366-8245-be540e7d60da) | ![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/5479869d-d57a-44bd-b49c-da15911a5195) |
|      Back-end         |        Front-end         |       Front-end            |     Front-end       |      Back-end          | 
|      배포 & Git 관리, Member API, Image Upload ,Oauth2, Deploy, Swagger |        Main page, Mypage, Header, Footer |      Header, Question Page, Question Detail, Question List |     Register, Login, FindPw, Question List |      Q & A API, BookMark & Tag, Pagination, TextEditor, Answer List | 
| <a href="https://github.com/jarajiri"> 🔗GitHub</a> | <a href="https://github.com/leekihyeok">🔗GitHub </a> | <a href="https://github.com/sabb12">🔗GitHub</a>  | <a href="https://github.com/yzlybe">🔗GitHub</a> | <a href="https://github.com/hyein310">🔗GitHub</a> | 

<br />

## ✍️ 프로젝트 소개

국내 개발자들을 위한 코드 공유가 가능하고 개발자들과의 실시간 질답이 가능한 Q & A 커뮤니티
<br />

### 🗓️ 프로젝트 일정
- 프로젝트 기획 및 피그마/기능 정리 : 24.05.31 - 24.06.03
- 개발 : 24.06.04 - 24.06.13 
- 배포 및 문제 수정 : 24.06.14 - 24.06.19
- 발표 준비 및 테스트 : 24.06.20 - 24.06.21

<br />

## 🧾 ERD
![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/ca27417e-7329-4114-960e-8d4ad4a46b8c)

<br/>

## 🎈 Figma
https://www.figma.com/design/4xB64yTeT8LmHpCDvh1WyD/%EC%9D%B4%EC%AA%BD%EC%A0%80%EC%AA%BD?node-id=0-1&t=q7nFwwg2E1oTayCQ-0

<br/>


---
## ⭐ 주요 기능 
### 1. 메인
![메인 페이지](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/f523e75c-0dae-46bd-addd-37166756fa47)
##### - SCSS Transform, Transition을 통해 지속적 움직임
##### - Mouse 올리고 내리는 것을 통해 현재 Div 높이 계산 후 슬라이드 작업
<br/>

### 2. 헤더
![검색](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/4665b0d2-671c-40d1-b876-edb436d251e2)
##### - 닉네임 6자 이상 시, 글자 수 제한 함수 이용 ... 으로 표시
##### - 검색 창 Focus시 검색 예시 표시, 내용 클릭시 "[sort]:" 형식이 Input 창에 자동 완성
<br/>


### 3. 로그인 & 회원가입 & 비밀번호 찾기
##### - 컴포넌트 활용해서 페이지마다 다른 내용을 props를 통해 type으로 구분해서 사용
##### - onChange와 useState 사용해서 유저 입력에 따라 상태 즉각적 피드백
##### - 각 Input값 별로 체크하여 유효하지 않은 값이 존재할 경우, 다음 단계로 넘어갈 수 없도록 차단
##### - 유저가 입력한 이메일로 인증 코드를 전송하고 실제 유저임을 인증
<br/>


### 4. 마이페이지
![마이페이지 - 본인](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/af82a6e9-2956-4fa6-a1a7-6eb832efb176)  
![마이페이지 - 본인 (1)](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/833ce1b0-37f1-4ac2-8a81-ec6be23235e0)

##### - 질문 글의 태그에 따라서 가장 많은 횟수의 태그 3개 마이페이지 반영(색을 5개 선정 후 랜덤으로 매치)
##### - 닉네임, 태그, 나의 능력치, 자기소개를 다른 사람도 볼 수 있도록 함
##### - 내 활동 글에서 switch문을 통해 filter 함수 작성하여 질문, 답변 분류
##### - sort 이용해서 최신순, 평점순, 채택없는 글 정렬 가능
##### - 로그인한 본인의 마이페이지일 경우에만, 정보 수정 컴포넌트 보이게 하고 수행 가능하도록 함 
#####  (북마크 리스트, 프로필 수정, 닉네임 변경, 자기소개 변경, 비밀번호 변경, 탈퇴 등)
<br/>

### 5. 질문 
![질문 리스트 필터링](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/c4c7f902-4a7b-4d71-aac2-25377accd051)
#### 🏷️ 질문 리스트
##### - 질문 글의 상태에 따라 다양하게 반영 
#####  (채택됐을 경우 메달, 현상금이 걸린 질문일 경우, 파란 테두리, 투표의 +/-에 따라서 색 변동, 채택 질문일 경우 체크 표시 등)
##### - 페이지네이션과 정렬(최신순, 투표순, 답변없는 질문) 가능
<br/>

#### 🏷️ 질문 작성
##### - 질문의 제목, 내용, 태그 모두 입력 여부 확인
##### - </> 클릭 시 코드 블록 및 글자 색상 생성
##### - 태그 입력 후, Enter 누르면 태그 생성 가능(삭제 가능)
##### - 본인의 평판점수를 사용해서 질문에 현상금 걸기 가능 (질문이 잘보이도록 효과)
<br/>

#### 🏷️ 질문 상세
##### - 질문의 작성자만 질문 수정 / 삭제 가능, 본인 질문에 투표 및 답변 불가능
##### - 투표, 북마크 가능, 채택된 질문일 경우, 체크 표시
##### - 현상금 걸린 질문에 채택됐을 경우, 질문 현상금이 채택된 답변 작성자에게 이전
<br/>

### 6. 평판
![채택](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/6782f048-7dfe-40f3-b5d0-6dc6e6cda6be)

##### -  질문 / 답변 작성시 + 5
##### -  답변 채택시 + 20
##### -  질문 / 답변 투표 UP + 10
##### -  질문 / 투표 DOWN - 5
##### -  평판은 1이하로 떨어지지 않음
<br />


---
## 📒 시작 가이드
### .env
> DB_USERNAME=로컬DB계정 </br>
> DB_PASSWORD=로컬DB비밀번호 </br>
> DB_DATABASE=detalks </br>
> PORT=8080 </br>

<br />

### Installation

##### Backend
```
$ git clone https://github.com/sesac-this-way-and-that/detalks-back.git
$ cd detalks-back
$ npm install
$ npm run dev
```

##### Frontend
```
$ git clone https://github.com/sesac-this-way-and-that/detalks-front.git
$ cd detalks-front
$ npm install 
$ npm run start
```

---

## 🤖 사용 스택 Stacks 

### - Environment
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)             

<br/>

### - Config
![npm](https://img.shields.io/badge/npm-CB3837?style=for-the-badge&logo=npm&logoColor=white)        

<br/>

### - Development
- Frameworks, Platforms and Libraries
 
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/spring%20security-%236DB33F.svg?style=for-the-badge&logo=spring%20security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Redux](https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=Redux&logoColor=white)
![env](https://img.shields.io/badge/dotenv-ECD53F?style=for-the-badge&logo=dotenv&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Sass](https://img.shields.io/badge/Sass-CC6699?style=for-the-badge&logo=Sass&logoColor=white)

- Languages

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=Typescript&logoColor=white)

- Hosting

![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![EC2](https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)
![RDS](https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white)

- Design

![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Adobe Photoshop](https://img.shields.io/badge/adobe%20photoshop-%2331A8FF.svg?style=for-the-badge&logo=adobe%20photoshop&logoColor=white)

- Database

![Mysql](https://img.shields.io/badge/Mysql-4479A1?style=for-the-badge&logo=Mysql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)

<br/>

### - Communication
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white)

<br/>

---
## 📺 화면 구성 
#### - 메인 페이지  
![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/ee3961ac-c6ce-4c84-b783-3f18aaaf13c1)
<br />
  
#### - 로그인 & 회원가입 페이지
![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/d99583ec-3061-4c0e-af52-5b26914ae44d)
<br />


#### - 질문 리스트 페이지
![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/d6661039-2fc6-4b09-8862-9892fb2d0bdb)
<br />


#### - 질문 작성 페이지 
![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/afcfca7b-3605-4506-8720-d335d8b7a2c1)
<br />

#### - 마이페이지
![image](https://github.com/sesac-this-way-and-that/detalks-back/assets/133750746/c24d5da1-efbf-4e18-9ebe-532744d1ccb3)
<br />



