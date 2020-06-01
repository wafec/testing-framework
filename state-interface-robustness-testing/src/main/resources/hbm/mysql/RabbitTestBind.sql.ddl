

CREATE TABLE RABBIT_TEST_BIND (
    id int auto_increment,
    old_bind_id int,
    source_bind_id int,
    destination_bind_id int,
    message_device_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (old_bind_id) REFERENCES RABBIT_BIND (id),
    FOREIGN KEY (source_bind_id) REFERENCES RABBIT_BIND (id),
    FOREIGN KEY (destination_bind_id) REFERENCES RABBIT_BIND (id),
    FOREIGN KEY (message_device_id) REFERENCES MESSAGE_DEVICE_RABBIT (message_device_id)
);