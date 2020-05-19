
CREATE TABLE TEST_EXECUTION_CONTEXT (
    id int not null auto_increment,
    test_case_id int,
    test_case_fault_id int,
    test_input_id int,
    test_specs_id int,
    primary key (id),
    foreign key (test_input_id) references TEST_INPUT(id),
    foreign key (test_case_id) references TEST_CASE(id),
    foreign key (test_case_fault_id) references TEST_CASE_FAULT(id),
    foreign key (test_specs_id) references TEST_SPECS(id)
);