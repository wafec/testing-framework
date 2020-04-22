
CREATE TABLE STATE (
    id int not null auto_increment,
    name text,
    timeout int,
    required bit,
    primary key (id)
);
/