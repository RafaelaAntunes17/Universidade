#include "visao.h"
#include "posicao.h"
#include "mapa.h"
#include <math.h>
#include <stdlib.h>
#include <ncurses.h>
#define PI 3.14159265359

void visao(int x, int y, Posicoes *posicao[],Pedra *vida, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], int raio)
{
    char aux[n_linhas][n_colunas];

    for (int i = 0; i < n_linhas; i++)
    {
        for (int j = 0; j < n_colunas; j++)
            aux[i][j] = '0';
    }

    for (double angulo = 0; angulo < 2 * PI; angulo += 0.01)
    {
        for (int a = 1; a <= raio; a++)
        {
            float o = a * cos(angulo);
            float t = a * sin(angulo);

            if (x + o >= 0 && x + o < n_linhas && y + t >= 0 && y + t < n_colunas)
            {
                if (mapa[x + (int)o][y + (int)t] == '.' || mapa[x + (int)o][y + (int)t] == 'X' || mapa[x + (int)o][y + (int)t] == 'L' || mapa[x + (int)o][y + (int)t] == 'l'|| mapa[x + (int)o][y + (int)t] == 'o')
                    aux[x + (int)o][y + (int)t] = '1';
                else
                {
                    aux[x + (int)o][y + (int)t] = '1';
                    a = raio + 1;
                }
            }
        }
    }

    for (int i = 0; i < n_linhas; i++)
    {
        for (int j = 0; j < n_colunas; j++)
        {
            if (aux[i][j] == '1')
            {
                int cor = ver_cor(i, j, n_linhas, n_colunas, mapa, posicao,vida);
                attron(COLOR_PAIR(cor));
                mvaddch(i, j, mapa[i][j] | A_BOLD);
                attroff(COLOR_PAIR(cor));
                if (mapa[i][j] == 'l')
                    mapa[i][j] = '.';
            }
            else
                mvaddch(i, j, ' ');
        }
    }
}
