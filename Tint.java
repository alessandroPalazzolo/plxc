import java.util.Vector;

public class Tint extends Tipo  {
    private static Tint instancia = new Tint();

    private Tint(){
        super("int", 0);
    }

    public static Tint getInstancia(){
        return instancia;
    }

    public Objeto metodos(String m, Vector<Objeto> p){
        PLXC.errorYPara("");
        return null;
    }


    private Objeto[] checkTypes(Objeto o, Vector<Objeto> p){
        Tipo argType = ((Instancia) p.firstElement()).getTipo();

        if(argType != instancia){
            if (argType == Tfloat.getInstancia()){
                Vector<Objeto> temp = new Vector<Objeto>();
                temp.add(Tfloat.getInstancia());
                Objeto[] results =  {
                                        o.metodos("cast", temp), 
                                        p.firstElement()
                                    };
                return results;
            }
            
            if (argType == Tchar.getInstancia()){
                Vector<Objeto> temp = new Vector<>();
                temp.add(instancia);
                Objeto[] results =  {
                                        o, 
                                        p.firstElement().metodos("cast", temp)
                                    };
                return results;
            }

            PLXC.errorYPara("Illegal operation: " + p.firstElement().getNombreBloque() + " is " + argType + ".");
            return null;
        }

        Objeto[] results = { o, p.firstElement() };
        return results;
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
            PLXC.errorYPara("suma - can only sum 1 value.");
            return null;
        }
        if(!(p.firstElement() instanceof Instancia)){
            PLXC.errorYPara("suma - sum value is not a variable or value.");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("suma - " + o.getNombreBloque() + " is immutable.");
            return null;
        }
        
        TablaS.checkScope(p.firstElement());

        Objeto castedObjs[] = checkTypes(o,p);
        if (o != castedObjs[0]){
            return castedObjs[0].metodos("suma", p);
        }
        if (p.firstElement() != castedObjs[1]){
            Vector<Objeto> temp = new Vector<>();
            temp.add(castedObjs[1]);
            return castedObjs[0].metodos("suma", temp);
        }

        Objeto newObj = new Instancia(TablaS.newObj(), instancia, TablaS.bActual, false);
        PLXC.out.println(newObj.getNombreBloque() + " = " + o.getNombreBloque() + " + " + p.firstElement().getNombreBloque() + ";");
        return newObj;
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

        Objeto castedObjs[] = checkTypes(o,p);
        if(o != castedObjs[0]){
            return castedObjs[0].metodos("substraction", p);
        }

        Objeto newObj = new Instancia(TablaS.newObj(), instancia, TablaS.bActual, false);
        PLXC.out.println(newObj.getNombreBloque() + " = " + o.getNombreBloque() + " - " + p.firstElement().getNombreBloque() + ";");
        return newObj;
    }


    // PRODUCT
    private Objeto productHandler(Objeto o, Vector<Objeto> p){
        if(p.size() != 1){
            PLXC.errorYPara("product - can only sum 1 value.");
            return null;
        }
        if(!(p.firstElement() instanceof Instancia)){
            PLXC.errorYPara("product - product value is not a variable or value.");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("product - " + o.getNombreBloque() + " is immutable.");
            return null;
        }
       
        Objeto castedObjs[] = checkTypes(o,p);
        if(o != castedObjs[0]){
            return castedObjs[0].metodos("product", p);
        }

        Objeto newObj = new Instancia(TablaS.newObj(), instancia, TablaS.bActual, false);
        PLXC.out.println(newObj.getNombreBloque() + " = " + o.getNombreBloque() + " * " + p.firstElement().getNombreBloque() + ";");
        return newObj;
    }


    // DIVISION
    private Objeto divisionHandler(Objeto o, Vector<Objeto> p){
        if(p.size() != 1){
            PLXC.errorYPara("division - can only sum 1 value.");
            return null;
        }
        if(!(p.firstElement() instanceof Instancia)){
            PLXC.errorYPara("division - division value is not a variable or value.");
            return null;
        }
        if(o.getMutable()){
            PLXC.errorYPara("division - " + o.getNombreBloque() + " is immutable.");
            return null;
        }
        
        TablaS.checkScope(p.firstElement());

        
        Objeto castedObjs[] = checkTypes(o,p);
        if(o != castedObjs[0]){
            return castedObjs[0].metodos("division", p);
        }

        Objeto newObj = new Instancia(TablaS.newObj(), instancia, TablaS.bActual, false);
        PLXC.out.println(newObj.getNombreBloque() + " = " + o.getNombreBloque() + " / " + p.firstElement().getNombreBloque() + ";");
        return newObj;
    }


    // NEGATION
    private Objeto negationHandler(Objeto o){
        Objeto newObj = new Instancia(TablaS.newObj(), instancia, TablaS.bActual, false);
        PLXC.out.println(newObj.getNombreBloque() + " = " + "-" + o.getNombreBloque() + ";");
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

        if (((Tipo) p.firstElement()) == Tfloat.getInstancia()){
            castedObj = new Instancia(TablaS.newObj(), Tfloat.getInstancia(), TablaS.bActual, false);
            PLXC.out.println(castedObj.getNombreBloque() + " = " + "(" + p.firstElement().getNombreBloque() + ") " + o.getNombreBloque() + ";");
            return castedObj;
        }

        if (((Tipo) p.firstElement()) == Tchar.getInstancia()){
            castedObj = new Instancia(o.getNombreBloque(), Tchar.getInstancia(), TablaS.bActual, false);
            return castedObj;
        }

        PLXC.errorYPara("Cannot cast type int to type " + p.firstElement().getNombreBloque());
        return null;
    }


    // PRINT
    private Objeto printHandler(Objeto o){
        PLXC.out.println("print " + o.getNombreBloque() + ";");
        return null;
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
            case "suma":
                return sumaHandler(o, p);
            case "substraction":
                return substractionHandler(o, p);
            case "product":
                return productHandler(o, p);
            case "division":
                return divisionHandler(o, p);
            case "negation":
                return negationHandler(o);
            case "cast":
                return castHandler(o, p);
            case "print":
                return printHandler(o);
            default:
                PLXC.errorYPara("Operation not defined on type int.");
                return null;
        }
    }
}
