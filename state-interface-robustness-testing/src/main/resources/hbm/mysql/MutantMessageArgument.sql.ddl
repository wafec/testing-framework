
CREATE TABLE MUTANT_MESSAGE_ARGUMENT (
    id int not null auto_increment,
    fault_id int,
    message_argument_id int,
    mutation_data_value longtext,
    test_case_id int,
    primary key (id),
    foreign key (fault_id) references FAULT(id),
    foreign key (message_argument_id) references MESSAGE_ARGUMENT(id),
    foreign key (test_case_id) references TEST_CASE(id)
);