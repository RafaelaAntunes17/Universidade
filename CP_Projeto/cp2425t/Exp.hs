
-- (c) MP-I (1998/9-2006/7) and CP (2005/6-2024/25)

module Exp where

import Cp
import BTree
import LTree
import FTree
import System.Process
import GHC.IO.Exception
import St
import List hiding (lookup)
import Data.List
import RelCalc

-- (1) Datatype definition -----------------------------------------------------

data Exp v o =   Var v              -- expressions are either variables
               | Term o [ Exp v o ] -- or terms involving operators and
                                    -- subterms
               deriving (Show,Eq)

inExp = either Var (uncurry Term)
outExp(Var v) = i1 v
outExp(Term o l) = i2(o,l)

baseExp f g h = f -|- (g >< map h)

-- (2) Ana + cata + hylo -------------------------------------------------------

recExp x = baseExp id id x

cataExp g = g . recExp (cataExp g) . outExp

anaExp g = inExp . recExp (anaExp g) . g

hyloExp h g = cataExp h . anaExp g

-- (3) Map ---------------------------------------------------------------------

instance BiFunctor Exp
         where bmap f g = cataExp ( inExp . baseExp f g id )

-- (4) Examples ----------------------------------------------------------------

mirrorExp = cataExp (inExp . (id -|- (id><reverse)))

expLeaves :: Exp a b -> [a]
expLeaves = cataExp (either singl (concat . p2))

expOps :: Exp a b -> [b]
expOps = cataExp (either nil (cons . (id><concat)))

expWidth :: Exp a b -> Int
expWidth = length . expLeaves

expDepth :: Exp a b -> Int
expDepth = cataExp (either (const 1) (succ . (foldr max 0) . p2))

nodes :: Exp a a -> [a]
nodes = cataExp (either singl g) where g = cons . (id >< concat)

graph :: Exp (a, b) (c, d) -> Exp a c
graph = bmap fst fst

-- (5) Graphics (DOT / HTML) ---------------------------------------------------

cExp2Dot :: Exp (Maybe String) (Maybe String) -> String
cExp2Dot x = beg ++ main (deco x) ++ end where
     main b = concat $ (map f . nodes) b  ++ (map g . lnks . graph) b
     beg = "digraph G {\n    /* edge [label=0]; */\n    graph [ranksep=0.5];\n"
     end = "}\n"
     g(k1,k2) = "    " ++ show k1 ++ " -> " ++ show k2 ++ "[arrowhead=none];\n"
     f(k,Nothing) = "    " ++ show k ++ " [shape=plaintext, label=\"\"];\n"
     f(k,Just s) = "    " ++ show k ++ " [shape=circle, style=filled, fillcolor=\"#FFFF00\", label=\"" ++ s ++ "\"];\n"

dotpict t = do { writeFile "_.dot" (cExp2Dot t) ; system "open _.dot" }

exp2Html n (Var v) = [ LCell v n 1 ]
exp2Html n (Term o l) = g (expWidth (Term o l)) o (map (exp2Html (n-1)) l)
                        where g i o k = [ ICell o 1 i ] ++ (foldr (++) [] k)

expDisplay :: FilePath -> Exp String String -> IO ()
expDisplay fn = writeFile fn . exp2txt

exp2txt = concat . txtFlat . (html2Txt Str) .  (uncurry exp2Html . (split expDepth id))

type Html a = [ Cell a ]

data Cell a = ICell a Int Int | LCell a Int Int deriving Show

data Txt = Str String | Blk [ Txt ] deriving Show

inds :: [a] -> [Int]
inds [] = []
inds (h:t) = inds t ++ [succ (length t)]

seq2ff :: [a] -> [(Int,a)]
seq2ff = (uncurry zip) . (split inds id)

ff2seq m = map p2 m

txtFlat :: Txt -> [[Char]]
txtFlat (Str s) = [s]
txtFlat (Blk []) = []
txtFlat (Blk (a:l)) = txtFlat a ++ txtFlat (Blk l)

