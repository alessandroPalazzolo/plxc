import java_cup.runtime.*;

%%
%cup 
%%

// math operators
"+" { return new Symbol(sym.SUMA); }
"-" { return new Symbol(sym.SUBSTRACTION); }
"=" { return new Symbol(sym.ASIGNA); }
"*" { return new Symbol(sym.PRODUCT); }
"/" { return new Symbol(sym.DIVISION); }

// logic operators
"==" { return new Symbol(sym.EQUALS); }
"!=" { return new Symbol(sym.N_EQUALS); }
">" { return new Symbol(sym.GREATER); }
"<" { return new Symbol(sym.LESS); }
">=" { return new Symbol(sym.GREATER_EQ); }
"<=" { return new Symbol(sym.LESS_EQ); }
"&&" { return new Symbol(sym.AND); }
"||" { return new Symbol(sym.OR); }
"!" { return new Symbol(sym.NOT); }
"<-->" { return new Symbol(sym.BIMPLIES); }

// syntax
";" { return new Symbol(sym.PCOMA); }
"{" { return new Symbol(sym.A_LLAVE); }
"}" { return new Symbol(sym.C_LLAVE); }
"," { return new Symbol(sym.COMA); }
"(" { return new Symbol(sym.AP); }
")" { return new Symbol(sym.CP); }

// statements
"print" { return new Symbol(sym.PRINT); }
"if" { return new Symbol(sym.IF); }
"else if" { return new Symbol(sym.ELSEIF); }
"else" { return new Symbol(sym.ELSE); }
"do" { return new Symbol(sym.DO); }
"while" { return new Symbol(sym.WHILE); }
"for" { return new Symbol(sym.FOR); }
"exists" { return new Symbol(sym.EXISTS); }
"from" { return new Symbol(sym.FROM); }
"to" { return new Symbol(sym.TO); }
"step" { return new Symbol(sym.STEP); }

// types 
"int" { return new Symbol(sym.INT); }
"char" { return new Symbol(sym.CHAR); }
"float" { return new Symbol(sym.FLOAT); }
"boolean" { return new Symbol(sym.BOOLEAN); }

// variables
"true" { return new Symbol(sym.TRUE); }
"false" { return new Symbol(sym.FALSE); }
[0-9]+ { return new Symbol(sym.ENTERO, yytext()); }
[0-9]+\.[0-9]+(E[+-][0-9]+)? { return new Symbol(sym.REAL, yytext()); }
[_a-zA-Z][_a-zA-Z0-9]* { 
    if (!PLXC.tabla.contiene(yytext())){
        return new Symbol(sym.ID, yytext());
    }
    if (((Instancia)(PLXC.tabla.objeto(yytext()))).getTipo().toString() != "boolean"){
         return new Symbol(sym.ID, yytext());
    }
    return new Symbol(sym.ID_BOOL, yytext());
}
[’'`‘][^’][’'`‘] { return new Symbol(sym.CHARACTER, yytext()); }
// [’'`‘]\\[bnfrt\\\"\‘][’'`‘] { return new Symbol(sym.ESCAPEDCHAR, yytext()); }
['`]\\[bnfrt\\\"]['`] { return new Symbol(sym.ESCAPEDCHAR, yytext()); }
[’'`‘]\\u[0-9a-f]{4}[’'`‘] { return new Symbol(sym.UNICODECHAR, yytext()); }

// trash
[^] {}