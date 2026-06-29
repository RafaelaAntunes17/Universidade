import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle
from PyQt5.QtWidgets import QLineEdit
import re

class Circumference:
    def __init__(self, ax, x, y, radius, color, main_window):
        self.ax = ax
        self.x = x
        self.y = y
        self.radius = radius
        self.color = color
        self.main_window = main_window

        self.circle = Circle((self.x, self.y), self.radius, color=self.color, fill=False)
        self.ax.add_patch(self.circle)

        self.point_x = self.x + np.cos(np.pi / 4) * self.radius
        self.point_y = self.y + np.sin(np.pi / 4) * self.radius

        self.control_point, = self.ax.plot(self.point_x, self.point_y, 'ob')
        self.center_point, = self.ax.plot(self.x, self.y, 'og')

        self.toggle_center_point = False
        self.toggle_control_point = False

        self.ax.set(xlim=(-4,4), ylim=(-4,4))

        self.center_input = QLineEdit()
        self.center_input.setText(f'{self.x},{self.y}')
        self.center_input.setPlaceholderText("x, y")
        self.center_input.textChanged.connect(self.update_center)

        self.radius_input = QLineEdit()
        self.radius_input.setText(f'{self.radius}')
        self.radius_input.setPlaceholderText("r")
        self.radius_input.textChanged.connect(self.update_radius)
    
    def get_input(self):
        return self.center_input, self.radius_input

    def get_point(self, event):
        return np.hypot(self.point_x - event.xdata, self.point_y - event.ydata) <= 0.1

    def get_center(self, event):
        return np.hypot(self.x - event.xdata, self.y - event.ydata) <= 0.1

    def on_button_press(self, event):
        if event.xdata is None or event.ydata is None:
            return 
        if self.get_point(event):
            self.toggle_control_point = True
        elif self.get_center(event):
            self.toggle_center_point = True
    
    def on_button_release(self, event):
        self.toggle_control_point = False
        self.toggle_center_point = False

    def on_mouse_move(self, event):
        if event.xdata is None or event.ydata is None:
            return 

        if self.toggle_control_point:
            self.radius = np.hypot(self.x - event.xdata, self.y - event.ydata)
            self.circle.set_radius(self.radius)
            self.radius_input.setText(f'{self.radius:.3f}')
        elif self.toggle_center_point:
            self.x = event.xdata
            self.y = event.ydata
            self.circle.set_center((self.x, self.y))
            self.center_input.setText(f'{self.x:.3f},{self.y:.3f}')
            self.center_point.set_xdata([self.x])
            self.center_point.set_ydata([self.y])

        self.point_x = self.x + np.cos(np.pi / 4) * self.radius
        self.point_y = self.y + np.sin(np.pi / 4) * self.radius
        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        
        self.main_window.update_second_graph()
        
        plt.draw()

    def update_center(self):

        if not re.match(r'^-?\d+(\.\d+)?,-?\d+(\.\d+)?$', self.center_input.text()):
            return
        
        values = [v.strip() for v in self.center_input.text().split(',')]

        self.x = float(values[0])
        self.y = float(values[1])
        self.circle.set_center((self.x, self.y))
        self.center_point.set_xdata([self.x])
        self.center_point.set_ydata([self.y])
        self.point_x = self.x + np.cos(np.pi / 4) * self.radius
        self.point_y = self.y + np.sin(np.pi / 4) * self.radius
        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        self.main_window.update_second_graph()

        plt.draw()

    def update_radius(self):
        value = self.radius_input.text().strip()

        if not re.match(r'^(0\.\d+|[1-9]\d*(\.\d+)?)$', value) :
            return

        self.radius = float(value)
        self.circle.set_radius(self.radius)
        self.point_x = self.x + np.cos(np.pi / 4) * self.radius
        self.point_y = self.y + np.sin(np.pi / 4) * self.radius
        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        self.main_window.update_second_graph()

        plt.draw()

    def get_points(self):
        t = np.linspace(0, 2 * np.pi, 500)
        return self.x + np.cos(t) * self.radius, self.y + np.sin(t) * self.radius