html2Txt :: (a -> Txt) -> Html a -> Txt
html2Txt f = html . table . (foldr g u) 
             where u = Str "\n</tr>"
                   g c (Str s) = g c (Blk [Str s])
                   g (ICell a x y) (Blk b) = Blk ([ cell (f a) x y ] ++ b)
                   g (LCell a x y) (Blk b) = Blk ([ cell (f a) x y,  Str "\n</tr>\n<tr>"] ++ b)
                   html t = Blk [ Str("<meta charset=\"utf-8\"/>"++"<html>\n<body bgcolor=\"#F4EFD8\" " ++
                                        "text=\"#260000\" link=\"#008000\" " ++
                                        "vlink=\"#800000\">\n"),
                                   t,
                                   Str "</html>\n"
                                 ]
                   table t = Blk [ Str "<table style=\"border-collapse:collapse;border:0.5px solid black;\" cellpadding=1 cellspacing=0>",
                               t,
                               Str "</table>\n"
                             ]
                   cell c x y = Blk [ Str("\n<td style=\"border-collapse:collapse;border:0.4px solid black;\" rowspan=" ++
                                            itoa y ++
                                            " colspan=" ++
                                            itoa x ++
                                            " align=\"left\"" ++
                                            ">\n"),
                                       c,
                                            Str "\n</td>"
                                     ]
                   itoa x = show x

-- (6) Monad -------------------------------------------------------------------

muExp  = cataExp (either id (uncurry Term))

-- (7) Auxiliary functions -----------------------------------------------------

class (Show t) => Expclass t where
      pict :: t -> IO ExitCode
--------------------------------------------------------------------------------
instance (Show v, Show o) => Expclass (Exp v o) where
    pict = expShow "_.html" . bmap show show
--------------------------------------------------------------------------------
instance (Show a) => Expclass (BTree a) where
    pict = expShow "_.html" .  cBTree2Exp . (fmap show)

cBTree2Exp :: BTree a -> Exp [Char] a
cBTree2Exp = cataBTree (either (const (Var "nil")) h)
             where h(a,(b,c)) = Term a [b,c] 
--------------------------------------------------------------------------------
instance (Show a) => Expclass [a] where
    pict = expShow "_.html" .  cL2Exp . (fmap show)

cL2Exp [] = Var " "
cL2Exp l = Term "List" (map Var l)

--------------------------------------------------------------------------------
instance (Show a) => Expclass (LTree a) where
    pict = expShow "_.html" .  cLTree2Exp . (fmap show)

cLTree2Exp = cataLTree (either Var h)
             where h(a,b) = Term "Fork" [a,b] 
--------------------------------------------------------------------------------
cFTree2Exp = cataFTree (inExp . (id -|- (id><f))) where f(a,b)=[a,b]
--------------------------------------------------------------------------------
lnks :: Exp a a -> [(a, a)]
lnks (Var n) = []
lnks (Term n x) = (x >>= lnks) ++ [ (n,m) | Term m _ <- x ] ++ [ (n,m) | Var m <- x ]
--------------------------------------------------------------------------------
deco :: Num n => Exp v o -> Exp (n, v) (n, o)
deco e = fst (st (f e) 0) where
     f (Var e) = do {n <- get ; put(n+1); return (Var(n,e)) }
     f (Term o l) = do { n <- get ; put(n+1);
                         m <- sequence (map f l);
                         return (Term (n,o) m)
                       }
--------------------------------------------------------------------------------
untar :: (Ord v, Ord o) => [([o], v)] -> [Exp v o]
untar = a . (base id id untar) . c where
   a = sort.map inExp -- algebra
   c = join.(id><collect).sep. map((p2-|-assocr).distl.(outList >< id)) -- coalgebra
   base a b y = map(b -|- a >< y)

tar :: Exp c a -> [([a], c)]
tar = cataExp g where
   g = either v o
   v = singl . (split nil id)
   o = (>>= f ) . lstr
   f (o,l)=[(o:a,x)|(a,x)<-l]

instance (Ord v, Ord o) => Ord (Exp v o) where
   Var v <= Var u = v <= u
   Var v <= Term o x = False
   Term o x <= Var v = True
   Term o x <= Term o' x' = o >= o'

{-- instance Ord (Exp String String) where
   Var v <= Var u = v <= u
   Var v <= Term o x = False
   Term o x <= Var v = True
   Term o x <= Term o' x' = o >= o'
--}
-------------------------------------------------------------------------------

-- (8) Functions dependent on your OS -------------------------------------

wopen = ("start/b "++)
mopen = ("open    "++)

--1) Windows

--open = wopen

--2) Mac OS

open = mopen 

expShow fn e = do { expDisplay fn (mirrorExp e) ; system(open fn) }

