import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public class TablaS {
        private Map<String, TreeMap<Integer, Objeto>> tabla;
        public static int bActual = 0;
        public static Tipo castActual = null;

        public TablaS(){
            tabla = new HashMap<String, TreeMap<Integer,Objeto>>();
        }

        private static int numEt = 0;
        public static String newEtiq(String name){
            String v = new String(name + Integer.toString(numEt));
            numEt++;
            return v;
        };
       
        private static int countBloque = 0;
        public static int newBloque(){
            int ba = bActual;
            bActual = ++countBloque;
            return ba;
        }

        private static int numObj = 0;
        public static String newObj(){
            String newObjName = new String("$t" + numObj);
            numObj++;
            return newObjName;
        }

        public static void printTrue(){
            PLXC.out.println("writec 116;");
            PLXC.out.println("writec 114;");
            PLXC.out.println("writec 117;");
            PLXC.out.println("writec 101;");
            PLXC.out.println("writec 10;");
        }

        public static void printFalse(){
            PLXC.out.println("writec 102;");
            PLXC.out.println("writec 97;");
            PLXC.out.println("writec 108;");
            PLXC.out.println("writec 115;");
            PLXC.out.println("writec 101;");
            PLXC.out.println("writec 10;");
        }

        public static void checkScope(Objeto obj){
            if (Objects.isNull(obj) || obj.getBloque() > bActual) {
                PLXC.errorYPara("Variable " + obj + " not declared or not visible.");
            }
        }

        public static String getAscii(String str, String format){
            switch(format){
                case "plain":
                    return String.valueOf((int) str.charAt(1));
                case "unicode":
                    // str format: '\U****'
                    return Integer
                                .toString(
                                    Integer.parseInt(
                                        str.substring(3,str.length()-1),
                                        16
                                    )
                                );
                case "escaped":
                    String ascii;
                    // str format: '\*' 
                    switch(str.charAt(2)){ 
                        case 'b': ascii = "8"; break;
                        case 'n': ascii = "10"; break;
                        case 'r': ascii = "13"; break;
                        case 't': ascii = "9"; break;
                        case '\\': ascii = "92"; break;
                        case '\'': ascii = "39"; break;
                        case '\"': ascii = "34"; break;
                        default: ascii = null;
                    }
                    return ascii;
                default:
                    return null;
            }
        }

        public boolean contiene(String n){
            return tabla.containsKey(n);
        }
        
        public boolean contiene(String n, int b){
            boolean containsByBlock = false;
            if (tabla.containsKey(n)){
                if (tabla.get(n).containsKey(b)){
                    containsByBlock = true;
                }
            }
            return containsByBlock;
        }

        public int majorBloque(String n){
            if (!tabla.containsKey(n)){
                return -1;
            }

            int maxBlock =  tabla
                .get(n)
                .keySet()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
            
            return maxBlock;
        }

        public Objeto objeto(String n){
            int maxBlock = majorBloque(n);
            return Optional
                    .ofNullable(tabla.get(n))
                    .map(m -> m.get(maxBlock))
                    .orElse(null);
        }
       
        public Objeto objeto(String n, int b){
            return Optional
                    .ofNullable(tabla.get(n))
                    .map(m -> m.get(b))
                    .orElse(null);
        }
        
        public void addObjeto(Objeto o) {
            if (!Objects.isNull(objeto(o.getNombre(), o.getBloque()))){
                PLXC.errorYPara("Variable already declared.");
                return;
            }

            if (!tabla.containsKey(o.getNombre())){
                tabla.put(o.getNombre(), new TreeMap<Integer, Objeto>());
            }

            tabla.get(o.getNombre()).put(o.getBloque(), o);
        }

        public void borrarBloque(int b){
            tabla.forEach((name, objs) -> {
                objs.keySet().removeIf(block -> block.intValue() == b);
            });

            tabla.entrySet().removeIf(name -> name.getValue().isEmpty());
        }
    }