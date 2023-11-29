package public_;

public interface MessageType {
    int SIGN_IN_SUCCESS = 1;//登录成功
    int SIGN_IN_FAIL = 2;//登录失败
    int SIGN_IN = 3;//登录请求
    int SIGN_UP = 4;//注册请求
    int SIGN_UP_SUCCESS = 5;//注册成功
    int SIGN_UP_FAIL = 6;//注册失败
    int ACCOUNT_REMOTE_SIGN_IN = 7;//账号异地登录
    int GET_ONLINE_USER = 8;//客户端请求获取在线用户列表
    int RETURN_ONLINE_USER = 9;//服务器返回在线用户列表
    int CLIENT_EXIT = 10;//客户端请求退出
    int CHAT_MESSAGE = 11;//普通地聊天信息
    int GET_LOGOUT_MESSAGE = 12;//客户端请求获取离线留言
    int RETURN_LOGOUT_MESSAGE = 13;//服务器返回离线留言
    int USER_NOT_EXIST = 14;//表示用户不存在
    int CHECK_USER_IS_EXIST = 15;//请求服务器检查用户是否存在
    int GROUP_CHAT_MESSAGE = 16;//群发消息
    int SEND_FILE_MESSAGE = 17;//发送文件
    int GET_FILE_NAME = 18;//客户端请求获取未接收的文件名
    int RETURN_FILE_NAME = 19;//服务端返回未接收的文件名
    int GET_FILE = 20;//客户端请求获取文件
    int RETURN_FILE = 21;//服务端返回文件
    int DELETE_FILE = 22;//客户端请求删除文件
    int SEND_GROUP_FILE = 23;//群发文件
    int SERVICE_SHUTDOWN = 24;//服务器关闭
    int SERVICE_SEND_MESSAGE = 25;//服务器推送消息
    int LOGOUT = 26;//服务器踢人
}
