
document.addEventListener("DOMContentLoaded", () => {
    const imageInput = document.getElementById("fileInput");
    const ocrButton = document.getElementById("ocrButton");
    const ocrResult = document.getElementById("ocrResult");
    const fileNameSpan = document.getElementById("fileName");

    fileInput.addEventListener("change", () => {
        const selectedFile = fileInput.files[0];
        if (selectedFile) {
            ocrButton.removeAttribute("disabled");
        } else {
            ocrButton.setAttribute("disabled", true);
        }
    });
    
    ocrButton.addEventListener("click", async () => {
        const file = imageInput.files[0];
        if (file) {
            // Mostrar el modal de carga
            const loadingModal = document.getElementById("loadingModal");
            loadingModal.style.display = "flex";

            const formData = new FormData();
            formData.append("image", file);

            const response = await fetch("/process-image", {
                method: "POST",
                body: formData,
            });

            const text = await response.text();
            ocrResult.textContent = text;

            // Ocultar el modal de carga después de completar la operación
            loadingModal.style.display = "none";
        }
    });

    fileInput.addEventListener("change", () => {
    const selectedFile = fileInput.files[0];
    if (selectedFile) {
        fileNameSpan.textContent = selectedFile.name;
    } else {
        fileNameSpan.textContent = "";
    }
});
    
});

