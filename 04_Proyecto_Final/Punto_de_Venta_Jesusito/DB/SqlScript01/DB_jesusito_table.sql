-- creacion de la base de datos 
create database baseJesusito_2;

use baseJesusito_2;

-- creacion de las tablas de las base de datos
create table persona (
	idPersona int not null auto_increment,
    cvTipoPersona int not null,
    nombre varchar(20) not null,
    apePaterno varchar(20) not null,
    apeMaterno varchar(20) not null,
    fechaNacimiento date not null,
    curpPersona varchar(18) not null,
    genero enum('M','H') not null,
    rfcPersona varchar(13) not null,
    emailPersona varchar(30) not null,
    municipio varchar(30) not null,
    estado varchar(20) not null,
    colonia varchar(20) not null,
    codPostal int not null,
    telefono varchar(10) not null,
    primary key(idPersona),
    foreign key(cvTipoPersona) references cTipoPersona(cvTipoPersona) -- fk tabla cTipoEmpleado
);
-- modificamos el nombre de la tabla a cTipoPersona
create table cTipoPersona(
	cvTipoPersona int not null auto_increment,
    dsTipoPersona enum('Empleado','Administrador', 'Cliente', 'Proveedor'),
    primary key(cvTipoPersona)
);

create table mUsuario(
	idUsuario int not null auto_increment,
    idEmpleado int not null,
    login varchar(30) not null unique,
    pass varchar(30) not null,
    fechaInicio date,
    fechaFin date,
    primary key(idUsuario),
    foreign key(idEmpleado) references persona(idPersona)
);
--
create table pedidos(
	idPedido int not null auto_increment,
    idEmpleado int not null,
    cvFormPago int not null,
    idProveedor int not null,
    folioP varchar(6) not null,
    IVA int not null,
    totalPedido int not null,
    fechaPedido date,
    primary key(idPedido),
    foreign key(idEmpleado) references persona(idPersona),
    foreign key(cvFormPago) references cFormPago(cvFormPago),
    foreign key(idProveedor) references persona(idPersona)
);

create table cFormPago(
	cvFormPago int not null auto_increment,
    dsFormPago enum('Credito', 'Debito', 'Efectivo') not null,
    primary key(cvFormPago)
);

create table detallesPedido(
	idPedido int not null auto_increment,
    idProducto int not null,
    cantProducto int not null,
    precioC int not null,
    subTotalPedido int not null,
    primary key(idPedido),
    foreign key(idProducto) references productos(idProductos)
);

create table productos(
	idProductos int not null auto_increment,
    idProveedor int not null,
    fechaEntrada date not null,
    precioVenta int not null,
    descripcionP varchar(30) not null,
    ubicacionP varchar(30) not null,
    precioC int not null,
    nPartesP int not null,
    marcaP varchar(30) not null,
    categoriaP varchar(30) not null,
    marcaCoche varchar(30) not null,
    modeloCoche varchar(30) not null,
    anioCoche date not null,
    primary key(idProductos),
    foreign key(idProveedor) references persona(idPersona)
);
