create table manager
(
    id bigint not null auto_increment,
    name varchar(200) not null,
    email varchar(200),
    create_at datetime not null default current_timestamp,
    update_at datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

create table schedule
(
    id bigint not null auto_increment,
    content varchar(200) not null,
    manager_id bigint not null,
    password varchar(10) not null,
    create_at datetime not null default current_timestamp,
    update_at datetime not null default current_timestamp on update current_timestamp,
    foreign key(manager_id) references manager(id),
    primary key (id)
);