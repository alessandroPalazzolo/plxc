import java.util.Vector;

public abstract class Tipo extends Objeto {
    public Tipo(String n, int b){
        super(n,b,false);
    }
    
    public abstract Objeto metodosInstancias(Objeto o, String m, Vector<Objeto> p);
}
