import numpy as np  
class Graphic:
    def __init__(self):
        pass  
    def calcular_sen(self, x, y):
        return np.sin(x) * np.cosh(y), np.cos(x) * np.sinh(y) 

    def calcular_cos(self, x,y):
        return np.cos(x) * np.cosh(y), -np.sin(x) * np.sinh(y)

    def calcular_exp(self, x,y):
        return np.exp(x) * np.cos(y), np.exp(x) * np.sin(y)


    def calcular_z_mais_1_por_z(self, x, y):
        denom = x**2 + y**2
        if np.any(denom == 0):
            denom = np.where(denom == 0, 1e-10, denom) 
        return x * (denom + 1) / denom, y * (denom - 1) / denom


        