package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.ResultSetRow;

import antlr.collections.List;
import model.Exemplar;
import model.Livro;
import model.Usuario;

public class LivroController {
	public static void listaLivros() {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		java.util.List results = crit.list();
		ArrayList<Livro> objs = (ArrayList<Livro>) results;
		for (int j = 0; j < objs.size(); j++) {
			System.out.println("Nome: " + objs.get(j).getNome() + " Editora: " + objs.get(j).getEditora() + " Edicao: "
					+ objs.get(j).getEdicao() + " Autores: " + objs.get(j).getAutores() + " Ano: "
					+ objs.get(j).getAno());
		}

		session.getTransaction().commit();
		session.close();

	}

	public static void buscaLivros() {
		String busca;
		System.out.println("Digite sua busca:");
		busca = new Scanner(System.in).nextLine();

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion editora = Restrictions.like("editora", busca + "%");
		Criterion nome = Restrictions.like("nome", busca + "%");
		
		try {
			int i = Integer.parseInt(busca);
			Criterion isbn = Restrictions.eq("isbn", Integer.valueOf(busca));
			LogicalExpression orExp = Restrictions.or(editora, nome);
			LogicalExpression orExp2 = Restrictions.or(orExp, isbn);
			crit.add(orExp2);
		} catch (NumberFormatException e) {
			LogicalExpression orExp = Restrictions.or(editora, nome);
			crit.add(orExp);
		}
		

		java.util.List results = crit.list();
		ArrayList<Livro> objs = (ArrayList<Livro>) results;
		if(results.isEmpty()) {
			System.out.println("Nenhum livro encontrado");
		}
		for (int j = 0; j < objs.size(); j++) {
			System.out.println("Nome: " + objs.get(j).getNome() + " Editora: " + objs.get(j).getEditora() + " Edicao: "
					+ objs.get(j).getEdicao() + " Autores: " + objs.get(j).getAutores() + " Ano: "
					+ objs.get(j).getAno());
		}

		session.getTransaction().commit();
		session.close();

	}

	public static Livro buscaLivroByNome(String busca) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion nome = Restrictions.eq("nome", busca);

		crit.add(nome);

		java.util.List<Livro> results = crit.list();
		if(results.isEmpty()) {
			return null;
		}
		session.getTransaction().commit();
		session.close();

		return results.iterator().next();
	}
	
	public static Livro buscaLivroByIsbn(Integer busca) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion isbn = Restrictions.eq("isbn", busca);

		crit.add(isbn);

		java.util.List<Livro> results = crit.list();
		if(results.isEmpty()) {
			return null;
		}
		session.getTransaction().commit();
		session.close();

		return results.iterator().next();
	}

	public static void removeLivro() {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		System.out.println("Informe o isbn do livro a ser removido:");
		Integer busca = new Scanner(System.in).nextInt();
		Livro livro = buscaLivroByIsbn(busca);
		if(livro == null) {
			System.out.println("Livro não encontrado");
			return;
		}
		session.delete(livro);
		System.out.println("Livro removido com sucesso!");
		session.getTransaction().commit();
		session.close();

	}

	public static void atualizaLivro() {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		System.out.println("Informe o isbn do livro a ser atualizado:");
		Integer busca = new Scanner(System.in).nextInt();
		Livro livro = buscaLivroByIsbn(busca);
		if(livro == null) {
			System.out.println("Livro não encontrado");
			return;
		}
		String nome, autores, editora, edicao;
		Integer ano, isbn;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome do livro:");
		nome = scanner.nextLine();
		livro.setNome(nome);

		System.out.println("Digite os autores do livro:");
		autores = scanner.nextLine();
		livro.setAutores(autores);

		System.out.println("Digite a editora do livro:");
		editora = scanner.nextLine();
		livro.setEditora(editora);
		
		System.out.println("Digite a edicao do livro:");
		edicao = scanner.nextLine();
		livro.setEdicao(edicao);

		System.out.println("Digite o ano do livro:");
		ano = scanner.nextInt();
		livro.setAno(ano);

		session.update(livro);
		System.out.println("Livro atualizado com sucesso!");
		session.getTransaction().commit();
		session.close();

	}

	public static void insereLivro() {
		Livro livro = new Livro();

		String nome, autores, editora, edicao;
		Integer ano, ex, isbn;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome do livro:");
		nome = scanner.nextLine();
		livro.setNome(nome);

		System.out.println("Digite os autores do livro:");
		autores = scanner.nextLine();
		livro.setAutores(autores);

		System.out.println("Digite a editora do livro:");
		editora = scanner.nextLine();
		livro.setEditora(editora);

		System.out.println("Digite a edicao do livro:");
		edicao = scanner.nextLine();
		livro.setEdicao(edicao);

		System.out.println("Digite o ISBN do livro:");
		isbn = scanner.nextInt();
		if(buscaLivroByIsbn(isbn) != null) {
			System.out.println("Já possui um livro cadastrado com esse isbn");
			return;
		}
		livro.setIsbn(isbn);

		System.out.println("Digite o ano do livro:");
		ano = scanner.nextInt();
		livro.setAno(ano);

		System.out.println("Digite o número de exemplares:");
		ex = scanner.nextInt();

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		ArrayList<Exemplar> exemplar = new ArrayList();
		for (int i = 0; i < ex; i++) {
			Exemplar temp = new Exemplar();
			temp.setSituacao(true);
			temp.setReservado(false);
			exemplar.add(temp);
		}
		livro.setExemplares(exemplar);
		session.save(livro);
		System.out.println("Livro cadastrado com sucesso!");
		session.getTransaction().commit();
		session.close();
	}

}
