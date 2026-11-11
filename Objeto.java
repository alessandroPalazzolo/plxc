import java.util.Vector;

public abstract class Objeto implements Comparable<Objeto> {
        private String nombre;
        private int bloque;
        private boolean mutable;

        public Objeto(String n, int b, boolean mut){
            nombre = n;
            bloque = b;
            mutable = mut;
        }

        public String getNombre(){
            return nombre;
        }
        public int getBloque(){
            return bloque;
        }
        public boolean getMutable(){
            return mutable;
        }

        public static Vector<Objeto> vectorize(Objeto ...objs){
            Vector<Objeto> temp = new Vector<>();

            for (Objeto obj : objs){
                temp.add(obj);
            }

            return temp;
        }
        
        public String getNombreBloque(){
            return (bloque != 0) ? nombre + "_" + bloque : nombre;
        }

        public boolean equals(Objeto obj){
            Objeto o = (Objeto) obj;
            return 
                o.getNombre().equals(this.getNombre()) &&
                o.getBloque() == this.getBloque();

        }

        public String toString(){
            return getNombreBloque();
        }

        @Override
        public int compareTo(Objeto c){
            if (this.getNombre().compareTo(c.getNombre()) < 0)
                return -1;
            if (this.getNombre().compareTo(c.getNombre()) > 0)
                return 1;

            if (this.getBloque() < c.getBloque())
                return -1;
            if (this.getBloque() > c.getBloque())
                return 1;

            return 0;
        }

        public abstract Objeto metodos(String m, Vector<Objeto> p);
    }