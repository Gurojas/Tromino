/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tromino;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 *
 * @author Gustavo Rojas
 */
public class Tromino extends Application {
    
    /* Piezas  
    1   22   3  44
    11   2  33  4   */
    private int tablero[][];
    private int n = 5;
    private BorderPane panel;
    private Pane panelRight;
    private Canvas canvas;
    private GridPane panelLeft;
    private ComboBox comboBox;
    private Label labelComboBox, labelTextField;
    private TextField textFieldI, textFieldJ;
    
    @Override
    public void start(Stage primaryStage) {
        this.tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
        this.colocarHueco(31,31);
        
        
        
        this.dividirCuadrantes(0,0,this.tablero.length,this.tablero.length);
        this.marcarTablero();
        this.mostrarTablero();
        
        
        this.panel = new BorderPane();
        this.panelRight = new Pane();
        
        this.canvas = new Canvas(600,600);
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        this.pintarTablero(gc);
        this.enrejarTablero(gc,Color.WHITE);
        
        
        
        this.panelRight.getChildren().add(this.canvas);
        this.panel.setRight(this.panelRight);
        BorderPane.setMargin(this.panelRight, new Insets(10));
        
        /*
        this.panelLeft = new GridPane();
        this.panelLeft.setGridLinesVisible(false);
        this.panelLeft.setAlignment(Pos.CENTER);
        this.panelLeft.setHgap(10);
        this.panelLeft.setVgap(10);
        
        this.labelComboBox = new Label("Tamaño (n):");
        
        this.comboBox = new ComboBox();
        this.comboBox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (comboBox.getSelectionModel().getSelectedItem().equals("0")){
                    n = 0;
                    tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
                    enrejarTablero(gc,Color.BLACK);
                    
                }
                else if (comboBox.getSelectionModel().getSelectedItem().equals("1")){
                    n = 1;
                    tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
                    enrejarTablero(gc,Color.BLACK);
                }
                
                else if (comboBox.getSelectionModel().getSelectedItem().equals("2")){
                    n = 2;
                    tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
                    enrejarTablero(gc,Color.BLACK);
                }
                
                else if (comboBox.getSelectionModel().getSelectedItem().equals("3")){
                    n = 3;
                    tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
                    enrejarTablero(gc,Color.BLACK);
                }
                else if (comboBox.getSelectionModel().getSelectedItem().equals("4")){
                    n = 4;
                    tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
                    enrejarTablero(gc,Color.BLACK);
                }
                else{
                    n = 5;
                    tablero = new int[(int)Math.pow(2, n)][(int)Math.pow(2, n)];
                    enrejarTablero(gc,Color.BLACK);
                }
                
            }
        });
        this.comboBox.getItems().addAll("0","1","2","3","4","5");
        
        this.labelTextField = new Label("Posicion hueco (i,j):");
        this.textFieldI = new TextField();
        this.textFieldI.setPrefWidth(10);
        
        this.textFieldJ = new TextField();
        this.textFieldJ.setPrefWidth(50);
        
        this.panelLeft.add(this.labelComboBox,0,0);
        this.panelLeft.add(this.comboBox, 1, 0);
        
        this.panelLeft.add(this.labelTextField, 0, 1);
        this.panelLeft.add(this.textFieldI, 1, 1);
        this.panelLeft.add(this.textFieldJ, 2, 1);
        
        this.panel.setLeft(this.panelLeft);
        BorderPane.setMargin(this.panelLeft, new Insets(10));
        */
        
        Scene scene = new Scene(this.panel,860,620);

        
        
        primaryStage.setTitle("Trominos");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        
    }
   
    
    /**
     * Metodo recursivo que marca los cuadrantes y divide en sub cuadrantes cada
     * vez que se llama a si mismo.
     * @param i0 Posicion de partida en las filas de la submatriz
     * @param j0 Posicion de partida en las columnas de la submatriz
     * @param f Dimension de la fila de la submatriz
     * @param c Dimension de la columna de la submatriz
     */
    private void dividirCuadrantes(int i0, int j0, int f, int c){
        
        int iHueco = this.getFilaHueco(i0,j0,f,c);
        int jHueco = this.getColumnaHueco(i0,j0,f,c);
        
        /* El metodo continua hasta que se llege a una matriz de dimensiones 2x2  */
        if (f-i0 > 2 && c-j0 > 2){
            /* Comprueba si el hueco o el espacio en blanco se encuentra en el 
            primer cuadrante*/
            if ((iHueco >= i0 && iHueco < (f+i0)/2) && (jHueco >= j0 && jHueco < (c+j0)/2)){
                int i = ((f+i0)/2) - 1; 
                int j = (c+j0)/2;
                this.tablero[i][j] = 6;
                i = (f+i0)/2;
                j = ((c+j0)/2) - 1;
                this.tablero[i][j] = 6;
                i = (f+i0)/2;
                j = (c+j0)/2;
                this.tablero[i][j] = 6;
            }

            /* Comprueba si el hueco o el espacio en blanco se encuentra en el 
            segundo cuadrante*/
            else if ((iHueco >= i0 && iHueco < (f+i0)/2) && (jHueco >= (c+j0)/2 && jHueco < c )){
                int i = ((f+i0)/2) - 1; 
                int j = ((c+j0)/2) - 1;
                this.tablero[i][j] = 6;
                i = (f+i0)/2;
                j = ((c+j0)/2) - 1;
                this.tablero[i][j] = 6;
                i = (f+i0)/2;
                j = (c+j0)/2;
                this.tablero[i][j] = 6;


            }
            /* Comprueba si el hueco o el espacio en blanco se encuentra en el 
            tercer cuadrante*/
            else if ((iHueco >= (f+i0)/2 && iHueco < f) && (jHueco >= j0 && jHueco < (c+j0)/2)){
                int i = ((f+i0)/2) - 1; 
                int j = ((c+j0)/2) - 1;
                this.tablero[i][j] = 6;
                i = ((f+i0)/2) - 1;
                j = (c+j0)/2;
                this.tablero[i][j] = 6;
                i = (f+i0)/2;
                j = (c+j0)/2;
                this.tablero[i][j] = 6;
            }
            /* Comprueba si el hueco o el espacio en blanco se encuentra en el 
            cuarto cuadrante*/
            else if ( (iHueco >= (f+i0)/2 && iHueco < f) && (jHueco >= (c+j0)/2 && jHueco < c) ){
                int i = ((f+i0)/2) - 1; 
                int j = ((c+j0)/2) - 1;
                this.tablero[i][j] = 6;
                i = ((f+i0)/2) - 1;
                j = (c+j0)/2;
                this.tablero[i][j] = 6;
                i = (f+i0)/2;
                j = ((c+j0)/2) - 1;
                this.tablero[i][j] = 6;
            }
            /* Sub divido la matriz en la mitades */
            int fila = (f+i0)/2;
            int i = i0;
            while (i < f){
                int columna = (c+j0)/2;
                int j = j0;
                while (j < c){
                    //System.out.println(i+","+j+","+fila+","+columna);
                    this.dividirCuadrantes(i, j,fila, columna);
                    j += columna-j0;
                    columna = columna + (columna-j0);
                }
                i += (fila-i0);
                fila = fila + (fila-i0);       
            }
        }
        
    }
    
