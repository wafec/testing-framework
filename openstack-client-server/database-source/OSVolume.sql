

CREATE TABLE OS_VOLUME (
    id int not null auto_increment,
    uid text,
    test_id int not null,
    name text,
    status text,
    availability_zone text,
    size int,
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES OS_TEST(id)
);