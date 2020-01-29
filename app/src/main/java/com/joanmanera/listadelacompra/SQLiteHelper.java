package com.joanmanera.listadelacompra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.joanmanera.listadelacompra.Models.Category;
import com.joanmanera.listadelacompra.Models.List;
import com.joanmanera.listadelacompra.Models.Product;

import java.util.ArrayList;
public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper sqlHelper=null;
    public static SQLiteHelper getInstance(Context context){
        if(sqlHelper==null){
            sqlHelper=new SQLiteHelper(context);
        }
        return sqlHelper;
    }

    private static final String dbName="ListaCompra.db";
    private static final int dbVersion=1;
    private static final String CREATE_CATEGORIAS ="CREATE TABLE Categorias (nombre STRING PRIMARY KEY, imagen INTEGER);";
    private static final String CREATE_PRODUCTOS ="CREATE TABLE Productos (nombre STRING PRIMARY KEY, categoria STRING REFERENCES Categorias (Nombre), image INTEGER);";
    private static final String CREATE_LISTAS ="CREATE TABLE ListasCompra (nombre STRING PRIMARY KEY, fecha STRING);";
    private static final String CREATE_ITEM_LISTA ="CREATE TABLE ItemsListaCompra (producto STRING REFERENCES Productos (nombre), nombreLista STRING REFERENCES ListasCompra (nombre) ON DELETE CASCADE ON UPDATE CASCADE, comprado BOOLEAN, PRIMARY KEY(producto, nombreLista));";

    private static final String[] INSERT_CATEGORIAS ={
            "INSERT INTO Categorias (Nombre, imagen) VALUES ('Carnes',"+  R.drawable.carne + " );" ,
            "INSERT INTO Categorias (Nombre, imagen) VALUES ('Frutas y verduras', "+  R.drawable.frutas + ");" ,
            "INSERT INTO Categorias (Nombre, imagen) VALUES ('Pescado', "+  R.drawable.pescado + ");" ,
            "INSERT INTO Categorias (Nombre, imagen) VALUES ('Quesos',"+  R.drawable.queso + " );" ,
            "INSERT INTO Categorias (Nombre, imagen) VALUES ('Otros', "+  R.drawable.pasta_dientes + ");"};

    private static final String[] INSERT_PRODUCTOS ={
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Emperador', 'Pescado', "+  R.drawable.emperador + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Ternera', 'Carnes', "+  R.drawable.ternera + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Queso curado', 'Quesos', "+  R.drawable.queso_curado + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Limones', 'Frutas y verduras', "+  R.drawable.limon + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Atun', 'Pescado', "+  R.drawable.atun + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Pollo', 'Carnes', "+  R.drawable.pollo + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Queso de cabra', 'Quesos', "+  R.drawable.queso_cabra + ");" ,
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Tomates', 'Frutas y verduras', "+  R.drawable.tomate + ");",
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Naranjas', 'Frutas y verduras', "+  R.drawable.naranja + ");",
            "INSERT INTO Productos (nombre, categoria, image) VALUES ('Pasta de Dientes', 'Otros', "+  R.drawable.pasta_dientes + ");"
    };
    private static final String INSERT_LISTAS ="INSERT INTO ListasCompra (nombre, fecha) VALUES ('Mi Lista', 'Wed Jan 22 00:32:49 GTM+01:00 2020');";

    private static final String[] INSERT_ITEM_LISTAS={
            "INSERT INTO ItemsListaCompra (producto, nombreLista, comprado) VALUES ('Tomates', 'Mi Lista', 'true');" ,
            "INSERT INTO ItemsListaCompra (producto, nombreLista, comprado) VALUES ('Ternera', 'Mi Lista', 'false');"};


    private SQLiteHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    private ArrayList<Category> categorias;
    private ArrayList<Product> productos;
    private ArrayList<List> listas;

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CATEGORIAS);
        db.execSQL(CREATE_PRODUCTOS);
        db.execSQL(CREATE_LISTAS);
        db.execSQL(CREATE_ITEM_LISTA);

        for (String cat:INSERT_CATEGORIAS
        ) {
            db.execSQL(cat);
        }
        for (String prod:INSERT_PRODUCTOS
        ) {
            db.execSQL(prod);
        }

        db.execSQL(INSERT_LISTAS);

        /*for (String itemList: INSERT_ITEM_LISTAS
        ) {
            db.execSQL(itemList);
        }*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean cargarDatos(){
        SQLiteDatabase db= this.getReadableDatabase();

        //Categorias
        categorias=new ArrayList<>();

        Cursor c=db.rawQuery("SELECT nombre,imagen FROM Categorias;",null);
        if(c.moveToFirst()){
            do{
                String s= c.getString(c.getColumnIndex("nombre"));
                int imagen=c.getInt(c.getColumnIndex("imagen"));
                categorias.add(new Category(s,imagen, new ArrayList<Product>()));
            }while (c.moveToNext());
        }
        c.close();

        //Productos
        productos=new ArrayList<>();

        c=db.rawQuery("SELECT nombre, categoria, image FROM Productos",null);
        if(c.moveToFirst()){
            do{
                String nombreProd= c.getString(c.getColumnIndex("nombre"));
                String catProd=c.getString(c.getColumnIndex("categoria"));
                int image = c.getInt(c.getColumnIndex("image"));
                Category categoriaProducto=null;
                for (Category feCategory: categorias) {
                    if(feCategory.getName().equals(catProd)){
                        categoriaProducto=feCategory;
                    }
                }
                Product product = new Product(nombreProd, image);
                productos.add(product);
                categoriaProducto.getProducts().add(product);
            }while (c.moveToNext());
        }
        c.close();

        //Listas
        listas=new ArrayList<>();

        c=db.rawQuery("SELECT nombre, fecha FROM ListasCompra",null);
        if(c.moveToFirst()){
            do{
                String nombreLista=c.getString(0);
                String fecha=c.getString(1);
                listas.add(new List(nombreLista,fecha, new ArrayList<Product>()));
            }while (c.moveToNext());
        }
        c.close();

        for (List feLista:listas
        ) {
            String[] argumentosListas={String.valueOf(feLista.getName())};
            c=db.rawQuery("SELECT producto, nombreLista FROM ItemsListaCompra where nombreLista=?",argumentosListas);
            if (c.moveToFirst()){
                do {
                    String nombreProductoLista=c.getString(0);
                    Product p= null;
                    for (Product feProd:productos) {
                        if(nombreProductoLista.equals(feProd.getName())){
                            p=feProd;
                        }
                    }
                    if (p!=null) {
                        feLista.getProducts().add(new Product(p.getName(), p.getImage()));
                    }
                }while (c.moveToNext());
            }
        }
        db.close();
        return true;
    }


    public ArrayList<Category> getCategorias() {
        return categorias;
    }

    public ArrayList<List> getListas() {
        return listas;
    }

    public void addProductoLista(Product producto, List listaCompra){
        boolean encontrado=false;

        for (Product p: listaCompra.getProducts()){
            if(p.getName().equals(producto.getName())){
                encontrado = true;
            }
        }

        SQLiteDatabase db=getWritableDatabase();

        if(!encontrado){
            listaCompra.getProducts().add(producto);

            ContentValues nuevoItem=new ContentValues();
            nuevoItem.put("producto",producto.getName());
            nuevoItem.put("nombreLista",listaCompra.getName());
            nuevoItem.put("comprado",false);

            db.insert("ItemsListaCompra",null,nuevoItem);
            db.close();
        }

    }

    public void removeProducto(Product product, List listaCompra){
        Product aux = null;
        for (Product p: listaCompra.getProducts()){
            if (p.getName().equals(product.getName())){
                aux = p;
            }
        }
        if (aux != null){
            listaCompra.getProducts().remove(aux);

            SQLiteDatabase db=getWritableDatabase();
            String[] args={product.getName(), listaCompra.getName()};

            db.delete("ItemsListaCompra", "producto=? AND nombreLista=?", args);
            db.close();
        }

    }

    public void addLista(List listaCompra, ArrayList<Product> productsList){
        boolean encontrado=false;

        for (List l: listas){
            if(l.getName().equals(listaCompra.getName())){
                encontrado = true;
            }
        }

        SQLiteDatabase db=getWritableDatabase();

        if(!encontrado){
            ContentValues nuevaLista=new ContentValues();
            nuevaLista.put("nombre",listaCompra.getName());
            nuevaLista.put("fecha",listaCompra.getDate());
            db.insert("ListasCompra",null,nuevaLista);
            listas.add(listaCompra);
            db.close();
            for (Product p: productsList){
                addProductoLista(p, listaCompra);
            }
        }
    }

}
