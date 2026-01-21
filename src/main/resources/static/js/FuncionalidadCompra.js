document.addEventListener("DOMContentLoaded", () => {
    const idClienteLogueado = localStorage.getItem("clienteId");
    const nombreLogueado = localStorage.getItem("nombreUsuario") || "Usuario";

    const nombreElemento = document.getElementById("nombre-usuario");
    const inicialElemento = document.getElementById("user-initial");

    if (nombreElemento) {
        nombreElemento.innerText = nombreLogueado;
    }
    
    if (inicialElemento && nombreLogueado !== "Usuario") {
        inicialElemento.innerText = nombreLogueado.charAt(0).toUpperCase();
    }

    if (idClienteLogueado) {
        cargarMisReservas(idClienteLogueado);
    } else {
        console.error("No hay ID de cliente en localStorage");

    }
});
function cargarMisReservas(idCliente) {
    const contenedor = document.getElementById("contenedor-reservas");
    const contador = document.getElementById("contador-reservas");

    fetch(`http://localhost:8080/productos/misCompras?clienteId=${idCliente}`)
        .then(response => {
            if (!response.ok) throw new Error("Error al obtener las reservas");
            return response.json();
        })
        .then(productos => {
            contenedor.innerHTML = ""; // Limpiamos los esqueletos iniciales
            contador.innerText = `${productos.length} productos`;

            if (productos.length === 0) {
                const mensajeVacio = document.createElement("p");
                mensajeVacio.className = "col-span-full text-center py-10 text-gray-500 italic";
                mensajeVacio.innerText = "Aún no tienes ninguna zapatilla reservada.";
                contenedor.appendChild(mensajeVacio);
                return;
            }

            productos.forEach(prod => {
                // 1. Crear el contenedor principal de la tarjeta
                const tarjeta = document.createElement("div");
                tarjeta.className = "bg-white p-4 rounded-xl shadow-sm border border-slate-100 flex items-center gap-4 hover:shadow-md transition-shadow";

                // 2. Crear el contenedor de la imagen
                const divImagen = document.createElement("div");
                divImagen.className = "w-24 h-24 flex-shrink-0 bg-gray-50 rounded-lg overflow-hidden";

                const img = document.createElement("img");
                img.src = prod.urlImagen;
                img.alt = prod.nombreProd;
                img.className = "w-full h-full object-cover";
                
                divImagen.appendChild(img);

                // 3. Crear el contenedor de la información
                const divInfo = document.createElement("div");
                divInfo.className = "flex-1";

                const titulo = document.createElement("h3");
                titulo.className = "font-bold text-slate-800 text-lg";
                titulo.innerText = prod.nombreProd;

                const detalles = document.createElement("p");
                detalles.className = "text-sm text-slate-500";
                detalles.innerText = `${prod.marca} - Talla ${prod.talla}`;

                // 4. Crear la fila de precio y estado
                const divPrecioEstado = document.createElement("div");
                divPrecioEstado.className = "flex justify-between items-center mt-2";

                const precio = document.createElement("span");
                precio.className = "text-slate-900 font-extrabold";
                precio.innerText = `${prod.precioProd.toFixed(2)}$`;

                const etiquetaEstado = document.createElement("span");
                etiquetaEstado.className = "text-[10px] bg-green-100 text-green-700 px-2 py-1 rounded-md uppercase font-bold";
                etiquetaEstado.innerText = "Comprado";

                // 5. Ensamblar todas las piezas
                divPrecioEstado.appendChild(precio);
                divPrecioEstado.appendChild(etiquetaEstado);

                divInfo.appendChild(titulo);
                divInfo.appendChild(detalles);
                divInfo.appendChild(divPrecioEstado);

                tarjeta.appendChild(divImagen);
                tarjeta.appendChild(divInfo);

                // 6. Añadir la tarjeta completa al contenedor de la página
                contenedor.appendChild(tarjeta);
            });
        })
        .catch(error => {
            console.error("Error:", error);
            const errorMsg = document.createElement("p");
            errorMsg.className = "text-red-500 text-center col-span-full";
            errorMsg.innerText = "Error al conectar con el servidor.";
            contenedor.appendChild(errorMsg);
        });
}
function cerrarSesion() {
    localStorage.removeItem("clienteId");
    localStorage.removeItem("nombreUsuario");
    window.location.href = "../index.html"; 
}