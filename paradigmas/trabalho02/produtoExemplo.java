public class produtoExemplo {
public static void main(String[] args){
produtoDao cs = new produtoDao();
System.out.println("Listando todos os dados da tabela produto");
for(produto temp : cs.buscaproduto()){
System.out.println("- " + temp.getcodigo()"- " + temp.getnome());
}
System.out.println("Inserindo dados na tabela produto");
c1 = new produto()
cs.insereproduto();
System.out.println("Listando todos os dados da tabela produto");
for(produto temp : cs.buscaproduto()){
System.out.println("- " + temp.getcodigo()"- " + temp.getnome());
}
System.out.println("Alterando o cliente 1");
cs.editaproduto();
System.out.println("Listando todos os dados da tabela produto");
for(produto temp : cs.buscaproduto()){
System.out.println("- " + temp.getcodigo()"- " + temp.getnome());
}
System.out.println("Apagando todos os registros da tabela produto");
for(produto temp : cs.buscaproduto()){
cs.removeproduto(codigo,temp.getcodigo());
}
System.out.println("Listando todos os dados da tabela produto");
for(produto temp : cs.buscaproduto()){
System.out.println("- " + temp.getcodigo()"- " + temp.getnome());
}
}
}
