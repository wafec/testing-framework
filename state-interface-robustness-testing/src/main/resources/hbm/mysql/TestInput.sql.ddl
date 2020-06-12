
CREATE TABLE TEST_INPUT (
    id int not null auto_increment,
    test_case_id int,
    action text,
    locked bit,
    primary key (id),
    foreign key (test_case_id) references TEST_CASE(id)
);

ALTER TABLE TEST_INPUT
    ADD COLUMN input_order int DEFAULT 0;

CREATE OR REPLACE VIEW TEST_CASE_VIEW
    AS
    SELECT
        T.id,
        T.uid,
        COUNT(I.id) as input_count
    FROM
        TEST_CASE T
            LEFT JOIN TEST_INPUT I ON I.test_case_id = T.id
    GROUP BY
        T.id,
        T.uid;