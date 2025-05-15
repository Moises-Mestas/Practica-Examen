-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 24-04-2025 a las 13:51:43
-- Versión del servidor: 8.0.30
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ms_catalogo1`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(1, 'Electrodomésticos'),
(2, 'Muebles'),
(3, 'Juguetes'),
(4, 'Herramientas'),
(5, 'Deportes'),
(6, 'Electrodomésticos'),
(7, 'Muebles'),
(8, 'Juguetes'),
(9, 'Herramientas'),
(10, 'Deportes'),
(11, 'Electrodomésticos'),
(12, 'Muebles'),
(13, 'Juguetes'),
(14, 'Herramientas'),
(15, 'Deportes'),
(16, 'Electrodomésticos'),
(17, 'Muebles'),
(18, 'Juguetes'),
(19, 'Herramientas'),
(20, 'Deportes'),
(21, 'Electrodomésticos'),
(22, 'Muebles'),
(23, 'Juguetes'),
(24, 'Herramientas'),
(25, 'Deportes'),
(26, 'Electrodomésticos'),
(27, 'Muebles'),
(28, 'Juguetes'),
(29, 'Herramientas'),
(30, 'Deportes'),
(31, 'Electrodomésticos'),
(32, 'Muebles'),
(33, 'Juguetes'),
(34, 'Herramientas'),
(35, 'Deportes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `categoria_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `categoria_id`) VALUES
(1, 'Refrigerador', 4),
(2, 'Silla de oficina', 5),
(4, 'Refrigerador', 4),
(5, 'Silla de oficina', 5),
(6, 'Muñeca', 6),
(7, 'Taladro', 7),
(8, 'Pelota de fútbol', 8),
(9, 'Refrigerador', 4),
(10, 'Silla de oficina', 5),
(11, 'Muñeca', 6),
(12, 'Taladro', 7),
(13, 'Pelota de fútbol', 8),
(14, 'Refrigerador', 4),
(15, 'Silla de oficina', 5),
(16, 'Muñeca', 6),
(17, 'Taladro', 7),
(18, 'Pelota de fútbol', 8),
(19, 'Refrigerador', 4),
(20, 'Silla de oficina', 5),
(21, 'Muñeca', 6),
(22, 'Refrigerador', 4),
(23, 'Silla de oficina', 5),
(24, 'Muñeca', 6),
(25, 'Refrigerador', 4),
(26, 'Silla de oficina', 5),
(27, 'Muñeca', 6),
(28, 'Taladro', 7),
(29, 'Pelota de fútbol', 8);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpg3xiei77fmdbpx20n8i9txs6` (`categoria_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `FKpg3xiei77fmdbpx20n8i9txs6` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
