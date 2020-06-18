

-- DROP TABLE OS_FLAVOR
CREATE TABLE OS_FLAVOR (
    id int not null auto_increment,
    uid text,
    name text,
    vcpus int,
    ram int,
    disk int,
    test_id int not null,
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES OS_TEST (id)
);