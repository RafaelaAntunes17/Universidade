#include "movimentação2.h"
#include "posicao.h"
#include <stdlib.h>
#include <ncurses.h>

void do_movement_action2(Posicoes *posicao, int dx, int dy, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas]) {
	if(mapa[posicao-> x + dx][posicao-> y + dy] != '#'){
			posicao->x += dx;
			posicao->y+= dy;
	}
}


void update2(Posicoes *posicao, int n_linhas, int n_colunas, char mapa[][n_colunas]) {
    int direcao = rand() % 4;
	int proxposicaoX = posicao -> x;
    int proxposicaoY = posicao->y;
  
if (direcao == 0) {
proxposicaoX--;
} else if (direcao == 1) {
    proxposicaoX++;
    } else if (direcao == 2) {
        proxposicaoY++;
        } else if (direcao == 3) {
        proxposicaoY--;
        }
     if (proxposicaoX >= 0 && proxposicaoX < n_linhas &&
        proxposicaoY >= 0 && proxposicaoY < n_colunas &&
        mapa[proxposicaoX][proxposicaoY] != '#') {
        
        posicao->x = proxposicaoX;
        posicao-> y = proxposicaoY;
      
   
	
    }

}

