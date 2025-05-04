#include "main_menu.h"
#include "jogo.h"
#include <stdio.h>
#include <string.h>
#include <menu.h>
#include <stdlib.h>

void escrever_no_meio(WINDOW *menu_win, int starty, int startx, int largura, char *string, chtype cor)
{
	int tamanho, x, y;
	float temp;

	if (menu_win == NULL)
		menu_win = stdscr;

	getyx(menu_win, y, x);

	if (startx != 0)
		x = startx;
	if (starty != 0)
		y = starty;
	if (largura == 0)
		largura = 80;

	tamanho = strlen(string);
	temp = (largura - tamanho) / 2;
	x = startx + (int)temp;
	wattron(menu_win, cor);
	mvwprintw(menu_win, y, x, "%s", string);
	wattroff(menu_win, cor);
	refresh();
}

void fechar_menu(int op, MENU *menu, ITEM **itens)
{
	unpost_menu(menu);
	free_menu(menu);
	for (int i = 0; i < op; ++i)
	{
		free_item(itens[i]);
	}
	clear();
}

int menu()
{

	char *opcoes[] = {
		"Jogar",
		"Como Jogar",
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
	init_pair(1, COLOR_YELLOW, COLOR_BLACK);

	itens = (ITEM **)calloc(op, sizeof(ITEM *));

	for (int i = 0; i < op; ++i)
		itens[i] = new_item(opcoes[i], 0);
	menu = new_menu((ITEM **)itens);

	getmaxyx(menu_win, y, x);

	y = y / 2 - 8;
	x = x / 2 - 8;
	wrefresh(menu_win);
	menu_win = newwin(7, 16, y, x);
	keypad(menu_win, TRUE);

	set_menu_win(menu, menu_win);
	set_menu_sub(menu, derwin(menu_win, 3, 11, 3, 2));

	box(menu_win, 0, 0);
	escrever_no_meio(menu_win, 1, 0, 16, "Menu", COLOR_PAIR(1));
	mvwaddch(menu_win, 2, 0, ACS_LTEE);
	mvwhline(menu_win, 2, 1, ACS_HLINE, 14);
	mvwaddch(menu_win, 2, 15, ACS_RTEE);
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
