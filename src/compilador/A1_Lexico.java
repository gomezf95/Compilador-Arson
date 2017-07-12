package compilador;

import java.util.StringTokenizer;

public final class A1_Lexico {

    //Se declara un "eslabon inicial de Token" del que se accedera a los demas
    //es importante denotat  que se inicializan sus valores con cadenas vacias, incluyendo el "atributo palabra"
    //Cada "nuevo token" se inicia con un "apuntador del siguiente eslabon" de tipo "Token" con valor nulo por defecto
    private Tokens eslabonToken = new Tokens("", "", "");
    
    //contadorToken cuenta cada Token evaluado para darle valor al indice
    //contadorSimbolo cuenta cada simbolo nuevo para dimencionar el String que se devolvera
    private int contadorToken = 0, contadorSimbolo = 0;
    
    //Para almacenar la cadena libre de espacios
    private String cadenaToken="";
    
    //Catalogador de palabras
    private int maquina(String codigo, int tipo, int posicion, String palabra) {
        
        //Salir si es el final del codigo
        if (codigo.length() > posicion) {
            //Recibe la letra que marca posicion y la almacena el caracter
            //Agregar es una bandera que indica la validez de una palabra que se puede agregar
            char caracter = codigo.charAt(posicion);
            boolean agregar = false;
            
            //Si es espacio, no es necesario evaluar el caracter, amenos que haya una excepcion
            if (!espacio(caracter) || excepcion(tipo)) {
                //Se agrega el caracter a la palabra que se esta formando
                palabra+=caracter;
                
                //Evaluacion de Tipo, inicia en 0 y va cambiando segun los caracteres
                switch (tipo) {
                    /*
                    case 0: Entra cuando la palabra solo tiene un caracter e inicia la evaluacion
                    Si el caracter es valido y no puede ser el inicial de una palabra mas grande,
                     entonces se añade sin mas sin cambiar su tipo, 
                    Si no, si es valido pero puede/debe ser parte de una palabra mas grande
                     entonces solo cambia su tipo para entrar a otro case en el siguiente ciclo
                    Si no, el caracter se invalido
                    */
                    case 0:
                        //Variable o palabra reserbada
                        if (letra(caracter)) {
                            tipo = 1;
                        //Inicio de octal, hexadecimal, binario o float (0.xxxx)
                        } else if (caracter == '0') {
                            tipo = 34;
                        //Entero o float
                        } else if (digito(caracter)) {
                            tipo = 2;
                        //Punto o floar(.xxxx)
                        } else if (caracter == '.') {
                            tipo = 33;
                        //cadena
                        } else if (caracter == '\"') {
                            tipo = 4;
                            palabra = "";
                        //caracter
                        } else if (caracter == '\'') {
                            tipo = 39;
                            palabra = "";
                            
                        //Otros simbolos
                        } else if (caracter == ';') {
                            agregar(6, ";");
                        } else if (caracter == ',') {
                            agregar(7, ",");
                        } else if (caracter == '(') {
                            agregar(10, "(");
                        } else if (caracter == ')') {
                            agregar(11, ")");
                        } else if (caracter == '[') {
                            agregar(12, "[");
                        } else if (caracter == ']') {
                            agregar(13, "]");
                        } else if (caracter == '{') {
                            agregar(14, "{");
                        } else if (caracter == '}') {
                            agregar(15, "}");
                        } else if (caracter == '=') {
                            tipo = 16;
                        } else if (caracter == '+') {
                            tipo = 17;
                        } else if (caracter == '-') {
                            tipo = 18;
                        } else if (caracter == '*') {
                            tipo = 19;
                        } else if (caracter == '/') {
                            tipo = 20;
                        } else if (caracter == '^') {
                            agregar(21, "^");
                        } else if (caracter == '%') {
                            agregar(22, "%");
                        } else if (caracter == '!') {
                            tipo = 26;
                        } else if (caracter == '>') {
                            tipo = 28;
                        } else if (caracter == '<') {
                            tipo = 29;
                        } else if (caracter == ':') {
                            agregar(32, ":");
                        } else if (caracter == '$') {
                            //agregar(40, "$");
                            return posicion;
                        } else if (caracter == '&') {
                            tipo = 45;
                        } else if (caracter == '|') {
                            tipo = 46;
                        } else if (caracter == '#') {
                            agregar(49, "#");
                        } else {
                            error(1, palabra);
                            return posicion;
                        }
                        break;

                    //Variable/Palabra Reservada
                    case 1:
                        if (caractervar(caracter)); else {
                            agregar = true;
                        }
                        break;

                    //Entero o escape a inicio de flotante
                    case 2:
                        if (digito(caracter)); else if (caracter == '.') {
                            tipo = 33;
                        } else {
                            agregar = true;
                        }
                        break;

                    //Flotante (luego del punto)
                    case 3:
                        if (digito(caracter)); else {
                            agregar = true;
                        }
                        break;
                    
                    //Cadena
                    case 4:
                        if (caracter == '\"') {
                            posicion++;
                            agregar = true;
                        } else if (codigo.length() > posicion + 1); else {
                            error(4, " \"" + palabra + " ");
                            return posicion;
                        }
                        break;

                    //Caracter (cerrar)
                    case 5:
                        if (caracter == '\'') {
                            posicion++;
                            agregar = true;
                        } else {
                            error(5, " '" + palabra + " ");
                            return posicion;
                        }
                        break;

                    //= o ==
                    case 16:
                        if (caracter == '=') {
                            agregar(25, "==");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    //+ , ++ o +=
                    case 17:
                        if (caracter == '+') {
                            agregar(23, "++");
                            tipo = 0;
                        } else if (caracter == '=') {
                            agregar(41, "+=");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    //- , -- , -= o ->
                    case 18:
                        if (caracter == '-') {
                            agregar(23, "--");
                            tipo = 0;
                        } else if (caracter == '=') {
                            agregar(42, "-=");
                            tipo = 0;
                        } else if (caracter == '>') {
                            agregar(50, "->");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    //* o *=
                    case 19:
                        if (caracter == '=') {
                            agregar(43, "*=");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    // '/', '/=' o  '//' '/*' '*/'(Comentarios)
                    case 20:
                        if (caracter == '=') {
                            agregar(44, "/=");
                            tipo = 0;
                            
                        // '//'
                        } else if (caracter == '/') {
                            do {
                                posicion++;
                                caracter = codigo.charAt(posicion);
                            } while (!salto(caracter) && codigo.length()
                                    > posicion + 1);
                            tipo = 0;
                            
                        // '/*'
                        } else if (caracter == '*') {
                            boolean bandera = false;
                            do {
                                posicion++;
                                caracter = codigo.charAt(posicion);
                                if (caracter == '*') {
                                    posicion++;
                                    caracter = codigo.charAt(posicion);
                                    if (caracter == '/') {
                                        bandera = true;
                                    } else {
                                        posicion--;
                                    }
                                }
                            } while (!bandera && codigo.length() > posicion + 1);
                            if (!bandera) {
                                error(99, "/*");
                                return posicion;
                            }
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    // ! o !=
                    case 26:
                        if (caracter == '=') {
                            agregar(27, "!=");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;
                        
                    // > , >= o escape a >>
                    case 28:
                        if (caracter == '=') {
                            agregar(30, ">=");
                            tipo = 0;
                        } else if (caracter == '>') {
                            tipo = 51;
                        } else {
                            agregar = true;
                        }
                        break;

                    // < , <= o escape a <<
                    case 29:
                        if (caracter == '=') {
                            agregar(31, "<=");
                            tipo = 0;
                        } else if (caracter == '<') {
                            tipo = 52;
                        } else {
                            agregar = true;
                        }
                        break;

                    //Punto o escape a float despues del punto
                    case 33:
                        if (digito(caracter)) {
                            tipo = 3;
                        } else {
                            agregar = true;
                        }
                        break;

                    //Inicio de octal, hexadecimal, binario o escape a float despues del punto
                    case 34:
                        if (octal(caracter)) {
                            tipo = 35;
                        } else if (caracter == 'x' || caracter == 'X') {
                            tipo = 36;
                        } else if (caracter == 'b' || caracter == 'B') {
                            tipo = 37;
                        } else if (caracter == '.') {
                            tipo = 3;
                        } else {
                            tipo = 2;
                            agregar = true;
                        }
                        break;

                    //Octal
                    case 35:
                        if (octal(caracter)); else {
                            agregar = true;
                        }
                        break;

                    //Hexadecimal
                    case 36:
                        if (hexadecimal(caracter)); else if (palabra.length() > 3) {
                            agregar = true;
                        } else {
                            error(36, palabra);
                            return posicion - 1;
                        }
                        break;

                    //Binario
                    case 37:
                        if (binario(caracter)); else if (palabra.length() > 3) {
                            agregar = true;
                        } else {
                            error(37, palabra);
                            return posicion - 1;
                        }
                        break;

                    //Caracter (Caracter entre las comillas ' ')
                    case 39:
                        if (caracter != '\'' && codigo.length() > posicion + 1) {
                            tipo = 5;
                        } else {
                            error(39, palabra);
                            return posicion;
                        }
                        break;
                    
                    // & o &&
                    case 45:
                        if (caracter == '&') {
                            agregar(47, "&&");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    // | o ||
                    case 46:
                        if (caracter == '|') {
                            agregar(48, "||");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    // >> o >>>
                    case 51:
                        if (caracter == '>') {
                            agregar(53, ">>>");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                        break;

                    // << o <<<
                    case 52:
                        if (caracter == '<') {
                            agregar(54, "<<<");
                            tipo = 0;
                        } else {
                            agregar = true;
                        }
                }
                
                //Si agregar esta activo, significa que se consiguio una palabra valida fuera del estado 0
                //Como a la palabra se le agrega un caracter en cada ciclo,
                //este caracter es el que debio funcionar como separador ( '.' , ',' . ';' etc)
                //por lo que antes de agregar la palabra, se quita este ultimo caracter
                //Se comprueba si la palabra es una palabra reservada
                //Finalmente, tipo se vuelve 0 y la posicion retrocede para evaluar el separador 
                if (agregar) {
                    palabra = palabra.substring(0, palabra.length() - 1);
                    if(tipo==1){
                    tipo = reservada(palabra, tipo);
                    }
                    agregar(tipo, palabra);
                    tipo = 0;
                    posicion--;
                }  
            //Si la palabra es un espacio y no hay excepcion en este caso
            //Si la palabra no es vacia, la palabra se agrega y el tipo se vuelve 0 para volver a evaluar desde 0
            } else if (!"".equals(palabra)) {
                if(tipo==1){
                tipo = reservada(palabra, tipo);
                }
                agregar(tipo, palabra);
                tipo = 0;
            }
            //Si tipo es 0, significa que no se debe contar la palabra ya que ha sido agregada anteriormente
            //Si no, la palabra se reenviar con su tipo para seguir evaluando
            if (tipo == 0) {
                palabra = "";
            }
            //Volver a llamar a la funcion, pero posicion se incrementa para evaluar la siguiente posicion 
            return maquina(codigo, tipo, posicion + 1, palabra);
        }
        //La posicion se regresa para evaluar si todos los caracteres se evaluaron
        return posicion;
    }

    //Excepciones para forzar la evaluacion de un caracter tipo espacio (\n,\t,' ')
    //Cuando llega un espacio, este cuenta como separador normalmente y se agrega tipo y palabra como validos por defecto
    //pero a modo de evitar aprobar casos no validos erroneamente, se especifican excepciones
    //4 y 39: Se esta enmedio de la evaluacion de una cadena o caracter y se pueden aceptar espacios dentro de estos
    //5: Esta esperando la clausura de caracter "' '" pero siguio un espacio, aun asi se debe entrar para evaluar el error
    //36 y 37: Las palabras pueden ser "0x" o "0b" seguidas por un espacio, en este caso no pueden ser aceptados, es mejor dejar que se evalue en los case
    private boolean excepcion(int tipo) {
        switch (tipo) {
            case 4:
            case 39:
            
            case 5:
            
            case 36:
            case 37:
                    
                return true;
            
            default:
                return false;
        }
    }

    //Recibido un agregar valido desde la maquina evaluadora
    //Se reenvia la informacion a un metodo del mismo nombre pero con el "eslabon inicial de Token" como parametro adicional
    //Ya que se ha agregado, significa que un Token ha sido añadido, y el contadoe de Token aumenta
    private void agregar(int tipo, String palabra) {
        agregar(tipo, palabra, eslabonToken);
        contadorToken++;
    }

    //En la primera llamada dede su funcion del mismo nombre, se recibira el "eslabon inicial de Token" y se le asigna como "Token actual"
    //(Inicio del bucle)
    //Si la "palabra" coincide con el "atriburo palabra" del "token actual",
    // entonces, el "contador de tokens" actual, se agrega a la "cadena de indices" del "token actual"
    //Si no, si el "apuntador del siguiente eslabon" del "token actual" es nulo(No apunta a nada)
    // entonces ese nulo se covierte en un "nuevo token" con los atributos de "palabra", "codigo/tipo", "descripcion"(Se ayuda de la funcion diccionario)
    // e "indices" con su propIO "contador de tokens" añadido
    // Cada "nuevo token" se inicia con un "apuntador del siguiente eslabon" de tipo "Token" con valor nulo por defecto
    // Contador de Simbolo incrementa en uno, ya que un simbolo nuevo ha sido añadido
    //Si no, el "apuntador del siguiente eslabon" se vuelve el token actual conserbando palabra y tip0 
    // (Volver al inicio del Bucle)
    public void agregar(int tipo, String palabra, Tokens actual) {
        if (actual.palabra.equals(palabra)) {
            actual.indices += ", " + contadorToken;
            cadenaToken+=actual.clave+" ";
        } else if (actual.siguiente == null) {
            actual.siguiente = new Tokens(diccionario(tipo, palabra),
                    contadorToken + "");
            cadenaToken+=actual.siguiente.clave+" ";
            contadorSimbolo++;
        } else {
            agregar(tipo, palabra, actual.siguiente);
        }
    }

    //Revisa si una palabra debe ser reservada
    private int reservada(String palabra, int tipo) {
        switch (palabra) {
            case "String":
                return 1000;
            case "int":
                return 1001;
            case "double":
                return 1002;
            case "char":
                return 1003;
            case "float":
                return 1004;
            case "if":
                return 2000;
            case "else":
                return 2001;
            case "for":
                return 2003;
            case "do":
                return 2004;
            case "while":
                return 2005;
            case "return":
                return 2006;
            case "switch":
                return 2007;
            case "case":
                return 2008;
            case "print":
                return 3000;
            default:
                return tipo;
        }
    }

    //Auxiliar para dar definicion y/o cambiar alguna clave a ser añadida cuando llega un nuevo simbolo
    private Tokens diccionario(int tipo, String palabra) {
        String definicion, clave=palabra;
        switch (tipo) {
            case 1:
                clave = "id";
                definicion = "Variable";
                break;

            case 2:
                clave = "ent";
                definicion = "Entero";
                break;

            case 3:
                clave = "flot";
                definicion = "Flotante";
                break;

            case 4:
                clave = "cad";
                definicion = "Cadena";
                break;

            case 5:
                clave = "car";
                definicion = "Caracter";
                break;

            case 6:
                definicion = "Punto y coma";
                break;

            case 7:
                definicion = "Coma";
                break;

            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                definicion = "Parentesis " + palabra;
                break;

            case 16:
                definicion = "Asignacion";
                break;

            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                definicion = "Simbolo Aritmetico";
                break;

            case 23:
                definicion = "Incremento";
                break;

            case 24:
                definicion = "Decremento";
                break;

            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                definicion = "Comparacion";
                break;

            case 32:
                definicion = "Dos Puntos";
                break;

            case 33:
                definicion = "Punto";
                break;

            case 35:
                clave = "oct";
                definicion = "Octal";
                break;

            case 36:
                clave = "hex";
                definicion = "Hexadeciman";
                break;

            case 37:
                clave = "bin";
                definicion = "Binario";
                break;

            case 40:
                clave = "fin";
                definicion = "Fin del Codigo";
                break;

            case 41:
            case 42:
            case 43:
            case 44:
                definicion = "Asignacion Especial";
                break;

            case 26:
            case 45:
            case 46:
            case 47:
            case 48:
                definicion = "Logico";
                break;

            case 49:
                definicion = "Preprocesador";
                break;

            case 50:
                definicion = "Flecha";
                break;

            case 51:
            case 52:
            case 53:
            case 54:
                definicion = "Operado de Turno";
                break;

            default:
                if (tipo < 0) {
                    clave = tipo+"";
                    definicion = "Error Lexema";
                } else if (tipo < 2000) {
                    definicion = "Reservada Tipo";
                } else if (tipo < 3000) {
                    definicion = "Reservada Flujo";
                } else {
                    definicion = "Reservada Funcion";
                }
        }
        return new Tokens(clave, palabra, definicion);
    }

    //Casos de error, imprimen un mensaje en pantalla y cambian su tipo a valor negativo
    private void error(int tipo, String palabra) {
        System.out.print("ERROR " + tipo + ":\n \"" + palabra + "\" ");
        char aux;
        switch (tipo) {
            case 1:
                System.out.println("Caracter Desconocido");
                break;

            case 4:
                System.out.print("Cadena inconclusa, se necesita otro '\"' para"
                        + " cerrarse");
                break;

            case 5:
                System.out.print("Caracter necesita de otro \"'\" para cerrarse"
                        + " y solo puede ser un caracter singular");
                break;

            case 36:
                System.out.print("Hexadecimal vacio");
                break;

            case 37:
                System.out.print("Binario vacio");
                break;

            case 39:
                System.out.print("Caracter necesita de otro \"'\" para cerrarse"
                        + " y no puede ser otro \"'\" ");
                break;

            case 99:
                System.out.print("Comentario no cerrado, se necesita un */ para"
                        + "cerrarse");
                break;
            default:
                System.out.print("?");
        }
        System.out.println("");
        agregar(tipo * -1, palabra);
    }

    private boolean letra(char caracter) {
        switch (caracter) {
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return true;
            default:
                return false;
        }
    }

    private boolean digito(char caracter) {
        switch (caracter) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    private boolean espacio(char caracter) {
        switch (caracter) {
            case ' ':
            case '\t':
            case '\n':
                return true;
            default:
                return false;
        }
    }

    private boolean salto(char caracter) {
        switch (caracter) {
            case '\n':
                return true;
            default:
                return false;
        }
    }

    private boolean caractervar(char caracter) {
        if (letra(caracter)) {
            return true;
        } else if (digito(caracter)) {
            return true;
        } else if (caracter == '_') {
            return true;
        }
        return false;
    }

    private boolean binario(char caracter) {
        switch (caracter) {
            case '0':
            case '1':
                return true;
            default:
                return false;
        }
    }

    private boolean octal(char caracter) {
        switch (caracter) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
                return true;
            default:
                return false;
        }
    }

    private boolean hexadecimal(char caracter) {
        if (digito(caracter)) {
            return true;
        } else {
            switch (caracter) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    return true;
                default:
                    return false;
            }
        }
    }

    //Se recorre desde i=0 hasta un numero igual al contador de simbolos
    //El contador de simbolos ayuda a construir la matriz y ayuda como tope
    //primero se inicia con el eslabonToken con el valor que se le dio inicialmente que no tenia nada inicializado, esta se saltata
    //en el bucle por como esta construido

    //(Inicio de bucle if, salir si no hay mas Simbolos)
    // La primera instruccion convierte el eslabon de token en su apuntador a siguienre eslabon
    // Al eslabon de Token, se le guarda sus atributos dentro de la matriz
    //(Volver al inicio del bucle if)
    //el eslabon de Token se termina de vaciar para quedar nulo (Ahorro insignificante, pero ahorro de memoria)
    //Devolver la matriz formada
    public String[][] lexicoMatriz() {
        String lexemas[][] = new String[contadorSimbolo][5];
        for (int i = 0; i < contadorSimbolo; i++) {
            eslabonToken = eslabonToken.siguiente;
            lexemas[i][0] = eslabonToken.palabra;
            lexemas[i][1] = eslabonToken.descripcion;
            lexemas[i][2] = eslabonToken.palabra.length() + "";
            lexemas[i][3] = eslabonToken.clave;
            lexemas[i][4] = eslabonToken.indices;
        }
        eslabonToken = null;
        return lexemas;
    }
    
    public String [] lexicoArreglo(){
        String lexemas[]=new String[contadorToken+1];
        StringTokenizer st = new StringTokenizer(cadenaToken);
        int contador=0;
        while (st.hasMoreTokens()) {
            lexemas[contador]=st.nextToken();
            contador++;
        }
        lexemas[contador]="$";
        return lexemas;
    }

    //El for es para que aun cuando encuentre un error, se salte ese "Caracter error" y continuar
    A1_Lexico(String codigo) {
        for (int posicion = 0; posicion < codigo.length(); posicion++) {
            posicion = maquina(codigo + '$', 0, posicion, "");
        }
    }

    public class Tokens {

        private final String clave, palabra, descripcion;
        private String indices;
        private Tokens siguiente = null;

        Tokens(String clave2, String palabra2, String descripcion2) {
            clave = clave2;
            palabra = palabra2;
            descripcion = descripcion2;
        }

        Tokens(Tokens token, String contador) {
            clave = token.clave;
            palabra = token.palabra;
            descripcion = token.descripcion;
            indices = contador;
        }
    }
}