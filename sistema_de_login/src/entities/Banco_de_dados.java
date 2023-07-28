package entities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


public class Banco_de_dados {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultset = null;
	
	public void conectar() {
		String servidor = "jdbc:mysql://localhost:3306/sistema_de_login?useSSL=false";
		String usuario = "root";
		String senha = "8585123445!";
		String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(servidor, usuario, senha);
			this.statement = this.connection.createStatement();
			
		} catch (Exception e) {
			System.out.println("Error " +e.getMessage());
		}
	}
	
	public boolean estaConectado() {
		if(this.connection != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void registrar (String email, String senha) {
		try {
			String query = "INSERT INTO login(email, senha) VALUES ('"+email+"', '"+senha+"');";
			this.statement.executeUpdate(query);
			System.out.println("registro realizado com sucesso!");
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void login (String email, String senha) {
		try {
			String query = "SELECT * FROM login WHERE email = '"+email+"' AND senha = '"+senha+"';";
			this.statement.executeQuery(query);
			ResultSet resultSet = this.statement.executeQuery(query);
			
			if (resultSet.next()) {
				Scanner input2 = new Scanner(System.in);
				
				while (true) {
					System.out.println("\nBem vindo, cadastro realizado com sucesso!\nDigite o número correspondente à ação que deseja executar:\n");
					System.out.println(" 1. exibir dados do usuário \n 2. Editar usuário \n 3. Deletar Usuário \n 4. Encerrar sessão");
					
					System.out.print("\nDigite o número da opção desejada: ");
					String opc = input2.nextLine();
					int opc2 = Integer.parseInt(opc);
					
					if (opc2 == 1) {
						dadosUsuario(email);
					} else if (opc2 == 2) {
						String [] error = {"campo vazio", "apenas letras", "apenas números", "caracteres especiais não são permitidos"};
						
						System.out.println("\nPara realizar uma alteração, digite o cpf do usuário,");
						System.out.println("o campo que deseja editar e a nova informação.\n");
						
						String cpfOpc = null;
						while (true) {
							System.out.print("Digite o cpf do usuário que deseja alterar: ");
							cpfOpc = input2.nextLine().trim();
							
							if (cpfOpc.isEmpty() == false) {
								if (ValidaCpf.isCpf(cpfOpc)) {
									try {
										Long.parseLong(cpfOpc);
										break;
									} catch (Exception e) {
										System.out.printf("Error: %s, tente novamente!\n", error[2]);
									}
									
								} else {
									System.out.println("Error: cpf inválido, tente novamente!");
								}
								
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						List<String> camposValidos = Arrays.asList("nome", "nome completo", "data de nascimento",
								"gênero", "genero", "telefone", "contato" );
						String coluna = null;
						while (true) {
							System.out.print("Digite qual campo você deseja alterar: ");
							coluna = input2.nextLine().trim().toLowerCase();;
							
							if (coluna.isEmpty() == false) {
								try {
									Long.parseLong(coluna);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (camposValidos.contains(coluna)) {
										break;
									} else {
										System.out.println("Error: campo inválido, tente novamente!");
									}
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						alterarUsuario(cpfOpc, coluna);
						
					} else if (opc2 == 3) {
						String [] error = {"campo vazio", "apenas letras", "apenas números", "caracteres especiais não são permitidos"};
						
						System.out.println("\npara remover um usuário do sistema,");
						System.out.println("basta digitar o cpf do usuário que deseja excluír\n");
						
						String cpfOpcApa = null;
						while (true) {
							System.out.print("Digite o cpf do usuário que deseja excluir: ");
							cpfOpcApa = input2.nextLine();
							
							if (cpfOpcApa.isEmpty() == false) {
								if (ValidaCpf.isCpf(cpfOpcApa)) {
									try {
										Long.parseLong(cpfOpcApa);
										apagarLogin(cpfOpcApa);
										break;
									} catch (Exception e) {
										System.out.printf("Error: %s, tente novamente!\n", error[2]);
									}
									
								} else {
									System.out.println("Error: cpf inválido, tente novamente!");
								}
								
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
							
						}
						break;
					} else if (opc2 == 4) {
						System.out.println("sessão encerrada!\n");
						break;
					} else {
						System.out.println("Error: opção inválido, tente novamente");
					}
					
				}
				
			} else {
				System.out.println("Error: login falhou, tente novamente!\n");	
			}
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	
	public void dadosUsuario(String email) {
		try {
			String query = "SELECT * FROM usuario WHERE email = '"+email+"';";
			this.resultset = this.statement.executeQuery(query);
			while(this.resultset.next()) {
			System.out.println("\nDados do usuário:");
			System.out.println("NOME: " + this.resultset.getString("nomeCompleto") + "\nDATA NASCIMENTO: "
					+ this.resultset.getString("dataNascimento") + "\nGÊNERO: " + this.resultset.getString("genero") + "\nCPF: " + this.resultset.getString("cpf") 
					+ "\nTELEFONE: " + this.resultset.getString("telefone"));
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void inserirDados(String nomeCompleto, String dataNascimento, String genero, String cpf, String telefone, String email) {
		try {
			String query = "INSERT INTO usuario(nomeCompleto, dataNascimento, genero, cpf, telefone, email)"
					+ " values('"+nomeCompleto+"', '"+dataNascimento+"', '"+genero+"', '"+cpf+"', '"+telefone+"', '"+email+"');";
			this.statement.executeUpdate(query);
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}
	
	
	public void procurarUsuario(String cpf) {
	    try {
	        String query = "SELECT * FROM usuario WHERE cpf = '"+cpf+"'";
	        this.resultset = this.statement.executeQuery(query);
	        
	        if (this.resultset.next()) {
	            do {
	            	System.out.println("\nUSUARIO:");
	    			System.out.println("NOME: " + this.resultset.getString("nomeCompleto") + "\nDATA NASCIMENTO: "
	    					+ this.resultset.getString("dataNascimento") + "\nGÊNERO: " + this.resultset.getString("genero") + "\nCPF: " + this.resultset.getString("cpf") 
	    					+ "\nTELEFONE: " + this.resultset.getString("telefone"));
	                System.out.println();
	            } while (this.resultset.next());
	        } else {
	        	System.out.println("usuário não foi encontando!\n");
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	
	public void apagarLogin(String cpf) {
		Scanner input = new Scanner(System.in);
	    try {
	        String query = "SELECT cpf FROM usuario WHERE cpf = '"+cpf+"';";
	        ResultSet resultSet = this.statement.executeQuery(query);

	        if (resultSet.next()) {
	            String deleteQuery = "DELETE usuario, login FROM usuario INNER JOIN login ON usuario.email = login.email WHERE usuario.cpf = '"+cpf+"';";
	            
            	System.out.print("Quer mesmo excluir este usuário? [S/N]:");
            	String confirm = input.nextLine().trim().toUpperCase();
            	
            	if (confirm.equals("S")) {
            		this.statement.executeUpdate(deleteQuery);
            		System.out.println("Usuário excluido com sucesso!\n");
            		
            		
            	} else {
            		System.out.println("Usuário não foi excluido!zn");
            	}
	            
	        } else {
	            System.out.println("cpf do usuário não encontrado!\n");
	        }

	    } catch (Exception e) {
	        System.out.println("Erro ao excluir usuário: " + e.getMessage() + "\n");
	    }
	}
	
	public void alterarUsuario (String cpf, String coluna) {
		Scanner input = new Scanner(System.in);
		String [] error = {"campo vazio", "apenas letras", "apenas números", "caracteres especiais não são permitidos"};
		try {
			String query = "SELECT cpf FROM usuario WHERE cpf = '"+cpf+"';";
	        ResultSet resultSet = this.statement.executeQuery(query);
	        
	        if (resultSet.next()) {
	        	if (coluna.equalsIgnoreCase("nome") || coluna.equalsIgnoreCase("nome completo")) {
					
					String nomeCompleto2 = null;
					while (true) {
						System.out.print("Digite seu nome completo: ");
						nomeCompleto2 = input.nextLine().trim();
						
						if (nomeCompleto2.isEmpty() == false) {
							try {
								Integer.parseInt(nomeCompleto2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (nomeCompleto2.matches("[A-Za-z ]+")) {
									break;										
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
		        	coluna.replace(coluna, "nomeCompleto");
		        	String mudarCadastro = "UPDATE usuario SET nomeCompleto = '"+nomeCompleto2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!");
		        	
	        	} else if (coluna.equalsIgnoreCase("dataNascimento") || coluna.equalsIgnoreCase("data de nascimento")) {
	        		
				       String dataNascimento2 = null;
				        while (true) {
				            System.out.print("Digite sua data de nascimento: ");
				            dataNascimento2 = input.nextLine().trim();

				            if (!dataNascimento2.isEmpty()) {
				                String validarData = "^(0[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
				                String validarSomenteNumeros = "\\d+";
				                String validarSomenteLetras = "[a-zA-Z]+";

				                if (dataNascimento2.matches(validarData)) {
				                    SimpleDateFormat formatoDataOriginal = new SimpleDateFormat("dd/MM/yyyy");
				                    SimpleDateFormat formatoDataSql = new SimpleDateFormat("yyyy-MM-dd");
				                    Date date = formatoDataOriginal.parse(dataNascimento2);
				                    dataNascimento2 = formatoDataSql.format(date);
				                    break;
				                } else if (dataNascimento2.matches(validarSomenteNumeros)) {
				                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente");
				                } else if (dataNascimento2.matches(validarSomenteLetras)) {
				                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente");
				                } else {
				                    System.out.println("Erro: formato de data inválida, tente novamente! (dd/mm/aaaa)");				                }
				            } else {
				                System.out.printf("Erro: %s, tente novamente!\n", error[0]);
				            }
				        }
					
					coluna.replace(coluna, "dataNascimento");
		        	String mudarCadastro = "UPDATE usuario SET dataNascimento = '"+dataNascimento2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!");
		        	
	        	}  else if (coluna.equalsIgnoreCase("gênero") || coluna.equalsIgnoreCase("genero")) {
		        	
					String genero2 = null;
					while (true) {
						System.out.print("Digite seu genero: ");
						genero2 = input.nextLine().trim().toLowerCase();	
						
						if (genero2.equals("masculino") || genero2.equals("feminino") ||
								genero2.equals("transgênero") || genero2.equals("transgenero") ||
								genero2.equals("gênero neutro") || genero2.equals("genero neutro") ||
								genero2.equals("não binário") || genero2.equals("nao binario") ||
								genero2.equals("não binario") || genero2.equals("nao binário"))
						{
							
							List<String> generosValidos = Arrays.asList("masculino", "feminino", "transgênero",
									"gênero neutro", "não binário");
							if (generosValidos.contains(genero2)) {
								break;
							} else {
								if (genero2.equals("transgenero")) {
									genero2 = "transgênero";
									break;
									
								} else if (genero2.equals("genero neutro")) {
									genero2 = "gênero neutro";
									break;
									
								} else if (genero2.equals("nao binario") ||
										genero2.equals("não binario") || genero2.equals("nao binário")) 
								{
									genero2 = "não binário";
									break;
								} else {
									System.out.println("Error: genero inválido, tente novamente!");
								}
							}
							
						} else {
							System.out.println("Error: genero inválido, tente novamente ");
						}
					}
	        		
					coluna.replace(coluna, "genero");
	        		String mudarCadastro = "UPDATE usuario SET genero = '"+genero2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!");
		        	
	        	} else if (coluna.equalsIgnoreCase("cpf")) {
	        		System.out.println("Error: cpf não pode ser alterado!");
	        		

	        	} else if (coluna.equalsIgnoreCase("telefone") || coluna.equalsIgnoreCase("contato")) {
	        		
					 List<String> dddsValidos = Arrays.asList(
					 "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "24", "27", "28",
					 "31", "32", "33", "34", "35", "37", "38", "41", "42", "43", "44", "45", "46", "47",
					 "48", "49", "51", "53", "54", "55", "61", "62", "63", "64", "65", "66", "67", "68",
					 "69", "71", "73", "74", "75", "77", "79", "81", "82", "83", "84", "85", "86", "87",
					 "88", "89", "91", "92", "93", "94", "95", "96", "97", "98", "99");
					
					String telefone2 = null;
					while (true) {
						System.out.print("Digite seu contato: ");
						telefone2 = input.nextLine().trim();
						
						if (telefone2.isEmpty() == false) {
							String dds = telefone2.substring(0, 2);
							
							if (dddsValidos.contains(dds)) {
								try {
									Long.parseLong(telefone2);
									if (telefone2.matches("[0-9]{11}")) {
										break;
									} else if (telefone2.length() < 11){
										System.out.print("Error: quantidade de números insuficientes, tente novamente!\n");
									} else if (telefone2.length() > 11) {
										System.out.println("Error: quantidade de números excede, tente novamente");
									}
								} catch (Exception e) {
									System.out.printf("Error: %s, tente novamente!\n", error[2]);
								}
								
							} else {
								System.out.println("Error: DDD inválido, tente novamente!");
							}
							
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
					coluna.replace(coluna, "telefone");
	        		String mudarCadastro = "UPDATE usuario SET telefone = '"+telefone2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!");
	        	

	        } else {
	        	System.out.println("Error: campo não existe, tente novamente!");
	        }
	      }	
	        
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	
    public static boolean validarSenha(String senha) {

        Pattern specialCharPattern = Pattern.compile("[!@#$%&*()\\-+=\\[\\]{}|,;:'\"<>,.?/]");
        Matcher specialCharMatcher = specialCharPattern.matcher(senha);
        boolean containsSpecialChar = specialCharMatcher.find();

        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(senha);
        boolean containsUppercase = uppercaseMatcher.find();

        Pattern numberPattern = Pattern.compile("\\d");
        Matcher numberMatcher = numberPattern.matcher(senha);
        boolean containsNumber = numberMatcher.find();

        boolean validLength = senha.length() >= 5 && senha.length() <= 13;

        return containsSpecialChar && containsUppercase && containsNumber && validLength;
    }
	
	public void verificarSenha(String email, String senha) {
		try {
			String query = "select senha from login where email = '"+email+"';";
			this.resultset = this.statement.executeQuery(query);
			
			if (this.resultset.next()) {
				String recebersenha = this.resultset.getString("senha");
				String veriSenha = recebersenha;
				if (veriSenha.equals(senha)) {
					System.out.println("senha correta");
				} else {
					System.out.println("senha incorreta");
				}
				
			} else {
				System.out.println("Error: senha não encontrada, tente novamente!");
			}
			
		} catch (Exception e) {
			System.out.println("Errro: " + e.getMessage());
		}
	}
	
	public void desconectar() {
		try {
			System.out.println("Sistema encerrado!");
			this.connection.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}