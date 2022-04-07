find . -name '*.txt' -exec rm -rf {} ';'
sed -i 's/***/###/g' '**.txt'
nohup &
awk '{print NR}' git-clone-commands.sh | tail -n 1
tail -n 100 xxx.log
ls -l | grep "^d" | wc -l # 统计文件夹数量
netstat -pan | grep 8080 # 查看端口占用
kill -9 pid

ping 127.0.0.1 # 测试域名或者服务器是否可用
telnet 127.0.0.1 80 # 测试端口通不通 (TCP port)
nc -vz 127.0.0.1 80 # 检查TCP端口通不通，NC是 Netstat工具的简称
nc -uz 127.0.0.1 8907 # 检查UDP端口通不通


systemctl status apollo-config.service