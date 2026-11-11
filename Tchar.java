import java.util.Vector;

public class Tchar extends Tipo {
    private static Tchar instancia = new Tchar();
    
    private Tchar(){
        super("char", 0);
    }

    public static Tchar getInstancia(){
        return instancia;
    }

    public Objeto metodos(String m, Vector<Objeto> p){
        PLXC.errorYPara("");
        return null;
    }


    private Objeto checkTypes(Objeto o, Vector<Objeto> p){
        Tipo argType = ((Instancia) p.firstElement()).getTipo();

        if( argType != instancia){
            if (argType == Tint.getInstancia()){
                Vector<Objeto> temp = new Vector<Objeto>();
                temp.add(Tint.getInstancia());
                return o.metodos("cast", temp);
            } else {
                PLXC.errorYPara("Illegal operation: " + p.firstElement().getNombreBloque() + " is " + argType + ".");
                return null;
            }
        } 

        return o;
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
            PLXC.errorYPara("asigna - Variable " + p.firstElement().getNombreBloque() + " is not " + instancia + ".");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("asigna - " + o.getNombreBloque() + " is immutable.");
            return null;
        }

        TablaS.checkScope(p.firstElement());

        PLXC.out.println(o.getNombreBloque() + " = " + p.firstElement().getNombreBloque() + ";");
        return p.firstElement();
    }


    // SUMA
    private Objeto sumaHandler(Objeto o, Vector<Objeto> p){
        if(p.size() != 1){
            PLXC.errorYPara("substraction - can only sum 1 value.");
            return null;
        }
        if(!(p.firstElement() instanceof Instancia)){
            PLXC.errorYPara("substraction - sub value is not a variable or value.");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("substraction - " + o.getNombreBloque() + " is immutable.");
            return null;
        }
        
        TablaS.checkScope(p.firstElement());
        Objeto castedObj = checkTypes(o, p);
        if(o != castedObj){
            return castedObj.metodos("suma", p);
        }
        
        PLXC.errorYPara("Illegal operation: cannot sum two variables of type char.");
        return null;
    }

    
    // SUBSTRACTION
    private Objeto substractionHandler(Objeto o, Vector<Objeto> p){
        if(p.size() != 1){
            PLXC.errorYPara("substraction - can only sum 1 value.");
            return null;
        }
        if(!(p.firstElement() instanceof Instancia)){
            PLXC.errorYPara("substraction - sub value is not a variable or value.");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("substraction - " + o.getNombreBloque() + " is immutable.");
            return null;
        }
        
        TablaS.checkScope(p.firstElement());
        Objeto castedObj = checkTypes(o, p);
        if(o != castedObj){
            return castedObj.metodos("substraction", p);
        }
        
        Vector<Objeto> temp = new Vector<>();
        temp.add(Tint.getInstancia());

        Objeto x = o.metodos("cast", temp);
        Objeto y = p.firstElement().metodos("cast", temp);
        Objeto newObj = new Instancia(TablaS.newObj(), Tint.getInstancia(), TablaS.bActual, false);
        PLXC.out.println(newObj.getNombreBloque() + " = " + x.getNombreBloque() + " - " + y.getNombreBloque() + ";");
        return newObj;
    }


    // CAST
    private Objeto castHandler(Objeto o, Vector<Objeto> p){
        if (!(p.firstElement() instanceof Tipo)){
            PLXC.errorYPara(p + " is not a valid type.");
            return null;
        }

        Objeto castedObj;

        if (((Tipo) p.firstElement()) == instancia){
            return o;
        }

        if (((Tipo) p.firstElement()) == Tint.getInstancia()){
            castedObj = new Instancia(o.getNombreBloque(), Tint.getInstancia(), TablaS.bActual, false);
            return castedObj;
        }

        PLXC.errorYPara("Cannot cast type char to type " + p.firstElement().getNombreBloque());
        return null;
    }


    // PRINT
    private Objeto printHandler(Objeto o){
        PLXC.out.println("printc " + o.getNombreBloque() + ";");
        return null;
    }


    // ROUTER
    public Objeto metodosInstancias(Objeto o, String m, Vector<Objeto> p){
        if (!(o instanceof Instancia)){
            PLXC.errorYPara("Object is not a variable or value.");
            return null;
        }

        if (((Instancia) o).getTipo() != instancia){
            PLXC.errorYPara("Variable " + o.getNombreBloque() + " is not " + instancia + ".");
            return null;   
        }

        TablaS.checkScope(o);

        switch(m) {
            case "asigna":
                return asignaHandler(o, p);
            case "suma":
                return sumaHandler(o, p);
            case "substraction":
                return substractionHandler(o, p);
            case "cast":
                return castHandler(o, p);
            case "print":
                return printHandler(o);
            default:
                PLXC.errorYPara("Operation not defined on type char.");
                return null;
        }
    }
}
