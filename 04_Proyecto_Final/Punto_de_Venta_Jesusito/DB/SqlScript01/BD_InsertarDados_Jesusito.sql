use basejesusito_2;
-- insertamos los datos de la tabla persona
insert into persona (cvTipoPersona, nombre, apePaterno, apeMaterno, fechaNacimiento, 
curpPersona, genero, rfcPersona, emailPersona, municipio, estado, colonia, codPostal, telefono)
values
	(1, 'luis', 'lopez', 'lopez', '1982-06-04', 'luislolo3948506JSD', 'H', 'luislolo39485', 'luis@gmail.com', 'comitan', 'Chiapas', 'la cueva', 30078, '9631241564'),
    (1, 'memo', 'lopez', 'hernandez', '1990-06-12', 'memo3948506JSDkwed', 'H', 'memo3948506JS', 'memo@gmail.com', 'comitan', 'Chiapas', 'el rosario', 30056, '9631246673'),
    (3, 'karla', 'lopez', 'aguilar', '1982-10-16', 'karlajlo3948506JSD', 'M', 'karlajlo39485', 'karla@gmail.com', 'comitan', 'Chiapas', 'las flores', 30045,'9631244790'),
    (4, 'maria', 'lopez', 'perez', '1998-06-24', 'mariaolo3948506JSD', 'M', 'mariaolo39485', 'maria@gmail.com', 'comitan', 'Chiapas', 'la cruz grande', 30074, '9631241521'),
    (4, 'jose', 'perez', 'perez', '1982-02-18', 'joselolo3948506JSD','H', 'joselolo39485', 'jose@gmail.com', 'comitan', 'Chiapas', 'jesusito', 30048, '9631241099');

-- insertamos datos en la tabla ctipoPersona
insert into cTipoPersona (dsTipoPersona)
	values
    ('Empleado'),
    ('Administrador'),
    ('Cliente'),
    ('Proveedor');

-- insertamos datos en mUsuario
insert into mUsuario(idEmpleado, login, pass, fechaInicio, fechaFin)
	values
    (1, 'luis2323', '123456789', '2023-04-25', '2024-04-25'),
    (2, 'memo3443', '24681012', '2023-04-26', '2024-04-26');
    
insert into cFormPago(dsFormPago)
	value
	('Credito'),
	('Debito'),
    ('Efectivo');
    
insert into productos(idProveedor, fechaEntrada, precioVenta, descripcionP, ubicacionP, precioC, nPartesP, marcaP, categoriaP, marcaCoche, modeloCoche, anioCoche)
	values
    (4, '2023-04-25', 300, 'aceite', 'pasillo 2', 200, 1, 'desconocido', 'motor', 'uso general', 'uso general', '2000-01-01'),
    (4, '2023-04-26', 400, 'bujia', 'pasillo 2', 350, 1, 'desconocido', 'motor', 'uso general', 'uso general', '2000-01-01'),
    (5, '2023-04-27', 400, 'balatas', 'pasillo 4', 300, 2, 'desconocido', 'llantas', 'uso general', 'uso general', '2000-01-01'),
    (5, '2023-04-28', 670, 'aromatizante', 'pasillo 4', 500, 1, 'desconocido', 'decorativo', 'uso general', 'uso general', '2000-01-01'),
    (4, '2023-04-29', 980, 'anticongelante', 'pasillo 1', 800, 1, 'desconocido', 'motor', 'uso general', 'uso general', '2000-01-01');
    