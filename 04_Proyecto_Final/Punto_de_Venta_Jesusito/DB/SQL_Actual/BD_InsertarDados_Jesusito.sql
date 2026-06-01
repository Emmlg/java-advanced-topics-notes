use basejesusito_2;

-- insertamos datos en la tabla ctipoPersona --
insert into cTipoPersona (cvTipoPersona, dsTipoPersona)
	values
    ('EmTip1','Empleado'),
    ('EmTip2', 'SuperEmpleado'),
    ('AdmTip', 'Administrador'),
    ('CliTip', 'Cliente'),
    ('ProTip','Proveedor');
    
-- insertamos los datos de la tabla persona
insert into persona (cvTipoPersona, nombre, apePaterno, apeMaterno, fechaNacimiento, curpPersona, genero, rfcPersona, emailPersona, municipio, estado, colonia, codPostal, telefono)
values
	('EmTip1', 'luis', 'Lopez', 'Lopez', '1982-06-04', 'luislolo3948506JSD', 'H', 'luislolo39485', 'luis@gmail.com', 'comitan', 'Chiapas', 'La Cueva', 30078, '9631241564'),
    ('CliTip', 'lisa', 'Aguilar', 'Aguilar', '1992-06-04', 'lisalala3948506JSD', 'M', 'lisalala39485', 'lisa@gmail.com', 'comitan', 'Chiapas', 'Las Cruzes', 30072, '9631241534'),
    ('CliTip', 'enrrique', 'Alfaro', 'lopez', '1984-06-04', 'enrrique3948506JSD', 'H', 'enrrique39485', 'enrrique@gmail.com', 'comitan', 'Chiapas', 'Las Flores', 30078, '9631241564'),
    ('CliTip', 'marco', 'Mendez', 'lopez', '1982-07-04', 'marcollo3948506JSD', 'H', 'marcollo39485', 'marco@gmail.com', 'comitan', 'Chiapas', 'El Rosario', 30078, '9631241324'),
    ('EmTip1', 'memo', 'Lopez', 'Hernandez', '1990-08-12', 'memo3948506JSDkwed', 'H', 'memo3948506JS', 'memo@gmail.com', 'comitan', 'Chiapas', 'El Rosario', 30056, '9631246673'),
    ('AdmTip', 'karla', 'Lopez', 'Aguilar', '1982-12-16', 'karlajlo3948506JSD', 'M', 'karlajlo39485', 'karla@gmail.com', 'comitan', 'Chiapas', 'Las Flores', 30045,'9631244790'),
    ('ProTip', 'maria', 'Lopez', 'Perez', '1978-04-24', 'mariaolo3948506JSD', 'M', 'mariaolo39485', 'maria@gmail.com', 'comitan', 'Chiapas', 'La Cruz Grande', 30074, '9631241521'),
    ('ProTip', 'carlos', 'Mendez', 'Mendez', '1992-02-18', 'joselolo3948506JSD','H', 'joselolo39485', 'jose@gmail.com', 'comitan', 'Chiapas', 'Jesusito', 30048, '9631241099'),
    ('ProTip', 'jose', 'Perez', 'Perez', '1982-02-18', 'joselolo3948506JSD','H', 'joselolo39485', 'jose@gmail.com', 'comitan', 'Chiapas', 'Jesusito', 30048, '9631241099');

insert into detalleProveedor(idProveedor, empresa, puesto)
	values
		(7, 'PCEX AutoMotive', 'Distribuidor general'),
        (8, 'ACDelco México', 'Gerente distribuidor'),
        (9, 'Auto-Rep Refacciones', 'Distribuidor general');


-- insertamos datos en mUsuario ----buscar detalle
insert into mUsuario(idUsuario, idEmpleado, login, pass, fechaInicio, fechaFin)
	values
    ('User001', 1, 'luis2323', '123456789', '2023-04-25', '2024-04-25'),
    ('User002', 5, 'memo3443', '24681012', '2023-04-26', '2024-04-26');
    
