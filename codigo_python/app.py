from flask import Flask, render_template, request, send_from_directory
from PIL import Image
import pytesseract
import os

app = Flask(__name__, static_url_path='/static')

UPLOAD_FOLDER = 'uploaded_images'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/')
def index():
    return render_template('index.html', num_images=5)

@app.route('/uploads/<filename>')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'], filename)

@app.route('/upload', methods=['POST'])
def upload():

    num_images = int(request.form['num_images'])

    for i in range(num_images):
        image = request.files[f'image_{i+1}']
        image.save(os.path.join(app.config['UPLOAD_FOLDER'], f'image_{i+1}.jpeg'))
        text = pytesseract.image_to_string(Image.open(os.path.join(app.config['UPLOAD_FOLDER'], f'image_{i+1}.jpeg')))
        print(f'Texto de imagen {i+1}:')
        print(text)

    return render_template('result.html', text=text, num_images=num_images)

if __name__ == '__main__':
    app.run(debug=True)
