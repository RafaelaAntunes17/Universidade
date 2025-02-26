\documentclass[11pt, a4paper, fleqn]{article}
\usepackage{cp2425t}
\makeindex

%================= lhs2tex=====================================================%
%include polycode.fmt
%format (div (x)(y)) = x "\div " y
%format succ = "\succ "
%format ==> = "\Longrightarrow "
%format map = "\map "
%format length = "\length "
%format fst = "\p1"
%format p1  = "\p1"
%format snd = "\p2"
%format p2  = "\p2"
%format Left = "i_1"
%format Right = "i_2"
%format i1 = "i_1"
%format i2 = "i_2"
%format >< = "\times"
%format >|<  = "\bowtie "
%format |-> = "\mapsto"
%format . = "\comp "
%format .=?=. = "\mathbin{\stackrel{\mathrm{?}}{=}}"
%format (kcomp (f)(g)) = f "\kcomp " g
%format -|- = "+"
%format conc = "\mathsf{conc}"
%format summation = "{\sum}"
%format (either (a) (b)) = "\alt{" a "}{" b "}"
%format (frac (a) (b)) = "\frac{" a "}{" b "}"
%format (uncurry f) = "\uncurry{" f "}"
%format (const (f)) = "\underline{" f "}"
%format TLTree = "\mathsf{TLTree}"
%format (Seq (a)) = "{" a "}^{*}"
%format (lcbr (x)(y)) = "\begin{lcbr}" x "\\" y "\end{lcbr}"
%format (lcbr3 (x)(y)(z)) = "\begin{lcbr}" x "\\" y "\\" z "\end{lcbr}"
%format (split (x) (y)) = "\conj{" x "}{" y "}"
%format (for (f) (i)) = "\for{" f "}\ {" i "}"
%format B_tree = "\mathsf{B}\mbox{-}\mathsf{tree} "
%format <$> = "\mathbin{\mathopen{\langle}\$\mathclose{\rangle}}"
%format Either a b = a "+" b
%format fmap = "\mathsf{fmap}"
%format NA   = "\textsc{na}"
%format NB   = "\textbf{NB}"
%format inT = "\mathsf{in}"
%format outT = "\mathsf{out}"
%format outLTree = "\mathsf{out}"
%format inLTree = "\mathsf{in}"
%format inFTree = "\mathsf{in}"
%format outFTree = "\mathsf{out}"
%format Null = "1"
%format (Prod (a) (b)) = a >< b
%format fF = "\fun F "
%format l2 = "l_2 "
%format Dist = "\fun{Dist}"
%format IO = "\fun{IO}"
%format LTree = "{\LTree}"
%format FTree = "{\FTree}"
%format inNat = "\mathsf{in}"
%format (cata (f)) = "\llparenthesis\, " f "\,\rrparenthesis"
%format (cataNat (g)) = "\cataNat{" g "}"
%format (cataList (g)) = "\llparenthesis\, " g "\,\rrparenthesis"
%format (cataLTree (x)) = "\llparenthesis\, " x "\,\rrparenthesis"
%format (cataFTree (x)) = "\llparenthesis\, " x "\,\rrparenthesis"
%format (cataRose (x)) = "\llparenthesis\, " x "\,\rrparenthesis_\textit{\tiny R}"
%format (ana (g)) = "\ana{" g "}"
%format (anaList (g)) = "\anaList{" g "}"
%format (anaLTree (g)) = "\lanabracket\," g "\,\ranabracket"
%format (anaRose (g)) = "\lanabracket\," g "\,\ranabracket_\textit{\tiny R}"
%format (hylo (g) (h)) = "\llbracket\, " g ",\," h "\,\rrbracket"
%format (hyloRose (g) (h)) = "\llbracket\, " g ",\," h "\,\rrbracket_\textit{\tiny R}"
%format Nat0 = "\N_0"
%format Rational = "\Q "
%format toRational = " to_\Q "
%format fromRational = " from_\Q "
%format muB = "\mu "
%format (frac (n)(m)) = "\frac{" n "}{" m "}"
%format (fac (n)) = "{" n "!}"
%format (underbrace (t) (p)) = "\underbrace{" t "}_{" p "}"
%format matrix = "matrix"
%format `ominus` = "\mathbin{\ominus}"
%format % = "\mathbin{/}"
%format <-> = "{\,\leftrightarrow\,}"
%format <|> = "{\,\updownarrow\,}"
%format `minusNat`= "\mathbin{-}"
%format ==> = "\Rightarrow"
%format .==>. = "\Rightarrow"
%format .<==>. = "\Leftrightarrow"
%format .==. = "\equiv"
%format .<=. = "\leq"
%format .&&&. = "\wedge"
%format cdots = "\cdots "
%format pi = "\pi "
%format (curry (f)) = "\overline{" f "}"
%format delta = "\Delta "
%format (plus (f)(g)) = "{" f "}\plus{" g "}"
%format ++ = "\mathbin{+\!\!+}"
%format Integer  = "\mathbb{Z}"
%format (Cp.cond (p) (f) (g)) = "\mcond{" p "}{" f "}{" g "}"
%format (square (x)) = x "^2"

