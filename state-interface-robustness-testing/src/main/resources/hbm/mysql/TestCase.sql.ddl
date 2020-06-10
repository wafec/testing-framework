
CREATE TABLE TEST_CASE (
    id int not null auto_increment,
    primary key (id),
    uid varchar(512) unique
);

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