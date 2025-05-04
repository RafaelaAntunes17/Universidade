#include "movimentação.h"
#include "posicao.h"
#include "caminho.h"
#include "combate.h"
#include <stdio.h>
#include <stdlib.h>
#include <ncurses.h>

#define PI 3.14159265359
int find_monstros(STATE *st, Posicoes *posicao, int dx, int dy){
	int find =0;
	for (int  i = 0; i < 5; i++)
	{
	if((st->playerX + dx == posicao[i].x) && (st->playerY + dy == posicao[i].y))
	
	find = 1;
	}
	return find;
	
	
}

int do_movement_action(STATE *st, Posicoes *posicao, int dx, int dy, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
	if (mapa[st->playerX + dx][st->playerY + dy] != '#' && find_monstros(st, posicao, dx, dy) == 0)
	{
		st->playerX += dx;
		st->playerY += dy;
		return 1;
	}
	return 0;
}

int update(STATE *st,Posicoes *posicao ,int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], Smokes *s[], int *index_s, Laser *l)
{
	int key = 0;

	key = getch();
	switch (key){
		case KEY_A1:
		case '7':
			st->lastkey[0] = -1;
			st->lastkey[1] = -1;
			return do_movement_action(st,posicao, -1, -1, n_linhas, n_colunas, mapa);
			break;
		case KEY_UP:
		case '8':
			st->lastkey[0] = -1;
			st->lastkey[1] = 0;
			return do_movement_action(st,posicao, -1, +0, n_linhas, n_colunas, mapa);
			break;
		case KEY_A3:
		case '9':
			st->lastkey[0] = -1;
			st->lastkey[1] = 1;
			return do_movement_action(st, posicao,-1, +1, n_linhas, n_colunas, mapa);
			break;
		case KEY_LEFT:
		case '4':
			st->lastkey[0] = 0;
			st->lastkey[1] = -1;
			return do_movement_action(st,posicao, +0, -1, n_linhas, n_colunas, mapa);
			break;
		case KEY_B2:
		case '5':
			break;
		case KEY_RIGHT:
		case '6':
			st->lastkey[0] = 0;
			st->lastkey[1] = 1;
			return do_movement_action(st, posicao,+0, +1, n_linhas, n_colunas, mapa);
			break;
		case KEY_C1:
		case '1':
			st->lastkey[0] = 1;
			st->lastkey[1] = -1;
			return do_movement_action(st,posicao, +1, -1, n_linhas, n_colunas, mapa);
			break;
		case KEY_DOWN:
		case '2':
			st->lastkey[0] = 1;
			st->lastkey[1] = 0;
			return do_movement_action(st,posicao, +1, +0, n_linhas, n_colunas, mapa);
			break;
		case KEY_C3:
		case '3':
			st->lastkey[0] = 1;
			st->lastkey[1] = 1;
			return do_movement_action(st,posicao, +1, +1, n_linhas, n_colunas, mapa);
			break;
		case 's':
			if (*index_s < 3)
				smoke(st ,n_linhas, n_colunas, mapa, s, index_s);
			break;
		case 'w':
			light(st, posicao, n_linhas, n_colunas, mapa, l);
			break;
		case 'q':
			endwin();
			exit(0);
			break;
	}
	return 0;
}