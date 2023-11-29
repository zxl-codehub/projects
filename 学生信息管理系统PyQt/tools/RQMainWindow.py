import sys
from PyQt5.QtWidgets import QMainWindow, QMessageBox, QWidget


class RQMainWindow(QWidget):
    def __init__(self, parent=None):
        super(RQMainWindow, self).__init__(parent)

    def closeEvent(self, event):
        reply = QMessageBox.question(self, '提示',
                                     "是否退出系统?",
                                     QMessageBox.Yes | QMessageBox.No,
                                     QMessageBox.No)
        if reply == QMessageBox.Yes:
            event.accept()
            sys.exit(0)
        else:
            event.ignore()
