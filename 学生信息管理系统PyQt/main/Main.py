#这里是整个程序的入口
import sys
from PyQt5.QtWidgets import QApplication
from view.sign_in_view import *

def main():
    app = QApplication(sys.argv)
    sign_in_window = SignInWindow()
    sign_in_window.show()
    sys.exit(app.exec_())

main()