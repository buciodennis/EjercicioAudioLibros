package net.ivanvega.fragmentosdinamicos;

import android.content.Context;

import java.util.Vector;

public class AdaptadorLibrosFiltro extends MiAdaptadorPersonalizado{
    private Vector<Libro> vectorSinFiltro;
    private java.util.Vector<Integer> indiceFiltro;

    private String busqueda = "";
    private String genero = "";
    private boolean novedad = false;
    private boolean leido = false;


    public AdaptadorLibrosFiltro(Context ctx, Vector<Libro> libros) {
        super(ctx, libros);
        vectorSinFiltro = libros;
        recalcularFiltro();
    }

    public void setBusqueda(String busqueda) {

        this.busqueda = busqueda.toLowerCase();
        recalcularFiltro();
    }
    public void setGenero(String genero) {
        this.genero = genero;
        recalcularFiltro();
    }
    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalcularFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalcularFiltro();
    }

    public void recalcularFiltro() {
        Vector<Libro> libros = new Vector<Libro>();
        indiceFiltro = new Vector<Integer>();
        for(int i=0; i<vectorSinFiltro.size(); i++){
            Libro l = vectorSinFiltro.elementAt(i);
            if(
                    (l.getTitulo().toLowerCase().contains(busqueda) || l.getAutor().toLowerCase().contains(busqueda))
                            && l.getGenero().startsWith(genero)
                            && ( !novedad || (novedad && !l.getNovedad()) )
                            && ( !leido || (leido && l.getLeido()))
            ){
                libros.add(l);
                indiceFiltro.add(i);
            }
        }
    }
    public Libro getItem(int posicion){
        return vectorSinFiltro.elementAt(indiceFiltro.elementAt(posicion));
    }
    public long getItemId(int pos){

        return indiceFiltro.elementAt(pos);
    }
    public void borrar(int pos){
        vectorSinFiltro.remove((int)getItemId(pos));
        recalcularFiltro();
    }
    public void insertar(Libro libro){
        vectorSinFiltro.add(libro);
        recalcularFiltro();
    }
}
