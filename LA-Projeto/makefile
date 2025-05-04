CC = gcc
CFLAGS = -Wall -Wextra -pedantic -O2
LDFLAGS =-lm -lcurses -lmenu
OBJFILES = main.o mapa.o visao.o movimentação.o distancia.o combate.o caminho.o main_menu.o modo.o jogo.o como_jogar.o menu_fim.o
TARGET = jogo

all: $(TARGET)

$(TARGET): $(OBJFILES)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJFILES) $(LDFLAGS)

clean: 
	rm -f $(OBJFILES) $(TARGET) *~
