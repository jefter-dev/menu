package br.edu.ifpb.poo.menu.model;

public class Views {
    // Visão simples
    public interface SimpleView {}

    // Visão detalhada
    public interface DetailedView extends SimpleView {}

    // Outras visões, se necessário
    public interface AdminView extends DetailedView {}
}
