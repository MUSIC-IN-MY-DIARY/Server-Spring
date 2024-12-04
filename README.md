# Music In My Diary - Server 🎵
**Music In My Diary**는 사용자의 일기를 기반으로 AI가 음악을 추천하고 가사를 작성해주는 서비스입니다. <br/>
_Cold Start 상태에서도 사용자 맞춤형 노래 추천을 할 순 없을까? A 가수가 커피에 대해 노래하면 어떨까?_ 하는 호기심에서 시작하였습니다. <br/>
**GPT turbo-3.5** 모델을 사용하여 초기 사용자도 맞춤형 서비스를 제공받을 수 있는 노래 애플리케이션입니다.

<br/>

# Stacks 🐱
**Development**
![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Springboot-6DB33F?style=flat&logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)

**Environment**
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)

<!-- 
## Infrastructure & DevOps
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat&logo=githubactions&logoColor=white)
-->

**Communication**
![Notion](https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white)
![Google Meet](https://img.shields.io/badge/Google%20Meet-00897B?style=flat&logo=googlemeet&logoColor=white)

<br/>

# 화면 구성 📺

| 주요 기능            | 주요 기능            |
|---------------------|---------------------|
| **로그인 & 로그아웃** | **노래 추천**        |
| ![로그인](https://github.com/user-attachments/assets/290acb13-773e-4633-a016-62e56af30dc8) | ![노래 추천](https://github.com/user-attachments/assets/8c1872ed-eea0-42cb-9cf8-4f417fbafa5a) |
| 사용자는 이메일과 비밀번호를 통해 로그인 및 회원가입이 가능합니다. | 사용자가 입력한 일기를 기반으로 AI가 3개 이하의 노래를 추천합니다.|
| **가사 생성**        | **북마크 조회**      |
| ![가사 생성](https://github.com/user-attachments/assets/95b185e7-f3bd-4188-a1aa-98a1e799b6a8) | ![북마크 조회](https://github.com/user-attachments/assets/adfa5377-ea2d-492e-92e1-d30ac4e1da40) |
| 사용자가 제공한 일기를 기반으로 AI가 가사를 생성합니다. | 저장한 노래와 가사를 북마크로 등록, 삭제, 재저장, 상세보기 기능을 제공합니다. |

<br/>

# 주요 기능 💅🏻 
### 🔓 로그인 기능 
- 이메일과 비밀번호로 로그인

### 🎶 AI 음악 추천 기능
- 사용자의 일기 내용을 분석하여 감정과 상황에 맞는 음악을 추천
- 추천된 음악에 대한 상세 정보 제공 (곡명, 아티스트, 장르 등)


### 📝 AI 가사 생성 기능
- 일기 내용을 바탕으로 사용자의 감정을 담은 가사 생성

### 🔖 북마크 기능
- AI가 추천한 음악과 생성한 가사를 일기와 함께 북마크로 등록, 제거 가능
- 저장된 북마크 목록은 3개씩 조회 
  - 노래 추천 북마크
    - 미리보기: 사용자가 작성한 일기내용과 추천 곡들의 노래명을 조회
    - 상세보기: 사용자 정보와 추천 받은 노래 정보를 모두 조회
  - 가사 생성 북마크
    - 미리보기: 사용자가 작성한 일기내용과 생성한 가사 내용 일부를 조회
    - 상세보기: 사용자 정보와 생성한 가사의 모든 정보를 조회

## 자세히 보기 🔗
- **유저 플로우**: [Day14 - 프로젝트 기반 음악 추천/작사 애플리케이션 개발 회고](https://velog.io/@ctndl/Day14.-%ED%94%84%EB%A1%AC%ED%94%84%ED%8A%B8-%EA%B8%B0%EB%B0%98-%EC%9D%8C%EC%95%85-%EC%B6%94%EC%B2%9C%EC%9E%91%EC%82%AC-%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98-%EA%B0%9C%EB%B0%9C-%ED%9A%8C%EA%B3%A0)  
- **기술적 구현**: [링크](#) <!-- 여기에 기술적 내용을 연결할 링크 추가 -->

