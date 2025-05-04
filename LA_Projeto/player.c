#include "player.h"
#include "posicao.h"
#include "mapa.h"
#include <ncurses.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

void verifica(int pos[2], int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas]){
     do{
        pos[0] = rand() %  n_linhas ;
        pos[1] =  rand() % n_colunas ;
        }while (mapa[pos[0]][pos[1]] == '#' || conta(n_linhas,n_colunas, mapa, pos[0], pos[1]) > 3);
    
}
void gerarposicao(Posicoes *posicao, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas]){
for (int i = 0; i < 5; i++){
		int pos[2];
		verifica(pos, n_linhas, n_colunas, mapa);
		posicao[i].x = pos[0];
		posicao[i].y = pos[1];
		}
}
