# Treinamento

Repositório para armazenar treinamentos da ZG Soluções.

## Dependências

- Python
- Pygments (pip install Pygments)
- Minted

## Preparando o Projeto

### Instalar o tema da ZG:

./install_lib.sh

### Preparar o editor

O projeto usa o Minted para highlight de código e esse usa o Pygments, que é externo ao LaTeX. Para que funcione normalmente, deve-se adicionar a opção -shell-escape no comando do LaTeX, ficando:

pdflatex -shell-escape

Para facilitar, adicione na opção de compilação do editor escolhido. No Kile, deve-se entrar em Settings > Configure Kile > Tools > Build > PDFLaTeX > Options e adicionar a opção.

### Compile o projeto de exemplo

Para testar que a configuração foi feita com sucesso, abra o 00-Exemplo/exemplo.tex e o compile. Esse projeto exemplifica como fazer algumas tarefas comuns nas apresentações.
