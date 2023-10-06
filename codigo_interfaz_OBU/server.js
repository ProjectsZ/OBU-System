const express = require("express");
const multer = require("multer");
const Tesseract = require("tesseract.js");
const fs = require("fs-extra");
const path = require("path");
const app = express();
const port = 3000;

app.use(express.static("public"));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

app.post("/process-image", upload.single("image"), async (req, res) => {
    if (!req.file) {
        return res.status(400).send("No se proporcionó ninguna imagen.");
    }
    try {
        const imageBuffer = req.file.buffer;
        const result = await Tesseract.recognize(imageBuffer, "eng");
        const extractedText = result.data.text;
        res.send(extractedText);
    } catch (error) {
        res.status(500).send("Ocurrió un error al procesar la imagen.");
    }
});

app.listen(port, () => {
    console.log(`Servidor en http://localhost:${port}`);
});