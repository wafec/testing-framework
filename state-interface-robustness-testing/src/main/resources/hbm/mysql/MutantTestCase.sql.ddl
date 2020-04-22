
CREATE TABLE MUTANT_TEST_CASE (
    id int not null auto_increment,
    test_case_id int,
    primary key (id),
    foreign key (test_case_id) references TEST_CASE(id)
);
/