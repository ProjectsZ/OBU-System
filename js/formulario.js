
// validando dni
document.getElementById('registro-form').addEventListener('submit', function(event) {
    const dniInput = document.getElementById('dni');
    const dniError = document.getElementById('dni-error');
    if (dniInput.value.length !== 8) {
      dniError.textContent = 'El DNI debe tener exactamente 8 dígitos.';
      event.preventDefault(); // Evitar el envío del formulario
    } else {
      dniError.textContent = ''; // Limpiar el mensaje de error
    }
});

// validar de que el nombre no contenga caracteres extraños
document.getElementById('registro-form').addEventListener('submit', function(event) {
    const apPaternoInput = document.getElementById('ap_paterno');
    const apPaternoError = document.getElementById('ap_paterno-error');
    const apMaternoInput = document.getElementById('ap_materno');
    const apMaternoError = document.getElementById('ap_materno-error');
    const apNombreInput = document.getElementById('ap_nombre');
    const apNombreError = document.getElementById('ap_nombre-error');
    const textPattern = /^[A-Za-z\s]+$/; // Expresión regular que permite letras y espacios

    if (!textPattern.test(apPaternoInput.value)) {
      apPaternoError.textContent = 'Introduce solo texto en el apellido paterno.';
      event.preventDefault(); // Evitar el envío del formulario
    } else {
      apPaternoError.textContent = ''; // Limpiar el mensaje de error
    }

    if (!textPattern.test(apMaternoInput.value)) {
      apMaternoError.textContent = 'Introduce solo texto en el apellido materno.';
      event.preventDefault(); // Evitar el envío del formulario
    } else {
      apMaternoError.textContent = ''; // Limpiar el mensaje de error
    }

    if (!textPattern.test(apNombreInput.value)) {
      apNombreError.textContent = 'Introduce solo texto en el Nombre.';
      event.preventDefault(); // Evitar el envío del formulario
    } else {
        apNombreError.textContent = ''; // Limpiar el mensaje de error
    }
});

// validando solo numeros del 2016 - 2023
document.getElementById('registro-form').addEventListener('submit', function(event) {
    const anhoCicloInput = document.getElementById('anho_ciclo');
    const anhoCicloError = document.getElementById('anho_ciclo-error');
    const currentYear = new Date().getFullYear();
    const minYear = 2016;
    const maxYear = 2023;

    if (
      isNaN(anhoCicloInput.value) ||
      anhoCicloInput.value < minYear ||
      anhoCicloInput.value > maxYear
    ) {
      anhoCicloError.textContent = `Ingrese un año entre ${minYear} y ${maxYear}.`;
      event.preventDefault(); // Evitar el envío del formulario
    } else {
      anhoCicloError.textContent = ''; // Limpiar el mensaje de error
    }
});

