

-- DROP TABLE OS_IMAGE
CREATE TABLE OS_IMAGE (
    id int not null auto_increment,
    uid text,
    name text,
    disk_format text,
    container_format text,
    test_id int not null,
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES OS_TEST (id)
);