package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import model.Emprestimo;
import model.Exemplar;
import model.Livro;
import model.Reserva;
import model.Usuario;

public class ReservaController {
	public static void insereReserva(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos, ArrayList<Reserva> reservas) {
		String nome, login;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o login do usuário:");
		login = scanner.nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, login);
		if(usuarioEncontrado == null) {
			System.out.println("Usuário não encontrado");
			return;
		}
		EmprestimoController emprestimo = new EmprestimoController();
		if (emprestimo.temMulta(usuarioEncontrado, emprestimos)) {
			System.out.println("O usuário possui multa pendente.");
			return;
		}
		if ((emprestimo.numeroEmprestimos(usuarioEncontrado, emprestimos) > 5 && usuarioEncontrado.getTipo() == 1)
				|| (emprestimo.numeroEmprestimos(usuarioEncontrado, emprestimos) >= 3 && usuarioEncontrado.getTipo() == 2)) {
			System.out.println("O usuário atingiu o limite de livros.");
			return;
		}
		System.out.println("Digite o nome do livro:");
		nome = scanner.nextLine();

		LivroController livro = new LivroController();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion l = Restrictions.eq("nome", nome);

		crit.add(l);

		java.util.List<Livro> result = crit.list();
		
		if(result.isEmpty()) {
			System.out.println("Livro não encontrado");
			return;
		}
		
		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();

		Exemplar exemplar = null;
		Boolean trocou = true;
		Integer i = 0;
		Iterator<Exemplar> it = exemplares.iterator();
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			if (!exemplar2.getReservado()) {
				exemplar = exemplar2;
				trocou = false;
				break;
			}
			i++;
		}

		if (trocou) {
			session.getTransaction().commit();
			session.close();
			System.out.println("Não há nenhum exemplar disponível");
			return;
		}

		exemplar.setReservado(true);
		session.update(exemplar);

		Reserva reserva = new Reserva(usuarioEncontrado, exemplar);
		reservas.add(reserva);

		session.getTransaction().commit();
		session.close();
	}
	
	public static void cancelaReserva(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos, ArrayList<Reserva> reservas) {
		String nome, login;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o login do usuário:");
		login = scanner.nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, login);
		if(usuarioEncontrado == null) {
			System.out.println("Usuário não encontrado");
			return;
		}
		System.out.println("Digite o nome do livro:");
		nome = scanner.nextLine();

		LivroController livro = new LivroController();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion l = Restrictions.eq("nome", nome);

		crit.add(l);

		java.util.List<Livro> result = crit.list();
		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();

		Exemplar exemplar = null;
		Boolean trocou = true;
		Integer i = 0;
		Iterator<Exemplar> it = exemplares.iterator();
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			for(int j = 0; j < reservas.size(); j++) {  
	            if(reservas.get(j).getUsuario().getLogin().equals(usuarioEncontrado.getLogin()) && reservas.get(j).getExemplar().getId().equals(exemplar2.getId())) {
	            	exemplar = exemplar2;
	            	reservas.remove(j);
	            	trocou = false;
	            }
	        }
			i++;
		}

		if (trocou) {
			session.getTransaction().commit();
			session.close();
			System.out.println("Não há nenhuma reserva com essas especificações");
			return;
		}

		exemplar.setReservado(false);
		session.update(exemplar);

		session.getTransaction().commit();
		session.close();
	}
	
	public static Boolean temReserva(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos, ArrayList<Reserva> reservas, String login, String nome) {
		
		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, login);
		
		LivroController livro = new LivroController();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion l = Restrictions.eq("nome", nome);

		crit.add(l);

		java.util.List<Livro> result = crit.list();
		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();

		Boolean trocou = true;
		Integer i = 0;
		Iterator<Exemplar> it = exemplares.iterator();
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			for(int j = 0; j < reservas.size(); j++) {  
	            if(reservas.get(j).getUsuario().getLogin().equals(usuarioEncontrado.getLogin()) && reservas.get(j).getExemplar().getId().equals(exemplar2.getId())) {
	            	trocou = false;
	            	return true;
	            }
	        }
			i++;
		}

		if (trocou) {
			session.getTransaction().commit();
			session.close();
			return false;
		}
		
		session.getTransaction().commit();
		session.close();
		return false;
	}
}
