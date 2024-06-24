![header](https://capsule-render.vercel.app/api?type=waving&color=1A4FD6&height=300&section=header&text=MatDil&fontColor=ffffff&fontSize=90)

# SpartaNbCamp-Outsourcing Project
[Team Project] <br>
배달 서비스에 필요한 기본 기능 등을 Spring Boot를 기반으로 <br>
- `CRUD(Create, Read, Update, Delete)기능`
- `REST API`
- `회원가입`
- `로그인`
- `JWT`
- `SECURITY`
- `주문`
- `음식점 생성`
- `음식 생성`
- `댓글`
- `좋아요`
등을 구현하여 공부하는 목적의 팀 프로젝트 입니다.

<br>
<br>

### NOTICE
- `구매자` , `판매자` 로 구분하여 서비스를 다르게 활용하고 이용할 수 있습니다.
- `관리자`는 admin을 통하여 서비스를 관리할 수 있습니다. [어드민 레포지토리](https://github.com/JavaOutSourcing/mat-dil_admin)

<br>
<br>
<br>

### Project Task Plan

1주일이라는 짧은 프로젝트 기간동안 4명의 팀원이 어떻게 효율적으로 프로젝트를 진행해야 할지 고민을 하던 중,<br>
모든 task를 나열하여 `우선순위` 를 선정하고, 날짜에 맞게 분배하여 진행하였습니다.

<img width="800" alt="image" src="https://github.com/JavaOutSourcing/mat-dil/assets/132278619/4f3e01c4-beb0-43fc-8317-71a2104840ed">



<br>
<br>
<br>

### Git Commit Convention

깃을 최대한 활용하기 위해 프로젝트를 시작하기 전 `convetion`을 작성하여 프로젝트를 시작하였습니다.

```
[Commit Message]
git commit -m "[Feat] 로그인 기능 구현 #3"
git commit -m "[Fix] UserServiced의 NullPointException 에러 수정 #2"
git commit -m "[Add] JavaConfig / JwtUtil 파일 추가 #2"
git commit -m "[Conflict] dev merge 충돌 해결 #4"

[Branch]
#1/login
#4/GlobalExceptionHandler
```
<br>

1. Git Convention
  - `Feat` : 새로운 기능에 대한 커밋
  - `Fix` : 버그 수정에 대한 커밋
  - `Chore` : 그 외 자잘한 수정에 대한 커밋
  - `Refactor` : 코드 리팩토링에 대한 커밋
  - `Test` : 테스트 코드 수정에 대한 커밋
  - `Add` : 파일 추가에 대한 커밋
  - `Conflict` :충돌 수정에 대한 커밋

<br>

2. Branch 전략
  - `main` : 개발 완료 및 배포를 위한 브랜치
  - `dev` : 개발 테스트를 위한 브랜치
  - `feature` : 개발 진행을 위한 브랜치

<br>

<img width="800" alt="image" src="https://github.com/JavaOutSourcing/mat-dil/assets/132278619/02b005f8-ce62-4fde-a37f-be33d1f019ed">

<br>
<br>
<br>
<br>

### ERD 
<br>

<img width="800" alt="image" src="https://github.com/JavaOutSourcing/mat-dil/assets/132278619/47366dec-aaef-4e10-ad4e-93aa3d27102c">

<br>
<br>
<br>
<br>

### API Documentation
약 40개가 넘는 API를 `POSTMAN`을 활용해 문서화 하였습니다.
[API 문서로 이동하기](https://documenter.getpostman.com/view/34878144/2sA3XWdz15)

<br>

<img width="800" alt="image" src="https://github.com/JavaOutSourcing/mat-dil/assets/132278619/c12b0ed9-a3df-42a8-8a61-31888074dccd">
<img width="800" alt="image" src="https://github.com/JavaOutSourcing/mat-dil/assets/132278619/a3d26cc1-623f-4d0c-9ada-a784ebd6eceb">
