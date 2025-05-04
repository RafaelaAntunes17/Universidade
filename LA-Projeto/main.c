#include "main_menu.h"
#include "modo.h"
#include "como_jogar.h"
#include <ncurses.h>
#include <stdlib.h>

int main() {
	while(1){
		int opcao = menu();
		refresh();
		switch(opcao){
			case 0:
			    modo(); 
			break;
			case 1:
				como_jogar();
			break;
			case 2:
				endwin();
				exit(0);
				break;
		}
		clear();
		refresh();
	}
    return 0;
}