%format (cataTree (f) (g)) = "\llparenthesis\, " f "\:" g "\,\rrparenthesis_{\textit{\tiny T}}"
%format (cataForest (f) (g)) = "\llparenthesis\, " f "\:" g "\,\rrparenthesis_{\textit{\tiny F}}"
%format (anaTree (f) (g)) = "\lanabracket\;\!" f "\:" g "\:\!\ranabracket_{\textit{\tiny T}}"
%format (anaForest (f) (g)) = "\lanabracket\;\!" f "\:" g "\:\!\ranabracket_{\textit{\tiny F}}"
%format (hyloTree (ft) (ff) (gt) (gf)) = "\llbracket\, " ft "\:" ff ",\," gt "\:" gf "\,\rrbracket_{\textit{\tiny T}}"
%format (hyloForest (ft) (ff) (gt) (gf)) = "\llbracket\, " ft "\:" ff ",\," gt "\:" gf "\,\rrbracket_{\textit{\tiny F}}"
%format inTree = "\mathsf{in}_{Tree}"
%format inForest = "\mathsf{in}_{Forest}"
%format outTree = "\mathsf{out}_{Tree}"
%format outForest = "\mathsf{out}_{Forest}"

%format (cata' (f) (g)) = "\llparenthesis\, " f "\:" g "\,\rrparenthesis"
%format (ana' (f) (g)) = "\lanabracket\;\!" f "\:" g "\:\!\ranabracket"
%format (hylo' (ft) (ff) (gt) (gf)) = "\llbracket\, " ft "\:" ff ",\," gt "\:" gf "\,\rrbracket"
%format .* = "\star " 
%------------------------------------------------------------------------------%


%====== DEFINIR GRUPO E ELEMENTOS =============================================%

\group{G99}
\studentA{102490}{Ana Francisca Antunes Taxa }
\studentB{102933}{José Afonso da Silva Miranda }
\studentC{102527}{Rafaela Antunes Pereira  }

%==============================================================================%

\begin{document}

\sffamily
\setlength{\parindent}{0em}
\emergencystretch 3em
\renewcommand{\baselinestretch}{1.25} 
\input{Cover}
\pagestyle{pagestyle}
\setlength{\parindent}{1em}
\newgeometry{left=25mm,right=20mm,top=25mm,bottom=25mm}

\section*{Preâmbulo}

Em \CP\ pretende-se ensinar a progra\-mação de computadores como uma disciplina
científica. Para isso parte-se de um repertório de \emph{combinadores} que
formam uma álgebra da programação % (conjunto de leis universais e seus corolários)
e usam-se esses combinadores para construir programas \emph{composicionalmente},
isto é, agregando programas já existentes.

Na sequência pedagógica dos planos de estudo dos cursos que têm esta disciplina,
opta-se pela aplicação deste método à programação em \Haskell\ (sem prejuízo
da sua aplicação a outras linguagens funcionais). Assim, o presente trabalho
prático coloca os alunos perante problemas concretos que deverão ser implementados
em \Haskell. Há ainda um outro objectivo: o de ensinar a documentar programas,
a validá-los e a produzir textos técnico-científicos de qualidade.

Antes de abordarem os problemas propostos no trabalho, os grupos devem ler
com atenção o anexo \ref{sec:documentacao} onde encontrarão as instruções
relativas ao \emph{software} a instalar, etc.

Valoriza-se a escrita de \emph{pouco} código que corresponda a soluções simples
e elegantes que utilizem os combinadores de ordem superior estudados na disciplina.

%if False
\begin{code}
{-# OPTIONS_GHC -XNPlusKPatterns #-}
{-# LANGUAGE GeneralizedNewtypeDeriving, DeriveDataTypeable, FlexibleInstances #-}
module Main where
import Cp
import List hiding (fac)
import Nat hiding (aux)
import LTree
import FTree
import Exp
-- import Probability
import Data.List hiding (find)
-- import Svg hiding (for,dup,fdiv)
import Control.Monad
import Control.Applicative hiding ((<|>))
import System.Process
import Data.Char
import Data.Ratio
import Control.Concurrent

main = undefined
\end{code}
%endif

\Problema

Esta questão aborda um problema que é conhecido pela designação '\emph{H-index of a Histogram}'
e que se formula facilmente:
\begin{quote}\em
O h-index de um histograma é o maior número |n| de barras do histograma cuja altura é maior ou igual a |n|.
\end{quote}
Por exemplo, o histograma 
\begin{code}
h = [5,2,7,1,8,6,4,9]
\end{code}
que se mostra na figura
	\histograma
tem |hindex h = 5|
pois há |5| colunas maiores que |5|. (Não é |6| pois maiores ou iguais que seis só há quatro.)

Pretende-se definida como um catamorfismo, anamorfismo ou hilomorfismo uma função em Haskell
\begin{code}
hindex :: [Int] -> (Int,[Int])
\end{code}
tal que, para |(i,x) = hindex h|, |i| é o H-index de |h| e |x| é a lista de colunas de |h| que para ele contribuem.

A proposta de |hindex| deverá vir acompanhada de um \textbf{diagrama} ilustrativo.

\Problema

Pelo \href{https://en.wikipedia.org/wiki/Fundamental_theorem_of_arithmetic}{teorema
fundamental da aritmética}, todo número inteiro positivo tem uma única factorização
prima.  For exemplo,
\begin{verbatim}
primes 455
[5,7,13]
primes 433
[433]
primes 230
[2,5,23]
\end{verbatim}

\begin{enumerate}

\item	
Implemente como anamorfismo de listas a função
\begin{code}
primes :: Integer -> [Integer] 
\end{code}
que deverá, recebendo um número inteiro positivo, devolver a respectiva lista
de factores primos.

A proposta de |primes| deverá vir acompanhada de um \textbf{diagrama} ilustrativo.

\item A figura mostra a ``\emph{árvore dos primos}'' dos números |[455,669,6645,34,12,2]|.

      \primes

Com base na alínea anterior, implemente uma função em Haskell que faça a
geração de uma tal árvore a partir de uma lista de inteiros:

\begin{code}
prime_tree :: [Integer] -> MyExp Integer Integer
\end{code}

\textbf{Sugestão}: escreva o mínimo de código possível em |prime_tree| investigando
cuidadosamente que funções disponíveis nas bibliotecas que são dadas podem
ser reutilizadas.%
\footnote{Pense sempre na sua produtividade quando está a programar --- essa
atitude será valorizada por qualquer empregador que vier a ter.}

\end{enumerate}

\Problema

A convolução |a .* b| de duas listas |a| e |b| --- uma operação relevante em computação
---  está muito bem explicada
\href{https://www.youtube.com/watch?v=KuXjwB4LzSA}{neste vídeo} do canal
\textbf{3Blue1Brown} do YouTube,
a partir de \href{https://www.youtube.com/watch?v=KuXjwB4LzSA&t=390s}{|t=6:30|}.
Aí se mostra como, por exemplo:
\begin{quote}
|[1,2,3] .* [4,5,6] = [4,13,28,27,18]| 
\end{quote}
A solução abaixo, proposta pelo chatGPT,
\begin{spec}
convolve :: Num a => [a] -> [a] -> [a]
convolve xs ys = [sum $ zipWith (*) (take n (drop i xs)) ys | i <- [0..(length xs - n)]]
  where n = length ys 
\end{spec}
está manifestamente errada, pois |convolve [1,2,3] [4,5,6] = [32]| (!).

Proponha, explicando-a devidamente, uma solução sua para |convolve|.
Valorizar-se-á a economia de código e o recurso aos combinadores \emph{pointfree} estudados
na disciplina, em particular a triologia \emph{ana-cata-hilo} de tipos disponíveis nas
bibliotecas dadas ou a definir.

\Problema

Considere-se a seguinte sintaxe (abstrata e simplificada) para \textbf{expressões numéricas} (em |b|) com variáveis (em |a|),
\begin{code}
data Expr b a =   V a | N b | T Op [ Expr b a ]  deriving (Show,Eq)

data Op = ITE | Add | Mul | Suc deriving (Show,Eq)
\end{code}
possivelmente condicionais (cf.\ |ITE|, i.e.\ o operador condicional ``if-then-else``).
Por exemplo, a árvore mostrada a seguir
        \treeA
representa a expressão
\begin{eqnarray}
        |ite (V "x") (N 0) (multi (V "y") (soma (N 3) (V "y")))|
        \label{eq:expr}
\end{eqnarray}
--- i.e.\ |if x then 0 else y*(3+y)| ---
assumindo as ``helper functions'':
\begin{code}
soma  x y = T Add [x,y]
multi x y = T Mul [x,y]
ite x y z = T ITE [x,y,z]
\end{code}

No anexo \ref{sec:codigo} propôe-se uma base para o tipo Expr (|baseExpr|) e a 
correspondente algebra |inExpr| para construção do tipo |Expr|.

\begin{enumerate}
\item        Complete as restantes definições da biblioteca |Expr|  pedidas no anexo \ref{sec:resolucao}.
\item        No mesmo anexo, declare |Expr b| como instância da classe |Monad|. \textbf{Sugestão}: relembre os exercícios da ficha 12.
\item        Defina como um catamorfismo de |Expr| a sua versão monádia, que deverá ter o tipo:
\begin{code}
mcataExpr :: Monad m => (Either a (Either b (Op, m [c])) -> m c) -> Expr b a -> m c
\end{code}
\item        Para se avaliar uma expressão é preciso que todas as suas variáveis estejam instanciadas.
Complete a definição da função
\begin{code}
let_exp :: (Num c) => (a -> Expr c b) -> Expr c a -> Expr c b
\end{code}
que, dada uma expressão com variáveis em |a| e uma função que a cada uma dessas variáveis atribui uma
expressão (|a -> Expr c b|), faz a correspondente substituição.\footnote{Cf.\ expressões |let ... in ...|.}
Por exemplo, dada
\begin{code}
f "x" = N 0
f "y" = N 5
f _   = N 99
\end{code}
ter-se-á
\begin{spec}
        let_exp f e = T ITE [N 1,N 0,T Mul [N 5,T Add [N 3,N 1]]]
\end{spec}
isto é, a árvore da figura a seguir: 
        \treeB

\item Finalmente, defina a função de avaliação de uma expressão, com tipo

\begin{code}
evaluate :: (Num a, Ord a) =>  Expr a b -> Maybe a
\end{code}

que deverá ter em conta as seguintes situações de erro:

\begin{enumerate}

\item \emph{Variáveis} --- para ser avaliada, |x| em |evaluate x| não pode conter variáveis. Assim, por exemplo,
        \begin{spec}
        evaluate e = Nothing
        evaluate (let_exp f e) = Just 40
        \end{spec}
para |f| e |e|  dadas acima.

\item \emph{Aridades} --- todas as ocorrências dos operadores deverão ter
      o devido número de sub-expressões, por exemplo:
        \begin{spec}
        evaluate (T Add [ N 2, N 3]) = Just 5
        evaluate (T Mul [ N 2 ]) = Nothing
        \end{spec}

\end{enumerate}

\end{enumerate}

\noindent
\textbf{Sugestão}: de novo se insiste na escrita do mínimo de código possível,
tirando partido da riqueza estrutural do tipo |Expr| que é assunto desta questão.
Sugere-se também o recurso a diagramas para explicar as soluções propostas.

\part*{Anexos}

\appendix

\section{Natureza do trabalho a realizar}
\label{sec:documentacao}
Este trabalho teórico-prático deve ser realizado por grupos de 3 alunos.
Os detalhes da avaliação (datas para submissão do relatório e sua defesa
oral) são os que forem publicados na \cp{página da disciplina} na \emph{internet}.

Recomenda-se uma abordagem participativa dos membros do grupo em \textbf{todos}
os exercícios do trabalho, para assim poderem responder a qualquer questão
colocada na \emph{defesa oral} do relatório.

Para cumprir de forma integrada os objectivos do trabalho vamos recorrer
a uma técnica de programa\-ção dita ``\litp{literária}'' \cite{Kn92}, cujo
princípio base é o seguinte:
%
\begin{quote}\em
	Um programa e a sua documentação devem coincidir.
\end{quote}
%
Por outras palavras, o \textbf{código fonte} e a \textbf{documentação} de um
programa deverão estar no mesmo ficheiro.

O ficheiro \texttt{cp2425t.pdf} que está a ler é já um exemplo de
\litp{programação literária}: foi gerado a partir do texto fonte
\texttt{cp2425t.lhs}\footnote{O sufixo `lhs' quer dizer
\emph{\lhaskell{literate Haskell}}.} que encontrará no \MaterialPedagogico\
desta disciplina des\-com\-pactando o ficheiro \texttt{cp2425t.zip}.

Como se mostra no esquema abaixo, de um único ficheiro (|lhs|)
gera-se um PDF ou faz-se a interpretação do código \Haskell\ que ele inclui:

	\esquema

Vê-se assim que, para além do \GHCi, serão necessários os executáveis \PdfLatex\ e
\LhsToTeX. Para facilitar a instalação e evitar problemas de versões e
conflitos com sistemas operativos, é recomendado o uso do \Docker\ tal como
a seguir se descreve.

\section{Docker} \label{sec:docker}

Recomenda-se o uso do \container\ cuja imagem é gerada pelo \Docker\ a partir do ficheiro
\texttt{Dockerfile} que se encontra na diretoria que resulta de descompactar
\texttt{cp2425t.zip}. Este \container\ deverá ser usado na execução
do \GHCi\ e dos comandos relativos ao \Latex. (Ver também a \texttt{Makefile}
que é disponibilizada.)

Após \href{https://docs.docker.com/engine/install/}{instalar o Docker} e
descarregar o referido zip com o código fonte do trabalho,
basta executar os seguintes comandos:
\begin{Verbatim}[fontsize=\small]
    $ docker build -t cp2425t .
    $ docker run -v ${PWD}:/cp2425t -it cp2425t
\end{Verbatim}
\textbf{NB}: O objetivo é que o container\ seja usado \emph{apenas} 
para executar o \GHCi\ e os comandos relativos ao \Latex.
Deste modo, é criado um \textit{volume} (cf.\ a opção \texttt{-v \$\{PWD\}:/cp2425t}) 
que permite que a diretoria em que se encontra na sua máquina local 
e a diretoria \texttt{/cp2425t} no \container\ sejam partilhadas.

Pretende-se então que visualize/edite os ficheiros na sua máquina local e que
os compile no \container, executando:
\begin{Verbatim}[fontsize=\small]
    $ lhs2TeX cp2425t.lhs > cp2425t.tex
    $ pdflatex cp2425t
\end{Verbatim}
\LhsToTeX\ é o pre-processador que faz ``pretty printing'' de código Haskell
em \Latex\ e que faz parte já do \container. Alternativamente, basta executar
\begin{Verbatim}[fontsize=\small]
    $ make
\end{Verbatim}
para obter o mesmo efeito que acima.

Por outro lado, o mesmo ficheiro \texttt{cp2425t.lhs} é executável e contém
o ``kit'' básico, escrito em \Haskell, para realizar o trabalho. Basta executar
\begin{Verbatim}[fontsize=\small]
    $ ghci cp2425t.lhs
\end{Verbatim}

\noindent Abra o ficheiro \texttt{cp2425t.lhs} no seu editor de texto preferido
e verifique que assim é: todo o texto que se encontra dentro do ambiente
\begin{quote}\small\tt
\verb!\begin{code}!
\\ ... \\
\verb!\end{code}!
\end{quote}
é seleccionado pelo \GHCi\ para ser executado.

\section{Em que consiste o TP}

Em que consiste, então, o \emph{relatório} a que se referiu acima?
É a edição do texto que está a ser lido, preenchendo o anexo \ref{sec:resolucao}
com as respostas. O relatório deverá conter ainda a identificação dos membros
do grupo de trabalho, no local respectivo da folha de rosto.

Para gerar o PDF integral do relatório deve-se ainda correr os comando seguintes,
que actualizam a bibliografia (com \Bibtex) e o índice remissivo (com \Makeindex),
\begin{Verbatim}[fontsize=\small]
    $ bibtex cp2425t.aux
    $ makeindex cp2425t.idx
\end{Verbatim}
e recompilar o texto como acima se indicou. (Como já se disse, pode fazê-lo
correndo simplesmente \texttt{make} no \container.)

No anexo \ref{sec:codigo} disponibiliza-se algum código \Haskell\ relativo
aos problemas que são colocados. Esse anexo deverá ser consultado e analisado
à medida que isso for necessário.

Deve ser feito uso da \litp{programação literária} para documentar bem o código que se
desenvolver, em particular fazendo diagramas explicativos do que foi feito e
tal como se explica no anexo \ref{sec:diagramas} que se segue.

\section{Como exprimir cálculos e diagramas em LaTeX/lhs2TeX} \label{sec:diagramas}

Como primeiro exemplo, estudar o texto fonte (\lhstotex{lhs}) do que está a ler\footnote{
Procure e.g.\ por \texttt{"sec:diagramas"}.} onde se obtém o efeito seguinte:\footnote{Exemplos
tirados de \cite{Ol18}.}
\begin{eqnarray*}
\start
|
	id = split f g
|
\just\equiv{ universal property }
|
     lcbr(
          p1 . id = f
     )(
          p2 . id = g
     )
|
\just\equiv{ identity }
|
     lcbr(
          p1 = f
     )(
          p2 = g
     )
|
\qed
\end{eqnarray*}

Os diagramas podem ser produzidos recorrendo à \emph{package} \Xymatrix, por exemplo:
\begin{eqnarray*}
\xymatrix@@C=2cm{
    |Nat0|
           \ar[d]_-{|cataNat g|}
&
    |1 + Nat0|
           \ar[d]^{|id + (cataNat g)|}
           \ar[l]_-{|inNat|}
\\
     |B|
&
     |1 + B|
           \ar[l]^-{|g|}
}
\end{eqnarray*}

\section{Código fornecido}\label{sec:codigo}

\subsection*{Problema 1}

\begin{code}
h :: [Int]
\end{code}

\subsection*{Problema 4}
Definição do tipo:
\begin{code}
inExpr = either V (either N (uncurry T))

baseExpr g h f = g -|- (h -|- id >< map f)
\end{code}
Exemplos de expressões:
\begin{code}
e = ite (V "x") (N 0) (multi (V "y") (soma (N 3) (V "y")))
i = ite (V "x") (N 1) (multi (V "y") (soma (N (3%5)) (V "y")))
\end{code}
Exemplo de teste:
\begin{code}
teste = evaluate (let_exp f i) == Just (26 % 245)
    where f "x" = N 0 ; f "y" = N (1%7)
\end{code}

%----------------- Soluções dos alunos -----------------------------------------%

\section{Soluções dos alunos}\label{sec:resolucao}
Os alunos devem colocar neste anexo as suas soluções para os exercícios
propostos, de acordo com o ``layout'' que se fornece.
Não podem ser alterados os nomes ou tipos das funções dadas, mas pode ser
adicionado texto ao anexo, bem como diagramas e/ou outras funções auxiliares
que sejam necessárias.

\noindent
\textbf{Importante}: Não pode ser alterado o texto deste ficheiro fora deste anexo.

\subsection*{Problema 1}


A abordagem escolhida para a execução deste problema foi o catamorfismo. O catamorfismo
é uma técnica de redução ou acumulação de uma estrutura de dados recursiva, como
listas, num único valor, aplicando transformações aos elementos de forma sistemática.

Definimos a função `hindex`, que recebe uma lista de inteiros e chama a função auxiliar
`auxhindex`. Esta função retorna uma tupla com o valor do índice h (`valorh`) e uma lista associada.
Após obter os resultados, a função `hindex` verifica se o índice h calculado (`valorh`) é maior do que o 
número de elementos na lista (`length xs`). Em caso afirmativo, retorna `(0, [])`, indicando que o índice h não 
pode ser atingido. Caso contrário, retorna o valor do índice h e a lista dos artigos ordenados.



\begin{code}

hindex xs = 
  let (valorh, lista) = auxhindex xs
  in if valorh > length xs
     then (0, [])
     else (valorh, lista)
\end{code}

A função `auxhindex` utiliza o catamorfismo através da função cataList. Esta função
aplica o catamorfismo à lista de entrada, ou seja, percorre a lista recursivamente e aplica 
a transformação definida pela função `processList`. O either é utilizado para tratar os dois
casos possíveis: quando a lista de entrada é válida (não vazia), a função `processList` é
aplicada, caso contrário, a função trata o caso base, retornando um valor padrão (0, []).

A função `processList` recebe um elemento da lista e calcula o índice h. Primeiro, ordena
 a lista de forma decrescente. O índice h é determinado pelo número de artigos com citações 
maiores ou iguais à sua posição na lista ordenada. O valor do índice h (`hValue`) é definido 
por três condições: se o artigo na posição `h-1` tem exatamente `h` citações, se o número de 
artigos é igual a `h`, ou ajustando conforme a lista. A lista dos artigos selecionados 
(`selectedList`) é formada com base nas mesmas condições, escolhendo os primeiros `h` ou `h+1` artigos.


\begin{code}
auxhindex :: [Int] -> (Int, [Int])
auxhindex = cataList ( either (const (0, [])) processList)
  where
    processList (x, (h, xs)) =
      let sorted = sortBy (flip compare) (x:xs)
          h = length [x | (x, i) <- zip sorted [1..], x >= i]
          hValue
                | sorted !! (h - 1) == h = sorted !! (h - 1)
                | length sorted == h     = sorted !! (h - 1)
                | otherwise              = sorted !! h

          selectedList
                      | length sorted == h               = take h sorted
                      | length sorted > h && sorted !! (h - 1) == h = take h sorted
                      | otherwise                        = take (h + 1) sorted

      in (hValue, selectedList)


\end{code}
\begin{eqnarray*}
\xymatrix@@C=2cm{
    |Int|
           \ar[r]^{|hindex|}
&
    |[Int]|
}
\end{eqnarray*}
\begin{eqnarray*}
\xymatrix@@C=2cm{
    |[Int]|
      \ar[d]_-{|cataList (either (const (0,[])) processList)|} 
      \ar[r]_-{out} 
& 
    |1 + Int * [Int]| 
      \ar[d]^{|id + (cataList (either (const (0,[])) processList))|}
\\
    |(Int,[Int])| 
& 
    |1 + Int * (Int,[Int])| 
      \ar[l]^-{|either (const (0,[])) processList|}
}
\end{eqnarray*}


\subsection*{Problema 2}
Primeira parte:
Este código define a função primes, que gera a lista de factores primos de um número utilizando um anamorfismo (anaList). A função anaList aplica o anamorfismo com base na lógica definida em decompor.
Se o valor de x for menor ou igual a 1, a função devolve Left (), indicando que a construção da lista deve parar.
Caso contrário, a função decompor encontra o menor divisor de x (um número y que divide x sem deixar resto), e devolve um par contendo esse factor e o valor resultante de dividir x por esse factor.
Este par representa o próximo elemento da lista decomposta (o factor) e o valor reduzido (x dividido pelo factor), continuando o processo recursivo até que o número seja completamente decomposto.
\begin{code}

primes = anaList decompor
  
decompor x
  | x <= 1    = Left ()  
  | otherwise = Right (fator, x `div` fator) 
  where
    fator = head [y | y <- [2..x], x `mod` y == 0]  





\end{code}
Segunda parte:
A função buildTree constrói uma árvore de fatores primos para um único número, utilizando a função primes para obter os fatores primos e organizá-los numa estrutura em árvore. A função mergeNodes combina várias árvores de fatores primos numa única árvore consolidada, começando por criar um nó raiz com valor 1 (Node 1) e utilizando a função auxiliar mergeSimilar para agrupar e combinar nós semelhantes. A função mergeSimilar processa uma lista de árvores, identificando nós com o mesmo valor e fundindo as suas subárvores. Para verificar se dois nós são iguais, a função isSimilar compara os valores dos seus nós. Por fim, a função $prime_tree$ é responsável por receber uma lista de números, remover duplicados, construir árvores individuais para cada número com buildTree e consolidá-las numa única árvore com mergeNodes.
\begin{code}

data MyExp a b = MyNode a [MyExp a b] | MyLeaf b deriving Show

buildTree n = 
  case primes n of
    []     -> MyLeaf n
    (p:ps) -> MyNode p [foldr (\prime acc -> MyNode prime [acc]) (MyLeaf n) ps]

mergeNodes trees = MyNode 1 (mergeSimilar trees)

mergeSimilar [] = []
mergeSimilar (MyNode a children : rest) =
  let (similar, others) = span (isSimilar a) rest
      mergedChildren = children ++ concatMap (\(MyNode _ c) -> c) similar
  in MyNode a mergedChildren : mergeSimilar others
mergeSimilar (leaf : rest) = leaf : mergeSimilar rest

isSimilar a (MyNode b _) = a == b
isSimilar _ _ = False

prime_tree nums = mergeNodes (map buildTree (nub nums))


\end{code}

\subsection*{Problema 3}

A função `convolve` calcula a convolução entre duas listas `xs` e `ys` utilizando recursão.
Ela aplica a função `cataList` com o parâmetro `alg`, que trata dois casos: se a lista estiver
vazia, retorna uma lista de zeros; caso contrário, utiliza a função `step`. A função `step`
pega um elemento `x` de `xs`, multiplica todos os elementos de `ys` por `x`, concatenando a
lista resultante com zeros, e soma com os resultados anteriores, gerando assim a convolução.
Por exemplo, para as listas `[1, 2, 3]` e `[4, 5, 6]`, o primeiro passo seria multiplicar
cada elemento de `[4, 5, 6]` por `1` e somar os resultados deslocados.

\begin{code}
convolve :: Num a => [a] -> [a] -> [a]
convolve xs ys = cataList alg xs
  where
    alg = either (const (replicate (length ys - 1) 0)) (uncurry step)
    step x xs = zipWith (+) (map (*x) ys ++ repeat 0) (0 : xs)

\end{code}

\subsection*{Problema 4}
Definição do tipo:
\begin{code}
outExpr (V x)     = Left x
outExpr (N n)     = Right (Left n)
outExpr (T op es) = Right (Right (op, es))


recExpr f = baseExpr id id f
\end{code}
\emph{Ana + cata + hylo}:
\begin{code}
cataExpr g = g . baseExpr id id (cataExpr g) . outExpr

anaExpr g = inExpr . baseExpr id id (anaExpr g) . g                
hyloExpr h g = cataExpr h . anaExpr g
\end{code}
\emph{Maps}:
\emph{Monad}:
\emph{Let expressions}:
\begin{code}
instance Monad (Expr b) where
    return = V
    (V a) >>= f = f a
    (N b) >>= _ = N b
    (T op es) >>= f = T op (map (>>= f) es)

instance Functor (Expr s) where
         fmap f t = do { a <- t ; return (f a) } 

instance Applicative (Expr s) where
   (<*>) = aap
   pure  = return

let_exp f = cataExpr (either f (either N (uncurry T)))
\end{code}
Catamorfismo monádico:
\begin{code}
mcataExpr g = k where
  k (V a) = g (Left a)
  k (N b) = g (Right (Left b))
  k (T op xs) = do
    ys <- sequence (map k xs)
    g (Right (Right (op, return ys)))
\end{code}
Avaliação de expressões:
\begin{code}



evaluate = cataExpr eval
  where
    eval :: (Num a, Ord a) => Either b (Either a (Op, [Maybe a])) -> Maybe a
    eval (Left _) = Nothing
    eval (Right (Left n)) = Just n
    eval (Right (Right (op, args))) = 
      case (op, args) of
        (Add, [Just x, Just y]) -> Just (x + y)
        (Mul, [Just x, Just y]) -> Just (x * y)
        (Suc, [Just x]) -> Just (x + 1)
        (ITE, [Just cond, tBranch, fBranch]) -> if cond /= 0 then tBranch else fBranch
        _ -> Nothing
\end{code}

%----------------- Índice remissivo (exige makeindex) -------------------------%

\printindex

%----------------- Bibliografia (exige bibtex) --------------------------------%

\bibliographystyle{plain}
\bibliography{cp2425t}

%----------------- Fim do documento -------------------------------------------%
\end{document}
