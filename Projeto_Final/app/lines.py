import numpy as np
from matplotlib.collections import LineCollection
from PyQt5.QtWidgets import QWidget

class Lines(QWidget):
    def __init__(self, ax, x_min, x_max, y_min, y_max, numero_linhas):
        super().__init__()
        self.ax = ax
        self.x_min = x_min
        self.x_max = x_max
        self.y_min = y_min
        self.y_max = y_max
        self.numero_linhas_v = numero_linhas
        self.numero_linhas_h = numero_linhas
        
        self.lines = []
        self.lines_hor = []
        self.lines_vert = []
      
        

    def create_horizontal_lines(self):
        
        self.lines_hor = np.linspace(self.y_min, self.y_max , self.numero_linhas_h)
        for y in self.lines_hor:
            line, = self.ax.plot([self.x_min, self.x_max], [y, y], color='blue', linestyle='-')
            self.lines.append(line)
            

    def create_vertical_lines(self):

        self.lines_vert = np.linspace(self.x_min, self.x_max , self.numero_linhas_v)
        for x in self.lines_vert:
            line, = self.ax.plot([x, x], [self.y_min, self.y_max], color='red', linestyle='-')
            self.lines.append(line)
        
            
    def clear_lines(self):
    
        for line in self.lines:
            if line in self.ax.lines:
                line.remove()
        self.lines.clear()
        self.lines_hor = np.array([])
        self.lines_vert = np.array([])

    
   

    def calcula_num_pontos(self):
       
        x = self.x_max - self.x_min
        y = self.y_max - self.y_min
        return int(max(x, y) * 50)

    def get_pointshor(self):
        num_pontos = self.calcula_num_pontos()
        x = []
        y = []
        t = np.linspace(self.x_min, self.x_max, num_pontos)  
        for y_pos in self.lines_hor:
            for i in t:
                x.append(i)  
                y.append(y_pos)  
        return np.array(x), np.array(y)

    def get_pointsvert(self):
        num_pontos = self.calcula_num_pontos()
        x = []
        y = []  
        t = np.linspace(self.y_min, self.y_max, num_pontos)  
        for x_pos in self.lines_vert:
            for i in t:
                x.append(x_pos)  
                y.append(i)  
        return np.array(x), np.array(y)

    def plot_on_ax2(self, ax2, xs, ys, zs, ms):
        num_pontos = self.calcula_num_pontos()
        segmentos_verticais = [
            np.column_stack([xs[i:i+num_pontos], ys[i:i+num_pontos]]) 
            for i in range(0, len(xs), num_pontos)
        ]

        segmentos_horizontais = [
            np.column_stack([zs[i:i+num_pontos], ms[i:i+num_pontos]]) 
            for i in range(0, len(zs), num_pontos)
        ]

        lc_verticais = LineCollection(segmentos_verticais, colors='red')
        lc_horizontais = LineCollection(segmentos_horizontais, colors='blue')

        return lc_verticais, lc_horizontais
