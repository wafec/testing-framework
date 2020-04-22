
CREATE TABLE TEST_MESSAGE (
    id int not null auto_increment,
    position int,
    message_id int,
    test_input_id int,
    primary key (id),
    foreign key (message_id) references MESSAGE (id),
    foreign key (test_input_id) references TEST_INPUT (id)
);
/