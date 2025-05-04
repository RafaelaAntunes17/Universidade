#include "ncurses.h"

void preenche(int n_linhas, int n_colunas, int dist[n_linhas][n_colunas], char mapa[n_linhas][n_colunas]){
	for(int i = 0; i < n_linhas; i++){
		for(int j = 0; j < n_colunas; j++){
			if(mapa[i][j] == '#')
				dist[i][j] = -1;
			else
				dist[i][j] = 0;
		}
	}
}

void distancia(int x, int y, int n_linhas, int n_colunas, int dist[n_linhas][n_colunas], char mapa[n_linhas][n_colunas]){
	preenche(n_linhas, n_colunas, dist, mapa);

	int t;

	if(y > n_colunas - y)
		t = y;
	else
		t = n_colunas - y;

	if(x > n_linhas - x && x > t)
		t = x;
	else if(n_linhas - x > x && n_linhas - x > t)
		t = n_linhas - x;

	for(int i = 1; i < t; i++){
		for(int index_x = x - i; index_x <= x + i; index_x++){
			if(index_x > 0 && index_x < n_linhas - 1){
				for(int index_y = y - i; index_y <= y + i; index_y++){
					if(index_y > 0 && index_y < n_colunas - 1){	
						if(dist[index_x][index_y] == 0){
							dist[index_x][index_y] = i;
						}
					}
				}
			}	
		}
	}

}