#include "combate.h"
#include "posicao.h"
#include <ncurses.h>
#include <stdlib.h>
#include <math.h>
#define PI 3.14159265359

void atualiza_smokes(Smokes s[], int *index_s, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
	for (int i = 0; i < *index_s; i++)
	{
		if (s[i].duracao > 0)
			s[i].duracao--;
		else if (s[i].duracao == 0)
			for (int x = s[i].posx; x < s[i].posx + 5; x++)
			{
				for (int y = s[i].posy; y < s[i].posy + 5; y++)
				{
					if (mapa[x][y] == 's')
						mapa[x][y] = '.';
				}
			}
	}
}

void smoke(STATE *st, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], Smokes s[], int *index_s)
{
	int x = st->playerX + st->lastkey[0] * 7;
	int y = st->playerY + st->lastkey[1] * 7;

	s[*index_s].posx = x - 2;
	s[*index_s].posy = y - 2;
	s[*index_s].duracao = 5;
	*index_s = *index_s + 1;

	for (int i = x - 2; i <= x + 2; i++)
	{
		for (int j = y - 2; j <= y + 2; j++)
		{
			float c1 = i - x;
			float c2 = j - y;
			float h = pow(c1, 2) + pow(c2, 2);
			h = sqrt(h);
			if (h <= 2)
			{
				if (mapa[i][j] == '.')
					mapa[i][j] = 's';
			}
		}
	}
}

float hip(int x, int y, int posx_l, int posy_l)
{
	int c1 = x - posx_l;
	int c2 = y - posy_l;
	float h = pow(c1, 2) + pow(c2, 2);
	return sqrt(h);
}

int ver_monstros(Posicoes *posicao, int x, int y){
	for(int i = 0; i < 5; i++)
		if(posicao[i].x == x && posicao[i].y == y)
			return i;

	return -1;
}

void light(STATE *st, Posicoes *posicao, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], Laser *l)
{
	int i = 0;
	int inc_x = st->lastkey[0];
	int inc_y = st->lastkey[1];

	int posx_l = st->playerX + inc_x;
	int posy_l = st->playerY + inc_y;
	int v = ver_monstros(posicao, posx_l, posy_l);

	while (hip(st->playerX, st->playerY, posx_l, posy_l) <= 6 && mapa[posx_l][posy_l] == '.' && v == -1)
	{
		l->pos[i++] = posx_l;
		l->pos[i++] = posy_l;
		mapa[posx_l][posy_l] = 'L';
		posx_l += inc_x;
		posy_l += inc_y;
		v = ver_monstros(posicao, posx_l, posy_l);
	}
	if(v != -1 && i + 1 != 11){
		l->pos[i] = posx_l;
		l->pos[i+1] = posy_l;

		damage(posicao, v, l);
	}
	l->dur = 0;
}

void damage(Posicoes *posicao, int n, Laser *l)
{
		for (int j = 0; j < 12; j += 2)
		{
			if (l->pos[j] == posicao[n].x && l->pos[j + 1] == posicao[n].y)
				posicao[n].n_vida -= 1;
		}
}

void ataque_monstros(STATE *st, int x, int y, int n_linhas, int n_colunas, char aux2[n_linhas][n_colunas], char mapa[n_linhas][n_colunas])
{

	double angulo_player = 0.00;
	int b = 1;

	for (double angulo = 0.0; angulo < 2 * PI; angulo += 0.01)
	{
		for (int a = 1; a <= 5; a++)
		{
			float o = a * cos(angulo);
			float t = a * sin(angulo);
			int l = x + (int)o;
			int c = y + (int)t;

			if (l >= 0 && l < n_linhas && c >= 0 && c < n_colunas)
			{

				if (l == st->playerX && c == st->playerY)
				{
					angulo_player = angulo;
					b = a;

					break;
				}
			}
		}
	}

	for (int a = 1; a < b; a++)
	{
		float o = a * cos(angulo_player);
		float t = a * sin(angulo_player);
		int l = x + (int)o;
		int c = y + (int)t;
		if (l >= 0 && l < n_linhas && c >= 0 && c < n_colunas)
		{
			if (mapa[l][c] == '.')
			{
				aux2[l][c] = '1';
			}
		}
		else if (mapa[l][c] == '#')
			break;
	}
	st->vida--;

	for (int i = 0; i < n_linhas; i++)
	{
		for (int j = 0; j < n_colunas; j++)
		{
			if (aux2[i][j] == '1')
			{
				mapa[i][j] = 'l';
			}
		}
	}
}