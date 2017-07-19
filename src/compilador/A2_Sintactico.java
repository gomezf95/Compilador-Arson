package compilador;

import java.util.StringTokenizer;

public class A2_Sintactico {

    private Pila tope = new Pila("$");
    private Nodo arbol = new Nodo("inicio", null, 0);
    private int contadorlineas, nivel = 0;
    private String pila = "", entrada = "";

    private void maquina(String entrada[], int posicion) {
        guardar(entrada, posicion);
        boolean error = false;
        String errortipo="";
        //System.out.println(entrada[posicion] + " " + tope.elemento);

        switch (tope.elemento) {

            //Incluir aqui las reglas}
            case "Clase":
                switch (entrada[posicion]) {
                    case "package":
                        desapilar();
                        apilar("Clase'");
                        apilar("Imp");
                        apilar(";");
                        apilar("id");
                        apilar("package");
                        break;
                    case "import":
                        desapilar();
                        apilar("Clase'");
                        apilar("Imp");
                        break;
                    case "class":
                    case "public":
                        desapilar();
                        apilar("Clase'");
                        break;
                    default:
                        error = true;
                        errortipo = "No se ha podido construir la Clase (1)";
                }
                break;
                
            case "Clase'":
                switch (entrada[posicion]) {
                    case "class":
                        desapilar();
                        apilar("}");
                        apilar("Fn");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        apilar("id");
                        apilar("class");
                        break;
                    case "public":
                        desapilar();
                        apilar("}");
                        apilar("Fn");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        apilar("id");
                        apilar("class");
                        apilar("public");
                        break;
                    default:
                        error = true;
                        errortipo = "No se ha podido construir la Clase (2)";;
                }
                break;

            case "Imp":
                switch (entrada[posicion]) {
                    case "import":
                        apilar(";");
                        apilar("Dir");
                        apilar("import");
                        break;
                    case "class":
                    case "public":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se ha podido construir la Clase (3)";;
                }
                break;

            case "Dir":
                switch (entrada[posicion]) {
                    case "id":
                        desapilar();
                        apilar("Dir'");
                        apilar("id");
                        break;
                    default:
                        error = true;
                        errortipo = "La Direccion de este import no es correcta (1)";;
                }
                break;

            case "Dir'":
                switch (entrada[posicion]) {
                    case ".":
                        apilar("id");
                        apilar(".");
                        break;
                    case ";":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "La Direccion de este import no es correcta (2)";
                }
                break;

            case "At":
                switch (entrada[posicion]) {
                    case "int":
                    case "float":
                    case "String":
                    case "char":
                    case "double":
                    case "id":
                        desapilar();
                        apilar("At'");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen los atributos(1)";
                }
                break;
                
            case "At'":
                switch (entrada[posicion]) {
                    case ",":
                        apilar("At2");
                        apilar(",");
                        break;
                    case "[":
                        apilar("]");
                        apilar("[");
                        break;
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen los atributos(2)";
                }
                break;
                
            case "At2":
                switch (entrada[posicion]) {
                    case "int":
                    case "float":
                    case "String":
                    case "char":
                    case "double":
                    case "id":
                        desapilar();
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen los atributos(3)";
                }
                break;

            case "Pa":
                switch (entrada[posicion]) {
                    case "id":
                    case "cad":
                    case "car":
                    case "ent":
                    case "flot":
                        desapilar();
                        apilar("Pa'");
                        apilar(entrada[posicion]);
                        break;
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen los parametros (1)";
                }
                break;

            case "Pa'":
                switch (entrada[posicion]) {
                    case ",":
                        apilar("Pa2");
                        apilar(",");
                        break;
                    case "[":
                        apilar("]");
                        apilar("E1");
                        apilar("[");
                        break;
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen los parametros (2)";
                }
                break;
                
            case "Pa2":
                switch (entrada[posicion]) {
                    case "id":
                    case "cad":
                    case "car":
                    case "ent":
                    case "flot":
                        desapilar();
                        apilar(entrada[posicion]);
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen los parametros (3)";
                }
                break;

            case "Fn":
                switch (entrada[posicion]) {
                    case "public":
                        apilar("Fnp");
                        apilar("public");
                        break;
                    case "private":
                    case "protected":
                        apilar("Fn'");
                        apilar(entrada[posicion]);
                        break;
                    case "void":
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        apilar("id");
                        apilar("void");
                        break;
                    case "int":
                        apilar("Fnent");
                        apilar("id");
                        apilar("int");
                        break;
                    case "float":
                    case "double":
                        apilar("Fnflot");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case "String":
                        apilar("Fncad");
                        apilar("id");
                        apilar("String");
                        break;
                    case "char":
                        apilar("Fncar");
                        apilar("id");
                        apilar("char");
                        break;
                    case "id":
                        apilar("O");
                        apilar("id");
                        break;
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion (1)";
                }
                break;

            case "Fn'":
                switch (entrada[posicion]) {
                    case "void":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        apilar("id");
                        apilar("void");
                        break;
                    case "int":
                        desapilar();
                        apilar("Fnent");
                        apilar("id");
                        apilar("int");
                        break;
                    case "float":
                    case "double":
                        desapilar();
                        apilar("Fnflot");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case "String":
                        desapilar();
                        apilar("Fncad");
                        apilar("id");
                        apilar("String");
                        break;
                    case "char":
                        desapilar();
                        apilar("Fncar");
                        apilar("id");
                        apilar("char");
                        break;
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion (2)";
                }
                break;

            case "Fnp":
                switch (entrada[posicion]) {
                    case "class":
                        desapilar();
                        apilar("Clase'");
                        break;
                    case "void":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        apilar("id");
                        apilar("void");
                        break;
                    case "int":
                        desapilar();
                        apilar("Fnent");
                        apilar("id");
                        apilar("int");
                        break;
                    case "float":
                    case "double":
                        desapilar();
                        apilar("Fnflot");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case "String":
                        desapilar();
                        apilar("Fncad");
                        apilar("id");
                        apilar("String");
                        break;
                    case "char":
                        desapilar();
                        apilar("Fncar");
                        apilar("id");
                        apilar("char");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion (3)";
                }
                break;

            case "Fnent":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        desapilar();
                        apilar("Fnent'");
                        apilar("Terminal");
                        apilar("E1");
                        apilar("=");
                        break;
                    case ",":
                        desapilar();
                        apilar("Fnent'");
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        desapilar();
                        apilar("Fnent'");
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case "(":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como entera (1)";
                }
                break;

            case "Fnent'":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        apilar("Terminal");
                        apilar("E1");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como entera (2)";
                }
                break;

