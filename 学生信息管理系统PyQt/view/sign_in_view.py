#登录窗口
from PyQt5.QtWidgets import QMainWindow, QGridLayout, QStackedWidget
from view.hall_view import *

class SignInWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()
        self.setFixedSize(self.width(), self.height())

    def initUI(self):
        self.setWindowTitle('开始界面')
        self.resize(300, 200)
        self.central_widget = QStackedWidget()
        self.setCentralWidget(self.central_widget)
        login_widget = LoginWidget(self)
        register_widget = RegisterWidget(self)
        self.central_widget.addWidget(login_widget)
        self.central_widget.addWidget(register_widget)
        self.central_widget.setCurrentIndex(0)

    def switch(self, index):
        self.central_widget.setCurrentIndex(index)

class LoginWidget(QWidget):
    def __init__(self, parent):
        super().__init__(parent)
        self.parent = parent
        grid = QGridLayout(self)
        label_username = QLabel('用户名:')
        self.le_username = QLineEdit()
        grid.addWidget(label_username, 0, 0)
        grid.addWidget(self.le_username, 0, 1)
        label_password = QLabel('密码:')
        self.le_password = QLineEdit()
        self.le_password.setEchoMode(QLineEdit.Password)
        grid.addWidget(label_password, 1, 0)
        grid.addWidget(self.le_password, 1, 1)
        button_login = QPushButton('登录')
        button_login.clicked.connect(self.login)
        grid.addWidget(button_login, 2, 0, 1, 2)
        button_register = QPushButton('注册')
        button_register.clicked.connect(self.register)
        grid.addWidget(button_register, 3, 0, 1, 2)

    def login(self):
        username = self.le_username.text()
        password = self.le_password.text()
        key = check_sign_in(username, password)
        if key == 1 or key == 2:
            QMessageBox.warning(None, '警告', '用户名或密码不能为空', QMessageBox.Ok)
        elif key == 3:
            self.hall_window = HallWindow(username)
            self.hall_window.show()
            self.parent.close()
        elif key == 4:
            QMessageBox.warning(None, '警告', '登录失败用户名或密码不正确', QMessageBox.Ok)

    def register(self):
        self.parent.switch(1)

class RegisterWidget(QWidget):
    def __init__(self, parent):
        super().__init__(parent)
        self.parent = parent
        grid = QGridLayout(self)
        label_username = QLabel('用户名:')
        self.le_username = QLineEdit()
        grid.addWidget(label_username, 0, 0)
        grid.addWidget(self.le_username, 0, 1, 1, 4)
        label_password = QLabel('密码:')
        self.le_password = QLineEdit()
        self.le_password.setEchoMode(QLineEdit.Password)
        grid.addWidget(label_password, 1, 0)
        grid.addWidget(self.le_password, 1, 1, 1, 4)
        label_confirm = QLabel('确认密码:')
        self.le_confirm = QLineEdit()
        self.le_confirm.setEchoMode(QLineEdit.Password)
        grid.addWidget(label_confirm, 2, 0)
        grid.addWidget(self.le_confirm, 2, 1, 1, 4)
        button_register = QPushButton('注册账号')
        button_register.clicked.connect(self.register)
        button_return = QPushButton('返回')
        button_return.clicked.connect(self.return_)
        grid.addWidget(button_register, 3, 0, 1, 2)
        grid.addWidget(button_return, 3, 4)


    def register(self):
        userName = self.le_username.text()
        password1 = self.le_password.text()
        password2 = self.le_confirm.text()
        key = check_userId_and_pwd(userName, password1, password2)
        if key == 1:
            QMessageBox.warning(None, '警告', '两次输入的密码不相同', QMessageBox.Ok)
        elif key == 2 or key == 3:
            QMessageBox.warning(None, '警告', '未输入用户名或密码', QMessageBox.Ok)
        elif key == 4:
            QMessageBox.warning(None, '警告', '用户已存在', QMessageBox.Ok)
        elif key == 5:
            QMessageBox.about(self, '注册通知', '注册成功')
            self.parent.switch(0)

    def return_(self):
        self.parent.switch(0)