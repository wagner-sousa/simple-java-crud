package com.model;
import java.util.ArrayList;

public class Turma {
    private String nome;
    private Professor professor;
    private ArrayList<Aluno> listaAlunos = new ArrayList<>();

    public Turma(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public ArrayList<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void adicionarAluno(Aluno aluno) {
        listaAlunos.add(aluno);
    }

    public int getQuantidadeAlunos() {
        return this.listaAlunos.size();
    }

    @Override
    public String toString() {
        return "Turma: " + this.nome + "\n" +
                "Professor: " + this.professor + "\n" + 
                "Alunos:\n" + 
                this.listaAlunos.stream().map(Object::toString).reduce((a, b) -> a + "\n" + b).get();
    }
}
