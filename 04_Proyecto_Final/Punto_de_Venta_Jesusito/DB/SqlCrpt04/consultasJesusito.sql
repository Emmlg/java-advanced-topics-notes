-- interface de pedidos
use basejesusito_2;

-- comboBox para la seleccion de los Proveedores
select idPersona, concat(apePaterno, ' ', apeMaterno, ' ', nombre, ' ',empresa, ' ', puesto ) as dsProv from persona, detalleproveedor where cvTipoPersona = 'ProTip' and idPersona = idProveedor;

-- comboBox para la seleccion de la forma de pago
select cvFormPago, dsFormPago from cformpago;

-- llamada del ultimo numero de folio de la forma('Folio-0001') de folio
select folioP from pedidos order by idPedido desc limit 1; 

-- llenamos las tablas en base a los productos correspondientes a un proveedor id existentes (7,8,9)
select idProductos, concat(descripcionP, ' ', marcaP, ' ', marcaCoche, ' ', modeloCoche, ' ', anioCoche) as infProducto, cantidadP, precioC from productos, persona 
where idPersona = 8 and idProveedor = idPersona;

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
insert into pedidos (idEmpleado, cvFormPago, idProveedor, folioP, subTotal, IVA, totalPedido, anticipo, fechaPedido, fechaEntrega, estado)
	values
		(1, 'efe', 7, 'Folio-0001', 2000.00, 1500.00, 317.00, 2301.00, '2023-05-10', '2023-06-10', "En Entrega"),
        (5, 'efe', 8, 'Folio-0002', 6385.00, 4500.00, 1215.00, 7595.00, '2023-05-11', '2023-06-11', 'Retrasado');
        
-- consulta que devuelve el ultimo id existente de pedido
select idPedido from pedidos order by idPedido desc limit 1;

-- consulta que devuelve los folios existentes de los pedidos
select folioP from pedidos;
        
-- consutamos la informacion de los pedidos en la base de datos gracias al folio que sera enviado desde el registro seleccionado
select detallespedido.idPedido ,idProducto, descripcionP, cantProducto, detallespedido.precioC, marcaP, marcaCoche, modeloCoche, anioCoche, subTotalPedido
from productos, detallespedido, pedidos 
where folioP = 'Folio-0002' and pedidos.idPedido = detallespedido.idPedido and productos.idProductos = detallespedido.idProducto;

-- buscar pedidos atraves del nombre del proveedor
select idEmpleado, cFormPago.dsFormPago, concat(persona.nombre, ' ', persona.apePaterno, ' ',persona.apeMaterno, ' ', empresa, ' ', puesto) as infoProv, 
folioP, pedidos.subTotal, IVA, totalPedido, fechaPedido, fechaEntrega 
from pedidos, persona, detalleproveedor, cFormPago
where pedidos.idProveedor = 7 and pedidos.idProveedor = persona.idPersona and detalleproveedor.idProveedor = persona.idPersona and pedidos.cvFormPago = cFormPago.cvFormPago;

-- las consultas estaran separadas lla que en java sera dificil separar los registros
select concat(nombre, ' ', apePaterno, ' ', apeMaterno) as empleado from persona, musuario 
where persona.idPersona = musuario.idEmpleado and idEmpleado = 1;

-- buscamos los pedidosen base a un rango de fechas
select idEmpleado, cFormPago.dsFormPago, concat(persona.nombre, ' ', persona.apePaterno, ' ',persona.apeMaterno, ' ', empresa, ' ', puesto) as infProv, 
folioP, pedidos.subTotal, IVA, totalPedido, fechaPedido, fechaEntrega 
from pedidos, persona, detalleproveedor, cFormPago
where pedidos.fechaPedido between '2023-05-10' and '2023-05-11' and pedidos.idProveedor = persona.idPersona and detalleproveedor.idProveedor = persona.idPersona 
and pedidos.cvFormPago = cFormPago.cvFormPago;