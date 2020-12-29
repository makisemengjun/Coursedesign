### 目录结构
    源代码位于./src/com/company中，可执行jar在./out/artifacts/Coursedesign_jar/Coursedesgin.jar中。
    源码使用git进行版本管理，可以较清晰的看到源码的历史。同时托管到了github上，链接为
    https://github.com/makisemengjun/Coursedesign
    暂时是私有仓库。
### 程序的运行
    若安装有mpv播放器，则在linux下可以使用java -jar ./out/artifacts/Coursedesign_jar/Coursedesgin.jar直接运行。
    在windows下应该双击上述文件即可。数据库使用MariaDB，若使用需要参考MariaDB.java中的用户名与密码。表的结构可以参见
    课程设计报告。若没有安装mpv播放器，参照下节。
### mpv播放器的安装
    mpv的官网为https://mpv.io/
    在各大linux发行版下推荐自行编译，一般没有官方的二进制包。在某些发行版也可以从官方、第三方源安装。
    为方便测试，可以参见以下某些发行版的安装。
    Ubuntu和Debian下可以使用
    apt install mpv 安装。
    Fedora可启用rpmfusion源，随后使用
    dnf install mpv 安装。
    Gentoo有官方二进制包，网址为https://packages.gentoo.org/packages/media-video/mpv
    MacOS用户可以通过Homebrew安装。
    windows用户可以在https://sourceforge.net/projects/mpv-player-windows/files/ 下载安装。
    若下载过慢可能需要使用工具，也可以在java课程群联系我（黎梓亿 2017051626 信息与计算科学），
    或通过QQ邮箱592161867@qq.com发送邮件与我联系。
    注意，需要将mpv.exe的路径添加到系统环境变量，可以在终端使用mpv --version确认是否添加成功。
### 程序的使用
    只需要打开对应的网站的直播间，在地址栏就可以看见直播房间号。也可以在主播的个人空间等地方找到。
    输入房间号再点击播放就可以开始观看直播。也可以通过数据库实现输入主播名称再点击播放按钮开始观看，
    但需要安装数据库并建立相关的表格。
