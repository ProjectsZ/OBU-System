/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obu.system;

/**
 *
 * @author Skyrider
 */

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import utilidades.StringConverter;


public class system extends javax.swing.JFrame {
    
    TableRowSorter<TableModel> rowSorter = null;
    DefaultTableModel model = null;
//    TableRowSorter<? extends TableModel> rowSorter =
//                (rs instanceof TableRowSorter) ? (TableRowSorter<? extends TableModel>) rs : null;

    
    private static final long serialVersionUID = 1L;
    private boolean debug = true;
    
    // Convierte el modelo de tabla en un ArrayList de ArrayLists
    private static ArrayList<ArrayList<Object>> dataList = null;

    private static  Object[] rowData;
    /**
     * Creates new form system
     */
    public system() {
        initComponents();
        
        DefaultTableModel model = loadExcelData("C:\\Users\\SkyRider\\Pictures\\tessdata\\RELACION-COMEDOR.xlsx");

        jtable_Angular.setModel(model);
        
        JSON_data_export();
    }
    
    public static DefaultTableModel loadExcelData(String filePath) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nº");
        model.addColumn("CODIGO");
        model.addColumn("APELLIDOS Y NOMBRES");
        model.addColumn("SEXO");
        model.addColumn("ESCUELA PROFESIONAL");
        model.addColumn("PPS");
        model.addColumn("COMEDOR");
        model.addColumn("ESTADO");

         try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Suponiendo que deseas cargar la primera hoja
           
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    rowData = new Object[row.getLastCellNum()];
                    

