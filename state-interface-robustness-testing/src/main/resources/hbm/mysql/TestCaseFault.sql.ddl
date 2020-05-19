
CREATE TABLE TEST_CASE_FAULT (
    id int not null auto_increment,
    test_case_id int,
    primary key (id),
    foreign key (test_case_id) references TEST_CASE(id)
);