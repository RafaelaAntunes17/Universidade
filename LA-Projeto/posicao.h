typedef struct state{
	int playerX;
	int playerY;
    int vida;
	int lastkey[2];
}STATE;

typedef struct {
    int x;
    int y;
    int n_vida;
    int direcao;
    int lastkey[2];
}Posicoes;
typedef struct {
    int x;
    int y;
    int utilizada;
    int n; 
}Pedra;

typedef struct{
    int dis;
    int posx;
    int posy;
}Ataque;
