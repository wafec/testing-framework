

CREATE TABLE RABBIT_EXCHANGE (
    id int auto_increment,
    name text,
    exchange_type text,
    message_device_id int,
    virtual_host text,
    PRIMARY KEY (id),
    FOREIGN KEY (message_device_id) REFERENCES MESSAGE_DEVICE_RABBIT (message_device_id)
);