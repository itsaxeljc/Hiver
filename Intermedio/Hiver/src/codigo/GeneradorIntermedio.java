/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.util.StringTokenizer;

/**
 *
 * @author jonat
 */
public class GeneradorIntermedio {
    
    String codigo, cabecera = "";
    static String generado = "";
    
    public GeneradorIntermedio(String codeIntermedio){
        codigo = codeIntermedio;
    }
    
    public String getGenerado(){
        return generado;
    }
    
    public static void main(String[] args) {
        //  Expresiones regulares
        String
                //entero = "[0-9]+",
                //decimal = "[0-9]*.[0-9]+",
                //id = "([(a-z)(A-Z)](\\w)*)",
                //identdec = "("+id+"|"+entero+"|"+decimal+")",
                //ope = "[+-/*]",
                //expresion = "(\\s)*"+id+"(\\s)*=(\\s)*(\\()*(\\s)*"+identdec+"(\\s)*((\\()*"+ope+"(\\s)*"+identdec+"(\\s)*(\\))*)*(\\s)*(;)",
                //comentario = "%//%",
                ///sentenciaIn = "(\\s)*"+id+"(\\s)*=(\\s)*in(\\()(\\s)*"+id+"(\\s)*(\\))(;)",
                sentenciaIn = ".*=in[\\(].*[\\)];.*",
                //sentenciaIf = "if[\\(].*[\\)]{",
                main3 = "((\\s)*END(\\s)*(\\})(\\s)*)";
                //"var1=in(pin1);"
        
        //  Variable para testear
        String test = " variable = in(     pin1); // hola es un comentario";
        
        //  Dividir en lineas el codigo
        StringTokenizer lineasCode = new StringTokenizer(test, "\n");
        while(lineasCode.hasMoreTokens()){
            //  Quitar los espacios de todas las lineas
            String linea = lineasCode.nextToken().replaceAll("\\s","");
            //  Linea que se está analizando
            System.out.println(linea);
            
            //  Comparar la linea con cada sentencia/expresión regular
            if(linea.matches(sentenciaIn)){
                String[] sinPar = linea.split("[\\)];");
                String[] vars = sinPar[0].split("=in[\\(]");
                generado += vars[0] + "=digitalRead(" + vars[1] + ");\n";
            }
        }
        System.out.println(generado);
    }
    
}
