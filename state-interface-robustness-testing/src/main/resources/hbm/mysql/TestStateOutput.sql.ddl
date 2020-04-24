
CREATE TABLE TEST_STATE_OUTPUT (
    id int not null auto_increment,
    output_text longtext,
    output_time date,
    test_state_id int,
    primary key (id),
    foreign key (test_state_id) references TEST_STATE (id)
);
/