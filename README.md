# MES (제조실행시스템) 프로젝트

## 프로젝트 소개
- MES(제조실행시스템, Manufacturing Execution System) 포트폴리오 프로젝트입니다.
- 자동차 엔진 부품 생산관리 시스템을 주제로 설계했습니다.
- TMS(운송관리시스템) 실무 경험을 바탕으로, 생산 현장의 실적/작업지시 흐름을 반영해 설계했습니다.

## 배포 URL
- https://mes-frontend-zoyo.vercel.app

## 테스트 계정
| 구분 | 아이디 | 비밀번호 |
| --- | --- | --- |
| 관리자 | admin | admin123 |
| 게스트 | guest | guest123 |

## 기술스택
**백엔드**: Spring Boot, JPA, MyBatis, PostgreSQL, JWT, Spring Security

**프론트**: React, AG Grid, Chart.js, axios

## 주요 기능
- 기준정보 관리 (품목, 거래처)
- 작업지시 관리 (등록, 작업시작, 작업마감)
- 생산실적 입력 (바코드 스캔, 수동입력)
- 작업실적현황 및 일마감
- JWT 기반 로그인/권한 관리
- 공통코드 관리
- 사용자 관리

## ERD

`src/main/java/com/mes/mes_project/entity` 하위 JPA Entity를 기준으로 작성했습니다.

```mermaid
erDiagram
    MES_ITEM ||--o{ MES_WORK_ORDER : "item_id"
    MES_WORK_ORDER ||--o{ MES_PROD_RESULT : "work_order_id"
    MES_WORK_ORDER ||--o{ MES_PROD_INCENTIVE : "work_order_id"

    MES_USER {
        bigint id PK
        string username UK "NOT NULL, length 20"
        string password "NOT NULL, 암호화된 비밀번호"
        string name "이름, length 50"
        string role "권한 (ADMIN, USER), default USER"
        string use_yn "사용여부, default Y"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_ITEM {
        bigint id PK
        string item_code UK "제품코드, NOT NULL, length 20"
        string item_name "제품명, NOT NULL, length 100"
        string spec "스펙"
        string unit "단위"
        int unit_price "단가"
        int incentive_rate "인센티브율(%)"
        string use_yn "사용여부, default Y"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_CLIENT {
        bigint id PK
        string client_code UK "거래처코드, NOT NULL, length 20"
        string client_name "거래처명, NOT NULL, length 100"
        string biz_no "사업자번호"
        string client_type "거래처유형(매입/매출)"
        string tel "전화번호"
        string zip_code "우편번호"
        string address "주소"
        string address_detail "상세주소"
        double lat "위도"
        double lng "경도"
        string use_yn "사용여부, default Y"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_COMMON_CODE {
        bigint id PK
        bigint group_id FK "MES_CODE_GROUP.id 참조 (이 ERD에는 미포함)"
        string code "NOT NULL, length 20"
        string code_name "NOT NULL, length 50"
        string description "length 200"
        int sort_order "정렬순서"
        string use_yn "사용여부(1자리), default Y"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_WORK_ORDER {
        bigint id PK
        string work_order_no UK "작업지시번호, NOT NULL, length 30"
        bigint item_id FK "MES_ITEM.id 참조, NOT NULL"
        int plan_qty "계획수량"
        date plan_date "작업일자"
        string status "WAIT / IN_PROGRESS / DONE, default WAIT"
        string line "생산라인, length 50"
        string remark "비고, length 255"
        string use_yn "사용여부, default Y"
        string confirm_yn "확정여부, default N"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_PROD_RESULT {
        bigint id PK
        bigint work_order_id FK "MES_WORK_ORDER.id 참조, NOT NULL"
        string worker "작업자, length 50"
        int scan_qty "바코드 스캔수량, default 0"
        int manual_qty "수동입력수량, default 0"
        int total_qty "합계(scan_qty+manual_qty)"
        date prod_date "생산일자"
        string remark "비고, length 255"
        string use_yn "사용여부, default Y"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_PROD_INCENTIVE {
        bigint id PK
        bigint work_order_id FK "MES_WORK_ORDER.id 참조, NOT NULL"
        string worker "작업자, length 50"
        int qty "수량"
        int unit_price "단가"
        int incentive_rate "계산 당시 인센티브비율, default 0"
        bigint amount "인센티브 금액(qty*unit_price*incentive_rate)"
        string confirm_yn "확정여부, default N"
        date close_date "일마감 기준일자"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_DAILY_CLOSE {
        bigint id PK
        date close_date UK "마감일자, NOT NULL"
        string close_yn "마감여부(1자리), default N"
        string closed_by "마감자, length 50"
        datetime closed_at "마감일시"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }

    MES_MONTH_CLOSE {
        bigint id PK
        string year_month UK "마감년월 예) 2024-06, NOT NULL, length 7"
        string close_yn "마감여부(1자리), default N"
        string closed_by "마감자, length 50"
        datetime closed_at "마감일시"
        string created_by
        string updated_by
        datetime created_at
        datetime updated_at
    }
```

## 관계 설명
- **MES_ITEM (1) : MES_WORK_ORDER (N)** — 작업지시는 하나의 품목을 참조 (`item_id`)
- **MES_WORK_ORDER (1) : MES_PROD_RESULT (N)** — 생산실적은 하나의 작업지시에 귀속 (`work_order_id`)
- **MES_WORK_ORDER (1) : MES_PROD_INCENTIVE (N)** — 인센티브는 하나의 작업지시에 귀속 (`work_order_id`)
- **MES_COMMON_CODE → MES_CODE_GROUP** — `group_id`로 코드그룹을 참조하지만, 요청 범위(9개 테이블)에 포함되지 않아 이 ERD에서는 컬럼만 표기하고 관계선은 생략했습니다.
- **MES_USER, MES_CLIENT, MES_DAILY_CLOSE, MES_MONTH_CLOSE** — 다른 테이블과 직접적인 FK 관계 없이 독립적으로 운영됩니다. (`created_by`/`updated_by`는 사용자 아이디를 문자열로만 기록하며 FK가 아닙니다)
- **MES_PROD_INCENTIVE.close_date** — `MES_DAILY_CLOSE.close_date`와 논리적으로 연결되는 값이지만, DB상 FK 제약으로 걸려있지는 않습니다.

## 화면 스크린샷
(추후 추가)
