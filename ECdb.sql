begin transaction;
.mode column
.header on
drop table goods;
drop table category;
drop table goodStatus;
drop table user;
drop table shoppingDetail;
drop table orders;
drop table orderDetail;
drop table orderStatus;
drop table comment;
--�½���Ʒ��
create table goods(
	id integer primary key autoincrement,
	name verchar(20) unique not null,
	price real not null check(price>=0),
	stock integer not null check(stock>=0),
	describe text,
	categoryId integer not null,
	gStatusId integer not null check(gStatusId>=0),
	foreign key(categoryId) references category(id),
	foreign key(gStatusId) references goodStatus(id)
);

--�½�������Ϣ��
create table category(
	id integer primary key autoincrement,
	category verchar(10) not null unique
);

--���Ĭ�ϵķ���
--insert into category(category) values('��װ����');
--insert into category(category) values('ʳƷ��ʳ');
--insert into category(category) values('����Ʒ');
--insert into category(category) values('�����Ʒ');

--�½���Ʒ״̬��
create table goodStatus(
	id integer primary key autoincrement,
	status verchar(10) not null unique
);

--���Ĭ�ϵ�״̬��Ϣ
--insert into goodStatus(status) values('���¼�');
--insert into goodStatus(status) values('������');
--insert into goodStatus(status) values('������');

--�½��û���
create table user(
	id integer primary key autoincrement,
	userName verchar(20) unique not null,
	pwd verchar(20) not null,
	address text,
	email text,
	exp integer check(exp>=0),
	level integer check(level>=0),
	balance real check(balance>=0)
);
--���Ĭ�ϵĹ���Ա��Ϣ
insert into user(userName,pwd,address,email,exp,level,balance) values('admin','admin','aaa','aaa',0,0,0);

--�½��������
create table shoppingDetail(
	id integer primary key autoincrement,
	gid integer,
	uid integer,
	num integer check(num>=0),
	totalPrice real check(totalPrice>=0),
	foreign key(gid) references goods(id),
	foreign key(uid) references user(id)
);

--�½�������
create table orders(
	id integer primary key autoincrement,
	uid integer,
	date text,
	oStatusId integer not null check(oStatusId>=0),
	totalPrice real check(totalPrice>=0),
	foreign key(uid) references user(id),
	foreign key(oStatusId) references orderStatus(id)
);

--�½�������ϸ���
create table orderDetail(
	id integer primary key autoincrement,
	gid integer,
	oid integer,
	num integer check(num>=0),
	totalPrice real check(totalPrice>=0),
	oStatusId integer not null check(oStatusId>0),
	foreign key(gid) references goods(id),
	foreign key(oid) references orders(id),
	foreign key(oStatusId) references orderStatus(id)
);

--�½�����״̬��
create table orderStatus(
	id integer primary key autoincrement,
	status verchar(10) not null unique
);

--���Ĭ�ϵ�״̬��Ϣ
--insert into orderStatus(status) values('���ύ');
--insert into orderStatus(status) values('�����');
--insert into orderStatus(status) values('������');
--insert into orderStatus(status) values('�ѷ���');
--insert into orderStatus(status) values('���');


--�½���Ʒ���۱�
create table comment(
	id integer primary key autoincrement,
	gid integer,
	userName verchar(20),
	oid integer,
	content text,
	date text,
	foreign key(gid) references goods(id),
	foreign key(oid) references orders(id)
);

--�½������Ϣ��
create table radEnvelope(
	id integer primary key autoincrement,
	envelopeName verchar(20),
	sumMoney real check(balance>=0),
	beGrab real,
	balance real check(balance>=0)
);

create table userGotRadEnv(
	id integer primary key autoincrement,
	userName verchar(20),
	date text,
	rid integer references radEnvelope(id)
);
commit;


