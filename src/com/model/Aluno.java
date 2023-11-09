package com.model;

import java.util.ArrayList;

import com.database.dao.AlunoDAO;

public class Aluno extends Pessoa {
    
    private double nota;

    public Aluno() {
    }
    
    public Aluno(String nome, int idade, double nota) {
        super(nome, idade);
        this.nota = nota;
    }

    /**
     * @return double return the nota
     */
    public double getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return super.toString() + " - Nota: " + this.getNota();
    }

    public static ArrayList<Aluno> list() {
        return new AlunoDAO().list();
    }

    @Override
    public void insert() {
        new AlunoDAO(this).insert();
    }

    public static Aluno getById(int id) {
        return new AlunoDAO().getById(id);
    }

    @Override
    public void destroy() {
        new AlunoDAO(this).destroy();
    }
}
