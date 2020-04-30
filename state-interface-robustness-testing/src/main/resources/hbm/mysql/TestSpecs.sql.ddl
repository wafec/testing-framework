
CREATE TABLE TEST_SPECS (
    id int not null auto_increment,
    primary key (id)
);


CREATE TABLE TEST_SPECS_ENVIRONMENT (
    test_specs_id int not null,
    environment_id int not null,
    primary key (test_specs_id, environment_id),
    foreign key (test_specs_id) references TEST_SPECS(id),
    foreign key (environment_id) references ENVIRONMENT(id)
);


CREATE TABLE TEST_SPECS_MESSAGE_DEVICE (
    test_specs_id int not null,
    message_device_id int not null,
    primary key (test_specs_id, message_device_id),
    foreign key (test_specs_id) references TEST_SPECS(id),
    foreign key (message_device_id) references MESSAGE_DEVICE(id)
);