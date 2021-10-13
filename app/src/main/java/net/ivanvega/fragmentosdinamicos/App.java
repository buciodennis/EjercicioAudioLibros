package net.ivanvega.fragmentosdinamicos;

import java.util.Vector;

public class App {
    private Vector<Libro> vectorLibros;
    boolean leido = false;
    boolean novedad = false;

    // @Override
    public void onCreate() {
        // super.onCreate();
        vectorLibros = Libro.ejemplosLibros();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public void recalcularFiltro() {
        Vector<Libro> libros = new Vector<>();
        Vector<Integer> indiceFiltro = new Vector<>();
        for(int i=0;i<Libro.ejemplosLibros().size(); i++){
            Libro l = Libro.ejemplosLibros().get(i);
            if(
                    ( !novedad || (!novedad || !l.getNovedad()) )
                            && ( !leido || (leido && l.getLeido()))
            ){
                libros.add(l);
                indiceFiltro.add(i);
            }
        }
        Libro.libros=libros;
    }
}
