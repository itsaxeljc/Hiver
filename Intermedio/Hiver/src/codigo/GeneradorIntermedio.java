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
    
    static String codigo, cabecera = "";
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
                sentenciaFor = "for[\\(].*[\\)]\\{.*",
                sentenciaSleep = "sleep[\\(].*[\\)];.*",
                sentenciaDef = "def.*;.*",
                sentenciaOut = "out[\\(].*[\\)];.*",
                //sentenciaIf = "if[\\(].*[\\)]{",
                main3 = "((\\s)*END(\\s)*(\\})(\\s)*)";
                //"var1=in(pin1);//comment"
        
        //  Variable para testear
        String test = "def tinyint var1;// hola es un comentario";
        
        //  Dividir en lineas el codigo
        StringTokenizer lineasCode = new StringTokenizer(test, "\n");
        while(lineasCode.hasMoreTokens()){
            //  Quitar los espacios de todas las lineas
            String linea = lineasCode.nextToken().replaceAll("\\s","").toLowerCase();
            //  Linea que se está analizando
            System.out.println(linea);
            
            //  Comparar la linea con cada sentencia/expresión regular
            if(linea.matches(sentenciaIn)){
                String[] sinPar = linea.split("[\\)];");
                String[] vars = sinPar[0].split("=in[\\(]");
                generado += vars[0] + "=digitalRead(" + vars[1] + ");\n";
            }else if(linea.matches(sentenciaFor)){
                String[] sinComment = linea.split("\\{");
                //  for(defintx;x<x;x++)
                String[] tipoVar = sinComment[0].split("def");
                // [1] = intx;.. ó charx;...
                //  Tipos tinyint, int, float, double
                if(tipoVar[1].matches("tinyint.*")){
                    String[] sentenciaInter = tipoVar[1].split("tinyint");
                    generado += "for(tinyint " + sentenciaInter[1] + "{\n";
                }else if(tipoVar[1].matches("int.*")){
                    String[] sentenciaInter = tipoVar[1].split("int");
                    generado += "for(int " + sentenciaInter[1] + "{\n";
                }else if(tipoVar[1].matches("float.*")){
                    String[] sentenciaInter = tipoVar[1].split("float");
                    generado += "for(float " + sentenciaInter[1] + "{\n";
                }else if(tipoVar[1].matches("double.*")){
                    String[] sentenciaInter = tipoVar[1].split("double");
                    generado += "for(double " + sentenciaInter[1] + "{\n";
                }
            }else if (linea.matches(sentenciaSleep)){
                String[] sinPar = linea.split("[\\)];");
                String[] vars = sinPar[0].split("[\\(]");
                generado += "delay(" + vars[1] + ");\n"; 
            }else if (linea.matches(sentenciaDef)){
                String[] sinComm = linea.split(";");
                
                String[] tipoVar = sinComm[0].split("def");
                // intx ó charx
                //  Tipos tinyint, int, float, double
                if(tipoVar[1].matches("tinyint.*")){
                    String[] declaracion = tipoVar[1].split("tinyint");
                    generado += "tinyint " + declaracion[1] + ";\n";
                }else if(tipoVar[1].matches("int.*")){
                    String[] declaracion = tipoVar[1].split("int");
                    generado += "int " + declaracion[1] + ";\n";
                }else if(tipoVar[1].matches("float.*")){
                    String[] declaracion = tipoVar[1].split("float");
                    generado += "float " + declaracion[1] + ";\n";
                }else if(tipoVar[1].matches("double.*")){
                    String[] declaracion = tipoVar[1].split("double");
                    generado += "double " + declaracion[1] + ";\n";
                }
            }else if (linea.matches(sentenciaOut)){            
                generado += "lcd.clear();" + "\n" + "lcd.setCursor(0,0);" + "\n";
                String[] sinPar = linea.split("[\\)];");
                String[] vars = sinPar[0].split("[,]");
                generado += "lcd.print(" + vars[1];
                for(int i=2; i<vars.length; i++){
                    generado += "," + vars[i];               
                }
                generado += ");\n";
            }

        }
        cabecera = "";
        generado = cabecera + generado;
        System.out.println(generado);
    }
    
}
