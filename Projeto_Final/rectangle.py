import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle
import numpy as np
from PyQt5.QtWidgets import QLineEdit
import re

class Rect:
    def __init__(self, ax, x, y, width, height, color, main_window):
        self.ax = ax
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        self.color = color
        self.main_window = main_window

        self.rectangle = Rectangle((self.x, self.y), self.width, self.height, color=self.color, fill=False)
        self.ax.add_patch(self.rectangle)
        
        self.center_x = self.x + self.width / 2
        self.center_y = self.y + self.height / 2

        self.center_point, = self.ax.plot(self.center_x, self.center_y, 'or')

        self.point_x = self.x
        self.point_y = self.y + self.height

        self.control_point, = self.ax.plot(self.point_x, self.point_y, 'ob')
        
        self.center_input = QLineEdit()
        self.center_input.setText(f'{self.center_x},{self.center_y}')
        self.center_input.setPlaceholderText("x, y")
        self.center_input.textChanged.connect(self.update_center)
        
        self.width_input = QLineEdit()
        self.width_input.setText(f'{self.width}')
        self.width_input.setPlaceholderText("width")
        self.width_input.textChanged.connect(self.update_width)
        
        self.height_input = QLineEdit()
        self.height_input.setText(f'{self.height}')
        self.height_input.setPlaceholderText("height")
        self.height_input.textChanged.connect(self.update_height)

        self.toggle_center_point = False
        self.toggle_control_point = False
    
    def get_input(self):
        return self.center_input, self.width_input, self.height_input

    def get_point(self, event):
        return np.hypot(self.point_x - event.xdata, self.point_y - event.ydata) <= 0.05

    def get_center(self, event):
        return np.hypot(self.center_x - event.xdata, self.center_y - event.ydata) <= 0.05

    def on_button_press(self, event):
        if event.xdata is None or event.ydata is None:
            return
        elif self.get_point(event):
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
            distance_x = self.point_x - event.xdata
            distance_y = self.point_y - event.ydata

            self.point_x = event.xdata
            self.point_y = event.ydata

            self.height -= distance_y
            self.width += distance_x

            self.x = self.point_x

            self.rectangle.set_x(self.x)
            self.rectangle.set_height(self.height)
            self.rectangle.set_width(self.width)
        elif self.toggle_center_point:
            distance_x = self.center_x - event.xdata
            distance_y = self.center_y - event.ydata

            self.x -= distance_x
            self.y -= distance_y

            self.point_x = self.x
            self.point_y = self.y + self.height
            self.rectangle.set_xy((self.x, self.y))

        self.center_x = self.x + self.width / 2
        self.center_y = self.y + self.height / 2

        self.center_point.set_xdata([self.center_x])
        self.center_point.set_ydata([self.center_y])

        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        self.main_window.update_second_graph()

        plt.draw()
        
    def update_center(self):
        if not re.match(r'^-?\d+(\.\d+)?,-?\d+(\.\d+)?$', self.center_input.text()):
            return

        values = [float(v.strip()) for v in self.center_input.text().split(',')]

        delta_x = values[0] - self.center_x
        delta_y = values[1] - self.center_y

        self.x += delta_x
        self.y += delta_y
        self.center_x = values[0]
        self.center_y = values[1]

        self.rectangle.set_xy((self.x, self.y))
        self.point_x = self.x
        self.point_y = self.y + self.height
        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        self.center_point.set_xdata([self.center_x])
        self.center_point.set_ydata([self.center_y])
        self.main_window.update_second_graph()

        plt.draw()

    def update_width(self):
        if not re.match(r'^\d+(\.\d+)?$', self.width_input.text()):
            return

        new_width = float(self.width_input.text())
        self.width = new_width

        self.point_x = self.x
        self.point_y = self.y + self.height
        self.rectangle.set_width(self.width)
        self.center_input.setText(f'{self.center_x:.3f},{self.center_y:.3f}')
        self.height_input.setText(f'{self.height}')
        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        self.main_window.update_second_graph()

        plt.draw()

    def update_height(self):
        if not re.match(r'^\d+(\.\d+)?$', self.height_input.text()):
            return

        new_height = float(self.height_input.text())
        self.height = new_height

        self.point_x = self.x
        self.point_y = self.y + self.height
        self.rectangle.set_height(self.height)
        self.center_input.setText(f'{self.center_x:.3f},{self.center_y:.3f}')
        self.width_input.setText(f'{self.width}')
        self.control_point.set_xdata([self.point_x])
        self.control_point.set_ydata([self.point_y])
        self.main_window.update_second_graph()

        plt.draw()

    def get_points(self):
        perimeter = 2 * (self.width + self.height)
        t = np.linspace(0, perimeter, 200)
        x = []
        y = []

        for i in t:
            if i < self.width:
                x.append(self.x + i)
                y.append(self.y)
            elif i < self.width + self.height:
                x.append(self.x + self.width)
                y.append(self.y + (i - self.width))
            elif i < 2 * self.width + self.height:
                x.append(self.x + self.width - (i - self.width - self.height))
                y.append(self.y + self.height)
            else:
                x.append(self.x)
                y.append(self.y + self.height - (i - 2 * self.width - self.height))

        return np.array(x), np.array(y)