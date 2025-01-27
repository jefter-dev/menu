package br.edu.ifpb.poo.menu.model;

public class Views {
    // Visão simples
    public interface SimpleView {}

    // Visão detalhada
    public interface DetailedView extends SimpleView {}

    // Visão do cardápio
    public interface MenuView extends SimpleView {}

    // Outras visões, se necessário
    public interface AdminView extends DetailedView {}

    public interface ProductView extends MenuView {}

    public interface CategoryView extends MenuView {}
}
