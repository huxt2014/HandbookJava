create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table jpa_data_source (id bigint not null, data_source_type varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table jpa_task_config (id bigint not null, description varchar(255), name varchar(255), rule_configs json, task_status varchar(255), primary key (id)) engine=InnoDB
create table jpa_task_data_source (data_number integer, data_source_mapper json, task_config_id bigint not null, data_source_id bigint not null, primary key (task_config_id, data_source_id)) engine=InnoDB
alter table jpa_data_source add constraint UK_5fps7aq81lfqh9snvf1vmg9pw unique (name)
alter table jpa_task_config add constraint UK_6ner45rqlht2n9bv9lgt10uyt unique (name)
alter table jpa_task_data_source add constraint FK2fnsl7fbb0pt25n9cu03r79h8 foreign key (task_config_id) references jpa_task_config (id)
alter table jpa_task_data_source add constraint FK8vcif4e31w9biokbow9kob1p foreign key (data_source_id) references jpa_data_source (id)