insert into cFormPago(cvFormPago, dsFormPago)
	value
	('crd', 'Credito'),
	('deb', 'Debito'),
    ('efe', 'Efectivo');
    
insert into cTipoEstado(cvTipoEstado, dsTipoEstado)
	value
		('Ent', 'Entregado'),
        ('Cam', 'EnCamino'),
        ('Ret', 'Retrasado'),
        ('Can', 'Cancelado');
    
-- buscar detalle
insert into productos(idProductos, idProveedor, fechaEntrada, precioVenta, descripcionP, ubicacionP, precioC, cantidadP, nPartesP, marcaP, categoriaP, marcaCoche, modeloCoche, anioCoche )
	values
    ('3445343545', 7, '2023-04-25', 180.00, 'Aceite Motor Sintetico', 'pasillo 2', 169.00, 4, '234123dfgrdsd', 'LTH', 'Motor', 'uso general', 'uso general', 2001),
    ('2127852344', 8, '2023-04-25', 150.00, 'Aceite Castrol Edge', 'pasillo 2', 109.00, 4, '234123dfgrdsd', 'Castrol', 'Motor', 'uso general', 'uso general', 2002),
    ('2123649908', 8, '2023-04-25', 220.00, 'Aceite Mobil Super', 'pasillo 2', 195.00, 5, '234123dfgrdsd', 'Mobil', 'Motor', 'uso general', 'uso general', 2001),
    ('2122002345', 7, '2023-04-26', 70.00, 'NGK (3764) bkr6eix-11 iridio IX Bujia', 'pasillo 4', 49.00, 5, 'jbdgth3647jsh', 'IX', 'Motor', 'uso general', 'uso general', 2000),
    ('3452341234', 7, '2023-04-27', 62.00, 'NGK (7090-4PK) BKR5EGP Bujia G-Power', 'pasillo 4', 32.00, 7, 'jjmklmnsudios', 'G-Power', 'Motor', 'uso general', 'uso general', 2007),
    ('6079944657', 8, '2023-04-28', 60.00, 'Cobre Bosch X5DC con bujía de níquel', 'pasillo 4', 40.00, 4, 'mnmdllfjmn465', 'Bosch', 'Motor', 'uso general', 'uso general', 2008),
    ('5069744567', 9, '2023-04-29', 890.00, 'Balatas Delanteras ENERGIT', 'pasillo 1', 780.00, 7, 'mcjklm3987gh3', 'ENERGIT', 'Frenos', 'uso general', 'uso general', 2010),
    ('6778005678', 9, '2023-04-29', 3800.00, 'Balatas Delanteras TRW', 'pasillo 1', 2565.00, 8, 'mcjklm3987gh3', 'TRW', 'Frenos', 'uso general', 'uso general', 2005),
    ('7122346789', 9, '2023-04-29', 980.00, 'Balatas Delanteras BREMBO', 'pasillo 1', 815.00, 9, 'mcjklm3987gh3', 'BREMBO', 'Frenos', 'uso general', 'uso general', 2006),
    ('2155566532', 8, '2023-04-29', 4500.00, 'Bomba De Agua GRG', 'pasillo 5', 3900.00, 5, 'mcjklm3987gh3', 'GRG', 'Enfriamiento', 'uso general', 'uso general', 2003),
    ('3994859908', 9, '2023-04-29', 2500.00, 'Bomba De Agua SALERI', 'pasillo 5', 1880.00, 5, 'mcjklm3987gh3', 'SALERI', 'Enfriamiento', 'uso general', 'uso general', 2014),
    ('6607347654', 7, '2023-04-29', 1600.00, 'Bomba De Agua TOPRAN', 'pasillo 5', 1000.00, 7, 'mcjklm3987gh3', 'TOPRAN', 'Enfriamiento', 'uso general', 'uso general', 2018);