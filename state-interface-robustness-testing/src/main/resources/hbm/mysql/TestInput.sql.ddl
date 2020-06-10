
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