/**
 * CREATE Script for init of DB
 */


insert into sensor (id, date_created, is_deleted, name, status) values (1, now(), false, 'sensor1',
'OK');

insert into measurement (id, sensor_id, is_deleted, value, date_created) values (1, 1, false, 450,
now());
