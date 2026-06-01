-- creacion de la base de datos 
create database baseJesusito_2;

use baseJesusito_2;

-- modificamos el nombre de la tabla a cTipoPersona
create table cTipoPersona(
	cvTipoPersona varchar(6) not null,
    dsTipoPersona enum('Empleado', 'superEmpleado', 'Administrador', 'Cliente', 'Proveedor'),
    primary key(cvTipoPersona)
);

-- creacion de las tablas de las base de datos
create table persona (
	idPersona int not null auto_increment,
    cvTipoPersona varchar(6) not null,
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

create table mUsuario(
	idUsuario varchar(7) not null,
    idEmpleado int not null,
    login varchar(30) not null unique,
    pass varchar(30) not null,
    fechaInicio date,
    fechaFin date,
    primary key(idUsuario),
    foreign key(idEmpleado) references persona(idPersona)
);

create table cFormPago(
	cvFormPago varchar(3) not null,
    dsFormPago enum('Credito', 'Debito', 'Efectivo') not null,
    primary key(cvFormPago)
);

--
create table pedidos(
	idPedido int not null auto_increment,
    idEmpleado int not null,
    cvFormPago varchar(3) not null,
    idProveedor int not null,
    folioP varchar(10) not null,
    IVA double not null,
    totalPedido double not null,
    fechaPedido date,
    primary key(idPedido),
    foreign key(idEmpleado) references persona(idPersona),
    foreign key(cvFormPago) references cFormPago(cvFormPago),
    foreign key(idProveedor) references persona(idPersona)
);

create table productos(
	idProductos varchar(12) not null,
    idProveedor int not null,
    fechaEntrada date not null,
    precioVenta double not null,
    descripcionP varchar(80) not null,
    ubicacionP varchar(60) not null,
    precioC double not null,
    cantidadP int not null,
    nPartesP varchar(13) not null,
    marcaP varchar(30) not null,
    categoriaP varchar(30) not null,
    marcaCoche varchar(30) not null,
    modeloCoche varchar(30) not null,
    anioCoche int not null,
    primary key(idProductos),
    foreign key(idProveedor) references persona(idPersona)
);

create table detallesPedido(
	idPedido int not null,
    idProducto varchar(12) not null,
    cantProducto int not null,
    precioC double not null, -- modificar
    subTotalPedido double not null,
    -- primary key(idPedido),
    foreign key(idProducto) references productos(idProductos)
);