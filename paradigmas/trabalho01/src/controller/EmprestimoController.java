package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.collection.PersistentBag;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Collection;

import model.Emprestimo;
import model.Exemplar;
import model.Livro;
import model.Reserva;
import model.Usuario;

public class EmprestimoController {
	public static void listaEmprestimos(ArrayList<Emprestimo> emprestimos) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		for (int j = 0; j < emprestimos.size(); j++) {
			System.out.println("Nome: " + emprestimos.get(j).getUsuario().getNome());
			System.out.println("Livro: " + emprestimos.get(j).getExemplar().getLivro_id());
			System.out.println("Data de início: " + fmt.format(emprestimos.get(j).getData_inicio()));
			System.out.println("Data final: " + fmt.format(emprestimos.get(j).getData_fim()));
			if (emprestimos.get(j).getData_devolucao() == null) {
				System.out.println("Data de devolução: LIVRO NÃO DEVOLVIDO");
			} else {
				System.out.println("Data de devolução: " + fmt.format(emprestimos.get(j).getData_devolucao()));
			}
			System.out.println("Multa: R$ " + emprestimos.get(j).getMulta());
		}
	}

	public static void buscaEmprestimoByLivro(ArrayList<Emprestimo> emprestimos) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String busca;
		System.out.println("Digite sua busca");
		busca = new Scanner(System.in).nextLine();

		LivroController livro = new LivroController();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion nome = Restrictions.eq("nome", busca);
		try {
			int i = Integer.parseInt(busca);
			Criterion isbn = Restrictions.eq("isbn", Integer.valueOf(busca));
			LogicalExpression orExp = Restrictions.or(nome, isbn);
			crit.add(orExp);
		} catch (NumberFormatException e) {
			crit.add(nome);
		}

		java.util.List<Livro> result = crit.list();
		if (result.isEmpty()) {
			System.out.println("Livro não encontrado.");
			session.getTransaction().commit();
			session.close();
			return;
		}
		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();
		Iterator<Exemplar> it = exemplares.iterator();
		Integer j = 0;
		Boolean achou = false;
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			for (j = 0; j < emprestimos.size(); j++) {
				if (emprestimos.get(j).getExemplar().getId().equals(exemplar2.getId())) {
					System.out.println("Nome: " + emprestimos.get(j).getUsuario().getNome());
					System.out.println("Data de início: " + fmt.format(emprestimos.get(j).getData_inicio()));
					System.out.println("Data final: " + fmt.format(emprestimos.get(j).getData_fim()));
					if (emprestimos.get(j).getData_devolucao() == null) {
						System.out.println("Data de devolução: LIVRO NÃO DEVOLVIDO");
					} else {
						System.out.println("Data de devolução: " + fmt.format(emprestimos.get(j).getData_devolucao()));
					}
					System.out.println("Multa: R$ " + emprestimos.get(j).getMulta());
					achou = true;
					break;
				}
			}
		}
		if (!achou) {
			System.out.println("Nenhum empréstimo encontrado.");
		}
		session.getTransaction().commit();
		session.close();
	}

	public static void buscaEmprestimoByUsuario(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String busca;
		System.out.println("Digite o login do usuário");
		busca = new Scanner(System.in).nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, busca);
		if (usuarioEncontrado == null) {
			System.out.println("Usuário não existe.");
			return;
		}
		Integer j = 0;
		Boolean achou = false;
		for (j = 0; j < emprestimos.size(); j++) {
			if (emprestimos.get(j).getUsuario().getLogin().equals(usuarioEncontrado.getLogin())) {
				System.out.println("Nome: " + emprestimos.get(j).getUsuario().getNome());
				System.out.println("Data de início: " + fmt.format(emprestimos.get(j).getData_inicio()));
				System.out.println("Data final: " + fmt.format(emprestimos.get(j).getData_fim()));
				if (emprestimos.get(j).getData_devolucao() == null) {
					System.out.println("Data de devolução: LIVRO NÃO DEVOLVIDO");
				} else {
					System.out.println("Data de devolução: " + fmt.format(emprestimos.get(j).getData_devolucao()));
				}
				System.out.println("Multa: R$ " + emprestimos.get(j).getMulta());
				achou = true;
			}
		}
		if (!achou) {
			System.out.println("Nenhum empréstimo encontrado.");
		}
		return;
	}

	public static void buscaEmprestimoByExemplar(ArrayList<Emprestimo> emprestimos) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Integer busca;
		System.out.println("Digite o id do exemplar:");
		busca = new Scanner(System.in).nextInt();

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		Criteria crit = session.createCriteria(Exemplar.class);

		Criterion id = Restrictions.eq("id", busca);

		crit.add(id);

		java.util.List<Exemplar> results = crit.list();
		if (results.isEmpty()) {
			System.out.println("Nenhum exemplar encontrado.");
			session.getTransaction().commit();
			session.close();
			return;
		}
		Integer j = 0;
		Boolean achou = false;
		for (j = 0; j < emprestimos.size(); j++) {
			if (emprestimos.get(j).getExemplar().getId().equals(results.iterator().next().getId())) {
				System.out.println("Nome: " + emprestimos.get(j).getUsuario().getNome());
				System.out.println("Data de início: " + fmt.format(emprestimos.get(j).getData_inicio()));
				System.out.println("Data final: " + fmt.format(emprestimos.get(j).getData_fim()));
				if (emprestimos.get(j).getData_devolucao() == null) {
					System.out.println("Data de devolução: LIVRO NÃO DEVOLVIDO");
				} else {
					System.out.println("Data de devolução: " + fmt.format(emprestimos.get(j).getData_devolucao()));
				}
				System.out.println("Multa: R$ " + emprestimos.get(j).getMulta());
				achou = true;
			}
		}
		if (!achou) {
			System.out.println("Nenhum empréstimo encontrado.");
		}
		session.getTransaction().commit();
		session.close();
		return;
	}

	public static void pagaMulta(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos) {
		String busca;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o login do usuário");
		busca = scanner.nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, busca);
		if (usuarioEncontrado == null) {
			System.out.println("Usuário não existe.");
			return;
		}
		Integer j = 0;
		Boolean achou = false;
		for (j = 0; j < emprestimos.size(); j++) {
			if (emprestimos.get(j).getUsuario().getLogin().equals(usuarioEncontrado.getLogin())) {
				Float zero = (float) 0;
				if (emprestimos.get(j).getMulta() > 0) {
					System.out.println("Valor da multa a ser paga: R$ " + emprestimos.get(j).getMulta());
					System.out.println("Pagar multa? (1- sim 2- não)");
					Integer pagar = scanner.nextInt();
					if (pagar == 1) {
						emprestimos.get(j).setMulta(zero);
					}
					achou = true;
					System.out.println("Deseja continuar procurando multas? (1- sim 2- não)");
					Integer procurar = scanner.nextInt();
					if (procurar == 2) {
						break;
					}
				}
			}
		}
		if (!achou) {
			System.out.println("O usuário não possui multas.");
		}
		return;
	}

	public static void buscaEmprestimo(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos) {
		Integer menu;

		Scanner scanner = new Scanner(System.in);

		System.out.println("1-Livro 2-Exemplar 3-Usuário:");
		menu = scanner.nextInt();

		switch (menu) {
		case 1:
			buscaEmprestimoByLivro(emprestimos);
			break;
		case 2:
			buscaEmprestimoByExemplar(emprestimos);
			break;
		case 3:
			buscaEmprestimoByUsuario(usuarios, emprestimos);
			break;
		}
	}

	public static void renovaEmprestimo(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos) {
		String nome, login;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o login do usuário:");
		login = scanner.nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, login);

		System.out.println("Digite o nome do livro:");
		nome = scanner.nextLine();

		LivroController livro = new LivroController();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();
		session.beginTransaction();

		Session session2 = factory.openSession();
		session2.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion l = Restrictions.eq("nome", nome);

		crit.add(l);

		java.util.List<Livro> result = crit.list();

		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();
		Iterator<Exemplar> it = exemplares.iterator();
		Emprestimo emprestimo = null;
		Integer j = 0;
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			for (j = 0; j < emprestimos.size(); j++) {
				if (emprestimos.get(j).getExemplar().getId().equals(exemplar2.getId())
						&& emprestimos.get(j).getUsuario().getLogin().equals(usuarioEncontrado.getLogin())
						&& emprestimos.get(j).getData_devolucao() == null) {
					emprestimo = emprestimos.get(j);
					break;
				}
			}
		}

		String sdata_renov;
		Date data_renov = null, data_fim = null;

		System.out.println("Data de renovação:(dd/MM/yyyy)");
		sdata_renov = scanner.nextLine();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			data_renov = format.parse(sdata_renov);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (emprestimo != null) {
			if (!emprestimo.getExemplar().getReservado()) {
				if (data_renov.getTime() < emprestimo.getData_fim().getTime()) {
					emprestimo.setData_inicio(data_renov);
					if (usuarioEncontrado.getTipo() == 2) {
						Integer dia = (int) (data_renov.getTime() / 1000 + (86400 * 7));
						data_fim = new java.util.Date((long) dia * 1000);
					} else if (usuarioEncontrado.getTipo() == 1) {
						Integer dia = (int) (data_renov.getTime() / 1000 + (86400 * 15));
						data_fim = new java.util.Date((long) dia * 1000);
					}
					emprestimo.setData_fim(data_fim);
					System.out.println("Empréstimo renovado até: " + data_fim);
				} else {
					System.out.println("O usuário não pode renovar este livro, pois o período de empréstimo terminou.");
				}
			} else {
				System.out.println("O livro já foi reservado por outro usuário.");
			}
		}

		session2.getTransaction().commit();
		session2.close();

		session.getTransaction().commit();
		session.close();
	}

	public static void devolveLivro(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos) {
		String nome, login;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o login do usuário:");
		login = scanner.nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, login);

		if (usuarioEncontrado == null) {
			System.out.println("Usuário não encontrado");
			return;
		}

		System.out.println("Digite o nome do livro:");
		nome = scanner.nextLine();

		LivroController livro = new LivroController();
		SessionFactory factory = new Configuration().configure().buildSessionFactory();

		Session session = factory.openSession();
		session.beginTransaction();

		Session session2 = factory.openSession();
		session2.beginTransaction();

		Criteria crit = session.createCriteria(Livro.class);

		Criterion l = Restrictions.eq("nome", nome);

		crit.add(l);

		java.util.List<Livro> result = crit.list();
		if (result.isEmpty()) {
			System.out.println("Livro não encontrado");
			session.getTransaction().commit();
			session.close();
			return;
		}
		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();
		Iterator<Exemplar> it = exemplares.iterator();
		Emprestimo emprestimo = null;
		Integer j = 0;
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			for (j = 0; j < emprestimos.size(); j++) {
				if (emprestimos.get(j).getExemplar().getId().equals(exemplar2.getId())
						&& emprestimos.get(j).getUsuario().getLogin().equals(usuarioEncontrado.getLogin())
						&& emprestimos.get(j).getData_devolucao() == null) {
					emprestimo = emprestimos.get(j);
					break;
				}
			}
		}

		String sdata_dev;
		Date data_dev = null;

		System.out.println("Data de devolucao:(dd/MM/yyyy)");
		sdata_dev = scanner.nextLine();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			data_dev = format.parse(sdata_dev);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (emprestimo != null) {
			emprestimo.setData_devolucao(data_dev);
			if (emprestimo.getData_devolucao().getTime() > emprestimo.getData_fim().getTime()) {
				Float multa = (float) ((emprestimo.getData_devolucao().getTime() - emprestimo.getData_fim().getTime())
						/ 1000);
				multa = ((multa / 60) / 60) / 24;
				emprestimo.setMulta(multa);
				System.out.println("O usuário deverá pagar uma multa de R$ " + multa);
			} else {
				System.out.println("O usuário não foi multado");
			}

			Exemplar exemplar = emprestimo.getExemplar();
			exemplar.setSituacao(true);
			session2.update(exemplar);
		} else {
			System.out.println("Empréstimo não encontrado.");
		}

		session2.getTransaction().commit();
		session2.close();

		session.getTransaction().commit();
		session.close();

	}

	public static Boolean temMulta(Usuario usuario, ArrayList<Emprestimo> emprestimos) {

		for (int j = 0; j < emprestimos.size(); j++) {
			if (emprestimos.get(j).getUsuario().getLogin().equals(usuario.getLogin())
					&& emprestimos.get(j).getMulta() > (float) 0.0) {
				return true;
			}
		}

		return false;
	}

	public static Integer numeroEmprestimos(Usuario usuario, ArrayList<Emprestimo> emprestimos) {
		Integer cont = 0;
		for (int j = 0; j < emprestimos.size(); j++) {
			if (emprestimos.get(j).getUsuario().getLogin().equals(usuario.getLogin())
					&& emprestimos.get(j).getData_devolucao() == null) {
				cont++;
			}
		}
		return cont;
	}

	public static void insereEmprestimo(ArrayList<Usuario> usuarios, ArrayList<Emprestimo> emprestimos,
			ArrayList<Reserva> reservas) {
		String nome, login;
		String sdata_inicio;
		Date data_inicio = null, data_fim = null;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o login do usuário:");
		login = scanner.nextLine();

		UsuarioController usuario = new UsuarioController();
		Usuario usuarioEncontrado = usuario.buscaUsuarioByLogin(usuarios, login);
		if (usuarioEncontrado == null) {
			System.out.println("Usuário não encontrado");
			return;
		}
		if (temMulta(usuarioEncontrado, emprestimos)) {
			System.out.println("O usuário não pode retirar livros, pois possui multa(s) pendente.");
			return;
		}
		if ((numeroEmprestimos(usuarioEncontrado, emprestimos) > 5 && usuarioEncontrado.getTipo() == 1)
				|| (numeroEmprestimos(usuarioEncontrado, emprestimos) >= 3 && usuarioEncontrado.getTipo() == 2)) {
			System.out.println("O usuário não pode retirar livros, pois atingiu o limite de livros.");
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
		if (result.isEmpty()) {
			System.out.println("Livro não encontrado");
			session.getTransaction().commit();
			session.close();
			return;
		}
		java.util.Collection<Exemplar> exemplares = result.iterator().next().getExemplares();

		Exemplar exemplar = null;
		Boolean trocou = true;
		Integer i = 0;
		Iterator<Exemplar> it = exemplares.iterator();
		ReservaController aux = new ReservaController();
		while (it.hasNext()) {
			Exemplar exemplar2 = it.next();
			if (exemplar2.getSituacao() && !exemplar2.getReservado()) {
				exemplar = exemplar2;
				trocou = false;
				break;
			} else if (exemplar2.getSituacao() && exemplar2.getReservado()) {
				if (aux.temReserva(usuarios, emprestimos, reservas, login, nome)) {
					exemplar = exemplar2;
					exemplar.setReservado(false);
					trocou = false;
					break;
				}
			}
			i++;
		}

		if (trocou) {
			session.getTransaction().commit();
			session.close();
			System.out.println("Não há nenhum exemplar disponível");
			System.out.println("Deseja fazer uma reserva? (1- sim 2- não)");
			Integer reserva = scanner.nextInt();
			if (reserva == 1) {
				aux.insereReserva(usuarios, emprestimos, reservas);
			}
			return;
		}

		exemplar.setSituacao(false);
		session.update(exemplar);

		System.out.println("Data de inicio:(dd/MM/yyyy)");
		sdata_inicio = scanner.nextLine();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			data_inicio = format.parse(sdata_inicio);
			c.setTime(data_inicio);
			if (usuarioEncontrado.getTipo() == 2) {
				Integer dia = (int) (data_inicio.getTime() / 1000 + (86400 * 7));
				data_fim = new java.util.Date((long) dia * 1000);
			} else if (usuarioEncontrado.getTipo() == 1) {
				Integer dia = (int) (data_inicio.getTime() / 1000 + (86400 * 15));
				data_fim = new java.util.Date((long) dia * 1000);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Emprestimo emprestimo = new Emprestimo(usuarioEncontrado, exemplar, data_inicio, data_fim);
		emprestimos.add(emprestimo);

		session.getTransaction().commit();
		session.close();
	}

}
