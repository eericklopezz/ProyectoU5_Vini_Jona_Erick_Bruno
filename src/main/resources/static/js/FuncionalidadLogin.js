var inputUsuario = document.getElementById('inputUsuario');
var inputContraseña = document.getElementById('inputContraseña');
var botonLogin = document.getElementById('Acceder');
const mensajeError = document.getElementById('mensajeError');

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
    
    mensajeError.classList.add('hidden');

    fetch("http://localhost:8080/login?nombre=" + nombreCodificado + "&contra=" + contraseñaCodificada)
        .then(response => {
            // Verificamos si la respuesta es correcta
            if (!response.ok) throw new Error("Error en el servidor");
            return response.text();
        })
        .then(text => {
			if (!text || text.trim() === "" || text === "null") {
                manejarRespuestaLogin(null);
            } else {
                const resObj = JSON.parse(text);
                manejarRespuestaLogin(resObj);
            }
        })
        .catch(error => {
            console.log('Error en la solicitud:', error);
            mostrarError("Error al conectar con el servidor.");
        });
}

function manejarRespuestaLogin(Cliente) {
	if (Cliente && Cliente.id) {
		localStorage.setItem("clienteId", Cliente.id);
		            localStorage.setItem("nombreUsuario", Cliente.nombre);
		console.log("funciona el login xd");
		window.location.href = "/html/PaginaPrincipal.html";
	}else{
		mostrarError("Los datos no coinciden con ninguna cuenta.");
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
function mostrarError(texto) {
    mensajeError.innerText = texto;
    mensajeError.classList.remove('hidden');
}

botonLogin.addEventListener('click', inicializarLogin);