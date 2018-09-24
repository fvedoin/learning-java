package model;

import java.util.Date;

public class Usuario {
	private String nome;
	private String login;
	private Integer tipo;

	public Usuario(String nome, String login, Integer tipo) {
		this.nome = nome;
		this.login = login;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
}
