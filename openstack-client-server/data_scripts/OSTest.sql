
-- DROP TABLE OS_TEST
CREATE TABLE OS_TEST (
    id int not null auto_increment,
    alias text not null,
    username text,
    passwd text,
    project_name text,
    user_domain_name text,
    project_domain_name text,
    auth_url text,
    PRIMARY KEY (id)
);


-- DROP TABLE OS_TEST_LOG
CREATE TABLE OS_TEST_LOG (
    id int not null auto_increment,
    test_id int not null,
    log text not null,
    log_date timestamp not null default current_timestamp
    PRIMARY KEY (id),
    FOREIGN KEY (test_id) REFERENCES OS_TEST(id)
);