#include "mapa.h"
#include "posicao.h"
#include <ncurses.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

int conta(int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], int linha, int coluna)
{
  int acc = 0;
  for (int i = linha - 1; i <= linha + 1; i++)
  {
    for (int j = coluna - 1; j <= coluna + 1; j++)
      if (mapa[i][j] == '#')
        acc++;
  }
  return acc;
}

void verifica_posicao(int pos[2], int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
  do
  {
    pos[0] = rand() % n_linhas;
    pos[1] = rand() % n_colunas;
  } while (mapa[pos[0]][pos[1]] == '#' || conta(n_linhas, n_colunas, mapa, pos[0], pos[1]) > 3);
}

void muda_mapa_p(int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
  int v = 0;

  for (int linha = 1; linha < n_linhas - 1; linha++)
  {
    for (int coluna = 1; coluna < n_colunas - 1; coluna++)
    {
      if (v > 0)
      {
        mapa[linha][coluna] = '#';
      }
      else
      {
        mapa[linha][coluna] = '.';
      }

      int c = conta(n_linhas, n_colunas, mapa, linha, coluna);

      if (c > 5 || c <= 2)
        v = 1;
      else
        v = 0;
    }
  }
}

void muda_mapa_s(int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
  int v = 0;

  for (int linha = 1; linha < n_linhas - 1; linha++)
  {
    for (int coluna = 1; coluna < n_colunas - 1; coluna++)
    {
      if (v > 0)
      {
        mapa[linha][coluna] = '#';
      }
      else
      {
        mapa[linha][coluna] = '.';
      }

      int c = conta(n_linhas, n_colunas, mapa, linha, coluna);
      if (c < 5)
        v = 1;
      else
        v = 0;
    }
  }
}

void limite_horizontal(int l, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
  int i = 0;
  while (i < n_colunas)
  {
    mapa[l][i] = '#';
    i++;
  }
}

void limite_vertical(int c, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
  int i = 0;
  while (i < n_linhas)
  {
    mapa[i][c] = '#';
    i++;
  }
}

void gerar_mapas(int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas])
{
  int perc = n_linhas * n_colunas * 0.3 - (n_linhas + n_colunas - 4);
  int i = 0;

  limite_horizontal(0, n_linhas, n_colunas, mapa);
  limite_horizontal(n_linhas - 1, n_linhas, n_colunas, mapa);
  limite_vertical(0, n_linhas, n_colunas, mapa);
  limite_vertical(n_colunas - 1, n_linhas, n_colunas, mapa);

  while (i < perc)
  {
    int linha = rand() % n_linhas;
    int coluna = rand() % n_colunas;
    if (mapa[linha][coluna] != '#')
    {
      mapa[linha][coluna] = '#';
      i++;
    }
  }

  for (int i = 0; i < n_linhas; i++)
  {
    for (int j = 0; j < n_colunas; j++)
    {
      if (mapa[i][j] != '#')
        mapa[i][j] = '.';
    }
  }

  for (int i = 0; i < 5; i++)
  {
    muda_mapa_s(n_linhas, n_colunas, mapa);
    muda_mapa_p(n_linhas, n_colunas, mapa);
  }

  for (int i = 1; i < n_linhas - 1; i++)
  {
    for (int j = 1; j < n_colunas - 1; j++)
    {
      if (conta(n_linhas, n_colunas, mapa, i, j) <= 4)
        mapa[i][j] = '.';
    }
  }
}

int ver_cor(int i, int j, int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], Posicoes *posicao, Pedra *vida)
{
  int c = 0;
  switch (mapa[i][j])
  {
  case 'X':
    for (int m = 0; m < 5; m++)
    {
      if (posicao[m].x == i && posicao[m].y == j)
      {
        if (posicao[m].n_vida == 3)
        {
          c = 2; // Verde
        }
        else if (posicao[m].n_vida == 2)
        {
          c = 3; // Amarelo
        }
        else if (posicao[m].n_vida == 1)
        {
          c = 1; // Vermelho
        }
        else if (posicao[m].n_vida <= 0)
        {
          mapa[i][j] = '.';
          posicao[m].x = posicao[m].y = -1;
        }
      }
    }
    break;
  case 'L':
    c = 4; // azul
    break;
  case 'l':
    c = 1; // vermelho
    break;
  case 'o':
    if (vida->utilizada == 0)
      c = 2; // verde
    else if (vida->utilizada == 1)
    {
      mapa[i][j] = '.';
    }
    break;
  default:
    c = 7; // branco
    break;
  }

  return c;
}

void imprimir_mapa(int n_linhas, int n_colunas, char mapa[n_linhas][n_colunas], Posicoes posicao[], Pedra *vida)
{
  for (int i = 0; i < n_linhas; i++)
  {
    for (int j = 0; j < n_colunas; j++)
    {
      int cor = ver_cor(i, j, n_linhas, n_colunas, mapa, posicao, vida);
      attron(COLOR_PAIR(cor));
      mvaddch(i, j, mapa[i][j] | A_BOLD);
      attroff(COLOR_PAIR(cor));
      if (mapa[i][j] == 'l')
        mapa[i][j] = '.';
    }
  }
}
