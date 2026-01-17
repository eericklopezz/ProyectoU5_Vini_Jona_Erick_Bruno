package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProyectoUd5Application {

	private final JdbcTemplate jdbcTemplate;

	public ProyectoUd5Application(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	// Creacion de tablas e insert del admin
	@GetMapping("/crear")
	public String crearTabla() {
		// tabla de clientes
		jdbcTemplate.execute("DROP TABLE IF EXISTS clientes");
		jdbcTemplate.execute("DROP TABLE IF EXISTS productos");
		jdbcTemplate.execute("CREATE TABLE clientes (" + "id SERIAL PRIMARY KEY, " + "nombre VARCHAR(60), "
				+ "apellido VARCHAR(60), " + "contraseña VARCHAR(60), " + "saldo DOUBLE, "
				+ "admin BOOLEAN DEFAULT FALSE)");
		// tabla de productos
		jdbcTemplate.execute("CREATE TABLE productos (" + "id SERIAL PRIMARY KEY, " + "nombreProd VARCHAR(60), "
				+ "marca INT, " + "talla INT, " + "precioProd DOUBLE, " + "reservado BOOLEAN DEFAULT FALSE)");

		// creacion del primer administrador
		jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contraseña, saldo, admin) VALUES (?,?,?,?,?)",
				"admin1", "-", "admin1", 0.0, true);

		return ("Base de datos creada correctamente!");
	}

	// Inserts de las zapatillas que hay en stock
	@GetMapping("/productosIniciales")
	public String insertarProductosIniciales() {

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado) VALUES (?,?,?,?,?)",
				"Nike Air Force 1", 1, 42, 110.0, false);

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado) VALUES (?,?,?,?,?)",
				"Adidas Stan Smith", 2, 41, 95.0, false);

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado) VALUES (?,?,?,?,?)",
				"Puma RS-X", 3, 43, 120.0, false);

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado) VALUES (?,?,?,?,?)",
				"New Balance 550", 4, 44, 130.0, false);

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado) VALUES (?,?,?,?,?)",
				"Nike Dunk Low", 1, 42, 115.0, false);

		return "Productos iniciales insertados correctamente";
	}

	// endpoint del registro de un cliente (he puesto el admin como false
	@GetMapping("/registro")
	public int registrarCliente(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "apellido", defaultValue = "-") String apellido,
			@RequestParam(value = "contraseña", defaultValue = "-") String contrasenya,
			@RequestParam(value = "saldo", defaultValue = "0") double saldo) {
		return jdbcTemplate.update(
				"INSERT INTO clientes (nombre, apellido, contraseña, saldo, admin) VALUES (?,?,?,?,?)", nombre,
				apellido, contrasenya, saldo, false);
	}

	// El mapper para devolver las zapatillas
	public class ProductoMapper implements RowMapper<Producto> {
		@Override
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Producto(rs.getInt("id"), rs.getString("nombreProd"), rs.getInt("marca"), rs.getInt("talla"),
					rs.getDouble("precioProd"), rs.getBoolean("reservado"));
		}
	}

	// endpoint que lista los productos para la web usando el mapper
	@GetMapping("/listarProductos")
	public List<Producto> listarProductos() {
		return jdbcTemplate.query("SELECT * FROM productos", new ProductoMapper());
	}

	public static void main(String[] args) {
		// Proyecto UD5
		SpringApplication.run(ProyectoUd5Application.class, args);
	}

}
