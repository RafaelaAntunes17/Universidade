{-- 
import Data.List (sort)
hindex :: [Int] -> (Int,[Int])
hindex [] = (0,[])
hindex h = (head x, x)
    where
        listasposs = gera(sort h)
        x = last (cata listasposs)

gera :: [Int] -> [[Int]]
gera [] = []
gera h = map (\n -> filter (>= n) h) [1..length h]


cata :: [[Int]] -> [[Int]]
cata listas = filter (\x -> not (null x) && length x >= head x) listas



main :: IO ()
main = do
    let lista = [5, 2, 7, 1, 8, 5, 6, 4, 9, 9]
    print(gera (sort (lista)))
    print (cata (gera (sort lista)))

    print (hindex lista)


-- Função que encontra o menor divisor primo de n
divisorPrimo :: Integer -> Integer
divisorPrimo n
    | n <= 1    = error "Não existe divisor primo para números menores ou iguais a 1"
    | n `mod` 2 == 0 = 2  -- Se n for divisível por 2, o menor divisor primo é 2
    | otherwise = aux n 3  -- Se não, começa a procurar a partir de 3 (números ímpares)
    where
        -- Função auxiliar para procurar o menor divisor primo começando de d
        aux :: Integer -> Integer -> Integer
        aux n d
            | d * d > n = n          -- Se d^2 for maior que n, então n é primo
            | n `mod` d == 0 = d     -- Se encontrar um divisor, retorna o divisor
            | otherwise = aux n (d + 2)  -- Tenta o próximo número ímpar

-- Função que retorna a lista de fatores primos
primes :: Integer -> [Integer]
primes 0 = []
primes 1 = []
primes n = valorprime : primes (n `div` valorprime)
    where 
        valorprime = divisorPrimo n

-- Função principal para testar
main :: IO ()
main = do
    let n = 3
    print (primes n)  -- Espera-se [2, 3] para 6 = 2 * 3


import Data.List (nub)

data Exp a b = Node a [Exp a b] | Leaf b deriving Show

-- Função para encontrar o menor divisor primo
menorDivisor :: Int -> Int
menorDivisor n = head [x | x <- [2..n], n `mod` x == 0]

-- Função para gerar a lista de fatores primos de um número
primes :: Int -> [Int]
primes n
  | n <= 1    = []
  | otherwise = p : primes (n `div` p)
  where p = menorDivisor n

-- Função para construir a árvore de fatores primos para um único número
buildTree :: Int -> Exp Int Int
buildTree n = 
  case primes n of
    []     -> Leaf n
    (p:ps) -> Node p [foldr (\prime acc -> Node prime [acc]) (Leaf n) ps]

-- Função para mesclar árvores mantendo a ordem
mergeNodes :: [Exp Int Int] -> Exp Int Int
mergeNodes trees = Node 1 (mergeSimilar trees)

-- Função auxiliar para mesclar nós similares
mergeSimilar :: [Exp Int Int] -> [Exp Int Int]
mergeSimilar [] = []
mergeSimilar (Node a children : rest) =
  let (similar, others) = span (isSimilar a) rest
      mergedChildren = children ++ concatMap (\(Node _ c) -> c) similar
  in Node a mergedChildren : mergeSimilar others
mergeSimilar (leaf : rest) = leaf : mergeSimilar rest

-- Função auxiliar para verificar se dois nós são similares
isSimilar :: Int -> Exp Int Int -> Bool
isSimilar a (Node b _) = a == b
isSimilar _ _ = False

-- Função principal para construir a árvore de primos
primeTree :: [Int] -> Exp Int Int
primeTree nums = mergeNodes (map buildTree (nub nums))

-- Exemplo de uso
main :: IO ()
main = do
  let nums = [455, 669, 6645, 34, 12, 2]
  print $ primeTree nums



-- Definição do tipo
data Expr b a = V a | N b | T Op [Expr b a] deriving (Show, Eq)
data Op = ITE | Add | Mul | Suc deriving (Show, Eq)

-- Função outExpr
outExpr (V x)    = Left x
outExpr (N y)    = Right (Left y)
outExpr (T op l) = Right (Right (op, l))

-- Função baseExpr (mantida como estava)
baseExpr f g h = f -|- (g -|- (id >< map h))

recExpr f = baseExpr id id f

inExpr = either V (either N (uncurry T))

cataExpr g = g . baseExpr id id (cataExpr g) . outExpr

anaExpr g = inExpr . baseExpr id id (anaExpr g) . g

hyloExpr h g = cataExpr h . anaExpr g


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

let_exp f g x = f x >>= \y -> g x y

mcataExpr g = g .! (baseExpr return return (mcataExpr g) . outExpr)

evaluate :: (Num a, Ord a) => Expr a b -> Maybe a
evaluate = cataExpr alg
  where
    alg :: (Num a, Ord a) => Either b (Either a (Op, [Maybe a])) -> Maybe a
    alg (Left _) = Nothing  -- Variável não instanciada
    alg (Right (Left n)) = Just n  -- Número
    alg (Right (Right (op, ms))) = 
      case (op, ms) of
        (Add, [Just x, Just y]) -> Just (x + y)
        (Mul, [Just x, Just y]) -> Just (x * y)
        (Suc, [Just x]) -> Just (x + 1)
        (ITE, [Just c, t, e]) -> if c /= 0 then t else e
        _ -> Nothing  -- Aridade incorreta ou subexpressão inválida

printTest :: Show a => String -> Maybe a -> IO ()
printTest desc result = do
    putStrLn $ "Teste: " ++ desc
    putStrLn $ "Resultado: " ++ show result
    putStrLn ""

main :: IO ()
main = do
    putStrLn "Testando a função evaluate:\n"

    printTest "Número simples" $ 
        evaluate (N 5)

    printTest "Variável" $ 
        evaluate (V "x")

    printTest "Adição simples" $ 
        evaluate (T Add [N 2, N 3])

    printTest "Multiplicação simples" $ 
        evaluate (T Mul [N 2, N 3])

    printTest "Sucessor" $ 
        evaluate (T Suc [N 5])
    let expr = T ITE [N 1, N 42, N 0]
    evaluate expr
-- Resultado esperado: Just 42


import Cp
import List
import Data.List (sortBy)

hindex :: [Int] -> (Int, [Int])
hindex xs = 
  let (hVal, sortedList) = computeHIndex xs
  in if hVal > length xs
     then (0, [])
     else (hVal, sortedList)

computeHIndex :: [Int] -> (Int, [Int])
computeHIndex = cataList ( either (const (0, [])) processList)
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



main :: IO ()
main = do
    let inputList = [10,20,30]
  
    
    putStrLn "\nResultado do hyloList cataGen geraGen:"
    let result = hindex inputList
    print result
    
    putStrLn "\nExplicação do resultado:"
    putStrLn "1. geraGen gera sublistas começando de cada elemento."
    putStrLn "2. cataGen filtra as sublistas, mantendo apenas aquelas onde o comprimento é >= ao primeiro elemento."



import List
import Cp
import Data.List (nub)

data Exp a b = Node a [Exp a b] | Leaf b deriving Show

primes = anaList decompor

decompor x
  | x <= 1    = Left ()  
  | otherwise = Right (fator, x `div` fator) 
  where
    fator = head [y | y <- [2..x], x `mod` y == 0]

-- Função para construir a árvore de fatores primos para um único número
buildTree :: Int -> Exp Int Int
buildTree n = 
  case primes n of
    []     -> Leaf n
    (p:ps) -> Node p [foldr (\prime acc -> Node prime [acc]) (Leaf n) ps]

-- Função para mesclar árvores mantendo a ordem
mergeNodes :: [Exp Int Int] -> Exp Int Int
mergeNodes trees = Node 1 (mergeSimilar trees)

-- Função auxiliar para mesclar nós similares
mergeSimilar :: [Exp Int Int] -> [Exp Int Int]
mergeSimilar [] = []
mergeSimilar (Node a children : rest) =
  let (similar, others) = span (isSimilar a) rest
      mergedChildren = children ++ concatMap (\(Node _ c) -> c) similar
  in Node a mergedChildren : mergeSimilar others
mergeSimilar (leaf : rest) = leaf : mergeSimilar rest

-- Função auxiliar para verificar se dois nós são similares
isSimilar :: Int -> Exp Int Int -> Bool
isSimilar a (Node b _) = a == b
isSimilar _ _ = False

-- Função principal para construir a árvore de primos
primeTree :: [Int] -> Exp Int Int
primeTree nums = mergeNodes (map buildTree (nub nums))

-- Exemplo de uso
main :: IO ()
main = do
  let nums = [455, 669, 6645, 34, 12, 2]
  print $ primeTree nums
--}
import Cp

