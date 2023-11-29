from tools.RQMainWindow import RQMainWindow
from view.class_view import ClassManageWindow
from view.manage_system_view import *
from view.modify_pwd_view import *

class HallWindow(RQMainWindow):
    def __init__(self, userId, parent=None):
        super(HallWindow, self).__init__(parent)
        self.userId = userId
        self.setStyleSheet("font-size:20px")
        self.resize(500, 600)
        self.setWindowTitle("大厅")
        self.setFixedSize(self.width(), self.height())

        self.label = QLabel("欢迎用户：" + self.userId, self)
        self.modify_pwd_button = QPushButton("修改当前用户密码", self)
        self.manage_student_info_button = QPushButton("管理学生信息", self)
        self.query_grade_button = QPushButton("查询班级", self)

        button_size = (300, 80)
        self.modify_pwd_button.setFixedSize(*button_size)
        self.manage_student_info_button.setFixedSize(*button_size)
        self.query_grade_button.setFixedSize(*button_size)

        self.modify_pwd_button.clicked.connect(self.on_button_modify_pwd_clicked)
        self.manage_student_info_button.clicked.connect(self.on_button_manage_student_info_clicked)
        self.query_grade_button.clicked.connect(self.on_button_query_grade_info_clicked)

        # 设置布局
        vbox = QVBoxLayout()
        vbox.addSpacing(150)
        vbox.addWidget(self.label, alignment=Qt.AlignCenter)

        hbox1 = QHBoxLayout()
        hbox1.addStretch()
        hbox1.addWidget(self.modify_pwd_button)
        hbox1.addStretch()

        hbox2 = QHBoxLayout()
        hbox2.addStretch()
        hbox2.addWidget(self.manage_student_info_button)
        hbox2.addStretch()

        hbox3 = QHBoxLayout()
        hbox3.addStretch()
        hbox3.addWidget(self.query_grade_button)
        hbox3.addStretch()

        vbox.addLayout(hbox1)
        vbox.addLayout(hbox2)
        vbox.addLayout(hbox3)
        vbox.addStretch()

        self.setLayout(vbox)

    def on_button_manage_student_info_clicked(self):
        self.stuManageWindow = StuManageWindow()
        self.stuManageWindow.show()

    def on_button_modify_pwd_clicked(self):
        self.modifyPwdWindow = modifyPwdWindow(self.userId)
        self.modifyPwdWindow.show()

    def on_button_query_grade_info_clicked(self):
        self.ClassManageWindow = ClassManageWindow()
        self.ClassManageWindow.show()