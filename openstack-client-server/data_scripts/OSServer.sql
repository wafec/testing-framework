

CREATE TABLE OS_SERVER (
    id int not null auto_increment,
    uid text,
    test_id int not null,
    name text not null,
    image_id int not null,
    flavor_id int not null,
    network_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (image_id) REFERENCES OS_IMAGE (id),
    FOREIGN KEY (flavor_id) REFERENCES OS_FLAVOR (id),
    FOREIGN KEY (test_id) REFERENCES OS_TEST (id),
    FOREIGN KEY (network_id) REFERENCES OS_NETWORK (id)
);