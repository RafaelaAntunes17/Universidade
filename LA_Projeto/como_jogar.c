#include "como_jogar.h"
#include <ncurses.h>
void como_jogar()
{
	WINDOW *win;

	win = initscr();
	start_color();

	init_color(COLORS, 200, 100, 50);
	cbreak();
	noecho();
	keypad(stdscr, TRUE);
	int x, y;
	getmaxyx(win, y, x);

	y = y / 2 - 7;
	x = x / 2 - 21;

	win = newwin(14, 42, y, x);
	int c = 1;
	do
	{
		box(win, 0, 0);
		wattron(win, A_BOLD);
		mvwprintw(win, 0, 1, "Teclas para jogar:");
		wattroff(win, A_BOLD);
		mvwprintw(win, 1, 1, "key up para mover para cima ");
		mvwprintw(win, 2, 1, "key down para mover para baixo");
		mvwprintw(win, 3, 1, "key left para mover para a esquerda");
		mvwprintw(win, 4, 1, "key right para mover para direita");
		mvwprintw(win, 5, 1, "Tecla s para as bombas de nevoeiro");
		mvwprintw(win, 6, 1, "Tecla w para disparar ");
		wattron(win, A_BOLD);
		mvwprintw(win, 7, 1, "Objetivo");
		wattroff(win, A_BOLD);
		mvwprintw(win, 8, 1, "Matar todos os monstros.");
		wattron(win, A_BOLD);
		mvwprintw(win, 9, 1, "Dicas");
		wattroff(win, A_BOLD);
		mvwprintw(win, 10, 1, "As pedras da vida restauraram 4 de vida.");

		mvwprintw(win, 13, 15, "Enter para voltar ao menu:");
		wmove(win, 0, 0);
		wrefresh(win);
	} while ((c = wgetch(win)) != 10);

	endwin();
}