PARSER = IR

JAVAC = javac -classpath $(CLASSPATH)
JAVA = java -classpath $(CLASSPATH)
YACC = $(CUP) -parser $(PARSER) -nosummary
LEX = jflex -q

CUP = cup
# or
# CUP = java -classpath $(CUP_JAR) java_cup.Main
CUP_JAR = ./cup.jar

CLASSPATH = $(CUP_JAR):

$(PARSER).class : $(PARSER).java Yylex.java ./classes/nodes/*.java ./classes/other/*.java
	$(JAVAC) $^

$(PARSER).java sym.java : ir.cup
	@echo "* ignore the message regarding terminal symbol ERROR" >&2
	$(YACC) $^

Yylex.java : ir.lex
	$(LEX) $^


run : $(PARSER).class
	@$(JAVA) $(PARSER) < test.in

clean: 
	rm *.class
	rm *.java~
	cd classes/nodes/
	rm *.class
	cd ../other
	rm *.class