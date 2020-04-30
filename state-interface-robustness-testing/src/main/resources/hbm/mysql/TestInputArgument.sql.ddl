
CREATE TABLE TEST_INPUT_ARGUMENT (
    id int not null auto_increment,
    name text,
    data_type text,
    data_value longtext,
    test_input_id int,
    primary key (id),
    foreign key (test_input_id) references TEST_INPUT (id)
);