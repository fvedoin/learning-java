public class produto {
private Integer codigo;
public Integer getcodigo(){ return codigo; }
public void setcodigo(Integer codigo){ this.codigo = codigo; }
private String nome;
public String getnome(){ return nome; }
public void setnome(String nome){ this.nome = nome; }
public produto(Integer codigo,String nome){
this.codigo = codigo;this.nome = nome;
}
}
