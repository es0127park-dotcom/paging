# Paging Study Lab

노션 `기술 블로그 > 기술 샘플링 with AI > 페이징 (Paging)` 페이지의 예제 `(0)`부터 `(6)`까지를
하나의 실행 가능한 Spring Boot 프로젝트와 단계별 Git 브랜치로 재구성한 저장소다.

`main` 브랜치는 최종 완성본이고, `step-0`부터 `step-6`까지는 학습 순서를 따라가며
기능이 누적되는 형태로 관리한다.

## 목표

- 순수 Java로 페이징 공식을 먼저 이해한다.
- JdbcTemplate으로 `LIMIT / OFFSET` 기반 페이징을 직접 구현한다.
- JPA `Pageable`, `Page`로 프레임워크 방식의 페이징을 익힌다.
- 검색 조건 유지, 페이지 블록 계산, 현재 페이지 강조를 붙인다.
- 커서 페이징을 REST API와 간단한 화면으로 확인한다.
- 마지막으로 초보가 자주 틀리는 포인트를 코드와 가이드에 반영한다.

## 브랜치 구성

| 브랜치 | 커밋 | 의미 | 핵심 내용 |
|---|---|---|---|
| `step-0` | `cd6015b` | 프로젝트 시작점 | Spring Boot 뼈대, 홈 화면, 가이드 화면 |
| `step-1` | `45d6dc7` | 순수 Java 페이징 | `PagingInfo`, `startIndex`, `endIndex`, `totalPages` 계산 |
| `step-2` | `59d8019` | JdbcTemplate 페이징 | H2 + `LIMIT / OFFSET` + Mustache 목록 |
| `step-3` | `a640f5c` | JPA 페이징 | `Pageable`, `Page`, `page - 1` 처리 |
| `step-4` | `4a32494` | 검색 + 페이지 블록 | 검색 조건 유지, 이전/다음, 3개 블록, 현재 페이지 강조 |
| `step-5` | `2ec05a1` | 커서 페이징 | `cursor` 기반 REST API, 더보기 화면 |
| `step-6` | `419b0cf` | 마무리 | README/가이드 보강, 커서 서비스 테스트 |
| `main` | `419b0cf` | 최종본 | 예제 `(0) ~ (6)` 전체 포함 |

## 브랜치 이동 예시

```bash
git switch step-1
git switch step-4
git switch main
```

추천 확인 순서는 `step-1 -> step-2 -> step-3 -> step-4 -> step-5 -> main`이다.

## 실행 방법

### 1. 애플리케이션 실행

```bash
./gradlew bootRun
```

실행 후 브라우저에서 아래 주소로 접속한다.

- 홈: `http://localhost:8080`
- H2 콘솔: `http://localhost:8080/h2-console`

### 2. 테스트 실행

```bash
./gradlew test
```

## 단계별 진입점

| 경로 | 대응 예제 | 설명 |
|---|---|---|
| `/` | `(0)` | 전체 학습 흐름과 각 단계 진입 링크 |
| `/steps/pure-java` | `(1)` | 순수 Java 페이징 공식과 결과 확인 |
| `/jdbc/boards?page=1` | `(2)` | JdbcTemplate + Mustache 목록 페이징 |
| `/jpa/boards?page=1` | `(3)` | JPA `Pageable` 기반 목록 |
| `/jpa/boards/search?keyword=샘플&page=1` | `(4)` | 검색 조건 유지 + 3개 블록 계산 |
| `/cursor` | `(5)` | 커서 페이징 데모 화면 |
| `/api/cursor/boards?cursor=86&size=10` | `(5)` | 커서 페이징 API |
| `/guide` | `(6)` | 학습 마무리와 실수 방지 포인트 |

## 현재 `main`에 들어있는 내용

### 1. 순수 Java 페이징

- `PagingInfo`로 `offset`, `limit`, `startPage`, `endPage`, `hasPrevious`, `hasNext`를 계산한다.
- 전체 데이터가 0개거나 요청 페이지가 범위를 벗어나도 안전하게 보정한다.

관련 파일:

- `src/main/java/com/example/paging/dto/PagingInfo.java`
- `src/main/java/com/example/paging/service/PureJavaPagingService.java`
- `src/main/java/com/example/paging/controller/PureJavaPagingController.java`

### 2. JdbcTemplate 페이징

- H2 메모리 DB에 `board` 테이블을 만들고 샘플 게시글 95개를 주입한다.
- `ORDER BY id DESC LIMIT ? OFFSET ?`로 실제 DB 페이징을 수행한다.
- Mustache 화면에서 이전/다음 및 페이지 번호를 출력한다.

관련 파일:

