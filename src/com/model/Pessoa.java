package com.model;
import com.database.IModel;

public abstract class Pessoa implements IModel {
    private Integer id;

    private String nome;
    private int idade;


    public Pessoa() {
    }


    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
    
    /**
     * @return String return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return int return the idade
     */
    public int getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " - Idade: " + idade;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
