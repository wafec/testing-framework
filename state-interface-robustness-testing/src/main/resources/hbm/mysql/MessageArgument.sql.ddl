
CREATE TABLE MESSAGE_ARGUMENT (
    id int not null auto_increment,
    message_order int,
    name text,
    data_type text,
    data_value longtext,
    primary key (id)
);