
class Persona {
    constructor(idAlumno, codigo,nombreCompleto, sexo,escuelaProfesional, pps, comedor, estado) {
      this.idAlumno = idAlumno || '';
      this.codigo = codigo || '';
      this.nombreCompleto = nombreCompleto || '';
      this.sexo = sexo || '';
      this.escuelaProfesional = escuelaProfesional || '';
      this.pps = pps || '';
      this.comedor = comedor || '';   
      this.estado = estado || '';
    }
}

class Unasino{
    constructor(dni, ap_paterno, ap_materno, nombre, escuela_profesional, correo_institucional) {
      this.dni = dni;
      this.ap_paterno = ap_paterno;
      this.ap_materno = ap_materno;
      this.nombre = nombre;
      this.escuela_profesional = escuela_profesional;
      this.correo_institucional = correo_institucional;
    }
  }


let datosPersonas = [
  {idAlumno: 1, codigo: 20210619, nombreCompleto: 'ABURTO YSUIZA, JANIRA JAMILETH', sexo: 'F', escuelaProfesional: 'ADMINISTRACION', comedor: 'SI', estado: 'ESTUDIANTE', pps: 14.53} ,
  {idAlumno: 2, codigo: 20200121, nombreCompleto: 'ACOSTA ARELLANO, CECILIA JESSICA', sexo: 'F', escuelaProfesional: 'ADMINISTRACION', comedor: 'SI', estado: 'ESTUDIANTE', pps: 14.9} ,
  {idAlumno: 3, codigo: 20210277, nombreCompleto: 'ACOSTA HUAMAN, JAMIR ABEL', sexo: 'M', escuelaProfesional: 'INGENIERIA MECANICA ELECTRICA', comedor: 'SI', estado: 'ESTUDIANTE', pps: 12.43},
  {idAlumno: 4, codigo: 20210312, nombreCompleto: 'ACOSTA LAVI, JHONATAN PATRICIO', sexo: 'M', escuelaProfesional: 'ZOOTECNIA', comedor: 'SI', estado: 'ESTUDIANTE', pps: 11.92},
  {idAlumno: 5, codigo: 20230116, nombreCompleto: 'AGUILAR PEÑA, ANTHONY ISAI', sexo: 'M', escuelaProfesional: 'CONTABILIDAD', comedor: 'SI', estado: 'INGRESANTE', pps: ""},
  {idAlumno: 6, codigo: 20220238, nombreCompleto: 'AGUIRRE ANDRADE, HEISEMBERG JESUS', sexo: 'M', escuelaProfesional: 'INGENIERIA EN RECURSOS NATURALES RENOVABLES', comedor: 'SI', estado: 'ESTUDIANTE', pps: 12.7},
  {idAlumno: 7, codigo: 20170273, nombreCompleto: 'AGUIRRE VALENZUELA, JHAMLET', sexo: 'M', escuelaProfesional: 'AGRONOMIA', comedor: 'SI', estado: 'ESTUDIANTE', pps: 11.8},
  {idAlumno: 8, codigo: 20210314, nombreCompleto: 'ALANIA MALMA, FERNANDO KEID', sexo: 'M', escuelaProfesional: 'INGENIERIA EN CONSERVACION DE SUELOS Y AGUA', comedor: 'SI', estado: 'ESTUDIANTE', pps: 11.67},
  {idAlumno: 9, codigo: 20180235, nombreCompleto: 'ALANIA RAMOS, JACOB ENOC', sexo: 'M', escuelaProfesional: 'INGENIERIA MECANICA ELECTRICA', comedor: 'SI', estado: 'ESTUDIANTE', pps: 12.25},
  {idAlumno: 10, codigo: 20220434, nombreCompleto: 'ALANIA REYES, MELANY', sexo: 'F', escuelaProfesional: 'INGENIERIA EN CONSERVACION DE SUELOS Y AGUA', comedor: 'SI', estado: 'ESTUDIANTE', pps: 13.77},
  {idAlumno: 11, codigo: 20220435, nombreCompleto: 'ALANIA TRINIDAD, YORDIN ROSSEL', sexo: 'M', escuelaProfesional: 'INGENIERIA AMBIENTAL', comedor: 'SI', estado: 'ESTUDIANTE', pps: 13.82},
];

let datosUnasinos = [];

let tempPersona = {};

let escuelaProfesional = [
  'NINGUNO',
  'ADMINISTRACION',
  'AGRONOMIA',
  'CONTABILIDAD',
  'ECONOMIA',
  'INGENIERIA AMBIENTAL',
  'INGENIERIA EN CONSERVACION DE SUELOS Y AGUA',
  'INGENIERIA EN RECURSOS NATURALES RENOVABLES',
  'INGENIERIA EN INDUSTRIAS ALIMENTARIAS',
  'INGENIERIA EN INFORMATICA Y SISTEMAS',
  'INGENIERIA FORESTAL',
  'INGENIERIA MECANICA ELECTRICA',
  'ZOOTECNIA',
]

