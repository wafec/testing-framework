
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