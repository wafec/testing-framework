
CREATE TABLE MESSAGE (
    id int not null auto_increment,
    source text,
    destination text,
    name text,
    sequence_id bigint,
    entry_date date,
    primary key (id)
);