package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteAdmin {
	static String servidor = "http://localhost:8080"; // cambiar
	static HttpClient cliente = HttpClient.newHttpClient();
	static HttpRequest peticion;
	static HttpResponse<String> respuesta = null;

	private static final AtomicInteger contadorId = new AtomicInteger(1);

	private static boolean Login(Scanner sc) {
		System.out.println("-- INICIE SESION COMO ADMINISTRADOR --");
		System.out.println("Introduzca usuario: ");
		String nombreAdmin = sc.nextLine();
		System.out.println("Introduzca contraseña: ");
		String contraseñaAdmin = sc.nextLine();

		String url = servidor + "/login?nombre=" + nombreAdmin.replace(" ", "%20") + "&contra=" + contraseñaAdmin;
		String json = endPoint(url);
		String esAdmin = extraerDato(json, "admin");

		if (esAdmin.equalsIgnoreCase("true")) {
			System.out.println("Acceso concedido. Bienvenido, " + nombreAdmin + ".");
			return true;
		} else {
			System.out.println("Acceso denegado: Este usuario no es Administrador o no existe.");
			return false;
		}

	}

	private static void mostrarMenu() {

		System.out.println("-- MENU TIENDA --");
		System.out.println("1. Gestionar zapatillas");
		System.out.println("2. Gestionar clientes");
		System.out.println("3. Salir");

	}

	private static void mostrarMenuZapatillas(Scanner sc) {
		int opcion;
		
		do {
			System.out.println("-- MENU TIENDA ZAPATILLAS --");
			System.out.println("1. Insertar zapatillas");
			System.out.println("2. Mostrar zapatillas");
			System.out.println("3. Actualizar zapatillas");
			System.out.println("4. Borrar zapatillas");
			System.out.println("5. Salir");
			

			opcion = leerEntero(sc, "Seleccione una opción: ");

			switch (opcion) {
			case 1 -> insertarZapatillas(sc);
			case 2 -> mostrarZapatillas();
			case 3 -> actualizarZapatillas(sc);
			case 4 -> borrarZapatillas(sc);
			case 5 -> {
			}
			default -> System.out.println("Opción no válida.");
			}

		} while (opcion != 5);

	}

	private static void mostrarMenuClientes(Scanner sc) {
		int opcion;
		
		do {
			System.out.println("-- MENU CLIENTES --");
			System.out.println("1. Insertar cliente");
			System.out.println("2. Mostrar clientes");
			System.out.println("3. Actualizar cliente");
			System.out.println("4. Borrar cliente");
			System.out.println("5. Volver");


			opcion = leerEntero(sc, "Seleccione una opción: ");

			switch (opcion) {
			case 1 -> insertarClientes(sc);
			case 2 -> mostrarClientes(sc);
			case 3 -> actualizarClientes(sc);
			case 4 -> borrarClientes(sc);
			case 5 -> {
			}
			default -> System.out.println("Opción no válida.");
			}

		} while (opcion != 5);
	}

	private static void inicio() {

		endPoint(servidor + "/crear");
		endPoint(servidor + "/productosIniciales");

	}

	private static String extraerDato(String json, String clave) {
		try {
			String busqueda = "\"" + clave + "\":";
			int inicio = json.indexOf(busqueda) + busqueda.length();
			// Si es un String tendrá comillas, si es número no
			if (json.charAt(inicio) == '\"') {
				inicio++;
				int fin = json.indexOf("\"", inicio);
				return json.substring(inicio, fin);
			} else {
				int fin = json.indexOf(",", inicio);
				if (fin == -1)
					fin = json.indexOf("}", inicio);
				return json.substring(inicio, fin).trim();
			}
		} catch (Exception e) {
			return "0";
		}
	}

	private static void insertarClientes(Scanner sc) {
		System.out.println("=== INSERTAR NUEVO CLIENTE ===");

		int id = calcularId();

		String nombre = leerTextoNoVacio(sc, "Introduzca el nombre: ");
		String apellido = leerTextoNoVacio(sc, "Introduzca el apellido: ");
		String contra = leerTextoNoVacio(sc, "Introduzca la contraseña: ");
		double saldo = leerDoubleConRango(sc, "Introduzca el saldo inicial: ", 0, 10000);
		boolean esAdmin = leerBoolean(sc, "¿Es administrador? (true/false): ");

		String parametros = "?id=" + id + "&nombre=" + nombre.replace(" ", "%20") + "&apellido="
				+ apellido.replace(" ", "%20") + "&contra=" + contra + "&saldo=" + saldo + "&admin=" + esAdmin;

		String respuesta = endPoint(servidor + "/clientes/añadir" + parametros);

		System.out.println("Cliente registrado: " + nombre + " " + apellido);

	}

	private static void mostrarClientes(Scanner sc) {
		System.out.println("=== MOSTRAR CLIENTES ===");

		String json = endPoint(servidor + "/clientes/listar");

		String visual = json.replace("},{", "}\n{").replace("[", "").replace("]", "");

		System.out.println(visual);

	}

	private static void actualizarClientes(Scanner sc) {
		System.out.println("=== ACTUALIZAR CLIENTE ===");

		System.out.print("Introduzca el ID del cliente a modificar: ");
		int id = sc.nextInt();
		sc.nextLine();

		String json = endPoint(servidor + "/clientes/id?id=" + id);

		String nombreActual = extraerDato(json, "nombre");
		String apellidoActual = extraerDato(json, "apellido");
		String contraActual = extraerDato(json, "contra");
		double saldoActual = Double.parseDouble(extraerDato(json, "saldo"));

		String nombre, apellido, contra;
		double saldo;

		System.out.println("Editando cliente: " + nombreActual + " " + apellidoActual);

		if (leerBoolean(sc, "¿Desea cambiar el NOMBRE? (true/false): ")) {
			nombre = leerTextoNoVacio(sc, "Nuevo nombre: ");
		} else {
			nombre = nombreActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar el APELLIDO? (true/false): ")) {
			apellido = leerTextoNoVacio(sc, "Nuevo apellido: ");
		} else {
			apellido = apellidoActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar la CONTRASEÑA? (true/false): ")) {
			contra = leerTextoNoVacio(sc, "Nueva contraseña: ");
		} else {
			contra = contraActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar el SALDO? (true/false): ")) {
			saldo = leerDoubleConRango(sc, "Nuevo saldo: ", 0, 1000000);
		} else {
			saldo = saldoActual;
		}

		String parametros = "?id=" + id + "&nombre=" + nombre.replace(" ", "%20") + "&apellido="
				+ apellido.replace(" ", "%20") + "&contra=" + contra + "&saldo=" + saldo;

		String respuesta = endPoint(servidor + "/clientes/actualizar" + parametros);

		System.out.println("Cliente " + nombre + " actualizado con éxito. ");
	}

	private static void borrarClientes(Scanner sc) {
		System.out.println("=== ELIMINAR CLIENTE ===");

		System.out.println("Introduzca el ID del cliente a eliminar: ");

		int idCliente = sc.nextInt();
		sc.nextLine();

		endPoint(servidor + "/clientes/borrar?id=" + idCliente);

		System.out.println("Cliente borrado con exito");

	}

	private static void borrarZapatillas(Scanner sc) {
		System.out.println("=== BORRAR ZAPATILLA ===");

		System.out.println("Introduzca el ID de la zapatilla a eliminar: ");

		int idProd = sc.nextInt();
		sc.nextLine();

		endPoint(servidor + "/productos/eliminar?id=" + idProd);

		System.out.println("Zapatilla borrada con éxito");
	}

	private static void actualizarZapatillas(Scanner sc) {
		System.out.println("=== ACTUALIZAR ZAPATILLA ===");

		System.out.print("Introduzca el ID de la zapatilla a modificar: ");
		int idProd = sc.nextInt();
		sc.nextLine();

		String json = endPoint(servidor + "/productos/id?id=" + idProd);

		String marcaActual = extraerDato(json, "marca");
		String nombreActual = extraerDato(json, "nombreProd");
		int tallaActual = Integer.parseInt(extraerDato(json, "talla"));
		double precioActual = Double.parseDouble(extraerDato(json, "precioProd"));
		String urlActual = extraerDato(json, "urlImagen");

		// Variables para los nuevos datos
		String marca = "", nombre = "", urlImagen = "";
		int talla = 0;
		double precio = 0.0;
		boolean reservado = false;

		System.out.println("Zapatilla : " + nombreActual + " (" + marcaActual + ")");

		// 2. Preguntas condicionales
		if (leerBoolean(sc, "¿Desea cambiar la MARCA? (true/false): ")) {
			marca = leerTextoNoVacio(sc, "Nueva marca: ");
		} else {
			marca = marcaActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar el NOMBRE? (true/false): ")) {
			nombre = leerTextoNoVacio(sc, "Nuevo nombre: ");
		} else {
			nombre = nombreActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar la TALLA? (true/false): ")) {
			talla = leerEnteroConRango(sc, "Nueva talla (36-49): ", 36, 49);
		} else {
			talla = tallaActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar el PRECIO? (true/false): ")) {
			precio = leerDoubleConRango(sc, "Nuevo precio: ", 0.1, 500);
		} else {
			precio = precioActual;
		}

		if (leerBoolean(sc, "¿Desea cambiar la URL de la IMAGEN? (true/false): ")) {
			urlImagen = leerTextoNoVacio(sc, "Nueva URL: ");
		} else {
			urlImagen = urlActual;
		}

		String parametros = "?id=" + idProd + "&marca=" + marca.replace(" ", "%20") + "&nombreProd="
				+ nombre.replace(" ", "%20") + "&talla=" + talla + "&precioProd=" + precio + "&reservado=" + reservado
				+ "&urlImagen=" + urlImagen.replace(" ", "%20");

		String respuesta = endPoint(servidor + "/productos/actualizar" + parametros);

		System.out.println("Producto " + nombre + "actualizado con exito.");
	}

	private static void mostrarZapatillas() {
		System.out.println("=== MOSTRAR ZAPATILLAS ===");

		// la llamada al endpoint devuelve un string en bruto
		String json = endPoint(servidor + "/listarProductos");

		String visual = json.replace("},{", "}\n{") // separara cada objeto
				.replace("[", "") // el inicio de la lista es un [ lo quitamos
				.replace("]", ""); // el fin igual

		System.out.println(visual);
	}

	private static void insertarZapatillas(Scanner sc) {
		System.out.println("=== INSERTAR ZAPATILLAS ===");

		int id = calcularId();
		String marca = leerTextoNoVacio(sc, "Introduzca la marca: ");
		String nombre = leerTextoNoVacio(sc, "Introduzca el modelo (nombre): ");
		String urlImagen = leerTextoNoVacio(sc, "Introduzca la URL de la imagen del producto: ");
		int talla = leerEnteroConRango(sc, "Introduzca la talla (36-49): ", 36, 49);
		double precioProd = leerDoubleConRango(sc, "Introduzca el precio de producción (0.1 - 300): ", 0.1, 300);

		Producto producto = new Producto(id, marca, nombre, talla, precioProd, urlImagen);

		String parametros = "?marca=" + marca.replace(" ", "%20") + "&nombreProd=" + nombre.replace(" ", "%20")
				+ "&talla=" + talla + "&precioProd=" + precioProd + "&reservado=false&urlImagen="
				+ urlImagen.replace(" ", "%20");

		endPoint(servidor + "/productos/añadir" + parametros);

		System.out.println("Zapatilla creada correctamente: " + producto);

	}

	private static int calcularId() {
		return contadorId.getAndIncrement();
	}

	private static String leerTextoNoVacio(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		String texto = sc.nextLine().trim();

		while (texto.isEmpty()) {
			System.out.println("El valor no puede estar vacío.");
			System.out.print(mensaje);
			texto = sc.nextLine().trim();
		}
		return texto;
	}

	private static int leerEntero(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextInt()) {
			System.out.println("Introduzca un número válido.");
			sc.next();
			System.out.print(mensaje);
		}
		int valor = sc.nextInt();
		sc.nextLine();
		return valor;
	}

	private static int leerEnteroConRango(Scanner sc, String mensaje, int min, int max) {
		int valor = leerEntero(sc, mensaje);
		while (valor < min || valor > max) {
			System.out.printf("El valor debe estar entre %d y %d.%n", min, max);
			valor = leerEntero(sc, mensaje);
		}
		return valor;
	}

	private static double leerDoubleConRango(Scanner sc, String mensaje, double min, double max) {
		double valor = leerDouble(sc, mensaje);
		while (valor < min || valor > max) {
			System.out.printf("El valor debe estar entre %.2f y %.2f.%n", min, max);
			valor = leerDouble(sc, mensaje);
		}
		return valor;
	}

	private static double leerDouble(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextDouble()) {
			System.out.println("Introduzca un número válido.");
			sc.next();
			System.out.print(mensaje);
		}
		double valor = sc.nextDouble();
		sc.nextLine();
		return valor;
	}

	private static boolean leerBoolean(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextBoolean()) {
			System.out.println("Introduzca true o false.");
			sc.next();
			System.out.print(mensaje);
		}
		boolean valor = sc.nextBoolean();
		sc.nextLine();
		return valor;
	}

	private static String endPoint(String url) {
		String contenido = null;
		try {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			contenido = respuesta.body();
//			System.out.println(contenido);
		} catch (ConnectException e) { // si hay error de conexión
			System.out.println("ERROR DE CONEXIÓN. Intenta entrar más tarde");
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return contenido;
	}

	// Método que hace una primera llamada para comprobar la conexión
	private static void conexion() {
		// Llama al método conexión del servidor
		String url = String.format(servidor + "/conexion");
		System.out.println(endPoint(url));
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int opcion;
		int contador = 0;
		conexion();

		if (contador == 0) {
			inicio();
			contador++;
		}

		while (true) {
			boolean autenticado = false;

			while (!autenticado) {
				autenticado = Login(sc);
				if (!autenticado) {
					System.out.println("Intente de nuevo.");
				}
			}

			do {
				mostrarMenu();
				opcion = leerEntero(sc, "Seleccione una opción: ");

				switch (opcion) {
				case 1 -> mostrarMenuZapatillas(sc);
				case 2 -> mostrarMenuClientes(sc);
				case 3 -> System.out.println("Cerrando sesión... Volviendo al Login.");
				default -> System.out.println("Opción no válida.");
				}
				System.out.println();

			} while (opcion != 3);
		}
	}
}