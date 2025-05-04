typedef struct smokes{
	int posx;
	int posy;
	int duracao;
}Smokes;

typedef struct laser{
	int pos[12];
	int dur;
}Laser;

void atualiza_smokes();
void smoke();
void light();
void damage();
void ataque_monstros();
void gerar_novos_monstros();
void dano_monstros();
