alter table node add column status_comment varchar(250) after health_status_id;
alter table node add column status_time DATE after status_comment;