

CREATE TABLE RABBIT_QUEUE (
    id int auto_increment,
    name text,
    virtual_host text,
    message_device_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (message_device_id) REFERENCES MESSAGE_DEVICE_RABBIT (message_device_id)
);