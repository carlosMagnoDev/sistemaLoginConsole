# Sistema de Login e Cadastro

Este é um sistema de login e cadastro desenvolvido em Java. Ele permite que os usuários façam login com suas contas existentes ou se cadastrem para criar novas contas. O sistema utiliza um banco de dados para armazenar informações dos usuários, incluindo nome completo, data de nascimento, gênero, CPF e número de telefone.

## Funcionalidades

1. **Login**: Os usuários podem fazer login informando seu e-mail e senha. O sistema verifica a combinação de e-mail e senha no banco de dados para autenticar o usuário.

2. **Cadastro**: Os usuários podem se cadastrar criando uma nova conta. Eles devem fornecer um e-mail válido, uma senha que atenda a determinados critérios (pelo menos uma letra maiúscula, um caractere especial, um número e entre 5 e 13 caracteres), nome completo, data de nascimento, gênero, CPF e número de telefone. O sistema valida as informações inseridas e as armazena no banco de dados para uso posterior.

3. **Procurar Usuário**: O sistema permite que um usuário pesquise um usuário específico informando o CPF. Se o CPF for válido e existir no banco de dados, as informações desse usuário serão exibidas.

4. **Encerrar Sistema**: Essa opção permite encerrar o sistema e desconectar do banco de dados.

## Requisitos

Para executar o sistema, é necessário ter o Java instalado no ambiente. Além disso, o sistema faz uso da biblioteca Jansi para manipulação de cores no terminal.

## Como executar

1. Verifique se você tem o Java instalado em sua máquina.

2. Verifique se a biblioteca Jansi está disponível no classpath do projeto.

3. Compile o código-fonte e execute a classe `SistemaMenu` para iniciar o sistema.

4. O sistema será iniciado e exibirá um menu com opções para login, cadastro, pesquisa de usuário e encerramento.

## Observações

- Certifique-se de que o banco de dados esteja configurado corretamente para que o sistema possa armazenar e recuperar informações dos usuários.

- O sistema usa expressões regulares para validar as informações fornecidas pelo usuário, garantindo que sejam inseridas corretamente.

- O tratamento de erros é realizado em várias partes do código para evitar falhas inesperadas.

- O sistema utiliza cores para melhorar a experiência do usuário no terminal, graças ao uso da biblioteca Jansi.

## Limitações

Este sistema possui algumas limitações e áreas que serão melhoradas no futuro:

- A autenticação não implementa medidas avançadas de segurança, como hash de senhas, o que pode ser considerado uma vulnerabilidade.

- A validação de e-mail e CPF é baseada em expressões regulares simples, o que pode permitir algumas entradas inválidas.

- O sistema não possui uma interface gráfica, funcionando apenas em linha de comando.

- A biblioteca Jansi pode não ser suportada em todos os ambientes de execução, o que pode causar problemas de exibição de cores.

## Contribuindo

Sinta-se à vontade para contribuir com melhorias neste projeto. Você pode criar um fork do repositório, fazer suas alterações e enviar um pull request.
