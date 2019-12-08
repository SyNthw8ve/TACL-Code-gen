
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

i_add { return token(sym.I_ADD); }
i_sub { return token(sym.I_SUB); }
i_mul { return token(sym.I_MUL); }
i_div { return token(sym.I_DIV); }



""	{ return token(sym.ID /* ... */); }

#.*			{ /* ignore comments */ }

[\ \t\n]+		{ /* and whitespace */ }
