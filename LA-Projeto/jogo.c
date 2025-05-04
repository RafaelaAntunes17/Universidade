#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ncurses.h>
#include <time.h>
#include <math.h>
#include "combate.h"
#include "mapa.h"
#include "caminho.h"
#include "visao.h"
#include "movimentação.h"
#include "posicao.h"
#include "distancia.h"
#include "main_menu.h"
#include "menu_fim.h"
#define RED 255, 0, 0

void jogo(int tipo_modo)
{
	STATE st;
	Posicoes posicao[5];
	Pedra vida[1];
	Smokes s[3];
	Laser l;
	int index_s = 0;
	WINDOW *wnd = initscr();
	int n_colunas, n_linhas;
	getmaxyx(wnd, n_linhas, n_colunas);

	srandom(time(NULL));

	cbreak();
	noecho();
	nonl();
	intrflush(stdscr, false);
	keypad(stdscr, true);

	init_pair(COLOR_WHITE, COLOR_WHITE, COLOR_BLACK);
	init_pair(COLOR_YELLOW, COLOR_YELLOW, COLOR_BLACK);
	init_pair(COLOR_BLUE, COLOR_BLUE, COLOR_BLACK);
	init_pair(COLOR_GREEN, COLOR_GREEN, COLOR_BLACK);
	init_pair(2, COLORS, COLOR_BLACK);
	init_color(COLORS, 200, 100, 50);
	init_pair(COLOR_RED, COLOR_RED, COLOR_BLACK);
	init_pair(1, COLORS, COLOR_BLACK);

	char mapa[n_linhas][n_colunas];
	char aux2[n_linhas][n_colunas];

	gerar_mapas(n_linhas, n_colunas, mapa);

	int pos[2];
	int pos_vida[2];
	verifica_posicao(pos, n_linhas, n_colunas, mapa);

	st.playerX = pos[0];
	st.playerY = pos[1];
	st.vida = 15;

	int dist[n_linhas][n_colunas];
	int raio = 14;
	for (int i = 0; i < 5; i++)
	{
		verifica_posicao(pos, n_linhas, n_colunas, mapa);
		mapa[pos[0]][pos[1]] = 'X';
		posicao[i].x = pos[0];
		posicao[i].y = pos[1];
		posicao[i].n_vida = 3;
		posicao[i].direcao = 6;
	}
	vida->utilizada = 0;
	vida->n = 0;
	for (int i = 0; i < 12; i++)
		l.pos[i] = -1;

	while (1)
	{
		int monstros_vivos = 0;
		for (int i = 0; i < n_linhas; i++)
		{
			for (int j = 0; j < n_colunas; j++)
			{
				aux2[i][j] = '0';
			}
		}
		atualiza_smokes(s, &index_s, n_linhas, n_colunas, mapa);

		distancia(st.playerX, st.playerY, n_linhas, n_colunas, dist, mapa);
		if (st.vida == 9 && vida->n == 0)
		{
			verifica_posicao(pos_vida, n_linhas, n_colunas, mapa);
			mapa[pos_vida[0]][pos_vida[1]] = 'o';
			vida->x = pos_vida[0];
			vida->y = pos_vida[1];
			vida->n = 1;
		}
		if (tipo_modo == 1)
		{

			imprimir_mapa(n_linhas, n_colunas, mapa, posicao, vida);
		}
		else
		{
			visao(st.playerX, st.playerY, posicao,vida, n_linhas, n_colunas, mapa, raio);
		}
		
		for (int i = 0; i < 5; i++)
		{
			if (posicao[i].n_vida > 0)
				monstros_vivos++;
		}
		if (monstros_vivos == 0 || st.vida <= 0)
		{
			clear();
			fim(monstros_vivos, st.vida);
		}

		if (l.pos[0] != -1)
		{
			for (int i = 0; i < 12 && l.pos[i] != -1; i += 2)
			{
				mapa[l.pos[i]][l.pos[i + 1]] = '.';
			}
		}

		move(n_linhas - 1, 0);

		attron(COLOR_PAIR(COLOR_BLUE));
		printw("(%d, %d) %d %d", st.playerX, st.playerY, n_colunas, n_linhas);
		attroff(COLOR_PAIR(COLOR_BLUE));

		attron(COLOR_PAIR(COLOR_WHITE));
		mvaddch(st.playerX, st.playerY, '@' | A_BOLD);
		attroff(COLOR_PAIR(COLOR_WHITE));

		move(st.playerX, st.playerY);

		if (update(&st, &posicao, n_linhas, n_colunas, mapa, s, &index_s, &l) == 1)
		{
			if (st.playerX == vida->x && st.playerY == vida->y)
			{
				st.vida += 4;
				vida->utilizada = 1;
			}
			caminho(&posicao, &st, n_linhas, n_colunas, dist, mapa);
			for (int i = 0; i < 5; i++)
			{
				if (((sqrt(pow(posicao[i].x - st.playerX, 2) + pow(posicao[i].y - st.playerY, 2))) <= 4) && posicao[i].n_vida >0)
				{

					ataque_monstros(&st, posicao[i].x, posicao[i].y, n_linhas, n_colunas, aux2, mapa);
				}
			}
			
		}
		refresh();
	}
}