                    for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                        Cell cell = row.getCell(columnIndex);

                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    rowData[columnIndex] = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    rowData[columnIndex] = cell.getNumericCellValue();
                                    break;
                                default:
                                    rowData[columnIndex] = "";
                                    break;
                            }
                        } else {
                            rowData[columnIndex] = "";
                        }
                    }

                    model.addRow(rowData);
                }
            }

            fis.close();
        } catch (IOException e) {
            // Maneja la excepción de manera apropiada, como mostrar un mensaje de error al usuario o registrarla.
            e.printStackTrace();
        }

        return model;
    }
   
    
    private static final String filePath = "C:\\Users\\SkyRider\\Pictures\\tessdata\\data_java.json"; // Reemplaza con la ruta de tu archivo JSON

    
    public void JSON_data_export(){
        try {
            JSONArray jsonArray;
            File file = new File(filePath);

            if (file.exists()) {
                // Si el archivo existe, carga su contenido en un JSONArray
                jsonArray = loadJsonArray(file);
            } else {
                // Si el archivo no existe, crea un nuevo JSONArray
                jsonArray = new JSONArray();
            }

            // Realiza las ediciones necesarias en el objeto JSON
           // JSONObject nuevaInformacion = new JSONObject();
           // nuevaInformacion.put("campo1", "valor1");
           // nuevaInformacion.put("campo2", "valor2");
           // addDataToJson(jsonFile, "data1", nuevaInformacion);

            // Guarda el JSONArray en el archivo
            saveJsonArrayToFile(jsonArray, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static JSONArray loadJsonArray(File file) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }

        try {
            return new JSONArray(jsonContent.toString());
        } catch (org.json.JSONException e) {
            // Si el contenido no es válido, crea un nuevo JSONArray
            return new JSONArray();
        }
    }
    
    private static void saveJsonArrayToFile(JSONArray jsonArray, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jsonArray.toString(4)); // El 4 indica la cantidad de espacios de indentación para la salida JSON
        }
    }
     
    private static void addDataToJson(JSONObject json, String key, JSONObject dataObject) {
        // Verifica si la clave ya existe en el objeto JSON principal
        if (json.has(key)) {
            // Obtiene el objeto JSON existente bajo la clave
            JSONObject existingObject = json.getJSONObject(key);

            // Agrega todas las propiedades del nuevo objeto al objeto existente
            for (String property : dataObject.keySet()) {
                existingObject.put(property, dataObject.get(property));
            }
        } else {
            // Si la clave no existe, simplemente agrega el nuevo objeto
            json.put(key, dataObject);
        }
    }
    

    private static void saveJsonToFile(JSONObject json, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json.toString(4)); // El 4 indica la cantidad de espacios de indentación para la salida JSON
        }
    }
    
    private static String getFileExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf(".");
        
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1);
        } else {
            return ""; // No se encontró una extensión
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfURL_Image = new javax.swing.JTextField();
        jbOpen_angular = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel82 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jtfSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtable_Angular = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane13 = new javax.swing.JDesktopPane();
        jScrollPane89 = new javax.swing.JScrollPane();
        jlImage = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtData_OCR = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaData = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de conversion de imagen a texto");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Convertir imagen a texto");

        jbOpen_angular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons/ic_folder_open_black_24dp.png"))); // NOI18N
        jbOpen_angular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbOpen_angularActionPerformed(evt);
            }
        });

        jPanel82.setBackground(new java.awt.Color(204, 204, 204));
        jPanel82.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);

        jtfSearch.setToolTipText("Escribir un indicio a buscar (Nota*: Busqueda por ID o Numeración + ENTER)");
        jtfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfSearchKeyReleased(evt);
            }
        });
        jSplitPane1.setTopComponent(jtfSearch);

        jtable_Angular.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtable_Angular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtable_AngularMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtable_Angular);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("         Data-COMEDOR          ", jPanel82);

        jlImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jlImageMouseReleased(evt);
            }
        });
        jScrollPane89.setViewportView(jlImage);

        jDesktopPane13.add(jScrollPane89);
        jScrollPane89.setBounds(10, 10, 660, 340);

        jScrollPane1.setViewportView(jDesktopPane13);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("     Image     ", jPanel1);

        jtData_OCR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jtData_OCR);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab3", jPanel3);

        jLabel2.setText("Imagen: ");

        jtaData.setColumns(20);
        jtaData.setRows(5);
        jScrollPane3.setViewportView(jtaData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jtfURL_Image)
                                .addGap(18, 18, 18)
                                .addComponent(jbOpen_angular, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane2)
                            .addComponent(jScrollPane3))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfURL_Image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jbOpen_angular, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbOpen_angularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbOpen_angularActionPerformed
        
        JFileChooser chsr = new JFileChooser("C:\\Users\\SkyRider\\Pictures\\tessdata"); /*Modificar: el lugar del guardado*/
        // chsr.setFileFilter(fileNameFilter(jtfName.getText()));
       
        int resultSelectOptions = chsr.showOpenDialog(this);
        if(resultSelectOptions == JFileChooser.APPROVE_OPTION){
            //            chsr.showOpenDialog(null);
            File abrirArchivo=chsr.getSelectedFile(); /*Modificar, Corregir; en caso de que no se agregue ningun archivo */
            sFileName_URL=abrirArchivo.getAbsolutePath();
            jtfURL_Image.setText(sFileName_URL);
            
            // tipo de archivo
            
             // Obtener la descripción del filtro de archivo seleccionado
             File selectedFile = chsr.getSelectedFile();
             String fp = selectedFile.getAbsolutePath();
            String fileExtension = getFileExtension(fp);
            
            try{
                File image = new File(sFileName_URL);
                
                // peso en kb
                // Obtener el tamaño del archivo en bytes
                long fileSizeInBytes = image.length();

                // Convertir el tamaño a kilobytes (KB)
                double fileSizeInKB = fileSizeInBytes / 1024.0;
                
                System.setProperty("TESSDATA_PREFIX", "C:\\Users\\Skyrider\\Pictures\\tessdata"); // Reemplaza con la ruta correcta a tu directorio tessdata
        
                long startTime = System.nanoTime();
                
                ITesseract tesseract = new Tesseract();
                tesseract.setDatapath("C:\\Users\\Skyrider\\Pictures\\tessdata"); // los datos de tessdata
                
                
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                double seconds = (double) elapsedTime / 1_000_000_000.0; // Convertir nanosegundos a segundos

                
                String result = tesseract.doOCR(image);
                System.out.println("Texto extraído de la imagen:\n" + result);
                
                boolean foundMatch = false;
                String coincidence_text = "";
                for (Object value : rowData) {
                    if (value != null && value.toString().contains(result)) {
                        foundMatch = true;
                        coincidence_text = value.toString();
                        break;
                    }
                }

                Random random = new Random();
                // Generar un número aleatorio entre 0 y 50
                int randomNumber = random.nextInt(51);
                int id_new = random.nextInt(80778962);
                
                int score = foundMatch ? 100 : randomNumber;

                System.out.println("Puntaje: " + score);

                
                    rowSorter  = new TableRowSorter<>(jtData_OCR.getModel()); /*Search JTable*/

                    if (result.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                        jtData_OCR.setBackground(Color.white);

                    } else { //(?i) makes it match case insensitive
                        //                rowSorter.setRowFilter(RowFilter.notFilter(RowFilter.regexFilter(jtfSearch.getText())));
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + result));
                        
                        try {
                            // Crear un JSONArray para almacenar objetos JSON
                            JSONArray jsonArray = new JSONArray();
        
                            File file = new File(filePath);

                            // Si el archivo existe, carga su contenido en un objeto JSON
                            jsonArray = loadJsonArray(file);
                            
                            //[
                            //    {
                            //        "tiempo_de_carga_ms": 120,
                            //        "name": "solicitud_71223455",
                            //        "peso": 500,
                            //        "tipo": "png"
                            //    }
                            //]
                            
                            // Realiza las ediciones necesarias en el objeto JSON
                            JSONObject nuevaInformacion = new JSONObject();
                            nuevaInformacion.put("_id", id_new);
                            nuevaInformacion.put("load_time_ms", seconds);
                            nuevaInformacion.put("name", sFileName_URL);
                            nuevaInformacion.put("type", fileExtension);
                            nuevaInformacion.put("size_kb", fileSizeInKB);
                            nuevaInformacion.put("coincidence", score);
                            nuevaInformacion.put("coincidence_text", coincidence_text);
                            //addDataToJson(jsonFile, "", nuevaInformacion);
                            jsonArray.put(nuevaInformacion);
                            
                            // Guarda el objeto JSON en el archivo
                            saveJsonArrayToFile(jsonArray, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        
                        jtData_OCR.setBackground(new java.awt.Color(15, 207, 255));

                    }
                    jtData_OCR.setRowSorter(rowSorter);

                
                
                jtaData.append("\n" + result);
                
                fImg_url = new File(sFileName_URL);
                FileInputStream fis = new FileInputStream(image);
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for(int leerNum;(leerNum=fis.read(buf))!=-1;){
                    bos.write(buf,0,leerNum);
                }

                Image_URL=bos.toByteArray();

                /********************************************* Cargando imagen (Loading) FALTA MEJORARLO*/
                byte[] imagedata = bos.toByteArray();
                jlImage.setIcon(new ImageIcon(imagedata));
                /******************************************************************/
                jTabbedPane2.setSelectedIndex(0); /* see tab[0] */
                fis.close(); /* ????? */

                jTabbedPane2.setSelectedIndex(0);

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        //        else if (resultSelectOptions == JFileChooser.CANCEL_OPTION){
            //            JOptionPane.showMessageDialog(null,"Cancelado, al abrir la imagen");
            //        }
    }//GEN-LAST:event_jbOpen_angularActionPerformed

    private void jlImageMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlImageMouseReleased

    }//GEN-LAST:event_jlImageMouseReleased

    private void jtfSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSearchKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){

            String testeo_valor = jtfSearch.getText();

            if(StringConverter.isNumerico(testeo_valor)){

                String text = jtfSearch.getText();
                jTabbedPane2.setSelectedIndex(0);

                DefaultTableModel modeloTabla = (DefaultTableModel) jtable_Angular.getModel();
                rowSorter  = new TableRowSorter<>(jtable_Angular.getModel()); /*Search JTable*/
                // Limpia la tabla
                modeloTabla.setRowCount(0);

                try{
                     if (text.length() == 0) {
                        // Si no hay texto en el campo de búsqueda, mostrar todas las filas
                        rowSorter.setRowFilter(null);
                    } else {
                        // Crear un filtro para mostrar solo las filas que contienen la palabra "imagen"
                        RowFilter<TableModel, Object> rowFilter = RowFilter.regexFilter(Pattern.quote(text), 0); // 0 es el índice de la columna a filtrar
                        rowSorter.setRowFilter(rowFilter);
                    }

                }catch(Exception ez1){ System.err.println("Error (Click Image): "+ez1);
                }


            }else{
                JOptionPane.showMessageDialog(null,"Por favor introduce el `ID` o un Número valido!! ...");
            }
        }

    }//GEN-LAST:event_jtfSearchKeyPressed

    private void jtfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfSearchKeyReleased
        rowSorter  = new TableRowSorter<>(jtable_Angular.getModel()); /*Search JTable*/

        if (jtfSearch.getText().trim().length() == 0) {
            rowSorter.setRowFilter(null);
            jtable_Angular.setBackground(Color.white);

        } else { //(?i) makes it match case insensitive
            //                rowSorter.setRowFilter(RowFilter.notFilter(RowFilter.regexFilter(jtfSearch.getText())));
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + jtfSearch.getText()));
            jtable_Angular.setBackground(new java.awt.Color(15, 207, 255));

        }
        jtable_Angular.setRowSorter(rowSorter);

    }//GEN-LAST:event_jtfSearchKeyReleased

    private void jtable_AngularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtable_AngularMouseClicked
        

    }//GEN-LAST:event_jtable_AngularMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                //    if ("Nimbus".equals(info.getName())) {
             //       javax.swing.UIManager.setLookAndFeel(info.getClassName());
             //       break;
             //   }
                Properties props = new Properties();
                 props.put("logoString", "<Systems Image to text>");
                 props.getClass().getResource("/Images/ICONO_PS.png");
              //   props.put("locenseKey", "INSERT YOUR LICENSE KEY HERE");
                 AcrylLookAndFeel.setCurrentTheme(props);
                 UIManager.setLookAndFeel("com.jtattoo.plaf.skyrider.SkyriderLookAndFeel");
                //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                // UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(system.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new system().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane89;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton jbOpen_angular;
    private javax.swing.JLabel jlImage;
    private javax.swing.JTable jtData_OCR;
    private javax.swing.JTextArea jtaData;
    private javax.swing.JTable jtable_Angular;
    private javax.swing.JTextField jtfSearch;
    private javax.swing.JTextField jtfURL_Image;
    // End of variables declaration//GEN-END:variables

    private ImageIcon iiImagen = null;
    String sFileName_URL = null;
    byte[] Image_URL = null;
    
    File fImg_url = null;
    ///////////////////////////////////////
}
