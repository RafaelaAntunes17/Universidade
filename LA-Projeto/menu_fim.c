#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ncurses.h>
#include <time.h>
#include <menu.h>
#include <math.h>
#include <string.h>
#include "main.h"
#include "jogo.h"
#include "main.h"
#include "main_menu.h"
#define RED 255, 0, 0

int menu_fim(int num_mons, int vida_player)
{
	char *opcoes[] = {
		"Modo Facil",
		"Modo Dificil",
		"Sair",
		(char *)NULL,
	};

	ITEM **itens;
	ITEM *item;
	MENU *menu;
	int valor;
	int op = (sizeof(opcoes) / sizeof(opcoes[0]));
	int x, y;
	WINDOW *menu_win = initscr();

	start_color();

	cbreak();
	noecho();
	keypad(stdscr, TRUE);
	init_color(COLORS, 200, 100, 50);
	init_pair(1, COLORS, COLOR_BLACK);

	itens = (ITEM **)calloc(op, sizeof(ITEM *));

	for (int i = 0; i < op; ++i)
		itens[i] = new_item(opcoes[i], 0);
	menu = new_menu((ITEM **)itens);

	getmaxyx(menu_win, y, x);

	y = y / 2 - 8;
	x = x / 2 - 8;
	wrefresh(menu_win);
	menu_win = newwin(7, 20, y, x);
	keypad(menu_win, TRUE);

	set_menu_win(menu, menu_win);
	set_menu_sub(menu, derwin(menu_win, 3, 15, 3, 2));

	box(menu_win, 0, 0);
	wattron(menu_win, A_BOLD | A_BLINK);
	if (num_mons == 0)
		escrever_no_meio(menu_win, 1, 0, 20, "VENCEU", COLOR_PAIR(2));
	else if (vida_player <= 0)
		escrever_no_meio(menu_win, 1, 0, 20, "GAME OVER", COLOR_PAIR(1));

	wattroff(menu_win, A_BOLD | A_BLINK);
	mvwaddch(menu_win, 2, 0, ACS_LTEE);
	mvwhline(menu_win, 2, 1, ACS_HLINE, 18);
	mvwaddch(menu_win, 2, 19, ACS_RTEE);
	refresh();

	post_menu(menu);
	wrefresh(menu_win);

	int c = 0;
	while (1)
	{
		c = wgetch(menu_win);
		switch (c)
		{
		case KEY_DOWN:
			menu_driver(menu, REQ_DOWN_ITEM);
			break;
		case KEY_UP:
			menu_driver(menu, REQ_UP_ITEM);
			break;
		case ' ':
			item = current_item(menu);
			valor = item_index(item);
			fechar_menu(op, menu, itens);
			return valor;
			break;
		}
	}
}

int fim(int num_mon, int vida_player)
{
	while (1)
	{
		int tipo_modo;
		int opcao_GAME = menu_fim(num_mon, vida_player);
		refresh();
		switch (opcao_GAME)
		{
		case 0:
			tipo_modo = 1;
			jogo(tipo_modo);
			break;
		case 1:
			tipo_modo = 0;
			jogo(tipo_modo);
			break;
		case 2:
			endwin();
			exit(0);
		}
		clear();
		refresh();
	}
	return 0;
}
