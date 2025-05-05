import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from scipy.stats import pearsonr, ttest_ind

plt.style.use('ggplot')

# Funções de Plotagem
def plot_boxplot(data, column, group, title, xlabel, ylabel, filename):
    plt.figure(figsize=(8,6))
    sns.boxplot(x=group, y=column, data=data, palette="Set2")
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

def plot_violinplot(data, column, group, title, xlabel, ylabel, filename):
    plt.figure(figsize=(8,6))
    sns.violinplot(x=group, y=column, data=data, palette="Set3")
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

def plot_kde(data, column, group, title, xlabel, ylabel, filename):
    plt.figure(figsize=(10,6))
    sns.kdeplot(data=data[data[group]==0][column], label='Não Doente', shade=True)
    sns.kdeplot(data=data[data[group]==1][column], label='Doente', shade=True)
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.legend()
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()

# Carregar o dataset
try:
    df = pd.read_csv('myheart.csv')
except FileNotFoundError:
    print("Erro: O arquivo 'myheart.csv' não foi encontrado.")
    exit()

print(df.head())

# Verificar se todas as colunas necessárias estão presentes
colunas = ['idade', 'sexo', 'tensão','colesterol','batimento','temDoença']
for col in colunas:
    if col not in df.columns:
        print(f"Coluna '{col}' não encontrada no dataset.")
        exit()

print("\n-----------------------------------\n")        

# Calcular a percentagem da Doença no total da amostra
total = len(df)
doentes = df['temDoença'].sum()
percentagem_total = (doentes / total) * 100
print(f"Percentagem de doentes: {percentagem_total:.2f}%")

print("\n-----------------------------------\n")    

# Calcular a percentagem da Doença por Género
doentes_genero = df[df['temDoença'] == 1]['sexo'].value_counts()
total_genero = df['sexo'].value_counts()
percentagem_genero = (doentes_genero / total_genero) * 100
print("Percentagem de doentes por Género:")
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
percentagem_etario = percentagem_etario.sort_index()
print("Percentagem de doentes por Escalão Etário:")
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

# Determinar correlação com p-valores
correlacao_tensao, p_valor_tensao = pearsonr(df['tensão'], df['temDoença'])
correlacao_batimento, p_valor_batimento = pearsonr(df['batimento'], df['temDoença'])
print(f"Correlação entre a Tensão e a Doença: {correlacao_tensao:.2f} (p-valor: {p_valor_tensao:.4f})")
print(f"Correlação entre o Batimento e a Doença: {correlacao_batimento:.2f} (p-valor: {p_valor_batimento:.4f})")

# Plotar Box Plots
plot_boxplot(df, 'tensão', 'temDoença', 
            'Distribuição de Tensão por Status de Doença', 
            'Doença', 'Tensão (mmHg)', 
            'boxplot_tensao.png')

plot_boxplot(df, 'batimento', 'temDoença', 
            'Distribuição de Batimento por Status de Doença', 
            'Doença', 'Batimento (bpm)', 
            'boxplot_batimento.png')

# Plotar Violin Plots
plot_violinplot(df, 'tensão', 'temDoença', 
               'Distribuição de Tensão por Status de Doença', 
               'Doença', 'Tensão (mmHg)', 
               'violin_tensao.png')

plot_violinplot(df, 'batimento', 'temDoença', 
               'Distribuição de Batimento por Status de Doença', 
               'Doença', 'Batimento (bpm)', 
               'violin_batimento.png')

# Plotar KDE Plots
plot_kde(df, 'tensão', 'temDoença', 
         'Distribuição de Tensão por Status de Doença', 
         'Tensão (mmHg)', 'Densidade', 
         'kde_tensao.png')

plot_kde(df, 'batimento', 'temDoença', 
         'Distribuição de Batimento por Status de Doença', 
         'Batimento (bpm)', 'Densidade', 
         'kde_batimento.png')

# Geração da Página HTML
def generate_html(percent_total, percent_by_gender, percentagem_etario, percentagem_col,
                 correlacao_tensao, p_valor_tensao, correlacao_batimento, p_valor_batimento):
    # Converter as distribuições em listas para iteração
    age_dist_items = ''.join([f"<li>{etario}: {percentagem:.2f}%</li>" 
                              for etario, percentagem in percentagem_etario.items()])
    chol_dist_items = ''.join([f"<li>{col}: {percentagem:.2f}%</li>" 
                               for col, percentagem in percentagem_col.items()])
    gender_dist_items = ''.join([f"<li>{genero}: {percentagem:.2f}%</li>" 
                                 for genero, percentagem in percent_by_gender.items()])
    
    html_content = f"""
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Análise de Doenças Cardíacas</title>
        <style>
            body {{ font-family: Arial, sans-serif; margin: 40px; }}
            h1 {{ color: #2E8B57; }}
            img {{ max-width: 100%; height: auto; }}
            .section {{ margin-bottom: 50px; }}
            ul {{ list-style-type: none; padding: 0; }}
            li {{ margin-bottom: 5px; }}
        </style>
    </head>
    <body>
        <h1>Análise de Doenças Cardíacas</h1>
    
        <div class="section">
            <h2>Percentagem de Doentes</h2>
            <p>Percentagem de Doentes no Total da Amostra: <strong>{percent_total:.2f}%</strong></p>
            <h3>Percentagem por Género</h3>
            <ul>
                {gender_dist_items}
            </ul>
        </div>
    
        <div class="section">
            <h2>Distribuição da Doença por Escalões Etários</h2>
            <img src="boxplot_tensao.png" alt="Box Plot Tensão">
            <img src="violin_tensao.png" alt="Violin Plot Tensão">
            <img src="kde_tensao.png" alt="KDE Plot Tensão">
            <ul>
                {age_dist_items}
            </ul>
        </div>
    
        <div class="section">
            <h2>Distribuição da Doença por Níveis de Colesterol</h2>
            <img src="boxplot_colesterol.png" alt="Box Plot Colesterol">
            <img src="violin_colesterol.png" alt="Violin Plot Colesterol">
            <img src="kde_colesterol.png" alt="KDE Plot Colesterol">
            <ul>
                {chol_dist_items}
            </ul>
        </div>
    
        <div class="section">
            <h2>Correlação entre Tensão e Doença</h2>
            <p>Correlação de Pearson: <strong>{correlacao_tensao:.2f}</strong> (p-valor: <strong>{p_valor_tensao:.4f}</strong>)</p>
            <img src="correlacao_tensao.png" alt="Correlação Tensão e Doença">
        </div>
    
        <div class="section">
            <h2>Correlação entre Batimento e Doença</h2>
            <p>Correlação de Pearson: <strong>{correlacao_batimento:.2f}</strong> (p-valor: <strong>{p_valor_batimento:.4f}</strong>)</p>
            <img src="correlacao_batimento.png" alt="Correlação Batimento e Doença">
        </div>
    </body>
    </html>
    """
    return html_content

# Cálculo da percentagem total de doentes
percent_total = percentagem_total  # Já calculado anteriormente
percent_by_gender = percentagem_genero
percentagem_colesterol = percentagem_col  # Renomeado para clareza

# Geração do HTML
html_content = generate_html(percent_total, percentagem_genero, percentagem_etario, percentagem_col,
                             correlacao_tensao, p_valor_tensao, correlacao_batimento, p_valor_batimento)

# Salvar o HTML
with open('index.html', 'w', encoding='utf-8') as f:
    f.write(html_content)

print("Página 'index.html' criada com sucesso!")