function mostrarPanel(booleano){
  const panel = document.getElementById('panel');
  if(booleano){
    
    const spans = document.getElementsByTagName('span');
    // Convertir el HTMLCollection en un array para facilitar el recorrido
    const spansArray = Array.from(spans);
    // Eliminar cada span del array
    spansArray.forEach(span => span.remove());

    panel.classList.add("visible");
        
    const numeroCelda = document.createElement('span');
    numeroCelda.textContent =  1;
        
    const codigoCelda = document.createElement('span');
    codigoCelda.textContent = tempPersona.codigo;

    const nombresApellidosCelda = document.createElement('span');
    nombresApellidosCelda.textContent = tempPersona.nombreCompleto;

    const sexoCelda = document.createElement('span');
    sexoCelda.textContent = tempPersona.sexo;

    const escuelaProfesionalCelda = document.createElement('span');
    escuelaProfesionalCelda.textContent = tempPersona.escuelaProfesional;

    const promedioPonderadoCelda = document.createElement('span');
    promedioPonderadoCelda.textContent = tempPersona.pps;

    const comedorCelda = document.createElement('span');
    comedorCelda.textContent = tempPersona.comedor;

    const estadoCelda = document.createElement('span');
    estadoCelda.textContent = tempPersona.estado;

    panel.appendChild(numeroCelda);
    panel.appendChild(codigoCelda);
    panel.appendChild(nombresApellidosCelda);
    panel.appendChild(sexoCelda);
    panel.appendChild(escuelaProfesionalCelda);
    panel.appendChild(promedioPonderadoCelda);
    panel.appendChild(comedorCelda);
    panel.appendChild(estadoCelda);
    
    tempPersona = {};
  }else{
    panel.classList.remove("visible");
     
    const spans = document.getElementsByTagName('span');
    // Convertir el HTMLCollection en un array para facilitar el recorrido
    const spansArray = Array.from(spans);
    // Eliminar cada span del array
    spansArray.forEach(span => span.remove());
  }
}

activando =  true;//false;

/** Funcion para crear tags, <td> para la tabla */
function insertarDatosEnTabla(personas){
    const tabla = document.getElementById('tabla-personas');
    const tbody = tabla.getElementsByTagName('tbody')[0];

    personas.forEach((persona, index) => {
        const fila = document.createElement('tr');
        fila.addEventListener('click', function(){
          tempPersona = persona;
          // console.log(tempPersona);
          mostrarPanel(activando);
          // activando = !activando;
        });
    
        const numeroCelda = document.createElement('td');
        // numeroCelda.textContent = persona.idAlumno;
        numeroCelda.textContent = index + 1;
        
        const codigoCelda = document.createElement('td');
        codigoCelda.textContent = persona.codigo;
    
        const nombresApellidosCelda = document.createElement('td');
        nombresApellidosCelda.textContent = persona.nombreCompleto;
    
        const escuelaProfesionalCelda = document.createElement('td');
        escuelaProfesionalCelda.textContent = persona.escuelaProfesional;
    
        const promedioPonderadoCelda = document.createElement('td');
        promedioPonderadoCelda.textContent = persona.pps;
            
        fila.appendChild(numeroCelda);
        fila.appendChild(codigoCelda);
        fila.appendChild(nombresApellidosCelda);
        fila.appendChild(escuelaProfesionalCelda);
        fila.appendChild(promedioPonderadoCelda);
        tbody.appendChild(fila);
      });
  }

  // Eliminar datos de la tabla completa
  function deleteTabla(){
    const tabla = document.getElementById('tabla-personas');
    const tbody = tabla.getElementsByTagName('tbody')[0];

    // Eliminar todas las filas del tbody
    while (tbody.firstChild) {
      tbody.removeChild(tbody.firstChild);
    }
  }

  // filtro por seleccion de carrera, anho, etc
  function loadSelect(id_element, key, value_default){
    let indexSelectEPRO = document.getElementById(id_element).value;
    // alert(escuelaProfesional[indexSelectEPRO]);


    let listaEstudiantesXescuela = datosPersonas.filter(
        (persona) => {
          if(escuelaProfesional[indexSelectEPRO] === value_default){
            return persona[key];
          }else{
            return persona[key] === escuelaProfesional[indexSelectEPRO]
          }
         
        }
      );
    
    // console.log(listaEstudiantesXescuela);
    deleteTabla();
    insertarDatosEnTabla(listaEstudiantesXescuela);
  }

  function loadAnho(id_element){
    let indexSelectEPRO = document.getElementById(id_element).value;
    
    let listaEstudiantesXescuela = datosPersonas.filter(
        (persona) => {
          return persona['codigo'].toString().substring(0, 4) === indexSelectEPRO;
                   
        }
      );
    
    // console.log(listaEstudiantesXescuela);
    deleteTabla();
    insertarDatosEnTabla(listaEstudiantesXescuela);
  }
  

  // CARGANDO 10 DATOS PARA PRUEBAS
  insertarDatosEnTabla(datosPersonas);

  document.getElementById('formFile').addEventListener('change', function(event) {
    let archivo = event.target.files[0];
    
    let lector = new FileReader();
    
    lector.onload = function(e) {
      let datos = new Uint8Array(e.target.result);
      let libroTrabajo = XLSX.read(datos, { type: 'array' });
      
      let hojaCalculo = libroTrabajo.Sheets[libroTrabajo.SheetNames[0]];
      let jsonData = XLSX.utils.sheet_to_json(hojaCalculo);
  
      let personas = [];
      jsonData.forEach(persona => {
        personas.push(new Persona(persona['Nº'],
                                  persona.CODIGO,
                                  persona['APELLIDOS Y NOMBRES'],
                                  persona.SEXO,
                                  persona['ESCUELA PROFESIONAL'],
                                  persona.PPS,
                                  persona.COMEDOR,
                                  persona.ESTADO));
      });
      // Llamar a la función para insertar datos en la tabla
      insertarDatosEnTabla(personas);
      
      datosPersonas = [...personas];
      // console.log(datosPersonas);

    };
    lector.readAsArrayBuffer(archivo);

  });


  /* EVENTOS DE ESTILOS */

  window.addEventListener('scroll', function() {
    let piePagina = document.querySelector('.footer');
    let posicionDesplazamiento = window.innerHeight + window.scrollY;
    let alturaPagina = document.documentElement.scrollHeight - window.innerHeight;
  
    if (posicionDesplazamiento >= alturaPagina) {
      piePagina.classList.add('footer-hidden');
    } else {
      piePagina.classList.remove('footer-hidden');
    }
  });


  // open datos de estudiantes
  document.getElementById('formFileStudent').addEventListener('change', function(event) {
    let archivo = event.target.files[0];
    
    let lector = new FileReader();
    
    lector.onload = function(e) {
      let datos = new Uint8Array(e.target.result);
      let libroTrabajo = XLSX.read(datos, { type: 'array' });
      
      let hojaCalculo = libroTrabajo.Sheets[libroTrabajo.SheetNames[0]];
      let jsonData = XLSX.utils.sheet_to_json(hojaCalculo);
    
      let personas = [];
      jsonData.forEach(unasino => {
        personas.push(new Unasino(
          unasino['DNI'],
          unasino['AP. PATERNO'],
          unasino['AP. MATERNO'],
          unasino['NOMBRE'],
          unasino['ESCUELA PROFESIONAL'],
          unasino['CORREO INSTITUCIONAL']));
      });
      // Llamar a la función para insertar datos en la tabla
      insertarDatosEnTabla(personas);
      
      datosUnasinos = [...personas];
      // console.log(datosUnasinos);

    };
    lector.readAsArrayBuffer(archivo);

  });
  


