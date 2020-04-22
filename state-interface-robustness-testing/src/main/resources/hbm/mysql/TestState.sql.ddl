
CREATE TABLE TEST_STATE (
    id int not null auto_increment,
    position int,
    state_id int,
    test_input_id int,
    primary key (id),
    foreign key (state_id) references STATE(id),
    foreign key (test_input_id) references TEST_INPUT(id)
);
/