package codigo;
import static codigo.Tokens.*;
%%
%class Lexer
%type Tokens 

%line

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
    public String lexema;
public int GetLine() { return yyline + 1; }
%}
%%

tinyint | TINYINT {lexema=yytext(); return tip_tinyint;}

int | INT {lexema=yytext(); return tip_int;}

float | FLOAT {lexema=yytext(); return tip_float;}

double | DOUBLE {lexema=yytext(); return tip_double;}

char | CHAR {lexema=yytext(); return tip_char;}

boolean | BOOLEAN {lexema=yytext(); return tip_boolean;} 

\' ({Letra}*{Digito}*{Simbo}*" "*)+ \' { lexema=yytext(); return cadena_caracter;}

"if" | "IF" {lexema=yytext(); return res_if;}

"else" | "ELSE" {lexema=yytext(); return res_else;}
 
"while" | "WHILE" {lexema=yytext(); return res_while;}
 
"do" | "DO" {lexema=yytext(); return res_do;}
 
"for" | "FOR" {lexema=yytext(); return res_for;}
 
"exit" | "EXIT" {lexema=yytext(); return res_exit;}
 
"return" | "RETURN" {lexema=yytext(); return res_return;}
 
"function" | "FUNCTION" {lexema=yytext(); return res_function;}
 
"VOID" | "void" {lexema=yytext(); return res_void;} 
 
"null" | "NULL" {lexema=yytext(); return res_null;}
 
"INPUT" | "input" {lexema=yytext(); return res_input;} 
 
"output" | "OUTPUT" {lexema=yytext(); return res_output;} 
 
"in" | "IN" {lexema=yytext(); return res_in;}
 
"out" | "OUT" {lexema=yytext(); return res_out;}
 
"def" | "DEF" {lexema=yytext(); return res_def;} 
 
"true" | "TRUE" {lexema=yytext(); return res_true;}
 
"FALSE" | "false" {lexema=yytext(); return res_false;} 
 
"start" | "START" {lexema=yytext(); return res_start;}
 
"END" | "end" {lexema=yytext(); return res_end;} 
 
"array" | "ARRAY" {lexema=yytext(); return res_array;} 
 
type | "TYPE" {lexema=yytext(); return res_type;}
 
display | DISPLAY {lexema=yytext(); return res_display;} 
 
config | CONFIG {lexema=yytext(); return res_config;}
 
scale | SCALE {lexema=yytext(); return res_scale;}
 
pin | PIN {lexema=yytext(); return res_pin;}

volt | VOLT {lexema=yytext(); return res_volt;} 

wait | WAIT {lexema=yytext(); return res_wait;}
 
mode | MODE {lexema=yytext(); return res_model;}
 
sleep | SLEEP {lexema=yytext(); return res_sleep;} 
 
switch | SWITCH {lexema=yytext(); return res_switch;}

default | DEFAULT {lexema=yytext(); return res_default;} 
 
name | NAME {lexema=yytext(); return res_name;} 
 
max_volt | MAX_VOLT {lexema=yytext(); return res_max_volt;}

{espacio} {/*Ignore*/}

"/*"~"*/" {/*Ignore*/}

"//"{InputChar}* {/*Ignore*/}

"=" {lexema=yytext(); return asi_asignacion;}

"+" {lexema=yytext(); return ari_suma;}

"," {lexema=yytext(); return coma;}

"-" {lexema=yytext(); return ari_resta;}

"*" {lexema=yytext(); return ari_multiplicacion;}

"/" {lexema=yytext(); return ari_division;}

"and" {lexema=yytext(); return log_and;}

"or" {lexema=yytext(); return log_or;}

"xor" {lexema=yytext(); return log_xor;}

"not" {lexema=yytext(); return log_not;}

"<" {lexema=yytext(); return rel_menor;}

">" {lexema=yytext(); return rel_mayor;}

"<=" {lexema=yytext(); return rel_menor_igual;}

">=" {lexema=yytext(); return rel_mayor_igual;}

"<>" {lexema=yytext(); return rel_diferente;}

\=\= {lexema=yytext(); return rel_igual;}

";" {lexema=yytext(); return sig_punto_coma;}

"(" {lexema=yytext(); return par_abre;}

")" {lexema=yytext(); return par_cierra;}

"{" {lexema=yytext(); return llave_abre;}

"}" {lexema=yytext(); return llave_cierra;}

"[" {lexema=yytext(); return cor_abre;}

"]" {lexema=yytext(); return cor_cierra;}

":" {lexema=yytext(); return sim_dos_puntos;}

{Letra}({Letra}|{Digito}|_)* {lexema=yytext(); return Identificador;}

{numero} {lexema=yytext(); return Numero;}

{numero}{Iden}+({numero}|{Iden}|{NoSimbo})* {lexema=yytext(); return Numero_incorrecto1;}

{SimboCadena}+{numero}|{numero}{SimboCadena}+ {lexema=yytext(); return Numero_incorrecto3;}

{flotante}(\.|\.{numero})+{numero}* {lexema=yytext(); return Numero_incorrecto2;}

"\'"{numero} | {numero}"\'" | "\'"{numero}{Iden}{numero} | "\'"{numero}{Iden} {lexema=yytext(); return Numero_incorrecto4;}

{Iden}{NoSimbo}+{Iden}* | "\."{Iden} | {SimboCadena}+{Iden}|"\'"{Iden} | {Iden}"\'" | {NoSimbo}+{Iden} {lexema=yytext(); return Identificador_no_valido;}

{simbo_punto} {lexema=yytext(); return Error_simbo_punto;}

{NoSimbo}+ | "\_" | "\"" {lexema=yytext(); return Error_simbolo_no_valido;}

{numero}{NoSimbo}+{numero} | {numero}{NoSimbo}+ | {numero}"\." {lexema=yytext(); return Error_numerico;}

 . {lexema=yytext(); return ERROR;}



