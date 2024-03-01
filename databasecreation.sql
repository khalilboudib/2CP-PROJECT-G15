create database cp2_project ; 
use cp2_project ; 
create table clients (
    card_number bigint,
    card_expiring_date date ,
    first_name varchar(25) ,
    last_name varchar(25) ,
    user_adress varchar(75) ,
    public_key varbinary(256) ,
    server_private_key varbinary (256) 
) ;

