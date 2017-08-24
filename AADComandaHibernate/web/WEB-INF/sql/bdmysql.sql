create database comanda default character set utf8 collate utf8_unicode_ci;

create user ucomanda@localhost identified by 'ccomanda';

grant all on comanda.* to ucomanda;

flush privileges;

use comanda;

create table usuario(
    correo varchar(50) binary not null primary key,
    alias varchar(20) not null unique,
    clave varchar(40) not null,
    tipo enum('administrador','gerente','camarero') not null default 'camarero',
    activo tinyint not null default 0,
    fecha timestamp not null
) engine=innodb;

create table mesa(
    id int auto_increment primary key,
    nombre varchar(80) not null unique
) engine=innodb;

create table carta(
    id int auto_increment primary key,
    nombre varchar(80) not null unique,
    precio decimal(7,2) not null
) engine=innodb;

create table pedido(
    id int auto_increment primary key,
    fechahora timestamp not null,
    idmesa int not null,
    cerrado tinyint not null default 1,
    foreign key (idmesa) references mesa(id) on delete restrict on update restrict
) engine=innodb;

create table detallepedido(
    id int auto_increment primary key,
    idpedido int not null,
    idcarta int not null,
    cantidad int default 0,
    precio decimal(7,2) not null,
    unique(idpedido, idcarta),
    foreign key (idpedido) references pedido(id) on delete restrict on update restrict,
    foreign key (idcarta) references carta(id) on delete restrict on update restrict
) engine=innodb;