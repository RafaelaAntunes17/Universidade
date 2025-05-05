from lexer import tokens
import ply.yacc as yacc

tokens = tokens
   
def p_Programa(p):
    '''Programa : Declaracoes Corpo
                | Corpo'''
    if len(p) == 3:
        parser.assembly = f"\nSTART\n\n{p[1]}{p[2]}\nSTOP"
    else:
        parser.assembly = f"\nSTART\n\n{p[1]}\nSTOP"

def p_Corpo(p):
    """Corpo : Processo 
             | Corpo Processo  """
    if len(p) == 2:
        p[0] = f"{p[1]}"
    else:
        p[0] = f"{p[1]}\n{p[2]}"

def p_Declaracoes(p):
    """Declaracoes : Declaracao 
                   | Declaracoes Declaracao """
    if len(p) == 2:
        p[0] = f"{p[1]}"
    else:
        p[0] = f"{p[1]}{p[2]}"

def p_Declaracao(p):
    '''Declaracao : INTEIRO Variaveis'''
    p[0] = f"{p[2]}"
    
def p_Variaveis(p):
    '''Variaveis : Variavel
                 | Variaveis VIRGULA Variavel'''
    if len(p) == 2:
        p[0] = f"{p[1]}"
    else:
        p[0] = f"{p[1]}{p[3]}"
        
def p_Variavel(p):
    '''Variavel : ID'''
    if p[1] not in p.parser.registos:
        p.parser.registos.update({p[1] : p.parser.gp})
        p[0] = f"PUSHI 0\n"
        p.parser.ints.append(p[1])
        p.parser.gp += 1
    else:
        print(f"Erro: Variável '{p[1]}' já foi declarada anteriormente.")
        p.parser.sucesso = False

def p_Variavel_Array(p):
    '''Variavel : ID ABREPARENTESESRET NUMERO FECHAPARENTESESRET
                | ID ABREPARENTESESRET NUMERO FECHAPARENTESESRET ABREPARENTESESRET NUMERO FECHAPARENTESESRET '''
                
    if p[1] not in p.parser.registos:
        if len(p) > 5:
            dimensao1 = int(p[3])
            dimensao2 = int(p[6])
            tamanho = dimensao1 * dimensao2
            p.parser.registos.update({p[1] : (p.parser.gp, dimensao1, dimensao2)})
        else:
            tamanho = int(p[3])
            p.parser.registos.update({p[1] : (p.parser.gp, tamanho)})
        
        p[0] = f"PUSHN {tamanho}\n"
        p.parser.gp += tamanho
    else:
        print(f"Erro: Variável '{p[1]}' já foi declarada anteriormente.")
        p.parser.sucesso = False

def p_Processo(p):
    '''Processo : Processo_Imprimir
                | Processo_Entrada 
                | Processo_Se
                | Processo_Enquanto
                | Incremento
                | Decremento
                | Expressao
                | Atribuicao'''
    p[0] = f"{p[1]}"

def p_processo_Se(p):
    '''Processo_Se :  SE ABREPARENTESES Expressao FECHAPARENTESES ABRECHAVETAS Corpo FECHACHAVETAS '''
    p[0] = f'{p[3]}JZ l{p.parser.labels}\n{p[6]}l{p.parser.labels}: NOP\n'
    p.parser.labels += 1

def p_processo_Se_Senao(p):
    '''Processo_Se : SE ABREPARENTESES Expressao FECHAPARENTESES ABRECHAVETAS Corpo FECHACHAVETAS SENAO ABRECHAVETAS Corpo FECHACHAVETAS '''
    p[0] = f'{p[3]}JZ l{p.parser.labels}\n{p[6]}JUMP l{p.parser.labels}f\nl{p.parser.labels}: NOP\n{p[10]}l{p.parser.labels}f: NOP\n'
    p.parser.labels += 1

