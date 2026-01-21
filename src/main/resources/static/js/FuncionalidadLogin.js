var inputUsuario = document.getElementById('inputUsuario');
var inputContraseña = document.getElementById('inputContraseña');
var botonLogin = document.getElementById('Acceder');

function validarLogin(usuario, contraseña) {
	return usuario.trim() !== '' && contraseña.trim() !== '';
}

function limpiarFormulario() {
	inputUsuario.value = '';
	inputContraseña.value = '';
}

function enviarDatosLogin(usuario, contraseña) {
	var nombreCodificado = encodeURIComponent(usuario);
	var contraseñaCodificada = encodeURIComponent(contraseña);
	const response = fetch("http://localhost:8080/login?nombre=" + nombreCodificado + "&contra=" + contraseñaCodificada)
		.then(response => response.json())
		.then(resObj => {
			manejarRespuestaLogin(resObj);
		})
		.catch(error => {
			console.log('Error en la solicitud de login:', error);
		});
}

function manejarRespuestaLogin(Cliente) {
	if (Cliente != null) {
		console.log("funciona el login xd");
		window.location.href = "/html/PaginaPrincipal.html";
	}else{
		console.log("no funciona el login xd");
	}
}

function inicializarLogin() {
	var usuarioActual = inputUsuario.value;
	var contraseñaActual = inputContraseña.value;
	if (validarLogin(usuarioActual, contraseñaActual)) {

		enviarDatosLogin(usuarioActual, contraseñaActual);
	} else {
		alert("Por favor, complete todos los campos.");
	}

}

botonLogin.addEventListener('click', inicializarLogin);