# Transformação de Joukowski e Aplicações

Projeto desenvolvido no âmbito do estudo das **transformações conformes**, com foco na
**transformação de Joukowski** e na sua aplicação à modelação de perfis aerodinâmicos
(asas de aeronaves).

O projeto tem duas componentes:

- **`site/`** — relatório do projeto em formato de website (transformações conformes,
  transformação de Joukowski e aplicações em aerodinâmica).
- **`app/`** — ferramenta computacional interativa que visualiza como diferentes funções
  de variável complexa transformam figuras geométricas no plano.

🔗 **Relatório (site) online:** <!-- colar aqui o link do Netlify -->

## Contexto

Uma transformação conforme é uma função holomorfa, com derivada não nula, que preserva
ângulos localmente. A **transformação de Joukowski**, dada por

```
w = z + 1/z
```

quando aplicada a uma circunferência adequadamente posicionada produz uma curva com a
forma de um perfil de asa (aerofólio), o que a torna útil para modelar o escoamento em
torno de perfis alares.

## Ferramenta computacional (`app/`)

Aplicação com interface gráfica (PyQt5 + matplotlib) que permite escolher uma figura e
uma função e visualizar a transformação resultante.

- **Figuras:** Grelhas, Retângulo, Circunferência e modo Desenhar.
- **Funções:** sen(x), cos(x), exp(x) e a transformação de Joukowski (z + 1/z).
- Para obter um perfil de Joukowski: selecionar **Circunferência** + **z + 1/z**.

### Instalação e execução

```bash
cd app
python -m venv venv
source venv/bin/activate      # Linux / macOS
venv\Scripts\activate         # Windows
pip install -r ../requirements.txt
python main.py
```

## Relatório / site (`site/`)

Site estático (HTML, CSS, JavaScript, com MathJax para as fórmulas), publicado no Netlify.
Para abrir localmente, basta abrir `site/index.html` no navegador.

## Estrutura

```
Projeto_Final/
├── app/        Aplicação Python
│   ├── main.py
│   ├── circumference.py
│   ├── draw.py
│   ├── graphic.py
│   ├── lines.py
│   └── rectangle.py
├── site/       Relatório (website)
│   ├── index.html
│   ├── style.css
│   ├── script.js
│   └── img/
├── requirements.txt
└── README.md
```

## Autores

<!-- Adicionar aqui os nomes dos elementos do grupo -->

## Docente

Maria Irene Falcão
