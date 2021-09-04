function descargaCsv(archivoCsv, nombreArchivo) {
    var csvFile;
    var downloadLink;
    csvFile = new Blob([archivoCsv], {type: "text/csv"});//establecemos el tipo de archivo
    downloadLink = document.createElement("a");//preparamos la descarga
    downloadLink.download = nombreArchivo;//le damos nombre al linck de descarga
    downloadLink.href = window.URL.createObjectURL(csvFile);//cramos el link de descarga
    downloadLink.style.display = "none";
    document.body.appendChild(downloadLink);//anadimos el link al DOM
    downloadLink.click();//le damos un click al link de descarga
}
function crearCsv(nombreArchivo) {
    var archivo = [];
    var filas = document.querySelectorAll("table tr");//obtenemos  todas las lineas que estan dentro de la tabla
    for (var i = 0; i < filas.length; i++) { //exporamos la variable filas
        var fila = []; //creamos un arreglo nuevo que sera cada fila separada
        var columna = filas[i].querySelectorAll("td, th"); //obtenemos los valores que estan dentro de las etiquetas td y th
                                                        //y las agregamos pues son las columnas
        for (var j = 0; j < columna.length; j++) {//vemos el valor que esta dentro de columna
            fila.push(columna[j].innerText); //agregamos la palabra a la fila
        }
        archivo.push(fila.join(","));//separamos la parabla anterior con ","
    }
    //mandamos a descargar el nuevo archivo CSV
    descargaCsv(archivo.join("\n"), nombreArchivo); //con el join creamos nuevos registros donde haya un esoacio en blanco
}


