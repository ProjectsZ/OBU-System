/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.StringTokenizer;

/**
 *
 * @author SkyRider
 */
public class StringConverter {
    
    /* metodo para convertir cada palabra en mayuscula la primera letra */
    public static String converterTxtMayusANDminus(String string) {
        if (string == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        StringTokenizer st = new StringTokenizer(string," ");
        while (st.hasMoreElements()) {
            String ne = (String)st.nextElement();
            if (ne.length()>0) {
                builder.append(ne.substring(0, 1).toUpperCase());
                builder.append(ne.substring(1).toLowerCase()); //agregado
                builder.append(' ');
            }
        }
        return builder.toString();
    }
    
    /* metodo para ver si tiene un numero o no */
    public static boolean isNumerico(String valor) {
        return (valor.matches("[+-]?\\d*(\\.\\d+)?") && valor.equals("")==false);
    }
    
}
