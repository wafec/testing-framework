

CREATE TABLE RABBIT_BIND (
    id int auto_increment,
    virtual_host text,
    routing_key text,
    source_id int,
    destination_id int,
    message_device_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (source_id) REFERENCES RABBIT_EXCHANGE (id),
    FOREIGN KEY (destination_id) REFERENCES RABBIT_QUEUE (id),
    FOREIGN KEY (message_device_id) REFERENCES MESSAGE_DEVICE_RABBIT (message_device_id)
);