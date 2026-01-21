package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@SpringBootApplication
@RestController
public class ProyectoUd5Application {

	private final JdbcTemplate jdbcTemplate;

	public ProyectoUd5Application(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/conexion")
	public String conexion() {
		return "Conexión establecida";
	}

	// Creacion de tablas e insert del admin
	@GetMapping("/crear")
	public String crearTabla() {
		// tabla de clientes
		jdbcTemplate.execute("DROP TABLE IF EXISTS clientes");
		jdbcTemplate.execute("DROP TABLE IF EXISTS productos");
		jdbcTemplate.execute("CREATE TABLE clientes (" + "id SERIAL PRIMARY KEY, " + "nombre VARCHAR(60), "
				+ "apellido VARCHAR(60), " + "contra VARCHAR(60), " + "saldo DOUBLE, "
				+ "admin BOOLEAN DEFAULT FALSE)");
		// tabla de productos
		jdbcTemplate.execute("CREATE TABLE productos (" + "id SERIAL PRIMARY KEY, " + "nombreProd VARCHAR(60), "
				+ "marca VARCHAR(60), " + "talla INT, " + "precioProd DOUBLE, " + "reservado BOOLEAN DEFAULT FALSE, "
				+ "urlImagen VARCHAR(255))");

		// creacion del primer administrador
		jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?,?,?,?,?)",
				"admin1", "-", "admin1", 0.0, true);

		return ("Base de datos creada correctamente!");
	}

	// Inserts de las zapatillas que hay en stock con imagenes
	@GetMapping("/productosIniciales")
	public String insertarProductosIniciales() {

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Nike Air Force 1", "Nike", 42, 110.0, false, "/img/nikeAirForce1.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Nike Dunk Low", "Nike", 41, 115.0, false, "/img/nikeDunkLow.webp");
		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Adidas Campus x Bad Bunny", "Adidas", 42, 160.0, false, "/img/adidasCampusBadBunny.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Adidas Yeezy Boost 350 V2", "Adidas", 43, 220.0, false, "/img/adidasYeezyBoost350V2.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Air Force 1 Low NOCTA Drake", "Nike", 42, 180.0, false, "/img/airForce1LowNoctaDrake.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Air Force 1 Black Rope Laces", "Nike", 41, 125.0, false, "/img/airForce1BlackRopeLaces.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Timberland Premium 6 Inch Lace Up", "Timberland", 43, 190.0, false,
				"/img/timberlandPremium6InchLaceUp.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Air Jordan 1 Retro Low OG SP Travis Scott", "Nike", 44, 240.0, false,
				"/img/airJordan1RetroLowOGSPTravisScott.webp");
