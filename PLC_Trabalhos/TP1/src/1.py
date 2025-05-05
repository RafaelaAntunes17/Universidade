import pandas as pd
import matplotlib.pyplot as plt
import re

plt.style.use('ggplot')

try:
    df = pd.read_csv('myheart.csv')
except FileNotFoundError:
    print("Erro: O arquivo 'myheart.csv' não foi encontrado.")
    exit()

print(df.head())

colunas = ['idade', 'sexo', 'tensão','colesterol','batimento','temDoença']
for col in colunas:
    if col not in df.columns:
        print(f"Coluna '{col}' não encontrada no dataset.")
        exit()

print("\n-----------------------------------\n")        
# Calcular a percentagem da Doença no total da amostra e por Género
total = len(df)
doentes = df['temDoença'].sum()
percentagem_total = (doentes / total) * 100
print(f"Percentagem de doentes: {percentagem_total:.2f}%")

print("\n-----------------------------------\n")    

doentes_genero = df[df['temDoença'] == 1]['sexo'].value_counts()
total_genero = df['sexo'].value_counts()
percentagem_genero = (doentes_genero / total_genero) * 100
for genero, percentagem in percentagem_genero.items():
    print(f"Percentagem de doentes no género {genero}: {percentagem:.2f}%")
print("\n-----------------------------------\n")

