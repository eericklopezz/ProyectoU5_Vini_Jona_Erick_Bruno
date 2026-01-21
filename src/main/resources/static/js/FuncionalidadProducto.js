const idCliente = localStorage.getItem("clienteId");
const nombreUsuario = localStorage.getItem("nombreUsuario") || "Usuario";

document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const idProducto = params.get("id");
	
    if (idProducto) {
        cargarDatosProducto(idProducto);
        cargarTallas(idProducto);
        
        const boton = document.getElementById("botonComprar");
        if (boton) {
            boton.addEventListener("click", () => comprar(idProducto));
        }
    }
});

function cargarDatosProducto(id) {
    fetch("http://localhost:8080/productos/id?id=" + encodeURIComponent(id))
        .then(response => response.json())
        .then(producto => {
            document.getElementById("producto-titulo").innerText = producto.nombreProd;
            document.getElementById("breadcrumb-nombre").innerText = producto.nombreProd;
            document.getElementById("producto-precio").innerText = producto.precioProd + "$";

            const imgPrincipal = document.getElementById("producto-imagen");
            imgPrincipal.src = producto.urlImagen;
            imgPrincipal.alt = producto.nombreProd;

            const miniaturas = document.querySelectorAll(".grid-cols-4 img");
            miniaturas.forEach(m => m.src = producto.urlImagen);
        })
        .catch(error => console.error("Error cargando producto:", error));
}

function cargarTallas(id) {
    const contenedorTallas = document.getElementById("contenedor-tallas");
    contenedorTallas.innerHTML = "";
	mensajeError.classList.add('hidden');
    fetch("http://localhost:8080/productos/tallasDisponibles?id=" + encodeURIComponent(id))
        .then(response => response.json())
        .then(tallas => {
            for (var i = 0; i < tallas.length; i++) {
                const botonTalla = document.createElement("button");
                botonTalla.innerText = tallas[i];
                botonTalla.className = "border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200";
                contenedorTallas.appendChild(botonTalla);
            }
        })
        .catch(error => console.error("Error cargando tallas:", error));
}

function comprar(idProducto) {
    if (!idCliente) {
        alert("Debes iniciar sesión compai");
        return;
    }
	
    fetch("http://localhost:8080/comprar?clienteId=" + idCliente + "&productoId=" + idProducto)
        .then(response => response.text())
        .then(mensaje => {
            if (mensaje == "Compra realizada correctamente") {
                window.location.href = "/html/PaginaCompra.html";
            }else if(mensaje == "Saldo insuficiente"){
				mostrarError("Saldo insuficiente");
			}else {
                console.log("Algo fue mal bro: " + mensaje);
            }
        })
        .catch(error => {
            console.error("Error en la compra:", error);
            alert("Error de conexión con el servidor");
        });
}

function mostrarError(texto) {
    mensajeError.innerText = texto;
    mensajeError.classList.remove('hidden');
}