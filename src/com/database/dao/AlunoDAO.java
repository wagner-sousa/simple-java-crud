package com.database.dao;

import java.sql.ResultSet;

import com.database.DataAccessObject;
import com.model.Aluno;


public class AlunoDAO extends DataAccessObject<Aluno> {
    protected String table = "alunos";

    public AlunoDAO(Aluno model) {
        super(model);
    }

    public AlunoDAO() {
        super(new Aluno());
    }

    @Override
    public String getTable() {
        return this.table;
    }

    @Override
    public void validate() throws Exception {
        if(this.getModel().getNome().length() == 0) {
            throw new Exception("Nome obrigatório");
        }

        if(this.getModel().getIdade() == 0) {
            throw new Exception("Idade obrigatória");
        }

        if(this.getModel().getNota() == 0) {
            throw new Exception("Nota obrigatória");
        }
    }

    @Override
    public Aluno fillModel(ResultSet rs) {
        Aluno model = new Aluno();
        try {
            model.setId(rs.getInt("id"));
            model.setNome(rs.getString("nome"));
            model.setIdade(rs.getInt("idade"));
            model.setNota(rs.getDouble("nota"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return model;
    }

    
}
