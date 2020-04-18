public class clienteExemplo {
public static void main(String[] args){

    clienteDao cs = new clienteDao();
    System.out.println("Listando todos os dados da tabela cliente");
    for(cliente temp : cs.buscacliente()){
        System.out.println("- " + temp.getid()"- " + temp.getnome());
    }
    System.out.println("Inserindo dados na tabela cliente");
    c1 = new cliente()
    cs.inserecliente();
    System.out.println("Listando todos os dados da tabela cliente");
    for(cliente temp : cs.buscacliente()){
        System.out.println("- " + temp.getid()"- " + temp.getnome());
    }
    System.out.println("Alterando o cliente 1");
    cs.editacliente();
    System.out.println("Listando todos os dados da tabela cliente");
    for(cliente temp : cs.buscacliente()){
        System.out.println("- " + temp.getid()"- " + temp.getnome());
    }
    System.out.println("Apagando todos os registros da tabela cliente");
    for(cliente temp : cs.buscacliente()){
        cs.removecliente(id,temp.getid());
    }
    System.out.println("Listando todos os dados da tabela cliente");
    for(cliente temp : cs.buscacliente()){
        System.out.println("- " + temp.getid()"- " + temp.getnome());
    }
    }
}
