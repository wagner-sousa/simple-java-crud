package com.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class DataAccessObject<Model extends IModel> {
    protected String table;
    private Model model;
    protected String primaryKey = "id";

    public DataAccessObject(Model model) {
        this.setModel(model);
    }

    public String getTable() {
        return this.table;
    }

    public abstract void validate() throws Exception;

    public abstract Model fillModel(ResultSet rs);

    public void save() throws Exception {

        this.validate();

        if (this.model.getId() == null) {
            insert();
        } else {
            update();
        }
    }

    public void insert(Model model) {
        this.setModel(model);
        this.insert();
    }

    public void insert() {
        ArrayList<String> params = getParameters();

        String fieldNames = String.join(", ", params);
        String valuePlaceholders = "?,".repeat(params.size() - 1) + "?";

        String sql = "INSERT INTO " + DatabaseConnection.getPrefix() + getTable() +
                "(" + fieldNames + ") " +
                "VALUES (" + valuePlaceholders + ")";

        try {
            executeStatement(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update() throws Exception {
        ArrayList<String> params = getParameters();

        String setClause = params.stream()
                .map(param -> param + " = ?")
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        String sql = "UPDATE " + DatabaseConnection.getPrefix() + getTable() +
                " SET " + setClause +
                " WHERE " + this.getPrimaryKey() + " = ?";

        params.add(this.getPrimaryKey()); // Adicione o campo "id" para a cláusula WHERE

        try {
            executeStatement(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {

        String sql = "DELETE FROM " + 
                        DatabaseConnection.getPrefix() + getTable() +
                        " WHERE " + this.getPrimaryKey() + " = ? LIMIT 1";

        ArrayList<String> params = new ArrayList<>();
        params.add("id");

        try {
            executeStatement(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getParameters() {
        Class<?> minhaClasse = this.getModel().getClass();
        ArrayList<String> params = new ArrayList<>();

        while (minhaClasse != null) {
            Field[] campos = minhaClasse.getDeclaredFields();
            for (Field campo : campos) {
                campo.setAccessible(true);

                if (!campo.getName().equals(this.getPrimaryKey())) {
                    params.add(campo.getName());
                }
            }

            minhaClasse = minhaClasse.getSuperclass();
        }

        return params;
    }

    public void executeStatement(String sql, ArrayList<String> params) {
        try {
            Connection connection = DatabaseConnection.open();
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                String fieldName = params.get(i);
                Field field = getFieldFromHierarchy(fieldName, this.getModel().getClass());
                field.setAccessible(true);
                stmt.setObject(i + 1, field.get(model));
            }
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void executeStatement(String sql) {
        this.executeStatement(sql, new ArrayList<>());
    }

    public ResultSet executeQuery(String sql, ArrayList<String> params) {
        try {
            Connection connection = DatabaseConnection.open();
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                String fieldName = params.get(i);
                Field field = getFieldFromHierarchy(fieldName, this.getModel().getClass());
                field.setAccessible(true);
                stmt.setObject(i + 1, field.get(model));
            }
            return stmt.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String sql) {
        return this.executeQuery(sql, new ArrayList<>());
    }
    
    private Field getFieldFromHierarchy(String fieldName, Class<?> clazz) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field != null) {
                return field;
            }
        } catch (NoSuchFieldException ignored) {
        }
    
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            return getFieldFromHierarchy(fieldName, superClass);
        }
    
        throw new IllegalArgumentException("Campo não encontrado na hierarquia de classes.");
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

 
    public ArrayList<Model> list() {
        String sql = "SELECT * FROM " + DatabaseConnection.getPrefix() + getTable();
    
        try (ResultSet rs = this.executeQuery(sql)) {
    
            ArrayList<Model> models = new ArrayList<>();
         
            while (rs.next()) {
                models.add(fillModel(rs));
            }
    
            return models;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Model getById(int id) {
        String sql = "SELECT * FROM " + 
                        DatabaseConnection.getPrefix() + getTable() + 
                        " WHERE " + this.getPrimaryKey() + " =  " + id +
                        " LIMIT 1";        

                        System.out.println(sql);
        try (ResultSet rs = this.executeQuery(sql)) {
    
            return this.fillModel(rs);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}