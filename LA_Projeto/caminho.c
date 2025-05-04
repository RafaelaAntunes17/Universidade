#include "player.h"
#include "posicao.h"
#include "caminho.h"
#include "distancia.h"
#include "mapa.h"
#include "combate.h"
#include <ncurses.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

int direcoposta(int direcao, int previousDirection)
{
  if ((direcao == 0 && previousDirection == 1) ||
      (direcao == 1 && previousDirection == 0) ||
      (direcao == 2 && previousDirection == 3) ||
      (direcao == 3 && previousDirection == 2))
  {
    return 1;
  }
  return 0;
}

void mov_aleatorios(Posicoes *posicao, int i, int n_linhas, int n_colunas, int posmov[], char mapa[n_linhas][n_colunas])
{
  int direcao;

  posmov[0] = posicao[i].x;
  posmov[1] = posicao[i].y;
  do
  {
    direcao = rand() % 4;

    if (direcao == 0)
    {
      posmov[0]--;
    }
    else if (direcao == 1)
    {
      posmov[0]++;
    }
    else if (direcao == 2)
    {
      posmov[1]++;
    }
    else if (direcao == 3)
    {
      posmov[1]--;
    }
  } while (direcoposta(direcao, posicao[i].direcao) == 1 || (posicao[i].x == posmov[0] && posicao[i].y == posmov[1]));
  if (posmov[0] >= 0 && posmov[0] < n_linhas &&
      posmov[1] >= 0 && posmov[1] < n_colunas &&
      mapa[posmov[0]][posmov[1]] != '#')
  {

    posicao[i].direcao = direcao;
  }
}

int find(Posicoes *posicao, int posataquex, int posataquey)
{
  for (int i = 0; i < 5; i++)
  {
    if ((posataquex == posicao[i].x) && (posataquey == posicao[i].y))
      return 1;
  }
  return 0;
}

int minimo(Posicoes *posicao, STATE *st, Ataque posataque[], int x, int y)
{

  int minimo = 4;
  int minimo2 = 1000;
  int dif;

  for (int i = 0; i < 9; i++)
  {
    if (posataque[i].dis > 0 && posataque[i].dis < minimo2 && posataque[i].dis - 1 != 0 && find(posicao, posataque[i].posx, posataque[i].posy) == 0)
    {
      minimo2 = posataque[i].dis;
      minimo = i;
    }
    else if (minimo2 == 1)
    {
      if (x == st->lastkey[0] && y != st->lastkey[1])
      {
        dif = y - st->lastkey[1];
        minimo += dif;
      }
      else if (x != st->lastkey[0] && y == st->lastkey[1])
      {
        dif = x - st->lastkey[0];
        minimo += (3 * dif);
      }
    }

    else if (minimo2 == posataque[i].dis && posataque[i].dis - 1 != 0 && find(posicao, posataque[i].posx, posataque[i].posy) == 0)
    {
      float dminimo = sqrt(pow(posataque[minimo].posx - x, 2) + pow(posataque[minimo].posy - y, 2));
      float d = sqrt(pow(posataque[i].posx - x, 2) + pow(posataque[i].posy - y, 2));
      if (dminimo > d)
      {
        minimo2 = posataque[i].dis;
        minimo = i;
      }
    }
  }
  return minimo;
}

void direcao(Ataque posataque[], int proxposicaoX, int proxposicaoY, int n_linhas, int n_colunas, int dist[n_linhas][n_colunas])
{
  int k = 0;
  for (int i = proxposicaoX - 1; i <= proxposicaoX + 1; i++)
  {
    for (int j = proxposicaoY - 1; j <= proxposicaoY + 1; j++)
    {

      if (i >= 0 && i < n_linhas &&
          j >= 0 && j < n_colunas)
      {
        posataque[k].dis = dist[i][j];
        posataque[k].posx = i;
        posataque[k].posy = j;
        k++;
      }
    }
  }
}

void caminho_minimo(Posicoes *posicao, STATE *st, int i, int n_linhas, int n_colunas, int posmov[], int dist[n_linhas][n_colunas])
{
  int x = st->playerX;
  int y = st->playerY;
  if (posicao[i].x != -1)
  {
    int proxposicaoX = posicao[i].x;
    int proxposicaoY = posicao[i].y;

    Ataque posataque[10];

    direcao(posataque, proxposicaoX, proxposicaoY, n_linhas, n_colunas, dist);

    posmov[0] = posataque[minimo(posicao, st, posataque, x, y)].posx;
    posmov[1] = posataque[minimo(posicao, st, posataque, x, y)].posy;
  }
}

void caminho(Posicoes *posicao, STATE *st, int n_linhas, int n_colunas, int dist[n_linhas][n_colunas], char mapa[n_linhas][n_colunas])
{

  for (int i = 0; i < 5; i++)
  {
    int posmov[2];
    if ((sqrt(pow(posicao[i].x - st->playerX, 2) + pow(posicao[i].y - st->playerY, 2)) <= 70))
    {
      caminho_minimo(posicao, st, i, n_linhas, n_colunas, posmov, dist);
    }
    else
      mov_aleatorios(posicao, i, n_linhas, n_colunas, posmov, mapa);

    if (find(posicao, posmov[0], posmov[1]) == 0)
    {
      if (posmov[0] >= 0 && posmov[0] < n_linhas &&
          posmov[1] >= 0 && posmov[1] < n_colunas &&
          mapa[posmov[0]][posmov[1]] != '#')
      {
        mapa[posicao[i].x][posicao[i].y] = '.';
        mapa[posmov[0]][posmov[1]] = 'X';

        posicao[i].x = posmov[0];
        posicao[i].y = posmov[1];
      }
    }
  }
}
