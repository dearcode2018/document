
/* UUID.randomUUID().toString().replaceAll("-", "") */

/* mysql: sys_user */
create table sys_user (
	oid varchar(64) not null comment 'uuid',
	username varchar(64) not null comment '用户名',
	nickname varchar(64) comment '昵称 (用于显示)',
	password varchar(64) not null comment '密码',
	type varchar(24) not null default 'ADMIN-管理员, NORMAL-普通用户',
	status varchar(10) not null default 'ON' comment '用户状态 - 是否有效 默认 : 有效(ON) 无效(OFF)',
	primary key (oid)
) comment '用户表';

insert into sys_user(oid, username, nickname, password, type, status) 
values ('we546a75684sfaerw230', 'admin', 'Adaministrator', '123456', 'ADMIN', 'ON');

insert into sys_user(oid, username, nickname, password, type, status) 
values ('asdf34sv34zdfasf234', 'user', 'normal user', '123456', 'NORMAL', 'ON');

insert into sys_user(oid, username, nickname, password, type, status) 
values ('345sdf234sdfsf', 'me', 'normal me', '123456', 'NORMAL', 'OFF');

insert into sys_user(oid, username, nickname, password, type, status) 
values ('345sdf22asdfas34sdfsf', 'my', 'normal my', '123456', 'NORMAL', 'ON');



















