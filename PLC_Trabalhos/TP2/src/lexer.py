import ply.lex as lex
import sys

tokens = (
    # TIPOS
    'INTEIRO', 'NUMERO', 'ID', 'CITACAO', 'ARRAY',

    # OPERAÇÕES ARITMÉTICAS
    'ADICAO', 'SUBTRACAO', 'MULTIPLICACAO', 'DIVISAO', 'RESTO', 'INCREMENTO', 'DECREMENTO',

    # OPERAÇÕES RELACIONAIS
    'IGUAL', 'EQUIVALENTE',  'MENOR', 'MAIOR', 'MENORIGUAL', 'MAIORIGUAL',

    # PARÊNTESES E DELIMITADORES
    'ABREPARENTESES', 'FECHAPARENTESES', 'ABREPARENTESESRET', 'FECHAPARENTESESRET', 'DOISPONTOS', 'VIRGULA', 'ABRECHAVETAS', 'FECHACHAVETAS',

    # OPERAÇÕES LÓGICAS
    'E', 'OU', 'NAO',

    # OPERACÕES DE FUNÇÕES
    'ENTRADA', 'IMPRIMIR', 'SE', 'SENAO', 'ENQUANTO', 'FAZ', 'RETORNAR', 'DEFINIR'
)

# Literais (operadores e delimitadores simples)
literais = ['+', '-', '*', '/', '%', '=', '(', ')', ':', ',', '<', '>', '!', '[', ']', '{', '}']

# Dicionário de palavras reservadas
RESERVED = {
    'inteiro': 'INTEIRO',
    'entrada': 'ENTRADA',
    'imprimir': 'IMPRIMIR',
    'se': 'SE',
    'senao': 'SENAO',
    'enquanto': 'ENQUANTO',
    'faz': 'FAZ',
    'retornar': 'RETORNAR',
    'definir': 'DEFINIR',
    'array': 'ARRAY',
    'e': 'E',
    'ou': 'OU',
    'nao': 'NAO'
}

############################################################################
#                           DEFINIÇÃO DOS TOKENS                           #
############################################################################

# TIPOS

def t_CITACAO(t):
    r'"[^"\n]*"'
    return t

def t_NUMERO(t):
    r'\d+'
    t.value = int(t.value)
    return t

def t_ID(t):
    r'[a-zA-Z_][a-zA-Z0-9_]*'
    t.type = RESERVED.get(t.value, 'ID')  # Se for palavra reservada, atribui o token apropriado
    return t

# OPERAÇÕES ARITMÉTICAS
t_ADICAO = r'\+'
t_SUBTRACAO = r'-'
t_MULTIPLICACAO = r'\*'
t_DIVISAO = r'/'
t_RESTO = r'%'
t_INCREMENTO = r'\+\+'
t_DECREMENTO = r'--'

# OPERAÇÕES RELACIONAIS
t_IGUAL = r'='
t_EQUIVALENTE = r'=='
t_MENOR = r'<'
t_MAIOR = r'>'
t_MENORIGUAL = r'<='
t_MAIORIGUAL = r'>='

# PARÊNTESES E DELIMITADORES
t_ABREPARENTESES = r'\('
t_FECHAPARENTESES = r'\)'
t_ABREPARENTESESRET = r'\['
t_FECHAPARENTESESRET = r'\]'
t_DOISPONTOS = r':'
t_VIRGULA = r','
t_ABRECHAVETAS = r'{'
t_FECHACHAVETAS = r'}'

# OPERAÇÕES LÓGICAS
t_E = r'e'
t_OU = r'ou'
t_NAO = r'nao'


t_ignore = ' \t\r'

def t_newline(t):
    r'\n+'
    t.lexer.lineno += len(t.value)

def t_error(t):
    print(f"Caractere ilegal '{t.value[0]}'")
    t.lexer.skip(1)

lexer = lex.lex()

try:
    with open('tests/teste4.plc', 'r') as file:
        input = file.read()
        lexer.input(input)
        tok = lexer.token()
        while tok:
            print(tok)
            tok = lexer.token()
except FileNotFoundError:
    print("Arquivo não encontrado.")
    sys.exit(1)