// autorellenar datos
document.getElementById('click_buscarDNI').addEventListener('click', function() {
  const dniBusqueda = document.getElementById('dni').value;
  const resultadoBusqueda = datosUnasinos.find(unasino => unasino.dni === dniBusqueda);
  

  const apPaternoInput = document.getElementById('ap_paterno');
  const apMaternoInput = document.getElementById('ap_materno');
  const apNombreInput = document.getElementById('ap_nombre');
  const apCarreraSelect = document.getElementById('carrera');
  const apCorreoSelect = document.getElementById('institucion_correo');

  if (resultadoBusqueda) {
    console.log(resultadoBusqueda);
    apPaternoInput.value  = `${resultadoBusqueda.ap_paterno}`;
    apMaternoInput.value  = `${resultadoBusqueda.ap_materno}`;
    apNombreInput.value  = `${resultadoBusqueda.nombre}`;
    apCorreoSelect.value  = `${resultadoBusqueda.correo_institucional}`;

    if(resultadoBusqueda.escuela_profesional == 'ADMINISTRACION'){ apCarreraSelect.value = 1; }
    else if(resultadoBusqueda.escuela_profesional == 'AGRONOMIA'){ apCarreraSelect.value = 2; }
    else if(resultadoBusqueda.escuela_profesional == 'CONTABILIDAD'){ apCarreraSelect.value = 3; }
    else if(resultadoBusqueda.escuela_profesional == 'ECONOMIA'){ apCarreraSelect.value = 4; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA AMBIENTAL'){ apCarreraSelect.value = 5; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA EN CONSERVACION DE SUELOS Y AGUA'){ apCarreraSelect.value = 6; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA EN RECURSOS NATURALES RENOVABLES'){ apCarreraSelect.value = 7; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA EN INDUSTRIAS ALIMENTARIAS'){ apCarreraSelect.value = 8; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA EN INFORMATICA Y SISTEMAS'){ apCarreraSelect.value = 9; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA FORESTAL'){ apCarreraSelect.value = 10; }
    else if(resultadoBusqueda.escuela_profesional == 'INGENIERIA MECANICA ELECTRICA'){ apCarreraSelect.value = 11; }
    else if(resultadoBusqueda.escuela_profesional == 'ZOOTECNIA'){ apCarreraSelect.value = 12; }
    else{ apCarreraSelect.value =0; }

  } else {
    console.log("No se encontraron resultados");
  }
});
