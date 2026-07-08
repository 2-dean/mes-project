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
DELETE FROM MES_CODE_GROUP;

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
-- MES_CODE_GROUP
-- =============================================
INSERT INTO MES_CODE_GROUP (group_code, group_name, description, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ('CD001', '단위', '품목 단위코드', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD002', '상태', '작업지시 상태코드', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('CD003', '라인', '생산라인코드', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_COMMON_CODE
-- =============================================
INSERT INTO MES_COMMON_CODE (group_id, code, code_name, sort_order, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD001'), 'EA',  '개수',  1, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD001'), 'KG',  '무게',  2, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD001'), 'M',   '길이',  3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD001'), 'BOX', '박스',  4, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD001'), 'SET', '세트',  5, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD002'), 'WAIT',        '대기',    1, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD002'), 'IN_PROGRESS', '진행중',  2, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD002'), 'DONE',        '완료',    3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD003'), 'A_LINE', 'A라인', 1, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD003'), 'B_LINE', 'B라인', 2, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_CODE_GROUP WHERE group_code = 'CD003'), 'C_LINE', 'C라인', 3, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_WORK_ORDER (2026-07-01 ~ 2026-07-07 더미 + 오늘(진행중) 2건)
-- =============================================
INSERT INTO MES_WORK_ORDER (work_order_no, item_id, plan_qty, plan_date, status, line, remark, use_yn, confirm_yn, created_by, created_at, updated_by, updated_at)
VALUES
    -- 07-01
    ('WO-2026-004', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 320, DATE '2026-07-01', 'DONE', 'A라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-005', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 180, DATE '2026-07-01', 'DONE', 'C라인', '크랭크샤프트 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-006', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 450, DATE '2026-07-01', 'DONE', 'B라인', '실린더헤드 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-02
    ('WO-2026-007', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 210, DATE '2026-07-02', 'DONE', 'A라인', '실린더헤드 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-008', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 380, DATE '2026-07-02', 'DONE', 'B라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-03
    ('WO-2026-009', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 260, DATE '2026-07-03', 'DONE', 'A라인', '크랭크샤프트 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-010', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 500, DATE '2026-07-03', 'DONE', 'C라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-011', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 150, DATE '2026-07-03', 'DONE', 'B라인', '실린더헤드 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-04
    ('WO-2026-012', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 430, DATE '2026-07-04', 'DONE', 'A라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-013', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 220, DATE '2026-07-04', 'DONE', 'B라인', '크랭크샤프트 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-05
    ('WO-2026-014', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 300, DATE '2026-07-05', 'DONE', 'C라인', '실린더헤드 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-015', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 275, DATE '2026-07-05', 'DONE', 'A라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-016', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 190, DATE '2026-07-05', 'DONE', 'B라인', '크랭크샤프트 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-06
    ('WO-2026-017', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 340, DATE '2026-07-06', 'DONE', 'B라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-018', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 400, DATE '2026-07-06', 'DONE', 'A라인', '실린더헤드 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-07 (작업지시는 완료/확정되었지만 아직 일마감 전)
    ('WO-2026-019', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 230, DATE '2026-07-07', 'DONE', 'C라인', '크랭크샤프트 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-020', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 360, DATE '2026-07-07', 'DONE', 'A라인', '피스톤 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-021', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-003'), 280, DATE '2026-07-07', 'DONE', 'B라인', '실린더헤드 생산', 'Y', 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 오늘 (진행중, 생산실적 일부만 입력)
    ('WO-2026-022', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-001'), 300, CURRENT_DATE, 'IN_PROGRESS', 'A라인', '피스톤 생산', 'Y', 'N', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ('WO-2026-023', (SELECT id FROM MES_ITEM WHERE item_code = 'ITEM-002'), 250, CURRENT_DATE, 'IN_PROGRESS', 'B라인', '크랭크샤프트 생산', 'Y', 'N', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_PROD_RESULT (작업지시별 작업자 2~3명, 07-08은 일부만 입력)
-- =============================================
INSERT INTO MES_PROD_RESULT (work_order_id, worker, scan_qty, manual_qty, total_qty, prod_date, remark, use_yn, created_by, created_at, updated_by, updated_at)
VALUES
    -- 07-01
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-004'), 'user1', 150, 10, 160, DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-004'), 'user2', 120, 5,  125, DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-004'), 'user3', 100, 15, 115, DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-005'), 'user1', 90,  8,  98,  DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-005'), 'user2', 70,  12, 82,  DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-006'), 'user2', 250, 5,  255, DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-006'), 'user3', 180, 20, 200, DATE '2026-07-01', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-02
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-007'), 'user1', 100, 10, 110, DATE '2026-07-02', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-007'), 'user3', 80,  5,  85,  DATE '2026-07-02', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-008'), 'user1', 200, 15, 215, DATE '2026-07-02', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-008'), 'user2', 150, 8,  158, DATE '2026-07-02', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-008'), 'user3', 140, 0,  140, DATE '2026-07-02', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-03
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-009'), 'user2', 140, 10, 150, DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-009'), 'user3', 100, 20, 120, DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-010'), 'user1', 300, 5,  305, DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-010'), 'user2', 200, 12, 212, DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-010'), 'user3', 180, 18, 198, DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-011'), 'user1', 80,  7,  87,  DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-011'), 'user3', 60,  10, 70,  DATE '2026-07-03', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-04
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-012'), 'user1', 250, 10, 260, DATE '2026-07-04', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-012'), 'user2', 180, 5,  185, DATE '2026-07-04', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-012'), 'user3', 150, 15, 165, DATE '2026-07-04', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-013'), 'user2', 130, 8,  138, DATE '2026-07-04', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-013'), 'user3', 90,  12, 102, DATE '2026-07-04', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-05
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-014'), 'user1', 180, 5,  185, DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-014'), 'user2', 120, 10, 130, DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-014'), 'user3', 100, 20, 120, DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-015'), 'user1', 160, 8,  168, DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-015'), 'user2', 100, 12, 112, DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-016'), 'user2', 110, 5,  115, DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-016'), 'user3', 70,  15, 85,  DATE '2026-07-05', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-06
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-017'), 'user1', 200, 10, 210, DATE '2026-07-06', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-017'), 'user2', 150, 5,  155, DATE '2026-07-06', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-017'), 'user3', 110, 18, 128, DATE '2026-07-06', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-018'), 'user1', 240, 8,  248, DATE '2026-07-06', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-018'), 'user3', 150, 12, 162, DATE '2026-07-06', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-07 (아직 일마감 전)
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-019'), 'user1', 140, 5,  145, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-019'), 'user2', 100, 10, 110, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-019'), 'user3', 80,  20, 100, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-020'), 'user1', 220, 8,  228, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-020'), 'user2', 150, 12, 162, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-021'), 'user2', 170, 5,  175, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-021'), 'user3', 100, 15, 115, DATE '2026-07-07', NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 오늘 (진행중이라 생산실적 일부만 입력됨)
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-022'), 'user1', 80,  0,  80,  CURRENT_DATE, NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-023'), 'user2', 60,  5,  65,  CURRENT_DATE, NULL, 'Y', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_DAILY_CLOSE (2026-07-01 ~ 2026-07-06 마감처리, 07-07은 미마감)
-- =============================================
INSERT INTO MES_DAILY_CLOSE (close_date, close_yn, closed_by, closed_at, created_by, created_at, updated_by, updated_at)
VALUES
    (DATE '2026-07-01', 'Y', 'manager1', TIMESTAMP '2026-07-01 18:30:00', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    (DATE '2026-07-02', 'Y', 'manager1', TIMESTAMP '2026-07-02 18:30:00', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    (DATE '2026-07-03', 'Y', 'manager1', TIMESTAMP '2026-07-03 18:30:00', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    (DATE '2026-07-04', 'Y', 'manager1', TIMESTAMP '2026-07-04 18:30:00', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    (DATE '2026-07-05', 'Y', 'manager1', TIMESTAMP '2026-07-05 18:30:00', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    (DATE '2026-07-06', 'Y', 'manager1', TIMESTAMP '2026-07-06 18:30:00', 'SYSTEM', NOW(), 'SYSTEM', NOW());

-- =============================================
-- MES_PROD_INCENTIVE (일마감된 07-01~07-06 기준, amount = qty * unit_price * incentive_rate / 100)
-- =============================================
INSERT INTO MES_PROD_INCENTIVE (work_order_id, worker, qty, unit_price, incentive_rate, amount, confirm_yn, close_date, created_by, created_at, updated_by, updated_at)
VALUES
    -- 07-01
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-004'), 'user1', 160, 15000, 5, 120000, 'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-004'), 'user2', 125, 15000, 5, 93750,  'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-004'), 'user3', 115, 15000, 5, 86250,  'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-005'), 'user1', 98,  85000, 3, 249900, 'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-005'), 'user2', 82,  85000, 3, 209100, 'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-006'), 'user2', 255, 45000, 4, 459000, 'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-006'), 'user3', 200, 45000, 4, 360000, 'N', DATE '2026-07-01', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-02
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-007'), 'user1', 110, 45000, 4, 198000, 'N', DATE '2026-07-02', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-007'), 'user3', 85,  45000, 4, 153000, 'N', DATE '2026-07-02', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-008'), 'user1', 215, 15000, 5, 161250, 'N', DATE '2026-07-02', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-008'), 'user2', 158, 15000, 5, 118500, 'N', DATE '2026-07-02', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-008'), 'user3', 140, 15000, 5, 105000, 'N', DATE '2026-07-02', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-03
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-009'), 'user2', 150, 85000, 3, 382500, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-009'), 'user3', 120, 85000, 3, 306000, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-010'), 'user1', 305, 15000, 5, 228750, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-010'), 'user2', 212, 15000, 5, 159000, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-010'), 'user3', 198, 15000, 5, 148500, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-011'), 'user1', 87,  45000, 4, 156600, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-011'), 'user3', 70,  45000, 4, 126000, 'N', DATE '2026-07-03', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-04
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-012'), 'user1', 260, 15000, 5, 195000, 'N', DATE '2026-07-04', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-012'), 'user2', 185, 15000, 5, 138750, 'N', DATE '2026-07-04', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-012'), 'user3', 165, 15000, 5, 123750, 'N', DATE '2026-07-04', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-013'), 'user2', 138, 85000, 3, 351900, 'N', DATE '2026-07-04', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-013'), 'user3', 102, 85000, 3, 260100, 'N', DATE '2026-07-04', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-05
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-014'), 'user1', 185, 45000, 4, 333000, 'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-014'), 'user2', 130, 45000, 4, 234000, 'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-014'), 'user3', 120, 45000, 4, 216000, 'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-015'), 'user1', 168, 15000, 5, 126000, 'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-015'), 'user2', 112, 15000, 5, 84000,  'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-016'), 'user2', 115, 85000, 3, 293250, 'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-016'), 'user3', 85,  85000, 3, 216750, 'N', DATE '2026-07-05', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    -- 07-06
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-017'), 'user1', 210, 15000, 5, 157500, 'N', DATE '2026-07-06', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-017'), 'user2', 155, 15000, 5, 116250, 'N', DATE '2026-07-06', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-017'), 'user3', 128, 15000, 5, 96000,  'N', DATE '2026-07-06', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-018'), 'user1', 248, 45000, 4, 446400, 'N', DATE '2026-07-06', 'SYSTEM', NOW(), 'SYSTEM', NOW()),
    ((SELECT id FROM MES_WORK_ORDER WHERE work_order_no = 'WO-2026-018'), 'user3', 162, 45000, 4, 291600, 'N', DATE '2026-07-06', 'SYSTEM', NOW(), 'SYSTEM', NOW());