- `src/main/resources/schema.sql`
- `src/main/java/com/example/paging/init/SampleBoardInitializer.java`
- `src/main/java/com/example/paging/repository/JdbcBoardRepository.java`
- `src/main/java/com/example/paging/service/JdbcBoardService.java`
- `src/main/resources/templates/jdbc/boards.mustache`

### 3. JPA 페이징

- `JpaRepository`와 `PageRequest`를 사용한다.
- 화면 파라미터는 1페이지부터 시작하지만 JPA는 0페이지부터 시작하므로 `page - 1` 처리한다.

관련 파일:

- `src/main/java/com/example/paging/repository/BoardJpaRepository.java`
- `src/main/java/com/example/paging/service/JpaBoardService.java`
- `src/main/java/com/example/paging/controller/JpaBoardController.java`

### 4. 검색 조건 유지와 페이지 블록 계산

- `findByTitleContaining`으로 검색 + 페이징을 수행한다.
- 페이지 이동 링크에 `keyword`를 계속 붙여 검색 상태가 유지되도록 한다.
- `1 2 3`, `4 5 6` 같은 3개 블록 노출 방식을 적용한다.
- 현재 페이지는 강조하고, 잘못된 페이지 요청은 마지막 페이지로 보정한다.

관련 파일:

- `src/main/java/com/example/paging/dto/PageNumber.java`
- `src/main/java/com/example/paging/controller/JpaBoardController.java`
- `src/main/resources/templates/jpa/boards.mustache`

### 5. 커서 페이징

- 첫 페이지는 최신 글부터 `size + 1`개를 조회한다.
- 다음 페이지는 마지막으로 본 `id`보다 작은 데이터만 가져온다.
- `size + 1`개 조회 후 한 개를 잘라 `hasNext`를 판단한다.
- REST API와 간단한 "더보기" 화면 둘 다 제공한다.

관련 파일:

- `src/main/java/com/example/paging/repository/BoardCursorRepository.java`
- `src/main/java/com/example/paging/service/CursorBoardService.java`
- `src/main/java/com/example/paging/controller/CursorPagingController.java`
- `src/main/resources/templates/cursor/index.mustache`

## 프로젝트 구조

```text
src/main/java/com/example/paging
├─ controller
│  ├─ HomeController.java
│  ├─ PureJavaPagingController.java
│  ├─ JdbcBoardController.java
│  ├─ JpaBoardController.java
│  └─ CursorPagingController.java
├─ domain
│  └─ Board.java
├─ dto
│  ├─ PagingInfo.java
│  ├─ BoardPageResponse.java
│  ├─ PageNumber.java
│  ├─ PureJavaPagingView.java
│  └─ CursorPageResponse.java
├─ init
│  └─ SampleBoardInitializer.java
├─ repository
│  ├─ JdbcBoardRepository.java
│  ├─ BoardJpaRepository.java
│  └─ BoardCursorRepository.java
└─ service
   ├─ PureJavaPagingService.java
   ├─ JdbcBoardService.java
   ├─ JpaBoardService.java
   └─ CursorBoardService.java
```

## 학습 포인트 정리

### 반드시 기억할 것

1. JPA는 0페이지부터 시작한다.
2. 페이징에는 반드시 정렬 기준이 있어야 한다.
3. 검색 페이징은 링크에도 검색 조건을 유지해야 한다.
4. 마지막 페이지와 범위를 벗어난 페이지는 보정해야 한다.
5. `size`는 상한을 두어야 한다.

### OFFSET 방식과 커서 방식 차이

| 구분 | OFFSET 페이징 | 커서 페이징 |
|---|---|---|
| 기준 | `page`, `offset` | 마지막으로 본 데이터 |
| 장점 | 번호형 페이지 UI 구현이 쉽다 | 대용량에서 뒤로 갈수록 덜 느리다 |
| 단점 | 큰 `offset`에서 비효율적일 수 있다 | `10페이지로 점프` 같은 UI에 불리하다 |
| 적합한 화면 | 게시판, 관리자 목록 | 무한 스크롤, 더보기, 피드 |

## 검증 상태

- `./gradlew test` 통과
- `main` 브랜치 기준 저장소 정리 완료
- 단계별 브랜치 생성 완료: `step-0` ~ `step-6`

## 메모

- 샘플 데이터는 애플리케이션 시작 시 H2 메모리 DB에 자동 적재된다.
- 최종 구현은 "한 프로젝트 + 단계별 브랜치" 전략을 사용했다.
- 이유는 프로젝트를 여러 개로 쪼개는 방식보다 Git으로 차이를 추적하고 학습하기 쉽기 때문이다.
