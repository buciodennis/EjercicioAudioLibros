package net.ivanvega.fragmentosdinamicos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
private AdaptadorLibrosFiltro adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.contenedor_pequeno) != null &&
                getSupportFragmentManager()
                        .findFragmentById(R.id.contenedor_pequeno) == null
        ) {

            getSupportFragmentManager().beginTransaction().
                    setReorderingAllowed(true)
                    .add(
                            R.id.contenedor_pequeno,
                            SelectorFragment.class, null).commit();

        }

        FloatingActionButton fab= findViewById(R.id.fab);

        TabLayout tabs =(TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Todos"));
        tabs.addTab(tabs.newTab().setText("Nuevos"));
        tabs.addTab(tabs.newTab().setText("Leidos"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: //todos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(false);
                        break;
                    case 1: //nuevos
                        adaptador.setNovedad(true);
                        adaptador.setLeido(false);
                        break;
                    case 2://leidos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(true);
                        break;
                }
                adaptador.notifyDataSetChanged();

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void mostrarDetalle(int pos) {
        DetalleFragment detalleFragment =
                (DetalleFragment) getSupportFragmentManager().
                        findFragmentById(R.id.detalle_fragment);
        if (detalleFragment != null) {
            detalleFragment.setInfoLibro(pos);
        } else {

            detalleFragment =
                    new DetalleFragment();
            Bundle bundle = new Bundle();

            bundle.putInt(DetalleFragment.ARG_INDEX_LIBRO,
                    pos
            );

            detalleFragment.setArguments(
                    bundle
            );

            getSupportFragmentManager().beginTransaction().
                    setReorderingAllowed(true)
                    .replace(R.id.contenedor_pequeno, detalleFragment)
                    .addToBackStack(null)
                    .commit();
        }
        SharedPreferences pref=getSharedPreferences(
                "com.example.audiolibros_internal", MODE_PRIVATE
        );
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt("ultimo", pos);
        editor.commit();

    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.menu_preferencias) {
                Toast.makeText(this, "Preferencias", Toast.LENGTH_LONG).show();
                return true;

            }else if(id== R.id.menu_preferencias){
                Toast.makeText(this, "Preferencias", Toast.LENGTH_SHORT).show();
                return true;
            }else if(id== R.id.menu_ultimo){
                irUltimovisitado();
                return true;
            }
            else if(id== R.id.menu_acerca) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Mensaje de Acerca De");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.create().show();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public  void irUltimovisitado() {
            SharedPreferences pref = getSharedPreferences(
                    "com.example.audiolibros_internal", MODE_PRIVATE);
            int id = pref.getInt("ultimo", -1);
            if (id >= 0) {
                mostrarDetalle(id);
            } else {
                Toast.makeText(this, "Sin última vista", Toast.LENGTH_LONG) .show();
            }
        }
    }