            case "Fnflot":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        desapilar();
                        apilar("Fnflot'");
                        apilar("Terminal");
                        apilar("F1");
                        apilar("=");
                        break;
                    case ",":
                        desapilar();
                        apilar("Fnflot'");
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        desapilar();
                        apilar("Fnflot'");
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case "(":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como flotante (1)";
                }
                break;

            case "Fnflot'":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        apilar("Terminal");
                        apilar("F1");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como flotante (2)";
                }
                break;

            case "Fncad":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        desapilar();
                        apilar("Fncad'");
                        apilar("Terminal");
                        apilar("S");
                        apilar("=");
                        break;
                    case ",":
                        desapilar();
                        apilar("Fncad'");
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        desapilar();
                        apilar("Fncad'");
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case "(":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como cadena (1)";
                }
                break;

            case "Fncad'":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        apilar("Terminal");
                        apilar("S");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como cadena (2)";
                }
                break;

            case "Fncar":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        desapilar();
                        apilar("Fncar'");
                        apilar("Terminal");
                        apilar("C");
                        apilar("=");
                        break;
                    case ",":
                        desapilar();
                        apilar("Fncar'");
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        desapilar();
                        apilar("Fncar'");
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case "(":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como caracter (1)";
                }
                break;

            case "Fncar'":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    case "=":
                        apilar("Terminal");
                        apilar("C");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la funcion/variable como caracter (2)";
                }
                break;

            case "Terminal":
                switch (entrada[posicion]) {
                    case ";":
                    case ",":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "Asignacion esperaba ',' o ';'";
                }
                break;

            case "Terminal2":
                switch (entrada[posicion]) {
                    case ";":
                    case ",":
                    case "=":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "Declaracion de variable esperaba ',' ';' o '='";
                }
                break;

            case "In":
                switch (entrada[posicion]) {
                    case "int":
                        apilar("Vent");
                        apilar("id");
                        apilar("int");
                        break;
                    case "float":
                    case "double":
                        apilar("Vflot");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case "String":
                        apilar("Vcad");
                        apilar("id");
                        apilar("String");
                        break;
                    case "char":
                        apilar("Vcar");
                        apilar("id");
                        apilar("char");
                        break;
                    case "id":
                        apilar("In'");
                        apilar("id");
                        break;
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "Insruccion no reconocida(1)";
                }
                break;

            case "In'":
                switch (entrada[posicion]) {
                    case "(":
                        desapilar();
                        apilar(";");
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        break;
                    case "=":
                    case "++":
                    case "--":
                        desapilar();
                        apilar("O");
                        break;
                    case "id":
                        desapilar();
                        apilar(";");
                        apilar("Per");
                        apilar("id");
                        break;
                    default:
                        error = true;
                        errortipo = "Insruccion no reconocida(2)";
                }
                break;

            case "O":
                switch (entrada[posicion]) {
                    case "++":
                    case "--":
                        desapilar();
                        apilar(";");
                        apilar(entrada[posicion]);
                        break;
                    case "=":
                        desapilar();
                        apilar(";");
                        apilar("Op");
                        apilar("=");
                        break;
                    case "id":
                        desapilar();
                        apilar(";");
                        apilar("Per");
                        apilar("id");
                        break;
                    default:
                        error = true;
                        errortipo = "Operacion en identificador desconocida";
                }
                break;

            case "Op":
                switch (entrada[posicion]) {
                    case "id":
                        desapilar();
                        apilar("Op2");
                        apilar("id");
                        break;
                    case "ent":
                    case "flot":
                    case "cad":
                    case "car":
                        desapilar();
                        apilar("Op'");
                        apilar(entrada[posicion]);
                        break;
                    default:
                        error = true;
                        errortipo = "Operacion en igualar identificador desconocida(1)";
                }
                break;

            case "Op'":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                    case "%":
                        desapilar();
                        apilar("Op");
                        apilar(entrada[posicion]);
                        break;
                    default:
                        error = true;
                        errortipo = "Operacion en igualar identificador desconocida(2)";
                }
                break;
                
            case "Op2":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        break;
                    case "(":
                        desapilar();
                        apilar("Op'");
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                    case "%":
                        desapilar();
                        apilar("Op");
                        apilar(entrada[posicion]);
                        break;
                    default:
                        error = true;
                        errortipo = "Operacion en funcion desconocida";
                }
                break;

            case "Per":
                switch (entrada[posicion]) {
                    case ";":
                        desapilar();
                        break;
                    case "=":
                        desapilar();
                        apilar("Per'");
                        apilar("=");
                        break;
                    default:
                        error = true;
                        errortipo = "Operacion en tipo personalizado desconocida(1)";
                }
                break;
                
            case "Per'":
                switch (entrada[posicion]) {
                    case "Id":
                        desapilar();
                        apilar("Per2");
                        break;
                    case "new":
                        desapilar();
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        apilar("id");
                        apilar("new");
                        break;
                    default:
                        error = true;
                        errortipo = "Operacion en tipo personalizado desconocida(2)";
                }
                break;
                
            case "Vent":
                switch (entrada[posicion]) {
                    case "=":
                        apilar("Terminal");
                        apilar("E");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Cor");
                        break;
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la variable como entero";
                }
                break;

            case "Vflot":
                switch (entrada[posicion]) {
                    case "=":
                        apilar("Terminal");
                        apilar("F");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la variable como flotante";
                }
                break;

            case "Vcad":
                switch (entrada[posicion]) {
                    case "=":
                        apilar("Terminal");
                        apilar("S");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la variable como cadena";
                }
                break;

            case "Vcar":
                switch (entrada[posicion]) {
                    case "=":
                        apilar("Terminal");
                        apilar("C");
                        apilar("=");
                        break;
                    case ",":
                        apilar("id");
                        apilar(",");
                        break;
                    case "[":
                        apilar("Terminal2");
                        apilar("Cor");
                        break;
                    case ";":
                        desapilar();
                        apilar(";");
                        break;
                    default:
                        error = true;
                        errortipo = "No se reconocen la variable como caracter";
                }
                break;

            case "Cor":
                switch (entrada[posicion]) {
                    case "[":
                        apilar("]");
                        apilar("[");
                        break;
                    case ",":
                    case ";":
                    case "=":
                    case ")":
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "Cerradura incorrecta de corcheas";
                }
                break;

            case "Cor2":
                switch (entrada[posicion]) {
                    case "[":
                        apilar("]");
                        apilar("E1");
                        apilar("[");
                        break;
                    case ";":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "Cerradura incorrecta de corcheas con parametro";
                }
                break;

            case "E":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "id":
                        desapilar();
                        apilar("E1");
                        break;
                    case "new":
                        desapilar();
                        apilar("Cor2");
                        apilar("]");
                        apilar("E1");
                        apilar("[");
                        apilar("int");
                        apilar("new");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (1)";
                }
                break;

            case "E1":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "id":
                        desapilar();
                        apilar("E1'");
                        apilar("E2");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (2)";
                }
                break;

            case "E1'":
                switch (entrada[posicion]) {
                    case "+":
                    case "-":
                        apilar("E2");
                        apilar(entrada[posicion]);
                        break;
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (3)";
                }
                break;
                
            case "E2":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "id":
                        desapilar();
                        apilar("E2'");
                        apilar("E3");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (4)";
                }
                break;
                
            case "E2'":
                switch (entrada[posicion]) {
                    case "*":
                    case "/":
                        apilar("E3");
                        apilar(entrada[posicion]);
                        break;
                    case "+":
                    case "-":
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (5)";
                }
                break;
                
            case "E3":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "id":
                        desapilar();
                        apilar("E3'");
                        apilar("E4");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (6)";
                }
                break;
                
            case "E3'":
                switch (entrada[posicion]) {
                    case "^":
                    case "%":
                        apilar("E4");
                        apilar(entrada[posicion]);
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;

                    default:
                        error = true;
                        errortipo = "No es un entero (7)";
                }
                break;
                
            case "E4":
                switch (entrada[posicion]) {
                    case "ent":
                        desapilar();
                        apilar(entrada[posicion]);
                        break;
                    case "id":
                        desapilar();
                        apilar("Ef");
                        apilar(entrada[posicion]);
                        break;
                    case "(":
                        desapilar();
                        apilar(")");
                        apilar("E1");
                        apilar("(");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un entero (8)";
                }
                break;

            case "Ef":
                switch (entrada[posicion]) {
                    case "(":
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        break;
                    case "^":
                    case "%":
                        apilar("E4");
                        apilar(entrada[posicion]);
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;

                    default:
                        error = true;
                        errortipo = "No es una funcion entero";
                }
                break;

            case "F":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "flot":
                    case "id":
                        desapilar();
                        apilar("F1");
                        break;
                    case "new":
                        desapilar();
                        apilar("Cor2");
                        apilar("]");
                        apilar("E1");
                        apilar("[");
                        apilar("double");
                        apilar("new");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (1)";
                }
                break;

            case "F1":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "flot":
                    case "id":
                        desapilar();
                        apilar("F1'");
                        apilar("F2");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (2)";
                }
                break;
                
            case "F1'":
                switch (entrada[posicion]) {
                    case "+":
                    case "-":
                        apilar("F2");
                        apilar(entrada[posicion]);
                        break;
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (3)";
                }
                break;

            case "F2":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "flot":
                    case "id":
                        desapilar();
                        apilar("F2'");
                        apilar("F3");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (4)";
                }
                break;
                
            case "F2'":
                switch (entrada[posicion]) {
                    case "*":
                    case "/":
                        apilar("F3");
                        apilar(entrada[posicion]);
                        break;
                    case "+":
                    case "-":
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (5)";
                }
                break;
                
            case "F3":
                switch (entrada[posicion]) {
                    case "(":
                    case "ent":
                    case "flot":
                    case "id":
                        desapilar();
                        apilar("F3'");
                        apilar("F4");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (6)";
                }
                break;
                
            case "F3'":
                switch (entrada[posicion]) {
                    case "^":
                    case "%":
                        apilar("F4");
                        apilar(entrada[posicion]);
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case ")":
                    case "]":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (7)";
                }
                break;
                
            case "F4":
                switch (entrada[posicion]) {
                    case "ent":
                    case "flot":
                        desapilar();
                        apilar(entrada[posicion]);
                        break;
                    case "id":
                        desapilar();
                        apilar("Ff");
                        apilar(entrada[posicion]);
                        break;
                    case "(":
                        desapilar();
                        apilar(")");
                        apilar("F1");
                        apilar("(");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un flotante (8)";
                }
                break;

            case "Ff":
                switch (entrada[posicion]) {
                    case "(":
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        break;
                    case "^":
                    case "%":
                        apilar("F4");
                        apilar(entrada[posicion]);
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "]":
                    case ")":
                    case ",":
                    case ";":
                    case "$":
                        desapilar();
                        break;

                    default:
                        error = true;
                        errortipo = "No es una funcion flotante";
                }
                break;

            case "S":
                switch (entrada[posicion]) {
                    case "cad":
                        desapilar();
                        apilar("S'");
                        apilar("cad");
                        break;
                    case "id":
                        desapilar();
                        apilar("S2");
                        apilar("id");
                        break;
                    case "new":
                        desapilar();
                        apilar("Cor2");
                        apilar("]");
                        apilar("E1");
                        apilar("[");
                        apilar("String");
                        apilar("new");
                        break;
                    default:
                        error = true;
                        errortipo = "No es una cadena (1)";
                }
                break;

            case "S'":
                switch (entrada[posicion]) {
                    case "+":
                        desapilar();
                        apilar("S");
                        apilar("+");
                        break;
                    case ";":
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es una cadena (2)";
                }
                break;

            case "S2":
                switch (entrada[posicion]) {
                    case "+":
                        desapilar();
                        apilar("S");
                        apilar("+");
                        break;
                    case "(":
                        desapilar();
                        apilar("S'");
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        break;
                    case ";":
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es una cadena (3)";
                }
                break;

            case "C":
                switch (entrada[posicion]) {
                    case "car":
                        desapilar();
                        apilar("car");
                        break;
                    case "id":
                        desapilar();
                        apilar("C2");
                        apilar("id");
                        break;
                    case "new":
                        desapilar();
                        apilar("Cor2");
                        apilar("]");
                        apilar("E1");
                        apilar("[");
                        apilar("char");
                        apilar("new");
                        break;
                    default:
                        error = true;
                        errortipo = "No es un caracter (1)";
                }
                break;

            case "C2":
                switch (entrada[posicion]) {
                    case "(":
                        desapilar();
                        apilar(")");
                        apilar("Pa");
                        apilar("(");
                        break;
                    case ";":
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = "No es un caracter (2)";
                }
                break;
            default:
                error = true;
                errortipo = "Estructura incorrecta";

            //Hasta aqui agregar reglas
        }
        if (entrada[posicion].equals(tope.elemento)) {
            do {
                Nodo nuevonodo = new Nodo(entrada[posicion], arbol, nivel);
                arbol.siguiente = nuevonodo;
                arbol = nuevonodo;
                guardar(entrada, posicion);
                desapilar();
                posicion++;
            } while (posicion < entrada.length && entrada[posicion].equals(tope.elemento));
        }
        if (posicion < entrada.length && !error) {
            maquina(entrada, posicion);
        } else if (error) {
            System.out.println(errortipo);
            System.out.println("Pila Tope:        \t"+tope.elemento);
            System.out.println("Entrada Actual("+posicion+"):\t"+entrada[posicion]+"\n");
            tope = null;
        } else {
            tope = null;
        }
    }

    private void guardar(String entrada2[], int posicion) {
        Pila pilaaux = tope;
        String cadenaaux = "";
        while (pilaaux != null) {
            cadenaaux = pilaaux.elemento + " " + cadenaaux;
            pilaaux = pilaaux.siguiente;
        }
        pila += cadenaaux + "\n";
        cadenaaux = "";
        for (int i = posicion; i < entrada2.length; i++) {
            cadenaaux += entrada2[i] + " ";
        }
        entrada += cadenaaux + "\n";
        contadorlineas++;
    }

    public String[][] sintacticoMatriz() {
        String[][] sintaxis = new String[contadorlineas][2];
        StringTokenizer st1 = new StringTokenizer(pila, "\n");
        StringTokenizer st2 = new StringTokenizer(entrada, "\n");
        for (int i = 0; i < contadorlineas; i++) {
            sintaxis[i][0] = st1.nextToken();
            System.out.println(sintaxis[i][0]);
            sintaxis[i][1] = st2.nextToken();
        }
        return sintaxis;
    }

    public String arbol() {
        String cadenaArbol = "";
        while (arbol != null && arbol.anterior != null) {
            arbol = arbol.anterior;
        }
        while (arbol != null) {
            for (int i = 0; i < arbol.nivel; i++) {
                cadenaArbol += "  ";
            }
            cadenaArbol += arbol.nodo + "\n";
            arbol = arbol.siguiente;
        }
        return cadenaArbol;
    }

    private void apilar(String elemento) {
        Pila nuevotope = new Pila(elemento);
        nuevotope.siguiente = tope;
        tope = nuevotope;

        nivel++;

    }

    private void desapilar() {
        tope = tope.siguiente;
        nivel--;
    }

    public A2_Sintactico(String[] lexemas) {
        apilar("Clase");
        maquina(lexemas, 0);
    }

    public class Pila {

        private final String elemento;
        private Pila siguiente = null;

        Pila(String elemento2) {
            elemento = elemento2;
        }
    }

    public class Nodo {

        private final String nodo;
        private final Nodo anterior;
        private final int nivel;
        private Nodo siguiente = null;

        Nodo(String elemento, Nodo anterior2, int nivel2) {
            nodo = elemento;
            anterior = anterior2;
            nivel = nivel2;
        }
    }
}