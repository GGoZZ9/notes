 # 解决 LibreSSL SSL_connect: SSL_ERROR_SYSCALL in connection to github.com:443
 git config --global --add remote.origin.proxy ""
 # 修改remote 地址
 git remote set-url origin https://github.com/GGoZZ9/notes.git
 # 长期储存密码
 git config --global credential.helper store
 # git config 删掉所有代理，解决 connect refuse 443问题
 vim ~/.gitconfig
