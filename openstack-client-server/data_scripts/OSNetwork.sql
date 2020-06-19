

CREATE TABLE OS_NETWORK (
    id int not null auto_increment,
    uid text,
    name text,
    project_uid text,
    test_id int not null,
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES OS_TEST (id)
);