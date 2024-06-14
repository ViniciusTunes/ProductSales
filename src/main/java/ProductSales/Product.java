package ProductSales;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
    private int id;
    private String produto;
    private double valor;
    private int quantidade;
    private double total;
    private String data;


    public Product(int id, String produto, double valor, int quantidade, double total) {
        this.id = id;
        this.produto = produto;
        this.valor = valor;
        this.quantidade = quantidade;
        this.total = (valor * quantidade);
        this.data = this.data = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    public Product(String produto, double valor, int quantidade) {
        this.produto = produto;
        this.valor = valor;
        this.quantidade = quantidade;
        this.total = (valor * quantidade);
        this.data = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(new Date());
    }

    //Getters:
    public int getId() { return id; }

    public String getProduto() { return produto; }

    public double getValor() { return valor; }

    public int getQuantidade() { return quantidade; }

    public double getTotal() { return total; }

    public String getData() { return data; }
    //Setters:

    public void setProduto(String produto) { this.produto = produto; }

    public void setValor(double valor) { this.valor = valor; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public void setTotal(double total) { this.total = total; }

    public void setId(int id) { this.id = id; }

    public void setData(String data) { this.data = data; }
}
