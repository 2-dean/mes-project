-- =============================================
-- DELETE (FK 순서 주의: 자식 → 부모)
-- =============================================
DELETE FROM MES_PROD_INCENTIVE;
DELETE FROM MES_DAILY_CLOSE;
DELETE FROM MES_MONTH_CLOSE;
DELETE FROM MES_PROD_RESULT;
DELETE FROM MES_WORK_ORDER;
DELETE FROM MES_CLIENT;
DELETE FROM MES_ITEM;
DELETE FROM MES_USER;
DELETE FROM MES_COMMON_CODE;

-- =============================================
-- MES_USER (5명)
-- =============================================
INSERT INTO MES_USER (username, password, name, role, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ('admin',    '$2y$10$Ym5J9ujBExYr6SYCzDvNmusXfvMBquH7fO8b/SfvIRJLEXrYgzP3i', '관리자',  'ADMIN', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('manager1', '$2y$10$XB.DlEu.nsROob99PipDYOIilHcQBBHXsH3gk7bQRDFJXMgJ26xLu', '김현장',  'ADMIN', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('user1',    '$2y$10$NIVqYCun7ke0rdyEj5ryG.Ar1u1wTDwFPsHctgu9XVprjHWw9WccO', '홍길동',  'USER',  'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('user2',    '$2y$10$NIVqYCun7ke0rdyEj5ryG.Ar1u1wTDwFPsHctgu9XVprjHWw9WccO', '김철수',  'USER',  'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('user3',    '$2y$10$NIVqYCun7ke0rdyEj5ryG.Ar1u1wTDwFPsHctgu9XVprjHWw9WccO', '이영희',  'USER',  'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('guest',    '$2y$10$DoLO0TvVVBRbDa8860PIg.6lXPjLIH4FxKFRsCw8fdi5NzogtbwCy', '게스트',  'USER',  'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_ITEM (3개)
-- =============================================
INSERT INTO MES_ITEM (item_code, item_name, spec, unit, unit_price, incentive_rate, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ('ITEM-001', '피스톤',      'φ86mm / AL합금',     'EA', 15000, 5, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('ITEM-002', '크랭크샤프트', 'L=450mm / SCM435',   'EA', 85000, 3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('ITEM-003', '실린더헤드',   '4기통 / FC250',      'EA', 45000, 4, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_CLIENT (3개)
-- =============================================
INSERT INTO MES_CLIENT (client_code, client_name, biz_no, client_type, tel, zip_code, address, address_detail, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ('CLI-001', '현대자동차(주)',  '264-81-00201', '매출', '02-3464-1114', '06797', '서울특별시 서초구 헌릉로 12',     '현대자동차 본사', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CLI-002', '기아(주)',       '122-81-01490', '매출', '02-3464-1000', '06797', '서울특별시 서초구 양재동 231',    '기아 본사',       'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CLI-003', 'GM코리아(주)',   '124-81-21453', '매출', '032-589-2000', '21571', '인천광역시 부평구 무네미로 439', 'GM코리아 부평공장','Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_COMMON_CODE
-- =============================================
INSERT INTO MES_COMMON_CODE (group_code, group_name, code, code_name, sort_order, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ('CD001', '단위', 'EA',  '개수',  1, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD001', '단위', 'KG',  '무게',  2, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD001', '단위', 'M',   '길이',  3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD001', '단위', 'BOX', '박스',  4, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD001', '단위', 'SET', '세트',  5, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD002', '상태', 'WAIT',        '대기',    1, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD002', '상태', 'IN_PROGRESS', '진행중',  2, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD002', '상태', 'DONE',        '완료',    3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD003', '라인', 'A_LINE', 'A라인', 1, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD003', '라인', 'B_LINE', 'B라인', 2, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD003', '라인', 'C_LINE', 'C라인', 3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_WORK_ORDER (3개)
-- =============================================
INSERT INTO MES_WORK_ORDER (work_order_no, item_id, plan_qty, plan_date, status, line, remark, use_yn, confirm_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ('WO-2024-001', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 500,  '2024-07-01', 'DONE',        'A라인', '피스톤 정기생산',      'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2024-002', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 200,  '2024-07-05', 'IN_PROGRESS', 'B라인', '크랭크샤프트 긴급생산', 'Y', 'N', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2024-003', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 300,  '2024-07-10', 'WAIT',        'A라인', '실린더헤드 계획생산',   'Y', 'N', 'SYSTEM', NOW(), 'SYSTEM', NOW());
