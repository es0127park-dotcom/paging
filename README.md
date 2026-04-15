# paging

Notion `기술 블로그 > 기술 샘플링 with AI > 페이징 (Paging)` 페이지의 예제 `(0)`부터 `(6)`까지를
실행 가능한 Spring Boot 프로젝트로 재구성한 저장소다.

## 브랜치 가이드

- `step-0`: 학습 순서와 프로젝트 뼈대
- `step-1`: 순수 Java 페이징 계산
- `step-2`: Spring MVC + Mustache + JdbcTemplate 페이징
- `step-3`: Spring MVC + Mustache + JPA 페이징
- `step-4`: 검색 조건 유지 + 3개 블록 계산
- `step-5`: 커서 페이징 REST API
- `step-6`: 마무리 정리와 방어 코드
- `main`: 최종 완성본

## 실행

```bash
./gradlew bootRun
```

실행 후 [http://localhost:8080](http://localhost:8080)에서 단계별 예제를 볼 수 있다.