    private void prueba(int i0, int j0, int f, int c){
        /*
        int fila = (f+i0)/2;
        for (int i = i0; i < f; i += (fila-i0)) {
            int columna = (c+j0)/2;
            for (int j = j0; j < c; j += (columna-j0)) {
                //this.dividirrCuadrantes(i, j,fila, columna);
                System.out.println(i+","+j+","+fila+","+columna);
                columna = columna + (columna-j0);
            }
            fila = fila + (fila-i0);
        }
        */
        
        int fila = (f+i0)/2;
        int i = i0;
        while (i < f){
            int columna = (c+j0)/2;
            int j = j0;
            while (j < c){
                System.out.println(i+","+j+","+fila+","+columna);
                j += columna-j0;
                columna = columna + (columna-j0);
            }
            i += (fila-i0);
            fila = fila + (fila-i0);       
        }
        
    }
    /**
     * Metodo que permite seleccionar una posicion de la matriz para colocar 
     * el hueco o espacio en blanco
     * @param i Posicion i del hueco
     * @param j Posicion j del hueco
     */
    private void colocarHueco(int i, int j){
        int f = this.tablero.length;
        int c = this.tablero.length;
        if (i >= 0 && i < f && j >= 0 && j < c){
            /* El numero 5 hace referencia al hueco */
            this.tablero[i][j] = 5;
        }
    }
    /**
     * Metodo para obtener las coordenada i del hueco o espacio en blanco temporal,
     * para lo cual se busca en una sub matriz
     * @param i0 Posicion i de empezada en la sub matriz
     * @param j0 Posicion j de empezada en la sub matriz
     * @param f Dimension fila de la sub matriz 
     * @param c Dimension columna de la sub matriz
     * @return Retorna la coordenada i del hueco o espacio blanco temporal 
     */
    private int getFilaHueco(int i0, int j0, int f, int c){
        for (int i = i0; i < f; i++) {
            for (int j = j0; j < c; j++) {
                if (this.tablero[i][j] == 5 || this.tablero[i][j] ==6){
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * Metodo para obtener las coordenada j del hueco o espacio en blanco temporal,
     * para lo cual se busca en una sub matriz
     * @param i0 Posicion i de empezada en la sub matriz
     * @param j0 Posicion j de empezada en la sub matriz
     * @param f Dimension fila de la sub matriz 
     * @param c Dimension columna de la sub matriz
     * @return Retorna la coordenada j del hueco o espacio blanco temporal
     */
    private int getColumnaHueco(int i0, int j0, int f, int c){
        for (int i = i0; i < f; i++) {
            for (int j = j0; j < c; j++) {
                if (this.tablero[i][j] == 5 || this.tablero[i][j] ==6){
                    return j;
                }
            }
        }
        return -1;
    }
    
    
    
    
    /**
     * Muestra la matriz que simula el tablero en la consola
     */
    private void mostrarTablero(){
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero.length; j++) {
                System.out.print(this.tablero[i][j]+" ");
            }
            System.out.println("");
        }
    }
    /**
     * 
     */
    private void marcarTablero(){
        if (this.n != 0){
            for (int i = 0; i < this.tablero.length; i += 2) {
                for (int j = 0; j < this.tablero.length; j += 2) {
                    if (this.tablero[i][j] == 5 || this.tablero[i][j] == 6){

                        this.tablero[i][j+1] = 1;
                        this.tablero[i+1][j] = 1;
                        this.tablero[i+1][j+1] = 1;

                    }
                    else if (this.tablero[i][j+1] == 5 || this.tablero[i][j+1] == 6){
                        this.tablero[i][j] = 2;
                        this.tablero[i+1][j] = 2;
                        this.tablero[i+1][j+1] = 2;
                    }
                    else if (this.tablero[i+1][j] == 5 || this.tablero[i+1][j] == 6){
                        this.tablero[i][j] = 3;
                        this.tablero[i][j+1] = 3;
                        this.tablero[i+1][j+1] = 3;
                    }
                    else if (this.tablero[i+1][j+1] == 5 || this.tablero[i+1][j+1] == 6){
                        this.tablero[i][j] = 4;
                        this.tablero[i][j+1] = 4;
                        this.tablero[i+1][j] = 4;
                    }
                }
            }          
        }
    }
    /**
     * Metodo que crea los cuadrados para que se vea una pequeña separacion entre
     * bloque y bloque
     * @param gc Recibe un objeto GraphicsContext el cual se encarga de pintar
     * @param color Recibe un objeto el cual le dara color al enrejado
     * el tablero
     */
    private void enrejarTablero(GraphicsContext gc, Color color){
        double h = this.canvas.getHeight();
        double n = this.tablero.length;
        double proporcion = h/n;
        for (double i = 0; i <= h; i += proporcion) {
            gc.setStroke(color);
            gc.strokeLine(i,0,i,h);
            
        }
        
        double w = this.canvas.getWidth();
        proporcion = w/n;
        for (double j = 0; j <= w; j += proporcion) {
            gc.setStroke(color);
            gc.strokeLine(0,j,w,j);
        }
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeRect(0,0,w,h);
       
        
        
    }
    /**
     * Metodo que se encarga de colorear cada cuadro
     * 1 -> Color rojo
     * 2 -> Color azul
     * 3 -> Color amarillo
     * 4 -> Color verde
     * 5 -> Color blanco
     * @param gc Recibe un objeto GraphicsContext el cual se encarga de pintar
     * el tablero
     */
    private void pintarTablero(GraphicsContext gc){
        double w = this.canvas.getWidth();
        double h = this.canvas.getHeight();
        double n = this.tablero.length;
        double proporcion = h/n;
        
        double y = 0;
        for (int i = 0; i < this.tablero.length; i++) {
            double x = 0;
            for (int j = 0; j < this.tablero.length; j++) {
                if (this.tablero[i][j] == 1){
                    gc.setFill(Color.RED);
                    gc.fillRect(x, y, proporcion,proporcion);
                    
                }
                else if (this.tablero[i][j] == 2){
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x, y, proporcion,proporcion);
                    
                }
                else if (this.tablero[i][j] == 3){
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(x, y, proporcion,proporcion);
                }
                else if(this.tablero[i][j] == 4){
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x, y, proporcion,proporcion);
                }
                else if (this.tablero[i][j] == 5){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x, y, proporcion,proporcion);
                }
                /* Encuentra todo los espacios en blanco temporales y se ve
                que forman tienen para pintarlos segun el color del patron que 
                se encuentre*/
                else if (this.tablero[i][j] == 6){
                    /* se pilla un patron del caso de los rojos */
                    if (this.tablero[i][j] == 6 && this.tablero[i+1][j-1] == 6 && this.tablero[i+1][j] == 6){
                        
                        gc.setFill(Color.RED);
                        gc.fillRect(x, y, proporcion,proporcion);
                        gc.fillRect(x, y + proporcion , proporcion,proporcion);
                        gc.fillRect(x - proporcion, y + proporcion, proporcion,proporcion);
                    }
                    /* se pilla un patron del caso de los azules */
                    else if (this.tablero[i][j] == 6 && this.tablero[i+1][j] == 6 && this.tablero[i+1][j+1] == 6){
                        
                        gc.setFill(Color.BLUE);
                        gc.fillRect(x, y, proporcion,proporcion);
                        gc.fillRect(x, y + proporcion, proporcion,proporcion);
                        gc.fillRect(x + proporcion, y + proporcion, proporcion,proporcion);
                    }
                    /* se pilla un patron del caso de los amarillos */
                    else if (this.tablero[i][j] == 6 && this.tablero[i][j+1] == 6 && this.tablero[i+1][j+1] == 6){
                        // amarillo
                        gc.setFill(Color.YELLOW);
                        gc.fillRect(x, y, proporcion,proporcion);
                        gc.fillRect(x + proporcion, y, proporcion,proporcion);
                        gc.fillRect(x + proporcion, y + proporcion, proporcion,proporcion);
                    }
                    /* se pilla un patron del caso de los verdes */
                    else if (this.tablero[i][j] == 6 && this.tablero[i][j+1] == 6 && this.tablero[i+1][j] == 6){
                        // verder
                        gc.setFill(Color.GREEN);
                        gc.fillRect(x, y, proporcion,proporcion);
                        gc.fillRect(x + proporcion, y, proporcion,proporcion);
                        gc.fillRect(x , y + proporcion, proporcion,proporcion);
                    }
                    
                }
                x += proporcion;
            }
            y +=  proporcion;
        }
        
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
