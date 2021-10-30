package codigo;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char

Letra=[a-zA-Z]
Digito=[0-9]+

espacio=[\t\r\n ]+ 
Zero = 0
DecInt = [\+|\-][1-9][0-9]*
entero = ( {Zero} | {DecInt} )[lL]?
exponente = [eE] [\+\-]? [0-9]+
Float1 = [\+|\-]?[0-9]+ \. [0-9]+ {exponente}?
Float4 = [\+|\-]?[0-9]+ {exponente}?
flotante = ( {Float1} | {Float4} )
numero = ( {Float1} | {Float4} ) | [0-9]+ | {entero} | {exponente}
InputChar = [^\n\r]
CChar = [^\'\\\n\r] | {EscChar}
Simbo = "!"|"%"|"^"|"&"|"*"|"("|")"|"_"|"-"|"="|"+"|"{"|"["|"]"|"}"|":"|"“"|","|"<"|"."|">"|"/"|"\\"
NoSimbo = "%"|"^"|"&"|":"|"“"|"\."|"°"|"#"|"$"|"¡"|"?"|"¿"|"´"|"`"|"@"|"!"|"’"|"\“"
SimboCadena = "!"|"%"|"^"|"&"|"_"|"{"|"["|"]"|"}"|":"|"."|"\“"
Iden = {Letra}({Letra}|{Digito}|_)*
simbo_punto = {Simbo}+"\." 

%{
    private Symbol symbol(int type, Object value){
        return new Symbol (type, yycolumn, yyline,value);
    }
    private Symbol symbol(int type){
        return new Symbol (type, yycolumn,yyline);
    }
public int GetLine() { return yyline + 1; }
%}
%%

tinyint | TINYINT {return new Symbol(sym.tip_tinyint, yycolumn, yyline,yytext());}

int | INT {return new Symbol(sym.tip_int, yycolumn, yyline,yytext());}

float | FLOAT {return new Symbol(sym.tip_float , yycolumn, yyline,yytext()); }

double | DOUBLE {return new Symbol(sym.tip_double , yycolumn, yyline,yytext()); }

char | CHAR {return new Symbol(sym.tip_char , yycolumn, yyline,yytext()); }

boolean | BOOLEAN {return new Symbol(sym.tip_boolean , yycolumn, yyline,yytext()); } 

\' ({Letra}*{Digito}*{Simbo}*" "*)+ \' { return new Symbol(sym.cadena_caracter , yycolumn, yyline,yytext()); }

"if" | "IF" {return new Symbol(sym.res_if , yycolumn, yyline,yytext()); }

"else" | "ELSE" {return new Symbol(sym.res_else , yycolumn, yyline,yytext()); }
 
"while" | "WHILE" {return new Symbol(sym.res_while , yycolumn, yyline,yytext()); }
 
"do" | "DO" {return new Symbol(sym.res_do , yycolumn, yyline,yytext()); }
 
"for" | "FOR" {return new Symbol(sym.res_for , yycolumn, yyline,yytext()); }
 
"exit" | "EXIT" {return new Symbol(sym.res_exit , yycolumn, yyline,yytext()); }
 
"return" | "RETURN" {return new Symbol(sym.res_return , yycolumn, yyline,yytext()); }
 
"function" | "FUNCTION" {return new Symbol(sym.res_function , yycolumn, yyline,yytext()); }
 
"VOID" | "void" {return new Symbol(sym.res_void , yycolumn, yyline,yytext()); } 
 
"null" | "NULL" {return new Symbol(sym.res_null , yycolumn, yyline,yytext()); }
 
"INPUT" | "input" {return new Symbol(sym.res_input , yycolumn, yyline,yytext()); } 
 
"output" | "OUTPUT" {return new Symbol(sym.res_output , yycolumn, yyline,yytext()); } 
 
"in" | "IN" {return new Symbol(sym.res_in , yycolumn, yyline,yytext()); }
 
"out" | "OUT" {return new Symbol(sym.res_out , yycolumn, yyline,yytext()); }
 
"def" | "DEF" {return new Symbol(sym.res_def , yycolumn, yyline,yytext()); } 
 
"true" | "TRUE" {return new Symbol(sym.res_true , yycolumn, yyline,yytext()); }
 
"FALSE" | "false" {return new Symbol(sym.res_false , yycolumn, yyline,yytext()); } 
 
"start" | "START" {return new Symbol(sym.res_start , yycolumn, yyline,yytext()); }
 
"END" | "end" {return new Symbol(sym.res_end , yycolumn, yyline,yytext()); } 
 
"array" | "ARRAY" {return new Symbol(sym.res_array , yycolumn, yyline,yytext()); } 
 
type | "TYPE" {return new Symbol(sym.res_type , yycolumn, yyline,yytext()); }
 
display | DISPLAY {return new Symbol(sym.res_display , yycolumn, yyline,yytext()); } 
 
config | CONFIG {return new Symbol(sym.res_config , yycolumn, yyline,yytext()); }
 
scale | SCALE {return new Symbol(sym.res_scale , yycolumn, yyline,yytext()); }
 
pin | PIN {return new Symbol(sym.res_pin , yycolumn, yyline,yytext()); }

volt | VOLT {return new Symbol(sym.res_volt , yycolumn, yyline,yytext()); } 

wait | WAIT {return new Symbol(sym.res_wait , yycolumn, yyline,yytext()); }
 
mode | MODE {return new Symbol(sym.res_model , yycolumn, yyline,yytext()); }
 
sleep | SLEEP {return new Symbol(sym.res_sleep , yycolumn, yyline,yytext()); } 
 
switch | SWITCH {return new Symbol(sym.res_switch , yycolumn, yyline,yytext()); }

default | DEFAULT {return new Symbol(sym.res_default , yycolumn, yyline,yytext()); } 
 
name | NAME {return new Symbol(sym.res_name , yycolumn, yyline,yytext()); } 
 
max_volt | MAX_VOLT {return new Symbol(sym.res_max_volt , yycolumn, yyline,yytext()); }

{espacio} {/*Ignore*/}

"/*"~"*/" {/*Ignore*/}

"//"{InputChar}* {/*Ignore*/}

"=" {return new Symbol(sym.asi_asignacion , yycolumn, yyline,yytext()); }

"+" {return new Symbol(sym.ari_suma , yycolumn, yyline,yytext()); }

"," {return new Symbol(sym.coma , yycolumn, yyline,yytext()); }

"-" {return new Symbol(sym.ari_resta , yycolumn, yyline,yytext()); }

"*" {return new Symbol(sym.ari_multiplicacion , yycolumn, yyline,yytext()); }

"/" {return new Symbol(sym.ari_division , yycolumn, yyline,yytext()); }

"and" {return new Symbol(sym.log_and , yycolumn, yyline,yytext()); }

"or" {return new Symbol(sym.log_or , yycolumn, yyline,yytext()); }

"xor" {return new Symbol(sym.log_xor , yycolumn, yyline,yytext()); }

"not" {return new Symbol(sym.log_not , yycolumn, yyline,yytext()); }

"<" {return new Symbol(sym.rel_menor , yycolumn, yyline,yytext()); }

">" {return new Symbol(sym.rel_mayor , yycolumn, yyline,yytext()); }

"<=" {return new Symbol(sym.rel_menor_igual , yycolumn, yyline,yytext()); }

">=" {return new Symbol(sym.rel_mayor_igual , yycolumn, yyline,yytext()); }

"<>" {return new Symbol(sym.rel_diferente , yycolumn, yyline,yytext()); }

\=\= {return new Symbol(sym.rel_igual , yycolumn, yyline,yytext());}

";" {return new Symbol(sym.sig_punto_coma , yycolumn, yyline,yytext()); }

"(" {return new Symbol(sym.par_abre , yycolumn, yyline,yytext()); }

")" {return new Symbol(sym.par_cierra , yycolumn, yyline,yytext()); }

"{" {return new Symbol(sym.llave_abre , yycolumn, yyline,yytext()); }

"}" {return new Symbol(sym.llave_cierra , yycolumn, yyline,yytext()); }

"[" {return new Symbol(sym.cor_abre , yycolumn, yyline,yytext());}

"]" {return new Symbol(sym.cor_cierra , yycolumn, yyline,yytext()); }

":" {return new Symbol(sym.sim_dos_puntos , yycolumn, yyline,yytext()); }

{Letra}({Letra}|{Digito}|_)* {return new Symbol(sym.Identificador , yycolumn, yyline,yytext()); }

{numero} {return new Symbol(sym.Numero , yycolumn, yyline,yytext()); }

{numero}{Iden}+({numero}|{Iden}|{NoSimbo})* {return new Symbol(sym.Numero_incorrecto1 , yycolumn, yyline,yytext()); }

{SimboCadena}+{numero}|{numero}{SimboCadena}+ {return new Symbol(sym.Numero_incorrecto3 , yycolumn, yyline,yytext()); }

{flotante}(\.|\.{numero})+{numero}* {return new Symbol(sym.Numero_incorrecto2 , yycolumn, yyline,yytext()); }

"\'"{numero} | {numero}"\'" | "\'"{numero}{Iden}{numero} | "\'"{numero}{Iden} {return new Symbol(sym.Numero_incorrecto4 , yycolumn, yyline,yytext()); }

{Iden}{NoSimbo}+{Iden}* | "\."{Iden} | {SimboCadena}+{Iden}|"\'"{Iden} | {Iden}"\'" | {NoSimbo}+{Iden} {return new Symbol(sym.Identificador_no_valido , yycolumn, yyline,yytext()); }

{simbo_punto} {return new Symbol(sym.Error_simbo_punto , yycolumn, yyline,yytext()); }

{NoSimbo}+ | "\_" | "\"" {return new Symbol(sym.Error_simbolo_no_valido , yycolumn, yyline,yytext()); }

{numero}{NoSimbo}+{numero} | {numero}{NoSimbo}+ | {numero}"\." {return new Symbol(sym.Error_numerico, yycolumn, yyline,yytext());}

 . {return new Symbol(sym.ERROR , yycolumn, yyline,yytext());}



