
import java_cup.runtime.Symbol;

%%

%cup

%char
%line

%{
  private Symbol token(int id, Object value) {
    return new Symbol(id, yychar, yyline, value);
  }

  private Symbol token(int id) {
    return token(id, yytext());
  }
%}

%eofval{
  return token(sym.EOF);
%eofval}

%%
//SECTION: IR related

true|false { 
  
  if (yytext() == "true") {

    return token(sym.B_LIT, new Integer(1));
  }

  return token(sym.B_LIT, new Integer(0));  
}

"[" { return token(sym.LS_PAR); }
"]" { return token(sym.RS_PAR); }
"(" { return token(sym.L_PAR); }
")" { return token(sym.R_PAR); }
":" { return token(sym.COL); }
"," { return token(sym.COMMA); }
"-" { return token(sym.NEG); }

"id" { return token(sym.ID_K); }
"var" { return token(sym.VAR); }
"fun" { return token(sym.FUN); }
"function" { return token(sym.FUNCTION); }

"int" { return token(sym.INT); }
"bool" { return token(sym.BOOL); }
"void" { return token(sym.VOID); }

t[0-9]+ { return token(sym.I_TEMP); }
l[0-9]+ { return token(sym.LABEL); }

"<-" { return token(sym.IR_STORE); }

//SECTION: Int
i_add { return token(sym.I_ADD); }
i_sub { return token(sym.I_SUB); }
i_mul { return token(sym.I_MUL); }
i_div { return token(sym.I_DIV); }
i_inv { return token(sym.I_INV); }

i_eq  { return token(sym.I_EQ); }
i_ne  { return token(sym.I_NE); }
i_lt  { return token(sym.I_LT); }
i_le  { return token(sym.I_LE); }

i_copy { return token(sym.I_COPY); }

mod   { return token(sym.MOD); }

not   { return token(sym.NOT); }

i_value { return token(sym.I_VAL); }

i_gload { return token(sym.I_GLOAD); }
i_lload { return token(sym.I_LLOAD); }
i_aload { return token(sym.I_ALOAD); }

i_gstore { return token(sym.I_GSTORE); }
i_lstore { return token(sym.I_LSTORE); }
i_astore { return token(sym.I_ASTORE); }

i_call { return token(sym.I_CALL); }
i_return { return token(sym.I_RET); }

//SECTION: Control Flow

jump { return token(sym.JUMP); }
cjump { return token(sym.CJUMP); }

//SECTION: Procedure

call { return token(sym.P_CALL); }
return { return token(sym.P_RET); }

//SECTION: IO

i_print { return token(sym.I_PRINT); }
b_print { return token(sym.B_PRINT); }
i_read { return token(sym.I_READ); }
b_read { return token(sym.B_READ); }

[\ \t\n]+		{ /* and whitespace */ }

[0-9]+ { return token(sym.I_LIT, new Integer(yytext())); }
\@[_a-zA-Z][_a-zA-Z0-9]* { return token(sym.ID, yytext()); }