# Distribuição da Doença por Escalões Etários
ida_min = 30
ida_max = df['idade'].max()
intervalos = range(ida_min, (ida_max // 5 + 2) * 5, 5)
eti = [f"{i}-{i+4}" for i in intervalos[:-1]]
df['etario'] = pd.cut(df['idade'], bins=intervalos, labels=eti, right=False)
doentes_etario = df[df['temDoença'] == 1]['etario'].value_counts()
total_etario = df['etario'].value_counts()
percentagem_etario = (doentes_etario / total_etario) * 100
percentagem_etario = percentagem_etario.sort_index()  # Ordenar por escalões
for etario, percentagem in percentagem_etario.items():
    print(f"Percentagem de doentes no escalão etário {etario}: {percentagem:.2f}%")
print("\n-----------------------------------\n")    

# Calcular a distribuição da Doença por níveis de Colesterol
col_min = df['colesterol'].min()
col_max = df['colesterol'].max()
col_intervalos = range((int(col_min) // 10) * 10, int((col_max) // 10 + 1) *10, 10)
col_eti = [f"{i}-{i+9}" for i in col_intervalos[:-1]]
df['col'] = pd.cut(df['colesterol'], bins=col_intervalos, labels=col_eti, right=False)
na_count = df['col'].isna().sum()
doentes_col = df[df['temDoença'] == 1]['col'].value_counts().sort_index()
total_col = df['col'].value_counts().sort_index()
percentagem_col = (doentes_col / total_col) * 100
percentagem_col = percentagem_col.dropna()  # Remove NaN values
print("Percentagem de doentes por nível de colesterol:")
for col, percentagem in percentagem_col.items():
    print(f"{col}: {percentagem:.2f}%")
    
print("\n-----------------------------------\n")

# Determinar se há alguma correlação significativa entre a Tensão ou o Batimento e a ocorrência de doença
correlacao_tensao = df['tensão'].corr(df['temDoença'])
correlacao_batimento = df['batimento'].corr(df['temDoença'])
print(f"Correlação entre a Tensão e a Doença: {correlacao_tensao:.2f}")
print(f"Correlação entre o Batimento e a Doença: {correlacao_batimento:.2f}")
print("\n-----------------------------------\n")    


# Criar gráficos para as distribuições, explorando o módulo matplotlib
def graf_esc_eta(dis):
    groups = list(dis.keys())
    counts = list(dis.values)  # Corrigido: Remover os parênteses
    
    plt.figure(figsize=(10,6))
    plt.bar(groups, counts, color='skyblue')
    plt.title('Distribuição da Doença por Escalões Etários')
    plt.xlabel('Escalão Etário')
    plt.ylabel('Percentagem de Doentes (%)')  # Atualizado para refletir porcentagem
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.savefig('distribuicao_idade.png')
    plt.close()

def graf_niv_col(dis):
    levels = list(dis.keys())
    counts = list(dis.values)
    
    plt.figure(figsize=(10,6))
    plt.bar(levels, counts, color='salmon')
    plt.title('Distribuição da Doença por Níveis de Colesterol')
    plt.xlabel('Nível de Colesterol')
    plt.ylabel('Percentagem de Doentes (%)')  # Atualizado para refletir porcentagem
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.savefig('distribuicao_colesterol.png')
    plt.close()

def graf_corr(x, y, title, xlabel, ylabel, filename):
    plt.figure(figsize=(10,6))
    plt.scatter(x, y, alpha=0.5, color='green')
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()
    
# Plotar a Distribuição da Doença por Escalões Etários
graf_esc_eta(percentagem_etario)

# Plotar a Distribuição da Doença por Níveis de Colesterol
graf_niv_col(percentagem_col)

# Plotar a Correlação entre Tensão e Doença
graf_corr(df['tensão'], df['temDoença'], 
          'Correlação entre Tensão e Doença', 
          'Tensão', 'Doença', 
          'correlacao_tensao.png')

# Plotar a Correlação entre Batimento e Doença
graf_corr(df['batimento'], df['temDoença'], 
          'Correlação entre Batimento e Doença', 
          'Batimento', 'Doença', 
          'correlacao_batimento.png')

# Função para gerar o HTML
def gerar_html(percentagem_total, percentagem_genero, percentagem_etario, percentagem_col, correlacao_tensao, correlacao_batimento):
    html_content = f"""
    <!DOCTYPE html>
    <html lang="pt">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Resultados da Análise de Doenças Cardíacas</title>
        <style>
            body {{
                font-family: 'Arial', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f0f4f8;
                color: #333;
            }}

            h1, h2, h3 {{
                color: #2E8B57;
                text-align: center;
            }}

            .section {{
                background-color: #ffffff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
                margin: 20px 0;
            }}

            ul {{
                list-style-type: none;
                padding: 0;
            }}

            li {{
                background: #e2f0d9;
                margin: 10px 0;
                padding: 15px;
                border-radius: 6px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }}

            .container {{
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                margin: 20px 0;
            }}

            .grafico {{
                flex: 1 1 45%;
                margin: 10px;
                text-align: center;
            }}

            .grafico img {{
                max-width: 100%;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }}

            @media (max-width: 768px) {{
                .grafico {{
                    flex: 1 1 100%;
                }}
            }}
        </style>
    </head>
    <body>
        <h1>Resultados da Análise de Doenças Cardíacas</h1>
        <div class="section">
            <h2>Percentagem de Doentes</h2>
            <p>Percentagem total de doentes: {percentagem_total:.2f}%</p>
        </div>
        <div class="section">
            <h2>Percentagem de Doentes por Género</h2>
            <ul>
                {''.join(f'<li>Género {genero}: {percent:.2f}%</li>' for genero, percent in percentagem_genero.items())}
            </ul>
        </div>
        <div class="section">
            <h2>Percentagem de Doentes por Escalão Etário</h2>
            <ul>
                {''.join(f'<li>Escalão Etário {etario}: {percent:.2f}%</li>' for etario, percent in percentagem_etario.items())}
            </ul>
        </div>
        <div class="section">
            <h2>Percentagem de Doentes por Nível de Colesterol</h2>
            <ul>
                {''.join(f'<li>Nível de Colesterol {col}: {percent:.2f}%</li>' for col, percent in percentagem_col.items())}
            </ul>
        </div>
        <div class="section">
            <h2>Correlação</h2>
            <p>Correlação entre Tensão e Doença: {correlacao_tensao:.2f}</p>
            <p>Correlação entre Batimento e Doença: {correlacao_batimento:.2f}</p>
        </div>
        <div class="container">
            <div class="grafico">
                <h3>Distribuição da Doença por Escalões Etários</h3>
                <img src="distribuicao_idade.png" alt="Distribuição da Doença por Escalões Etários">
            </div>
            <div class="grafico">
                <h3>Distribuição da Doença por Níveis de Colesterol</h3>
                <img src="distribuicao_colesterol.png" alt="Distribuição da Doença por Níveis de Colesterol">
            </div>
            <div class="grafico">
                <h3>Correlação entre Tensão e Doença</h3>
                <img src="correlacao_tensao.png" alt="Correlação entre Tensão e Doença">
            </div>
            <div class="grafico">
                <h3>Correlação entre Batimento e Doença</h3>
                <img src="correlacao_batimento.png" alt="Correlação entre Batimento e Doença">
            </div>
        </div>
    </body>
    </html>
    """
    with open('index.html', 'w') as file:
        file.write(html_content)
    print("Arquivo 'index.html' criado com sucesso!")
    
# Gerar o HTML
gerar_html(percentagem_total, percentagem_genero, percentagem_etario, percentagem_col, correlacao_tensao, correlacao_batimento)
