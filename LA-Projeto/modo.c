#include "main.h"
#include "jogo.h"
#include "main_menu.h"
#include <stdio.h>
#include <string.h>
#include <menu.h>
#include <stdlib.h>

int modo_menu()
{
	char *opcoes[] = {
		"Modo Facil",
		"Modo Dificil",
		"Voltar ao menu",
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
	init_pair(1, COLOR_YELLOW, COLOR_BLACK);

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
	escrever_no_meio(menu_win, 1, 0, 20, "Modos", COLOR_PAIR(1));
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
		case 10:
			item = current_item(menu);
			valor = item_index(item);
			fechar_menu(op, menu, itens);
			return valor;
			break;
		}
	}
}

int modo()
{
	while (1)
	{
		int opcao_modo = modo_menu();
		int tipo_modo;
		refresh();
		switch (opcao_modo)
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

			main();
			break;
		}
		clear();
		refresh();
	}
	return 0;
}