-- Definição do tipo
data Expr b a = V a | N b | T Op [Expr b a] deriving (Show, Eq)
data Op = ITE | Add | Mul | Suc deriving (Show, Eq)

-- Função outExpr
outExpr (V x)    = Left x
outExpr (N y)    = Right (Left y)
outExpr (T op l) = Right (Right (op, l))

-- Função baseExpr (mantida como estava)
baseExpr f g h = f -|- (g -|- (id >< map h))

recExpr f = baseExpr id id f

inExpr = either V (either N (uncurry T))

cataExpr g = g . baseExpr id id (cataExpr g) . outExpr

anaExpr g = inExpr . baseExpr id id (anaExpr g) . g

hyloExpr h g = cataExpr h . anaExpr g


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

mcataExpr g = k where
  k (V a) = g (Left a)
  k (N b) = g (Right (Left b))
  k (T op xs) = do
    ys <- sequence (map k xs)
    g (Right (Right (op, return ys)))
    
evaluate :: (Num a, Ord a) => Expr a b -> Maybe a
evaluate = cataExpr alg
  where
    alg :: (Num a, Ord a) => Either b (Either a (Op, [Maybe a])) -> Maybe a
    alg (Left _) = Nothing
    alg (Right (Left n)) = Just n
    alg (Right (Right (op, ms))) = 
      case (op, ms) of
        (Add, [Just x, Just y]) -> Just (x + y)
        (Mul, [Just x, Just y]) -> Just (x * y)
        (Suc, [Just x]) -> Just (x + 1)
        (ITE, [Just c, t, e]) -> if c /= 0 then t else e
        _ -> Nothing

printTest :: Show a => String -> Maybe a -> IO ()
printTest desc result = do
    putStrLn $ "Teste: " ++ desc
    putStrLn $ "Resultado: " ++ show result
    putStrLn ""

main :: IO ()
main = do
    let f "x" = N 0 
        f "y" = N 5 
        e = T ITE [V "x", N 0, T Mul [V "y", T Add [N 3, V "y"]]]

    putStrLn "Testando a função evaluate:\n"

    printTest "Número simples" $ 
        evaluate (N 5)

    printTest "Variável" $ 
        evaluate (V "x")

    printTest "Adição simples" $ 
        evaluate (T Add [N 2, N 3])

    printTest "Multiplicação simples" $ 
        evaluate (T Mul [N 2, N 3])

    printTest "Sucessor" $ 
        evaluate (T Suc [N 5])
    
    printTest "ITE" $
        evaluate (let_exp f e)