from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import \
    QWidget, QVBoxLayout, \
    QHBoxLayout, QLabel, QLineEdit, QPushButton, QMessageBox
from tools.service import *

class modifyPwdWindow(QWidget):
    def __init__(self, userId):
        super().__init__()
        self.userId = userId
        self.setWindowTitle("修改密码")
        self.resize(600, 400)
        self.setFixedSize(self.width(), self.height())
        self.setStyleSheet("font-size: 20px;")

        label = QLabel(self)
        label.setText("修改密码")
        label.setFixedHeight(80)

        old_pwd_label = QLabel(self)
        old_pwd_label.setText("旧  密  码：")
        self.old_pwd_edit = QLineEdit(self)
        self.old_pwd_edit.setEchoMode(QLineEdit.Password)

        new_pwd_label = QLabel(self)
        new_pwd_label.setText("新  密  码：")
        self.new_pwd_edit = QLineEdit(self)
        self.new_pwd_edit.setEchoMode(QLineEdit.Password)

        confirm_pwd_label = QLabel(self)
        confirm_pwd_label.setText("确认新密码：")
        self.confirm_pwd_edit = QLineEdit(self)
        self.confirm_pwd_edit.setEchoMode(QLineEdit.Password)

        change_pwd_button = QPushButton(self)
        change_pwd_button.setText("确认修改")

        vbox = QVBoxLayout(self)
        vbox.addWidget(label, alignment=Qt.AlignCenter)

        hbox1 = QHBoxLayout()
        hbox1.addWidget(old_pwd_label)
        hbox1.addWidget(self.old_pwd_edit)

        hbox2 = QHBoxLayout()
        hbox2.addWidget(new_pwd_label)
        hbox2.addWidget(self.new_pwd_edit)

        hbox3 = QHBoxLayout()
        hbox3.addWidget(confirm_pwd_label)
        hbox3.addWidget(self.confirm_pwd_edit)

        vbox.addLayout(hbox1)
        vbox.addSpacing(20)
        vbox.addLayout(hbox2)
        vbox.addSpacing(20)
        vbox.addLayout(hbox3)
        vbox.addSpacing(50)
        vbox.addWidget(change_pwd_button, alignment=Qt.AlignCenter)

        change_pwd_button.clicked.connect(self.on_button_change_pwd_clicked)

    def on_button_change_pwd_clicked(self):
        userId = self.userId
        oldPwd = self.old_pwd_edit.text()
        newPwd1 = self.new_pwd_edit.text()
        newPwd2 = self.confirm_pwd_edit.text()
        key = modifyPwd(userId, oldPwd, newPwd1, newPwd2)
        if key == 1:
            QMessageBox.warning(None, '警告', '这三项都不能为空', QMessageBox.Ok)
        elif key == 2:
            QMessageBox.warning(None, '警告', '两次输入的新密码不相同', QMessageBox.Ok)
        elif key == 3:
            QMessageBox.warning(None, '警告', '新密码和旧密码相同', QMessageBox.Ok)
        elif key == 4:
            QMessageBox.warning(None, '警告', '旧密码错误', QMessageBox.Ok)
        elif key == 5:
            QMessageBox.about(self, '密码修改通知', '密码修改成功')