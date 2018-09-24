package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.Usuario;

public class UsuarioController {
	
	public static void listaUsuarios(ArrayList<Usuario> usuarios) {
		if(usuarios.isEmpty()) {
			System.out.println("Nenhum usuário cadastrado");
		}
		for(int j = 0; j < usuarios.size(); j++) {  
           	System.out.println("Nome: " + usuarios.get(j).getNome());
           	System.out.println("Login: " + usuarios.get(j).getLogin());
           	if(usuarios.get(j).getTipo() == 1) {
           		System.out.println("Tipo: professor");
           	}else {
           		System.out.println("Tipo: aluno");
           	}
        }
	}
	
	public static void buscaUsuario(ArrayList<Usuario> usuarios) {
		String busca;
		System.out.println("Digite sua busca:");
		busca=new Scanner(System.in).nextLine();
		Boolean achou = false;
		for(int j = 0; j < usuarios.size(); j++) {  
            if(usuarios.get(j).getNome().contains(busca) || usuarios.get(j).getLogin().equals(busca)) {
            	System.out.println("Nome: " + usuarios.get(j).getNome());
               	System.out.println("Login: " + usuarios.get(j).getLogin());
               	if(usuarios.get(j).getTipo() == 1) {
               		System.out.println("Tipo: professor");
               	}else {
               		System.out.println("Tipo: aluno");
               	}
            	achou = true;
            }
        }
		if(!achou) {
			System.out.println("Nenhum usuário encontrado");
		}
	}
	
	public static Usuario buscaUsuarioByLogin(ArrayList<Usuario> usuarios, String busca) {
		for(int j = 0; j < usuarios.size(); j++) {  
            if(usuarios.get(j).getLogin().equals(busca)) {
            	return usuarios.get(j);
            }
        }
		return null;
	}
	
	public static boolean removeUsuario(ArrayList<Usuario> usuarios) {
		String busca;
		System.out.println("Informe o login do usuário a ser removido:");
		busca=new Scanner(System.in).nextLine();
		for(int j = 0; j < usuarios.size(); j++) {  
            if(usuarios.get(j).getLogin().equals(busca)) {
            	usuarios.remove(j);
            	return true;
            }
        }
		return false;
	}
	
	public static boolean atualizaUsuario(ArrayList<Usuario> usuarios) {
		String busca;
		System.out.println("Informe o login do usuário a ser atualizado:");
		busca=new Scanner(System.in).nextLine();
		for(int j = 0; j < usuarios.size(); j++) {  
            if(usuarios.get(j).getLogin().equals(busca)) {
            	String nome, login;
            	Integer tipo;
        		Scanner scanner = new Scanner(System.in);
        		
        		System.out.println("--- Editando "+usuarios.get(j).getLogin()+" ---");
        		
        		System.out.println("Digite o nome do usuário:");
        		nome=scanner.nextLine();
        		
        		System.out.println("Digite o login do usuário:");
        		login=scanner.nextLine();
        		
        		System.out.println("Digite o tipo do usuário (1-professor 2-aluno):");
        		tipo = scanner.nextInt();
        		
        		Usuario usuario = new Usuario(nome, login, tipo);
        		usuarios.set(j, usuario);
            	return true;
            }
        }
		return false;
	}
	
	public static void insereUsuario(ArrayList<Usuario> usuarios) {
		String nome, login;
		Integer tipo;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite o nome do usuário:");
		nome=scanner.nextLine();
		
		System.out.println("Digite o login do usuário:");
		login=scanner.nextLine();
		if(buscaUsuarioByLogin(usuarios, login) != null) {
			System.out.println("Login indisponível");
			return;
		}
		System.out.println("Digite o tipo do usuário (1-professor 2-aluno):");
		tipo = scanner.nextInt();
		
		Usuario usuario = new Usuario(nome, login, tipo);
		usuarios.add(usuario);
		System.out.println("Usuário cadastrado com sucesso!");
	}
	
	public static void main(String[] args) {
				
		ArrayList<Usuario> objs = new ArrayList<Usuario>();
		insereUsuario(objs);		
		listaUsuarios(objs);
	}
}
