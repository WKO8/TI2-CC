package ti2cc;

import java.util.Scanner;

public class Principal {
	public DAO dao;
	public Carros carro;
	public Carros carros;
	public Scanner sc;
	
	public Principal() {
		this.dao = new DAO();
		this.carro = new Carros();
		this.carros = new Carros();
		this.sc = new Scanner(System.in);
	}
	
	
    public static void menu(){
        System.out.println("\tCadastro de clientes");
        System.out.println("0. Sair");
        System.out.println("1. Inserir");
        System.out.println("2. Listar");
        System.out.println("3. Excluir");
        System.out.println("4. Atualizar");
        System.out.println("Opcao:");
    }
    
    public void inserir(){
    	this.dao.conectar();
		//Inserir um elemento na tabela
    	System.out.println("=== INFORMACOES ===");
    	System.out.println("ID:");
    	int id = this.sc.nextInt();
    	
    	System.out.println("Modelo:");
    	String modelo = this.sc.nextLine();
    	
    	System.out.println("Cor:");
    	String cor = this.sc.nextLine();
    	
    	System.out.println("Dono:");
    	String dono = this.sc.nextLine();
    	
		this.carro = new Carros(id, modelo, cor, dono);
		if(dao.inserirCarro(carro) == true) {
			System.out.println("Inserção com sucesso -> " + carro.toString());
		}
		
		dao.close();
        
    }
    
    
    public void listar(){
    	
    	this.dao.conectar();
    	
    	//Mostrar usuários do sexo masculino
		System.out.println("==== Mostrar usuários === ");
		Carros[] carros = this.dao.getCarros();
		for(int i = 0; i < carros.length; i++) {
			System.out.println(carros[i].toString());
		}
		
		dao.close();
    }
    
    public void excluir(){
    	//Excluir usuário
    	this.dao.excluirCarro(this.carro.getId());
    }
    
    public void atualizar(){
    	//Atualizar usuário
    	System.out.println("Novo dono:");
    	String novo_dono = this.sc.nextLine();
		this.carro.setCor(novo_dono);
		this.dao.atualizarCarro(this.carro);
    }
    
    public void main(String[] args) {
    	
    	this.dao.conectar();
    	
        int opcao;
        
        do{
            menu();
            opcao = this.sc.nextInt();
            
            switch(opcao){
	            case 1:
	                listar();
	                break;
	                
	            case 2:
	                inserir();
	                break;
	                
	            case 3:
	                excluir();
	                break;
	                
	            case 4:
	                atualizar();
	                break;
	            
	            default:
	                System.out.println("Opção inválida.");
            }
        } while(opcao != 0);
        
        sc.close();
        dao.close();
    }
    
    
}

