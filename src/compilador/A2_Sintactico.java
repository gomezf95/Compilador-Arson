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
        int errortipo;
        //System.out.println(entrada[posicion] + " " + tope.elemento);

        switch (tope.elemento) {

            //Incluir aqui las reglas}
            case "Clase":
                switch (entrada[posicion]) {
                    case "package":
                        desapilar();
                        apilar("Clase'");
                        apilar("Imp");
                        apilar("Pack");
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
                        errortipo = 1;
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
                        apilar("public");
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Pack":
                switch (entrada[posicion]) {
                    case "package":
                        desapilar();
                        apilar(";");
                        apilar("id");
                        apilar("package");
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Imp":
                switch (entrada[posicion]) {
                    case "import":
                        desapilar();
                        apilar("Imp'");
                        break;
                    case "class":
                    case "public":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Imp'":
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
                        errortipo = 1;
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
                        errortipo = 1;
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
                        errortipo = 1;
                }
                break;

            case "At":
                switch (entrada[posicion]) {
                    case ")":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;
            case "Fn":
                switch (entrada[posicion]) {
                    case "public":
                    case "private":
                    case "protected":
                    case "void":
                    case "int":
                    case "float":
                    case "String":
                        desapilar();
                        apilar("Fn'");
                        break;
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Fn'":
                switch (entrada[posicion]) {
                    case "public":
                    case "private":
                    case "protected":
                        apilar("Fn'");
                        apilar("Fn3");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case "void":
                    case "int":
                    case "float":
                    case "String":
                        apilar("Fn'");
                        apilar("Fn2");
                        apilar("id");
                        apilar(entrada[posicion]);
                        break;
                    case "id":
                    case ";":
                    case "$":
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Fn2":
                switch (entrada[posicion]) {
                    case "(":
                        desapilar();
                        apilar("Fn3");
                        break;
                    case "=":
                        desapilar();
                        apilar("V");
                        break;
                    case "void":
                    case "int":
                    case "float":
                    case "String":
                    case "id":
                    case ";":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Fn3":
                switch (entrada[posicion]) {
                    case "(":
                        desapilar();
                        apilar("}");
                        apilar("In");
                        apilar("{");
                        apilar(")");
                        apilar("At");
                        apilar("(");
                        break;
                    case "void":
                    case "int":
                    case "float":
                    case "String":
                    case "id":
                    case "$":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "In":
                switch (entrada[posicion]) {
                    case "int":
                        desapilar();
                        apilar("In'");
                    case "}":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;
                
            case "In'":
                switch (entrada[posicion]) {
                    case "int":
                        desapilar();
                        apilar("V");
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;
                
            case "V":
                switch (entrada[posicion]) {
                    case "=":
                        desapilar();
                        apilar(";");
                        apilar("E1");
                        apilar("=");
                        break;
                    case "int":
                        desapilar();
                        apilar(";");
                        apilar("Vint");
                        apilar("id");
                        apilar("int");
                        break;
                    case "float":
                        desapilar();
                        apilar(";");
                        apilar("Vfloat");
                        apilar("id");
                        apilar("float");
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Vint":
                switch (entrada[posicion]) {
                    case "=":
                        desapilar();
                        apilar("E1");
                        apilar("=");
                        break;
                    case ";":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
                }
                break;

            case "Vfloat":
                switch (entrada[posicion]) {
                    case "=":
                        desapilar();
                        apilar("F1");
                        apilar("=");
                        break;
                    case ";":
                        desapilar();
                        break;
                    default:
                        error = true;
                        errortipo = 1;
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
                        errortipo = 1;
                }
                break;
            case "E1'":
                switch (entrada[posicion]) {
                    case "+":
                    case "-":
                        desapilar();
                        apilar("E1'");
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
                        errortipo = 2;
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
                        errortipo = 3;
                }
                break;
            case "E2'":
                switch (entrada[posicion]) {
                    case "*":
                    case "/":
                        desapilar();
                        apilar("E2'");
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
                        errortipo = 4;
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
                        errortipo = 3;
                }
                break;
            case "E3'":
                switch (entrada[posicion]) {
                    case "^":
                    case "%":
                        desapilar();
                        apilar("E3'");
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
                        errortipo = 4;
                }
                break;
            case "E4":
                switch (entrada[posicion]) {
                    case "ent":
                    case "id":
                        desapilar();
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
                        errortipo = 5;
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
                        errortipo = 1;
                }
                break;
            case "F1'":
                switch (entrada[posicion]) {
                    case "+":
                    case "-":
                        desapilar();
                        apilar("F1'");
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
                        errortipo = 2;
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
                        errortipo = 3;
                }
                break;
            case "F2'":
                switch (entrada[posicion]) {
                    case "*":
                    case "/":
                        desapilar();
                        apilar("F2'");
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
                        errortipo = 4;
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
                        errortipo = 3;
                }
                break;
            case "F3'":
                switch (entrada[posicion]) {
                    case "^":
                    case "%":
                        desapilar();
                        apilar("F3'");
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
                        errortipo = 4;
                }
                break;
            case "F4":
                switch (entrada[posicion]) {
                    case "ent":
                    case "flot":
                    case "id":
                        desapilar();
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
                        errortipo = 5;
                }
                break;
            default:
                error = true;
                errortipo = 0;

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
            System.out.print("Error sintactico");
        } else {
            tope = null;
        }
    }

    private void guardar(String entrada2[], int posicion) {
        Pila pilaaux = tope;
        String cadenaaux = "";
        while (pilaaux != null) {
            cadenaaux = pilaaux.elemento + cadenaaux;
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
