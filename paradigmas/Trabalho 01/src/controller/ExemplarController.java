package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import model.Exemplar;
import model.Livro;

public class ExemplarController {
	public static void buscaExemplares() {
		String busca;
		System.out.println("Digite sua busca:");
		busca = new Scanner(System.in).nextLine();

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion editora = Restrictions.like("editora", busca + "%");
		Criterion nome = Restrictions.like("nome", busca + "%");
		LogicalExpression orExp = Restrictions.or(editora, nome);
		crit.add(orExp);

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
	public static void resetaExemplares() {
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Exemplar.class);

		java.util.List<Exemplar> results = crit.list();
		
		Iterator<Exemplar> it = results.iterator();
	
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			exemplar2.setReservado(false);
			exemplar2.setSituacao(true);
			session.update(exemplar2);
		}
		
		session.getTransaction().commit();
		session.close();
		return;
	}
}
