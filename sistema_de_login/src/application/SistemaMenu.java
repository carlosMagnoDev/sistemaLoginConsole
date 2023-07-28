package application;
import java.util.*;
import java.nio.file.spi.FileSystemProvider;
import java.text.*;
import java.time.LocalDate;
import java.time.format.*;
import org.fusesource.jansi.AnsiConsole;

import entities.Banco_de_dados;
import entities.ValidaCpf;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.*;

public class SistemaMenu {
	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		
		Scanner input = new Scanner(System.in);
		String [] error = {"campo vazio", "apenas letras", "apenas números", "caracteres especiais não são permitidos"};
		
		Banco_de_dados banco_de_dados = new Banco_de_dados();
		banco_de_dados.conectar();
		if (banco_de_dados.estaConectado()) {
			while (true) {
				try {
					System.out.println("Bem-vindo ao Sistema de login! "
							+ "\nDigite o número correspondente à ação que deseja executar:\n");
					System.out.println(" 1. Faça seu login \n 2. Realizar um cadastro \n 3. Procurar Usuário \n 4. Encerrar sistema");
					
					System.out.print("Digite o número da opção desejada: ");
					String opc = input.nextLine();
					int opc2 = Integer.parseInt(opc);
					
					if (opc2 == 1) {
						
						String emailLogin = null;
						while (true) {
							System.out.print("\nDigite seu email: ");
							emailLogin = input.nextLine().trim();
							
							if (emailLogin.isEmpty() == false) {
								break;
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String senhaLogin = null;
						while (true) {
							System.out.print("Digite sua senha: ");
							senhaLogin = input.nextLine().trim();
							
							if (senhaLogin.isEmpty() == false) {
								break;
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}						
										
						banco_de_dados.verificarSenha(emailLogin, senhaLogin);
						banco_de_dados.login(emailLogin, senhaLogin);
						
					} else if (opc2 == 2) {
						while (true) {
							System.out.println("\nRegistre sua conta usando seu email e senha válidos!");
							
							String email = null;
							while (true) {
								System.out.print("Digite seu email: ");
								email = input.nextLine().trim();
								
								if (email.isEmpty() == false) {
									try {
										Integer.parseInt(email);
										System.out.printf("Error: %s, tente novamente!\n", error[1]);
									} catch (Exception e) {
										if (email.matches("[a-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
											break;
										} else {
											System.out.println("Error: email inválido, tente novamente!");
										}
									}
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[0]);
								}
							}
							
							String email2 = null;
							while (true) {
								System.out.print("Digite seu email novamente: ");
								email2 = input.nextLine().trim();
								
								if (email2.isEmpty() == false) {
									try {
										Integer.parseInt(email2);
										System.out.printf("Error: %s, tente novamente!\n", error[1]);
									} catch (Exception e) {
										if (email2.matches("[a-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
											if (email.equals(email2) == true) {
												break;
											} else {
												System.out.println("Error: o email não é igual!");
											}
										} else {
											System.out.println("Error: email inválido, tente novamente!");
										}
									}
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[0]);
								}
							}
							
							String senha = null;
					        while (true) {
					            System.out.println("\nSua senha deve conter:\n- Pelo menos uma letra maiúscula\n- Pelo menos um caractere especial (@!#)\n"
					                    + "- Pelo menos um número \n- Precisa ter entre 5 e 13 caracteres\n");
					            System.out.print("Digite sua senha: ");
					            senha = input.nextLine().trim();
					            
					            if (senha.isEmpty() == false) {
					            	if (Banco_de_dados.validarSenha(senha)) {
					            		break;
					            	} else {
					            		System.out.println("Error: senha inválida, tente novamente!");
					            	}
					            	
					                
					            } else {
					                System.out.println("Error: A senha está vazia. Tente novamente!");
					            }
					        }

							
							String senha2 = null;
					        while (true) {
					            System.out.print("Digite sua senha novamente: ");
					            senha2 = input.nextLine().trim();

					            if (senha2.isEmpty() == false) {
					            	if (Banco_de_dados.validarSenha(senha2)) {
					            		if (senha.equals(senha2) == true) {
					            			break;					            			
					            		} else {
					            			System.out.println("Error: senhas diferentes, tente novamente!");
					            		}
					            	} else {
					            		System.out.println("Error: senha inválida, tente novamente!");
					            	}
					            	
					                
					            } else {
					                System.out.println("Error: A senha está vazia. Tente novamente!");
					            }
					        }
					        
					        System.out.println("\nDigite agora seus dados para continuar com o cadastro!");
					        
					        String nomeCompleto = null;
							while (true) {
								System.out.print("Digite seu nome completo: ");
								nomeCompleto = input.nextLine().trim();
								
								if (nomeCompleto.isEmpty() == false) {
									try {
										Integer.parseInt(nomeCompleto);
										System.out.printf("Error: %s, tente novamente!\n", error[1]);
									} catch (Exception e) {
										if (nomeCompleto.matches("[A-Za-z ]+")) {
											break;										
										} else {
											System.out.printf("Error: %s, tente novamente!\n", error[3]);
										}
									}
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[0]);
								}
							}
							
						       String dataNascimento = null;
						        while (true) {
						            System.out.print("Digite sua data de nascimento: ");
						            dataNascimento = input.nextLine().trim();

						            if (!dataNascimento.isEmpty()) {
						                String validarData = "^(0[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
						                String validarSomenteNumeros = "\\d+";
						                String validarSomenteLetras = "[a-zA-Z]+";

						                if (dataNascimento.matches(validarData)) {
						                    SimpleDateFormat formatoDataOriginal = new SimpleDateFormat("dd/MM/yyyy");
						                    SimpleDateFormat formatoDataSql = new SimpleDateFormat("yyyy-MM-dd");
						                    Date date = formatoDataOriginal.parse(dataNascimento);
						                    dataNascimento = formatoDataSql.format(date);
						                    break;
						                } else if (dataNascimento.matches(validarSomenteNumeros)) {
						                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente!");
						                } else if (dataNascimento.matches(validarSomenteLetras)) {
						                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente!");
						                } else {
						                    System.out.println("Erro: formato de data inválida, tente novamente! (dd/mm/aaaa)");
						                }
						            } else {
						                System.out.printf("Erro: %s, tente novamente!\n", error[0]);
						            }
						        }
						        
						        String genero = null;
								while (true) {
									System.out.print("Digite seu genero: ");
									genero = input.nextLine().trim().toLowerCase();	
									
									if (genero.equals("masculino") || genero.equals("feminino") ||
											genero.equals("transgênero") || genero.equals("transgenero") ||
											genero.equals("gênero neutro") || genero.equals("genero neutro") ||
											genero.equals("não binário") || genero.equals("nao binario") ||
											genero.equals("não binario") || genero.equals("nao binário"))
									{
										
										List<String> generosValidos = Arrays.asList("masculino", "feminino", "transgênero",
												"gênero neutro", "não binário");
										if (generosValidos.contains(genero)) {
											break;
										} else {
											if (genero.equals("transgenero")) {
												genero = "transgênero";
												break;
												
											} else if (genero.equals("genero neutro")) {
												genero = "gênero neutro";
												break;
												
											} else if (genero.equals("nao binario") ||
													genero.equals("não binario") || genero.equals("nao binário")) 
											{
												genero = "não binário";
												break;
											} else {
												System.out.println("Error: genero inválido, tente novamente!");
											}
										}
										
									} else {
										System.out.println("Error: genero inválido, tente novamente ");
									}
								}
							
								String cpf = null;
								while (true) {
									System.out.print("Digite seu cpf: ");
									cpf = input.nextLine().trim();
									if(ValidaCpf.isCpf(cpf)==true) {
										break;
									}
									else {
										System.out.println("Error: cpf inválido, tente novamente!");
									}
								}
							
							 List<String> dddsValidos = Arrays.asList(
									 "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "24", "27", "28",
									 "31", "32", "33", "34", "35", "37", "38", "41", "42", "43", "44", "45", "46", "47",
									 "48", "49", "51", "53", "54", "55", "61", "62", "63", "64", "65", "66", "67", "68",
									 "69", "71", "73", "74", "75", "77", "79", "81", "82", "83", "84", "85", "86", "87",
									 "88", "89", "91", "92", "93", "94", "95", "96", "97", "98", "99");
									
									String telefone = null;
									while (true) {
										System.out.print("Digite seu contato: ");
										telefone = input.nextLine().trim();
										
										if (telefone.isEmpty() == false) {
											String dds = telefone.substring(0, 2);
											
											if (dddsValidos.contains(dds)) {
												try {
													Long.parseLong(telefone);
													if (telefone.matches("[0-9]{11}")) {
														break;
													} else if (telefone.length() < 11){
														System.out.print("Error: quantidade de números insuficientes, tente novamente!\n");
													} else if (telefone.length() > 11) {
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
							
								System.out.println("");
								banco_de_dados.registrar(email, senha);
								banco_de_dados.inserirDados(nomeCompleto, dataNascimento, genero, cpf, telefone, email);
								
								break;
						}
							
					} else if (opc2 == 3) {
						
						System.out.println("\npara pesquisar um usuário específico,");
						System.out.println("basta digite o cpf do usuário que você está procurando\n");
						
						String cpfOpcProc = null;
						while (true) {
							System.out.print("Digite o cpf do usuário que deseja achar: ");
							cpfOpcProc = input.nextLine();
							
							if (cpfOpcProc.isEmpty() == false) {
								if (ValidaCpf.isCpf(cpfOpcProc)) {
									try {
										Long.parseLong(cpfOpcProc);
										banco_de_dados.procurarUsuario(cpfOpcProc);
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
						

					} else if (opc2 == 4) {
						banco_de_dados.desconectar();
						break;						
					} else {
						System.out.println("Error: opções inválida, tente novamente!\n");
					}
					
				} catch (Exception e) {
					System.out.println("Error: valor digitado não existe, tente novamente!\n");
				}
				
			}
			
		} else {
			System.out.println("Error: falha no sistema!");
			banco_de_dados.desconectar();
		}
		AnsiConsole.systemUninstall();
	}
}