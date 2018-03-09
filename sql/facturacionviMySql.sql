-- phpmyadmin sql dump
-- version 2.10.2
-- http://www.phpmyadmin.net
-- 
-- servidor: localhost
-- tiempo de generación: 29-09-2016 a las 15:08:13
-- versión del servidor: 5.0.45
-- versión de php: 5.2.3

set sql_mode="no_auto_value_on_zero";

-- 
create database `facturacionvi`;

use `facturacionvi`;
-- 

-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `categoria`
-- 

create table `categoria` (
  `id` int(11) not null auto_increment,
  `nombre` varchar(50) not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `categoria`
-- 


-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `cliente`
-- 

create table `cliente` (
  `id` int(11) not null auto_increment,
  `nombre` varchar(50) not null,
  `apellido` varchar(50) not null,
  `direccion` varchar(50) not null,
  `telefono` varchar(50) not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `cliente`
-- 


-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `detfactura`
-- 

create table `detfactura` (
  `id` int(11) not null auto_increment,
  `idfactura` int(11) not null,
  `idinventario` int(11) not null,
  `cantidad` int(11) not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `detfactura`
-- 


-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `estadousuario`
-- 

create table `estadousuario` (
  `id` int(11) not null auto_increment,
  `nombre` varchar(50) collate utf8_spanish_ci not null,
  primary key  (`id`)
) engine=myisam  default charset=utf8 collate=utf8_spanish_ci auto_increment=3 ;

-- 
-- volcar la base de datos para la tabla `estadousuario`
-- 

insert into `estadousuario` values (1, 'activo');
insert into `estadousuario` values (2, 'bloqueado');

-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `factura`
-- 

create table `factura` (
  `id` int(11) not null auto_increment,
  `idusuario` int(11) not null,
  `idcliente` int(11) not null,
  `fecha` date not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `factura`
-- 


-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `inventario`
-- 

create table `inventario` (
  `id` int(11) not null auto_increment,
  `idproducto` int(11) not null,
  `fecha` date not null,
  `cantidad` int(11) not null,
  `precioin` int(11) not null,
  `precioout` int(11) not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `inventario`
-- 


-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `producto`
-- 

create table `producto` (
  `id` int(11) not null auto_increment,
  `nombre` varchar(50) not null,
  `descripcion` varchar(255) default null,
  `idcategoria` int(11) not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `producto`
-- 


-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `tipousuario`
-- 

create table `tipousuario` (
  `id` int(11) not null auto_increment,
  `nombre` varchar(50) collate utf8_spanish_ci not null,
  primary key  (`id`)
) engine=myisam  default charset=utf8 collate=utf8_spanish_ci auto_increment=3 ;

-- 
-- volcar la base de datos para la tabla `tipousuario`
-- 

insert into `tipousuario` values (1, 'administrador');
insert into `tipousuario` values (2, 'usuario');

-- --------------------------------------------------------

-- 
-- estructura de tabla para la tabla `usuario`
-- 

create table `usuario` (
  `id` int(11) not null auto_increment,
  `usuario` varchar(50) not null,
  `contrasena` varchar(50) not null,
  `nombre` varchar(50) not null,
  `idtipousuario` int(11) not null,
  `idestadousuario` int(50) not null,
  primary key  (`id`)
) engine=myisam default charset=utf8 auto_increment=1 ;

-- 
-- volcar la base de datos para la tabla `usuario`
-- 
