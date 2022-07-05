# JPA는 다양한 쿼리 방법을 지원

- JPQL
- JPA Criteria
- __Query Dsl__
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

## JPQL

- JPA를 사용하면 엔티티 객체를 중심으로 개발
-  문제는 검색 쿼리
- 검색을 할 때도 __테이블이 아닌 엔티티 객체를 대상으로 검색__
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
- 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요

- JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
- SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN(안심 표준이라 한다.) 지원 
- JPQL은 엔티티 객체를 대상으로 쿼리
- SQL은 데이터베이스 테이블을 대상으로 쿼리

## 단점
- 동적 쿼리를 만들기 어렵다
    - Stirng 타입으로 쿼리를 작성하기 때문에 특정 조건을 만족할 때 다른 값이 들어간다거나, 이러한 조건과 자주 바뀔 수 있는 동적인 값을 넣기가 어렵다. 
    - ex)
```java
String jpql = "select m from Member m where m.age > 18";

List<Member> result = em.createQuery(jpql, Member.class)
 .getResultList();
```

## Criteria

_Query Dsl_ 의 열화판 느낌이다.

```java
//Criteria 사용 준비
CriteriaBuilder cb = em.getCriteriaBuilder(); 
CriteriaQuery<Member> query = cb.createQuery(Member.class); 
//루트 클래스 (조회를 시작할 클래스)
Root<Member> m = query.from(Member.class); 
//쿼리 생성 CriteriaQuery<Member> cq = 
query.select(m).where(cb.equal(m.get("username"), “kim”)); 
List<Member> resultList = em.createQuery(cq).getResultList();
```
### 장점 
- 쿼리를 코드로 작성하는 것이기에 동적 쿼리에 유리하다.
    - 컴파일 단계에서 오타와 같은 에러를 잡아낼 수 있다.

### 단점
- __너무 복잡하고 실용성이 없다.__
- Criteria 대신 더 좋고 더 효용성이 높은 __QueryDsl__ 을 사용하도록 하자.

## QueryDsl 소개
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있다.
- JPQL 빌더 역할
- 컴파일 시점에 문법 오류를 찾을 수 있습니다.
- 동적쿼리 작성이 단순하고 편리하다.

## 네이티브 SQL 소개

- JPA가 제공하는 SQL을 직접 사용하는 기능
- JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
- 예) 오라클 CONNECT BY, 특정 DB만 사용하는 SQL 힌트

```java
String sql = 
 “SELECT ID, AGE, TEAM_ID, NAME FROM MEMBER WHERE NAME = ‘kim’"; 
List<Member> resultList = 
 em.createNativeQuery(sql, Member.class).getResultList(); 
```

## JDBC 직접 사용, SpringJdbcTemplate 등
- JPA를 사용하면서 JDBC 커넥션을 직접 사용하거나, 스프링 JdbcTemplate, 마이바티스 등을 함께 사용 가능
- 단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
- 예) JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트 수동 플러시
