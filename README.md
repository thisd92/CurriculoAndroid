# CurriculoDev

## Visão Geral
Este repositório contém a implementação de um aplicativo Android para gerenciamento de currículos utilizando Java no Android Studio. O Firebase é usado como banco de dados para armazenar e gerenciar as informações dos currículos.

## Funcionalidades Implementadas

- **Cadastro de Currículos**: Permite cadastrar novos currículos com informações pessoais, habilidades e links profissionais.
- **Edição de Currículos**: Permite editar currículos existentes.
- **Exclusão de Currículos**: Permite excluir currículos do banco de dados.
- **Avaliação de Currículos**: Permite adicionar notas de avaliação aos currículos.
- **Filtro e Ordenação**: Permite filtrar e ordenar currículos por nome e nota.
- **Swipe Gestures**: Implementa gestos de swipe para facilitar a navegação e ações no aplicativo.

## Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/curriculo-dev.git
   ```
2. Abra o projeto no Android Studio:

- Abra o Android Studio.
- Selecione "Open an existing Android Studio project".
- Navegue até o diretório onde você clonou o repositório e selecione-o.

3. Configure o Firebase:

- Adicione o arquivo google-services.json ao diretório app do projeto.
- No console do Firebase, configure o projeto para usar Realtime Database.

## Uso

1. **Cadastro de Currículo:**
   - Preencha os campos de nome, idade, LinkedIn, GitHub e selecione o gênero.
   - Marque as checkboxes das linguagens de programação que conhece.
   - Clique no botão "Salvar" para cadastrar o currículo.

2. **Edição de Currículo:**
   - Na lista de currículos, clique em um currículo para abrir a tela de edição.
   - Altere as informações necessárias e clique em "Salvar".

3. **Exclusão de Currículo:**
   - Na tela de edição do currículo, selecione "Deletar Currículo" no menu.
   - Confirme a exclusão no diálogo de confirmação.

4. **Avaliação de Currículo:**
   - Na tela de edição do currículo, selecione "Avaliar Currículo" no menu.
   - Insira a nota de avaliação e clique em "Salvar".

5. **Filtro e Ordenação:**
   - Utilize o menu na tela de lista de currículos para filtrar por nota ou ordenar por nome.

## Contribuição
Contribuições são bem-vindas! Por favor, siga as diretrizes no arquivo [CONTRIBUTING.md](CONTRIBUTING.md).

## Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Sinta-se à vontade para entrar em contato se tiver alguma dúvida ou precisar de mais assistência!

---