//------------------------------------
		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Crocs Classic Clog Marvel Spider-Man", "Crocs", 42, 65.0, false,
				"/img/crocsClassicClogMarvelSpiderMan.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Crocs Classic Clog Mater Kids", "Crocs", 36, 55.0, false, "/img/crocsClassicClogMaterKids.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Asics Gel-NYC White Oyster Grey", "Asics", 42, 150.0, false, "/img/asicsGelNYCWhiteOysterGrey.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Asics Gel-Kayano 14 Cream Sweet Pink", "Asics", 41, 160.0, false,
				"/img/asicsGelKayano14CreamSweetPink.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Jordan 4 Retro OG SP Undefeated (2025)", "Nike", 44, 300.0, false,
				"/img/jordan4RetroOGSPUndefeated.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Jordan 4 Retro Black Cat (2025)", "Nike", 43, 280.0, false, "/img/jordan4RetroBlackCat.webp");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Jordan 1 Retro High OG UNC Reimagined", "Nike", 42, 210.0, false,
				"/img/jordan1RetroHighOGUNCReimagined.webp");

		return "Productos iniciales insertados correctamente (10 zapatillas)";
	}

	// endpoint del registro de un cliente (he puesto el admin como false
	@GetMapping("/registro")
	public int registrarCliente(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "apellido", defaultValue = "-") String apellido,
			@RequestParam(value = "contra", defaultValue = "-") String contrasenya,
			@RequestParam(value = "saldo", defaultValue = "0") double saldo) {
		return jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?,?,?,?,?)",
				nombre, apellido, contrasenya, saldo, false);
	}

	// registro para la consola (ADMIN)
	@GetMapping("/registroConsola")
	public int registrarClienteConsola(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "apellido", defaultValue = "-") String apellido,
			@RequestParam(value = "contra", defaultValue = "-") String contrasenya,
			@RequestParam(value = "saldo", defaultValue = "0") double saldo) {
		return jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?,?,?,?,?)",
				nombre, apellido, contrasenya, saldo, true);
	}

	// El mapper para devolver las zapatillas
	public class ProductoMapper implements RowMapper<Producto> {
		@Override
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Producto(rs.getInt("id"), rs.getString("nombreProd"), rs.getString("marca"), rs.getInt("talla"),
					rs.getDouble("precioProd"), rs.getBoolean("reservado"), rs.getString("urlImagen"));
		}
	}

	// mapper de cliente

	public class ClienteMapper implements RowMapper<Cliente> {
		@Override
		public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("contra"), rs.getDouble("saldo"), rs.getBoolean("admin"));
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
			@RequestParam(value = "contra", defaultValue = "-") String contraseña) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM clientes WHERE nombre = ? AND contra = ?",
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
	public List<Producto> busquedaNombre(@RequestParam(value = "nombreProd", defaultValue = "-") String nombre) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE nombreProd LIKE ? AND reservado = false",
					new ProductoMapper(), "%" + nombre + "%");
		} catch (Exception e) {
			System.out.println("Error al buscar por nombre");
			return null;
		}
	}

	// busqueda de producto por id
	// endpoint para obtener un producto por ID
	@GetMapping("/productos/id")
	public Producto obtenerProductoPorId(@RequestParam(value = "id") int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM productos WHERE id = ?", new ProductoMapper(), id);
		} catch (Exception e) {
			System.out.println("Producto no encontrado con id: " + id);
			return null;
		}
	}

	// endpoint para ver las tallas disponibles de una zapatilla
	@GetMapping("/productos/tallasDisponibles")
	public List<Integer> obtenerTallasDisponibles(@RequestParam(value = "nombreProd") String nombreProd) {

		String sql = "SELECT DISTINCT talla " + "FROM productos " + "WHERE nombreProd = ? " + "AND reservado = false "
				+ "ORDER BY talla";

		return jdbcTemplate.queryForList(sql, Integer.class, nombreProd);
	}

	// ednpoint para ver marcas disponibles
	@GetMapping("/productos/marcasDisponibles")
	public List<String> obtenerMarcasDisponibles() {
		String sql = "SELECT DISTINCT marca FROM productos WHERE reservado = false";
		return jdbcTemplate.queryForList(sql, String.class);
	}

	// ahora el crud de clientes que solo se va a usar en consola
	// endpoint que lista a los clientes para la consola
	@GetMapping("/clientes/listar")
	public List<Cliente> listarClientes() {
		return jdbcTemplate.query("SELECT * FROM clientes", new ClienteMapper());
	}

	// endpoint para actualizar un cliente que solo se podra hacer por consola
	@GetMapping("/clientes/añadir")
	public int añadirCliente(@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "apellido") String apellido, @RequestParam(value = "contra") String contra,
			@RequestParam(value = "saldo") double saldo, @RequestParam(value = "admin") boolean admin) {
		return jdbcTemplate.update(
				"INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?, ?, ?, ?, ?)", nombre,
				apellido, contra, saldo, admin);
	}

	// endpoint para obtener el cliente desde la consola por ID
	@GetMapping("/clientes/id")
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
			@RequestParam(value = "apellido") String apellido, @RequestParam(value = "contra") String contrasenya,
			@RequestParam(value = "saldo") double saldo) {
		return jdbcTemplate.update("UPDATE clientes SET nombre = ?, apellido = ?, contra = ?, saldo = ? WHERE id = ?",
				nombre, apellido, contrasenya, saldo, id);
	}

	// endpoint de eliminar un cliente que solo lo podra hacer el admin desde la
	// consola
	@GetMapping("/clientes/borrar")
	public int borrarCliente(@RequestParam(value = "id") int id) {
		return jdbcTemplate.update("DELETE FROM clientes WHERE id = ?", id);
	}

	public static void main(String[] args) {
		// Proyecto UD5
		SpringApplication.run(ProyectoUd5Application.class, args);
	}

	// endpoints para el admin
	@GetMapping("/productos/añadir")
	public String añadirProducto(@RequestParam(value = "nombreProd") String nombre,
			@RequestParam(value = "marca") String marca, @RequestParam(value = "talla") int talla,
			@RequestParam(value = "precioProd") double precioProd,
			@RequestParam(value = "urlImagen") String urlImagen) {
		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				nombre, marca, talla, precioProd, false, urlImagen);

		return "Producto '" + nombre + "' añadido correctamente";
	}

	@GetMapping("/productos/actualizar")
	public String editarProducto(@RequestParam(value = "id") int idProd,
			@RequestParam(value = "nombreProd") String nombre, @RequestParam(value = "marca") String marca,
			@RequestParam(value = "talla") int talla, @RequestParam(value = "precioProd") double precioProd,
			@RequestParam(value = "reservado") boolean reservado, @RequestParam(value = "urlImagen") String urlImagen) {
		jdbcTemplate.update(
				"UPDATE productos SET nombreProd = ?, marca = ?, talla = ?, precioProd = ?, reservado = ?, urlImagen = ? WHERE id = ?",
				nombre, marca, talla, precioProd, reservado, urlImagen, idProd);

		return "Producto '" + nombre + "' actualizado correctamente";
	}

	@GetMapping("/productos/eliminar")
	public String eliminarProducto(@RequestParam(value = "id") String idProd) {

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

	// endpoint para el filtro
	@GetMapping("/productos/filtro")
	public List<Producto> filtrarProductos(@RequestParam(value = "marca", defaultValue = "%") String marca,
			@RequestParam(value = "nombreProd", defaultValue = "%") String nombre,
			@RequestParam(value = "talla", defaultValue = "0") int talla,
			@RequestParam(value = "precioMax", defaultValue = "9999") double precioMax) {

		String sql = """
				SELECT * FROM productos
				WHERE reservado = false
				AND marca LIKE ?
				AND nombreProd LIKE ?
				AND (? = 0 OR talla = ?)
				AND precioProd <= ?
				""";

		return jdbcTemplate.query(sql, new ProductoMapper(), marca, "%" + nombre + "%", talla, talla, precioMax);
	}

}