def p_processo_Enquanto(p):
    '''Processo_Enquanto : ENQUANTO ABREPARENTESES Expressao FECHAPARENTESES ABRECHAVETAS Corpo FECHACHAVETAS '''
    
    p[0] = f'l{p.parser.labels}c: NOP\n{p[3]}JZ l{p.parser.labels}f\n{p[6]}JUMP l{p.parser.labels}c\nl{p.parser.labels}f: NOP\n'
    p.parser.labels += 1

def p_Atribuicao(p):
    '''Atribuicao : ID IGUAL Expressao
            '''
    if p[1] in p.parser.registos:
        if p[1] in p.parser.ints:
            p[0] = f"{p[3]}STOREG {p.parser.registos[p[1]]}\n"
        else:
            print(f"Erro: Variável '{p[1]}' não é de tipo inteiro.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False

def p_Atribuicao_Array(p):
    '''Atribuicao : ID ABREPARENTESESRET Fator FECHAPARENTESESRET IGUAL Expressao'''
    if p[1] in p.parser.registos:
        if p[1] not in p.parser.ints and len(p.parser.registos.get(p[1])) == 2:
            p[0] = f'PUSHGP\nPUSHI {p.parser.registos.get(p[1])[0]}\nPADD\n{p[3]}{p[6]}STOREN\n'
        else:
            print(f"Erro: Variável '{p[1]}' não é um array.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False

def p_Atribuicao_Matriz(p):
    '''Atribuicao : ID ABREPARENTESESRET Fator FECHAPARENTESESRET ABREPARENTESESRET Fator FECHAPARENTESESRET IGUAL Expressao'''
    if p[1] in p.parser.registos:
        if p[1] not in p.parser.ints and len(p.parser.registos(p[1])) == 3:
            c = p.parser.registos.get(p[1])[2]
            p[0] = f'PUSHGP\nPUSHI {p.parser.register.get(p[1])[0]}\nPADD\n{p[3]}PUSHI {c} \nMUL{p[6]}\nADD\n{p[9]}STOREN\n'
        else:
            print(f"Erro: Variável '{p[1]}' não é uma matriz.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False
    

def p_processo_Imprimir_Expressao(p):
    '''Processo_Imprimir : IMPRIMIR ABREPARENTESES Expressao FECHAPARENTESES '''
    p[0] = f'{p[3]}WRITEF\nPUSHS "\\n"\nWRITES\n'
    
def p_processo_Imprimir_Texto(p):
    '''Processo_Imprimir : IMPRIMIR ABREPARENTESES CITACAO FECHAPARENTESES '''
    p[0] = f'PUSHS {p[3]}\nWRITES\n'

def p_processo_Entrada(p):
    '''Processo_Entrada : ENTRADA ABREPARENTESES Variavel FECHAPARENTESES '''
    if p[3] in p.parser.registos:
        if p[3] in p.parser.ints:
            p[0] = f'READ\nATOI\nSTOREG {p.parser.registos.get(p[3])}\n'
        else:
            print(f"Erro: Variável '{p[3]}' não é de tipo inteiro.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[3]}' não foi declarada.")
        p.parser.sucesso = False
    
def p_Incremento(p):
    '''Incremento : ID INCREMENTO'''
    if p[1] in p.parser.registos:
        p[0] = f"PUSHG {p.parser.registos[p[1]]}\nPUSHI 1\nADD\nSTOREG {p.parser.registos[p[1]]}\n"
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False

def p_Decremento(p):
    '''Decremento : ID DECREMENTO'''
    if p[1] in p.parser.registos:
        p[0] = f"PUSHG {p.parser.registos[p[1]]}\nPUSHI 1\nSUB\nSTOREG {p.parser.registos[p[1]]}\n"
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False

def p_Expressao(p):
    '''Expressao : Termo
                 | Expressao ADICAO Termo
                 | Expressao SUBTRACAO Termo
                 | Expressao EQUIVALENTE Expressao
                 | Expressao MAIOR Expressao
                 | Expressao MENOR Expressao
                 | Expressao MAIORIGUAL Expressao
                 | Expressao MENORIGUAL Expressao
                 | Expressao E Expressao
                 | Expressao OU Expressao
                 | NAO Expressao
                 '''
    bin_ops = {
        '+': 'ADD',
        '-': 'SUB',
        '==': 'EQUAL',
        '>': 'SUP',
        '<': 'INF',
        '>=': 'SUPEQ',
        '<=': 'INFEQ',
        'e': 'AND',
        'ou': 'OR'
    }

    if len(p) == 3:
        if p[1] == 'nao':
            p[0] = f"{p[2]} NOT\n"
    elif len(p) == 4:  
        if p[2] in bin_ops:
            p[0] = f"{p[1]}{p[3]}{bin_ops[p[2]]}\n"
    else:  
        p[0] = f"{p[1]}"
  
def p_Termo(p):
    ''' Termo : Fator
              | Termo MULTIPLICACAO Fator
              | Termo DIVISAO Fator
              | Termo RESTO Fator'''
    if len(p) > 2:
        if p[2] == '*':
            p[0] = f"{p[1]}{p[3]} MUL\n"
        elif p[2] == '/':
            p[0] = f"{p[1]}{p[3]} DIV\n"
        elif p[2] == '%':
            p[0] = f"{p[1]}{p[3]} MOD\n"
    else:
        p[0] = f"{p[1]}"
            
def p_Fator_Variavel(p):
    '''Fator : ID
             '''

    if p[1] in p.parser.registos:
        if p[1] in p.parser.ints:
            p[0] = f"PUSHG {p.parser.registos.get(p[1])}\n"        
        else:
            print(f"Erro: Variável '{p[1]}' não é de tipo inteiro.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[1]}' oi não foi declarada.")
        p.parser.sucesso = False
        
def p_fator_numero(p):
    '''Fator : NUMERO'''
    p[0] = f"PUSHI {p[1]}\n"
    
def p_fator_array(p):
    '''Fator : ID ABREPARENTESESRET Expressao FECHAPARENTESESRET
             '''
    if p[1] in p.parser.registos:
        if p[1] not in p.parser.ints and len(p.parser.registos.get(p[1])) == 2:
            p[0] = f'PUSHGP\nPUSHI {p.parser.registos.get(p[1])[0]}\nPADD\n{p[3]}LOADN\n'
        else:
            print(f"Erro: Variável '{p[1]}' não é um array.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False

def p_fator_matriz(p):
    '''Fator : ID ABREPARENTESESRET Expressao FECHAPARENTESESRET ABREPARENTESESRET Expressao FECHAPARENTESESRET'''
    if p[1] in p.parser.registos:
        if p[1] not in p.parser.ints and len(p.parser.registos.get(p[1])) == 3:
            c = p.parser.registos.get(p[1])[2]
            p[0] = f'PUSHGP\nPUSHI {p.parser.registos.get(p[1])[0]}\nPADD\n{p[3]}PUSHI {c}\nMUL\n{p[6]}ADD\nLOADN\n'
        else:
            print(f"Erro: Variável '{p[1]}' não é uma matriz.")
            p.parser.sucesso = False
    else:
        print(f"Erro: Variável '{p[1]}' não foi declarada.")
        p.parser.sucesso = False

def p_error(p):
    if p:
        print(f"Erro de sintaxe na linha {p.lineno}, coluna {p.lexpos}: {p.value}")
    else:
        print("Erro de sintaxe inesperado.")
    parser.sucesso = False

try:
    parser = yacc.yacc(debug=True)
    parser.labels = 0
    parser.gp = 0
    parser.assembly = ""
    parser.registos = {}
    parser.ints = []
    parser.sucesso = True
    with open("tests/teste4.plc") as f:
        parser.parse(f.read())
        if parser.sucesso:
            with open("tests/teste4.vm", "w") as k:
                k.write(parser.assembly)
                print(parser.assembly)
        else:
            print("Erro na compilação.")
except FileNotFoundError:
    print("Arquivo não encontrado.")