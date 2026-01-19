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

	// registro para la consola (ADMIN)
	@GetMapping("/registroConsola")
	public int registrarClienteConsola(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "apellido", defaultValue = "-") String apellido,
			@RequestParam(value = "contraseña", defaultValue = "-") String contrasenya,
			@RequestParam(value = "saldo", defaultValue = "0") double saldo) {
		return jdbcTemplate.update(
				"INSERT INTO clientes (nombre, apellido, contraseña, saldo, admin) VALUES (?,?,?,?,?)", nombre,
				apellido, contrasenya, saldo, true);
	}

	// El mapper para devolver las zapatillas
	public class ProductoMapper implements RowMapper<Producto> {
		@Override
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Producto(rs.getInt("id"), rs.getString("nombreProd"), rs.getString("marca"), rs.getInt("talla"),
					rs.getDouble("precioProd"), rs.getBoolean("reservado"));
		}
	}

	// mapper de cliente

	public class ClienteMapper implements RowMapper<Cliente> {
		@Override
		public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("contraseña"), rs.getDouble("saldo"), rs.getBoolean("admin"));
		}
	}

	// endpoint que lista los productos para la web usando el mapper
	@GetMapping("/listarProductos")
	public List<Producto> listarProductos() {
		return jdbcTemplate.query("SELECT * FROM productos", new ProductoMapper());
	}

	// endpoint para el login
	@GetMapping("/login")
	public Cliente login(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "contraseña", defaultValue = "-") String contraseña) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM clientes WHERE nombre = ? AND contraseña = ?",
					new ClienteMapper(), nombre, contraseña);

		} catch (Exception e) {
			System.out.println("Error en el proceso del Login");
			return null;
		}

	}

	// busqueda por talla
	@GetMapping("/productos/talla")
	public List<Producto> busquedaTalla(@RequestParam(value = "talla", defaultValue = "-") int talla) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE talla = ? AND reservado = false",
					new ProductoMapper(), talla);
		} catch (Exception e) {
			System.out.println("Error al buscar por talla");
			return null;
		}
	}

	// busqueda por marca
	@GetMapping("/productos/marca")
	public List<Producto> busquedaMarca(@RequestParam(value = "marca", defaultValue = "-") String marca) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE marca = ? AND reservado = false",
					new ProductoMapper(), marca);

		} catch (Exception e) {
			System.out.println("Error al buscar por marca");
			return null;
		}

	}

	// busqueda por precio max NO SE SI ESTE ESTA BIEN DEL TODO
	@GetMapping("/productos/precio")
	public List<Producto> busquedaPrecio(@RequestParam(value = "precioMax", defaultValue = "130") double precioMax) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE precioProd <= ? AND reservado = false",
					new ProductoMapper(), precioMax);
		} catch (Exception e) {
			System.out.println("Error al buscar por precio");
			return null;
		}
	}

	// busqueda por nombre
	@GetMapping("/productos/nombre")
	public List<Producto> busquedaNombre(@RequestParam(value = "nombre", defaultValue = "-") String nombre) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE nombreProd LIKE ? AND reservado = false",
					new ProductoMapper(), "%" + nombre + "%");
		} catch (Exception e) {
			System.out.println("Error al buscar por nombre");
			return null;
		}
	}

	// ahora el crud de clientes que solo se va a usar en consola
	// endpoint que lista a los clientes para la consola
	@GetMapping("/clientes/listar")
	public List<Cliente> listarClientes() {
		return jdbcTemplate.query("SELECT * FROM clientes", new ClienteMapper());
	}

	// endpoint para obtener el cliente desde la consola por ID
	@GetMapping("/clientes/{id}")
	public Cliente obtenerClientePorId(@RequestParam(value = "id") int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM clientes WHERE id = ?", new ClienteMapper(), id);
		} catch (Exception e) {
			System.out.println("Cliente no encontrado");
			return null;
		}
	}

	// endpoint para actualizar un cliente que solo se podra hacer por consola
	@GetMapping("/clientes/actualizar")
	public int actualizarCliente(@RequestParam(value = "id") int id, @RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "apellido") String apellido, @RequestParam(value = "contraseña") String contrasenya,
			@RequestParam(value = "saldo") double saldo) {
		return jdbcTemplate.update(
				"UPDATE clientes SET nombre = ?, apellido = ?, contraseña = ?, saldo = ? WHERE id = ?", nombre,
				apellido, contrasenya, saldo, id);
	}

	// endpoint de eliminar un cliente que solo lo podra hacer el admin desde la
	// consola
	@GetMapping("/clientes/borrar/{id}")
	public int borrarCliente(@RequestParam(value = "id") int id) {
		return jdbcTemplate.update("DELETE FROM clientes WHERE id = ?", id);
	}

	public static void main(String[] args) {
		// Proyecto UD5
		SpringApplication.run(ProyectoUd5Application.class, args);
	}

	// endpoints para el admin
	@GetMapping("/productos/añadir")
	public String añadirProduto(@RequestParam(value = "nombreProd") String nombre,
			@RequestParam(value = "marca") String marca, @RequestParam(value = "talla") int talla,
			@RequestParam(value = "precioProd") double precioProd) {

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado) VALUES (?,?,?,?,?)", nombre,
				marca, talla, precioProd, false);

		return "Producto '" + nombre + "' añadido correctamente";
	}

	@GetMapping("/productos/actualizar/{id}")
	public String editarProducto(@RequestParam(value = "idProd") int idProd,
			@RequestParam(value = "nombreProd") String nombre, @RequestParam(value = "marca") String marca,
			@RequestParam(value = "talla") int talla, @RequestParam(value = "precioProd") double precioProd,
			@RequestParam(value = "reservado") boolean reservado) {

		jdbcTemplate.update(
				"UPDATE productos SET nombre = ?, marca = ?, talla = ?, precioProd= ?, reservado = ? WHERE id = ?",
				nombre, marca, talla, precioProd, reservado, idProd);

		return "Producto '" + nombre + "' actualizado correctamente";
	}

	@GetMapping("/productos/eliminar/{id}")
	public String eliminarProducto(@RequestParam(value = "idProd") String idProd) {

		jdbcTemplate.update("DELETE FROM productos WHERE id = ?", idProd);

		return "Producto eliminado correctamente";
	}

	// endpoints para el filtro
	@GetMapping("/productos/disponibles")
	public List<Producto> productoDispo(@RequestParam(value = "reservado", defaultValue = "false") boolean reservado) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE reservado = ?", new ProductoMapper(), reservado);
		} catch (Exception e) {
			System.out.println("Error en la busqueda de productos disponibles");
			return null;
		}

	}

}
