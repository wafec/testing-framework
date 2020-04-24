
CREATE TABLE MESSAGE_DEVICE (
    id int not null auto_increment,
    device_type text not null,
    primary key (id)
);
/

CREATE TABLE MESSAGE_DEVICE_RABBIT (
    message_device_id int not null,
    url text,
    username text,
    passwd text,
    primary key (message_device_id),
    foreign key (message_device_id) references MESSAGE_DEVICE (id)
);
/