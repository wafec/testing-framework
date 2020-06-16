
CREATE TABLE TEST_SPECS (
    id int not null auto_increment,
    test_plan_id int not null,
    primary key (id),
    foreign key (test_plan_id) references TEST_PLAN (id)
);