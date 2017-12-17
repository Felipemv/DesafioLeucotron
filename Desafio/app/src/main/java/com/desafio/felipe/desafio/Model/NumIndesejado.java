package com.desafio.felipe.desafio.Model;

/**
 * Created by felipe on 15/12/17.
 */

public class NumIndesejado {

    private long id;
    private String nome;
    private String telefone;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
