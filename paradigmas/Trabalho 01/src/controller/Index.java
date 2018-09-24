package controller;

import java.util.ArrayList;
import java.util.Scanner;

import controller.LivroController;
import controller.UsuarioController;
import model.Usuario;
import model.Emprestimo;
import model.Livro;
import model.Reserva;

public class Index {

	public static int menu() {
		Integer op;
		System.out.println("------ MENU ------\n" + "1- Manter usuários\n" + "2- Manter livros\n"
				+ "3- Manter empréstimos\n" + "4- Manter reserva\n" + "5- Pagar multa\n" + "6- Sair");
		op = new Scanner(System.in).nextInt();
		return op;
	}

	public static int menuUsuario() {
		Integer op;
		System.out.println("----- MENU USUARIO -----\n" + "1- Inserir\n" + "2- Listar\n" + "3- Buscar\n"
				+ "4- Atualizar\n" + "5- Excluir\n");
		op = new Scanner(System.in).nextInt();
		return op;
	}

	public static int menuLivro() {
		Integer op;
		System.out.println("----- MENU LIVRO -----\n" + "1- Inserir\n" + "2- Listar\n" + "3- Buscar\n"
				+ "4- Atualizar\n" + "5- Excluir\n");
		op = new Scanner(System.in).nextInt();
		return op;
	}

	public static int menuEmprestimo() {
		Integer op;
		System.out.println("----- MENU EMPRESTIMO -----\n" + "1- Emprestar livro\n" + "2- Renovar empréstimo\n"
				+ "3- Devolver livro\n" + "4- Listar empréstimos\n" + "5- Buscar empréstimos");
		op = new Scanner(System.in).nextInt();
		return op;
	}
	
	public static int menuReserva() {
		Integer op;
		System.out.println("----- MENU RESERVA -----\n" + "1- Reservar livro\n" + "2- Cancelar reserva\n");
		op = new Scanner(System.in).nextInt();
		return op;
	}

	public static void main(String[] args) {
		
		Integer op, op2;

		UsuarioController usuario = new UsuarioController();
		EmprestimoController emprestimo = new EmprestimoController();
		ReservaController reserva= new ReservaController();

		ArrayList<Usuario> ul = new ArrayList<Usuario>();
		ArrayList<Emprestimo> el = new ArrayList<Emprestimo>();
		ArrayList<Reserva> rl = new ArrayList<Reserva>();

		LivroController livro = new LivroController();

		op = menu();
		while (true) {
			switch (op) {
			case 1:
				op2 = menuUsuario();
				switch (op2) {
				case 1:
					usuario.insereUsuario(ul);
					break;
				case 2:
					usuario.listaUsuarios(ul);
					break;
				case 3:
					usuario.buscaUsuario(ul);
					break;
				case 4:
					if (usuario.atualizaUsuario(ul)) {
						System.out.println("Usuário atualizado com sucesso!");
					} else {
						System.out.println("Erro ao atualizar usuário.");
					}
					break;
				case 5:
					if (usuario.removeUsuario(ul)) {
						System.out.println("Usuário removido com sucesso!");
					} else {
						System.out.println("Erro ao remover usuário.");
					}
					break;
				}
				break;
			case 2:
				op2 = menuLivro();
				switch (op2) {
				case 1:
					livro.insereLivro();
					break;
				case 2:
					livro.listaLivros();
					break;
				case 3:
					livro.buscaLivros();
					break;
				case 4:
					livro.atualizaLivro();
					break;
				case 5:
					livro.removeLivro();
					break;
				}
				break;
			case 3:
				op2 = menuEmprestimo();
				switch (op2) {
				case 1:
					emprestimo.insereEmprestimo(ul, el, rl);
					break;
				case 2:
					emprestimo.renovaEmprestimo(ul, el);
					break;
				case 3:
					emprestimo.devolveLivro(ul, el);
					break;
				case 4:
					emprestimo.listaEmprestimos(el);
					break;
				case 5:
					emprestimo.buscaEmprestimo(ul, el);
					break;
				}
				break;
			case 4:
				op2 = menuReserva();
				switch (op2) {
				case 1:
					reserva.insereReserva(ul, el, rl);
					break;
				case 2:
					reserva.cancelaReserva(ul, el, rl);
					break;
				}
				break;
			case 5:
				emprestimo.pagaMulta(ul, el);
				break;
			case 6:
				return;
			}
			op = menu();
		}
	}
}
