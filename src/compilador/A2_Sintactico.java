package compilador;

public class A2_Sintactico {
String[] pila1,pila2;
    
    private void f(String[] lexemas) {
        String cadena="";
        int tam = lexemas.length;
        for (int i=0;i<tam;i++){
            cadena+=lexemas[i]+" ";
        }
        pila1=new String[1];
        pila2=new String[1];
        pila1[0]="$E";
        pila2[0]=cadena;
    }

    String[][] sintacticoMatriz() {
        String[][] sintaxis=new String[1][2];
        sintaxis[0][0]=pila1[0];
        sintaxis[0][1]=pila2[0];
        return sintaxis;
    }
    
    public A2_Sintactico(String[] lexemas) {
        f(lexemas);
    }

    
    
}
