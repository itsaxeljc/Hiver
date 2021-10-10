package codigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flex {
    public Object[] recuperartokens(String codeAreaText){//Metodo para devolver los tokens y errores lexicos 
     ArrayList linea;
     ArrayList token;
     ArrayList lexema;
     ArrayList error;
     
     
                File archivo = new File("archivo.txt");
        PrintWriter escribir;
        try {
            escribir = new PrintWriter(archivo);
            escribir.print(codeAreaText);
            escribir.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FRMEjemplo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Reader lector = new BufferedReader(new FileReader("archivo.txt"));
            Lexer lexer= new Lexer(lector);
            String resultado = "";
            String resultado1 = "";
             linea = new ArrayList();
             token = new ArrayList();
             lexema = new ArrayList();
             error = new ArrayList();
            
            while (true) {
                Tokens tokens = lexer.yylex();
               
                
                if (tokens == null) {
                    break;
                }
switch (tokens) {
                    case ERROR:
                      
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add("Error lexico");
                        error.add(1);
                        resultado1+=lexer.lexema +" - Error léxico, Cadena no valida en la línea "+lexer.GetLine() +"\n";
                        break;
                    
                    case res_if:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens);
                        error.add(0);        
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case coma:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case Identificador: 
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case cadena_caracter:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case Numero:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case   res_else:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_while:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_do:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_for:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_exit:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_return:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_function:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_void:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break; 
                    case  res_null:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case   res_input:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_output:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_in:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_out:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_def:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_true:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_false:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_start:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_end:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_array:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_type:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_display:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_config:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_scale:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_pin:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  res_volt:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_wait:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_model:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_sleep:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_switch:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_default:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_name:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case res_max_volt:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case asi_asignacion:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case rel_igual:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case ari_suma:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case ari_resta:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case ari_multiplicacion:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case ari_division:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case log_and:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case log_or:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case log_xor:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case log_not:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case rel_menor:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case rel_mayor:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case rel_menor_igual:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  rel_mayor_igual:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case rel_diferente:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  sig_punto_coma:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case par_abre:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case par_cierra:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case llave_abre:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case llave_cierra:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case cor_abre:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case cor_cierra:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case sim_dos_puntos:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case tip_tinyint:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case tip_int:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case tip_float:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  tip_double:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  tip_char:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case  tip_boolean:
                        lexema.add( lexer.lexema); 
                        linea.add(lexer.GetLine());
                        token.add(tokens); error.add(0);
                        resultado += lexer.lexema + "  : Es un " + tokens + "\n";
                        break;
                    case Numero_incorrecto1:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                       resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                               " cadena no valida" + "\n";
                    break;
                    case Numero_incorrecto2:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                       resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                               " cadena no valida" + "\n";
                    break;
                    case Identificador_no_valido:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                            resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                               " cadena no valida" + "\n";
                    break;
                    case Error_simbo_punto:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                            resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                               " cadena no valida" + "\n";
                    break;
                   case Error_simbolo_no_valido:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                            resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                                " cadena no válida"+ "\n";
                    break; 
                   case Error_numerico:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                            resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                                " cadena no válida" + "\n";
                    break; 
                   case Numero_incorrecto3:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                            resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                               " cadena no válida" + "\n";
                    break; 
                   case Numero_incorrecto4:
                            error.add(1);
                            lexema.add(lexer.lexema);
                            linea.add(lexer.GetLine());
                            token.add(tokens);
                            resultado1+=lexer.lexema +" - Error léxico en la línea "+lexer.GetLine() +
                               " cadena no válida" + "\n";
                    break; 
                    default:
                        resultado += "Token: " + tokens + "\n";
                }
                
            }
            
            Object ob[]= new Object[5];
                ob[0]=linea;
                ob[1]=token;
                ob[2]=lexema;
                ob[3]=error;
                ob[4]=resultado1;

                return ob;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FRMEjemplo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FRMEjemplo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
