use master
go
create login admin with password = 'admin', default_database = rma, check_policy = off
go
use rma
go
create user admin from login admin
go
alter role admins add member admin
go
create login analyst with password = 'analyst', default_database = rma, check_policy = off
go
use rma
go
create user analyst from login analyst
go
alter role analysts add member analyst
go
create login engineer with password = 'engineer', default_database = rma, check_policy = off
go
use rma
go
create user engineer from login engineer
go
alter role engineers add member engineer
go
use master
go