
CREATE TABLE ENVIRONMENT (
    id int not null auto_increment,
    name text not null,
    state text,
    environment_type text,
    primary key (id)
);


CREATE TABLE ENVIRONMENT_VIRTUAL_BOX (
    environment_id int not null,
    snapshot text,
    priority int default 1,
    primary key (environment_id),
    constraint fk_environment foreign key (environment_id) references ENVIRONMENT(id)
);