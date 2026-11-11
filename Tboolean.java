import java.util.Vector;

public class Tboolean extends Tipo {
    private static Tboolean instancia = new Tboolean();

    private Tboolean(){
        super("boolean", 0);
    }

    public static Tboolean getInstancia(){
        return instancia;
    }

    public Objeto metodos(String m, Vector<Objeto> p){
        PLXC.errorYPara("");
        return null;
    }


    // ASIGNA
    private Objeto asignaHandler(Objeto o, Vector<Objeto> p){
        if(p.size() != 1){
            PLXC.errorYPara("asigna - can only assign 1 value.");
            return null;
        }
        if(!(p.firstElement() instanceof Instancia)){
            PLXC.errorYPara("asigna - assigned value is not a variable or value.");
            return null;
        }
        if(((Instancia) p.firstElement()).getTipo() != instancia){
            PLXC.errorYPara("asigna - Variable " + p.firstElement() + " is not " + instancia + ".");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("asigna - " + o + " is immutable.");
            return null;
        }

        TablaS.checkScope(p.firstElement());

        if (p.firstElement().toString().charAt(0) == '%'){
            String finalLabel = TablaS.newEtiq("Final"); 
            String[] labels = p.firstElement().toString().substring(1).split("-");
            String isTrue = labels[0];
            String isFalse = labels[1];

            PLXC.out.println(isTrue + ":");
            PLXC.out.println(o + " = " + "1;");
            PLXC.out.println("goto " + finalLabel + ";");
            PLXC.out.println(isFalse + ":");
            PLXC.out.println(o + " = " + "0;");
            PLXC.out.println(finalLabel + ":");

            return o;
        }

        PLXC.out.println(o + " = " + p.firstElement() + ";");
        return o;
    }

    // ROUTER
    public Objeto metodosInstancias(Objeto o, String m, Vector<Objeto> p){
        if(!(o instanceof Instancia)){
            PLXC.errorYPara("Object is not a variable or value.");
            return null;
        }

        if(((Instancia) o).getTipo() != instancia){
            PLXC.errorYPara("Variable " + o.getNombreBloque() + " is not " + instancia + ".");
            return null;
        }

        TablaS.checkScope(o);

        switch(m) {
            case "asigna":
                return asignaHandler(o, p);
            default:
                PLXC.errorYPara("Operation not defined on type boolean.");
                return null;
        }
    }    
}
