
CREATE TABLE MESSAGE_ARGUMENT_FAULT (
    id int not null auto_increment,
    fault_id int,
    message_argument_id int,
    fault_data longtext,
    test_case_fault_id int,
    primary key (id),
    foreign key (fault_id) references FAULT(id),
    foreign key (message_argument_id) references MESSAGE_ARGUMENT(id),
    foreign key (test_case_fault_id) references TEST_CASE_FAULT(id)
);