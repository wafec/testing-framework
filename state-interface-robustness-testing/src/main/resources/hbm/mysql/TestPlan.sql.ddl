

CREATE TABLE TEST_PLAN (
    id int not null auto_increment,
    name text,
    plan_type text,
    PRIMARY KEY (id)
);


CREATE TABLE TEST_PLAN_TEST_CASE (
    test_plan_id int not null,
    test_case_id int not null,
    FOREIGN KEY (test_plan_id) REFERENCES TEST_PLAN (id),
    FOREIGN KEY (test_case_id) REFERENCES TEST_CASE (id),
    PRIMARY KEY (test_plan_id, test_case_id)
);


CREATE TABLE TEST_PLAN_ENVIRONMENT (
    test_plan_id int not null,
    environment_id int not null,
    FOREIGN KEY (test_plan_id) REFERENCES TEST_PLAN (id),
    FOREIGN KEY (environment_id) REFERENCES ENVIRONMENT (id),
    PRIMARY KEY (test_plan_id, environment_id)
);


CREATE TABLE TEST_PLAN_MESSAGE_DEVICE (
    test_plan_id int not null,
    message_device_id int not null,
    FOREIGN KEY (test_plan_id) REFERENCES TEST_PLAN (id),
    FOREIGN KEY (message_device_id) REFERENCES MESSAGE_DEVICE (id)
);