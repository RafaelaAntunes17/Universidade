import numpy as np
import matplotlib.pyplot as plt
from matplotlib.widgets import Cursor, Button
from matplotlib.lines import Line2D
from PyQt5.QtCore import Qt


class Draw:
    def __init__(self, ax, canvas):
        self.ax = ax
        self.canvas = canvas
        self.coord = []
        self.points = []
        self.lines = []
        self.labels = []
        self.figure_close = False
        self.selected_index = None
        self.dragging_center = False

        self.cursor = Cursor(ax, horizOn=True, vertOn=True, useblit=True, color='red', linewidth=1)

        self.ax.grid()
        self.ax.set_xlim(0, 10)
        self.ax.set_ylim(0, 10)

        self.canvas.setFocusPolicy(Qt.StrongFocus)
        self.canvas.setFocus()

        self.canvas.mpl_connect('button_press_event', self.onclick)
        self.canvas.mpl_connect('key_press_event', self.on_key)
        self.canvas.mpl_connect('button_press_event', self.on_press)
        self.canvas.mpl_connect('button_release_event', self.on_release)
        self.canvas.mpl_connect('motion_notify_event', self.on_motion)

        ax_button = plt.axes([0.8, 0.05, 0.1, 0.075])
        self.btn_reset = Button(ax_button, 'Reset')
        self.btn_reset.on_clicked(self.reset)

        self.btn_hidelabels = Button(plt.axes([0.65, 0.05, 0.1, 0.075]), 'Hide')
        self.btn_hidelabels.on_clicked(self.hide_labels)

        self.center_point = None

    def onclick(self, event):
        if self.figure_close:
            return
        if event.xdata is not None and event.ydata is not None:
            x, y = event.xdata, event.ydata
            self.coord.append((x, y))

            point, = self.ax.plot(x, y, 'bo', markersize=5)
            self.points.append(point)
            label = self.ax.annotate(f'({x:.2f}, {y:.2f})', (x, y), textcoords="offset points", xytext=(5,5), ha='right')
            self.labels.append(label)

            self.canvas.draw_idle()

    def on_key(self, event):
        if event.key == 'enter' and len(self.coord) > 1 and not self.figure_close:
            x_vals, y_vals = zip(*self.coord)
            line = Line2D(x_vals + (x_vals[0],), y_vals + (y_vals[0],), color='black')
            self.ax.add_line(line)
            self.lines.append(line)
            self.figure_close = True
            self.update_draw()

    def hide_labels(self, event):
        for label in self.labels:
            visible = label.get_visible()
            label.set_visible(not visible)

        new_label = 'Show' if visible else 'Hide'
        self.btn_hidelabels.label.set_text(new_label)
        self.canvas.draw_idle()

    def reset(self, event):
        self.coord.clear()

        for point in self.points:
            point.remove()
        self.points.clear()

        for line in self.lines:
            line.remove()
        self.lines.clear()

        for label in self.labels:
            label.remove()
        self.labels.clear()

        if self.center_point:
            self.center_point.remove()
            self.center_point = None

        self.figure_close = False
        self.btn_hidelabels.label.set_text('Hide')
        self.canvas.draw_idle()

    def points_calculate(self, vertices, num_points):
        x = []
        y = []
        for i in range(len(vertices)):
            p1 = vertices[i]
            p2 = vertices[(i+1) % len(vertices)]
            t = np.linspace(0, 1, num_points)
            x_vals = p1[0] + (p2[0] - p1[0]) * t
            y_vals = p1[1] + (p2[1] - p1[1]) * t
            x.append(x_vals)
            y.append(y_vals)
        return np.array(x), np.array(y)

    def get_points(self):
        return self.points_calculate(self.coord, 200)

    def on_press(self, event):
        if event.xdata is None or event.ydata is None:
            return

        if self.center_point:
            center_x, center_y = self.get_center()
            if np.hypot(center_x - event.xdata, center_y - event.ydata) < 0.3:
                self.dragging_center = True
                return

        for i, (x, y) in enumerate(self.coord):
            if np.hypot(x - event.xdata, y - event.ydata) < 0.2:
                self.selected_index = i
                break

    def on_release(self, event):
        self.selected_index = None
        self.dragging_center = False

    def on_motion(self, event):
        if event.xdata is None or event.ydata is None:
            return

        if self.selected_index is not None:
            self.coord[self.selected_index] = (event.xdata, event.ydata)
            self.update_draw()
        elif self.dragging_center:
            center_x, center_y = self.get_center()
            dx = event.xdata - center_x
            dy = event.ydata - center_y
            self.coord = [(x + dx, y + dy) for x, y in self.coord]
            self.update_draw()

    def get_center(self):
        x_vals = [x for x, y in self.coord]
        y_vals = [y for x, y in self.coord]
        return np.mean(x_vals), np.mean(y_vals)

    def update_draw(self):
        for point in self.points:
            point.remove()
        self.points.clear()

        for label in self.labels:
            label.remove()
        self.labels.clear()

        for line in self.lines:
            line.remove()
        self.lines.clear()

        for x, y in self.coord:
            point, = self.ax.plot(x, y, 'bo', markersize=5)
            self.points.append(point)
            label = self.ax.annotate(f'({x:.2f}, {y:.2f})', (x, y), textcoords="offset points", xytext=(5,5), ha='right')
            self.labels.append(label)

        if self.figure_close and len(self.coord) > 1:
            x_vals, y_vals = zip(*self.coord)
            line = Line2D(x_vals + (x_vals[0],), y_vals + (y_vals[0],), color='black')
            self.ax.add_line(line)
            self.lines.append(line)

        if self.center_point:
            self.center_point.remove()

        if self.coord:
            center_x, center_y = self.get_center()
            self.center_point, = self.ax.plot(center_x, center_y, 'or')

        self.canvas.draw_idle()
