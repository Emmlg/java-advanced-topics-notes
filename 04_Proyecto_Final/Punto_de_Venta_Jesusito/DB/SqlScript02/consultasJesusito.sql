-- interface de pedidos
use basejesusito_2;
-- comboBox para la seleccion de los Proveedores
select idPersona, concat(apePaterno, ' ', apeMaterno, ' ', nombre) as nombreC from persona where cvTipoPersona = 'ProTip';

-- comboBox para la seleccion de la forma de pago
select cvFormPago, dsFormPago from cformpago;

-- llamada del ultimo numero de folio de la forma('Folio-0001') de folio
select folioP from pedidos order by idPedido desc limit 1; 

-- llenamos las tablas en base a los productos correspondientes a un proveedor id existentes (7,8,9)
select idProductos, descripcionP, cantidadP, precioC, marcaP, marcaCoche, modeloCoche, anioCoche from productos, persona where idPersona = 8 and idProveedor = idPersona;

-- retornamos el ultimo id generado en pedidos con la finalidad de poder asignar manualmente el siguiente id 
select idPedido from detallesPedido order by idPedido desc limit 1;

-- agregamos los registros a la tabla detalles de pedidos
insert into detallesPedido (idPedido, idProducto, cantProducto, precioC, subTotalPedido)
	values
		(1, '3445343545', 4, 170.00, 680.00),
        (1, '2122002345', 4, 60.00, 240.00),
        (1, '3452341234', 2, 32.00, 64.00),
        (1, '6607347654', 1, 1000.00, 1000.00),
        (2, '2123649908', 1, 195.00, 195.00),
        (2, '2127852344', 4, 100.00, 400.00),
        (2, '2155566532', 2, 3500.00, 7000.00);
        
        
-- agregamos los registros a la tabla de pedidos
insert into pedidos (idEmpleado, cvFormPago, idProveedor, folioP, IVA, totalPedido, fechaPedido)
	values
		(1, 'efe', 7, 'Folio-0001', 317.00, 2301.00, '2023-05-11'),
        (5, 'efe', 8, 'Folio-0002', 1215.00, 7595.00, '2023-05-10');
        
-- consutamos la informacion de los pedidos en la base de datos (en proceso)
select idProducto, descripcionP, cantProducto, detallespedido.precioC, subTotalPedido from pedidos, detallespedido, productos 
where folioP = 'Folio-0001' and pedidos.idPedido = detallespedido.idPedido;