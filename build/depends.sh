SET_RED="\033[1;31m"
SET_GREEN="\033[1;32m"
SET_BLUE="\033[1;34m"
RESET="\033[0m"
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在删除所有container"
echo -n -e ${RESET}
docker stop $(docker ps -aq) && docker rm $(docker ps -aq)
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo "删除成功"
else
  echo -n -e ${SET_RED}
  echo "删除失败"
fi
echo -n -e ${RESET}
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在删除./volums/mysql-volum/data"
echo -n -e ${RESET}
rm -rf ./volums/mysql-volum/data
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo "删除成功"
else
  echo -n -e ${SET_RED}
  echo "删除失败"
fi
echo -n -e ${RESET}
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在创建./volums/mysql-volum/data"
echo -n -e ${RESET}
mkdir ./volums/mysql-volum/data
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo "创建成功"
else
  echo -n -e ${SET_RED}
  echo "创建失败"
fi
echo -n -e ${RESET}
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在删除./volums/redis-volum/data"
echo -n -e ${RESET}
rm -rf ./volums/redis-volum/data
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo -n "删除成功"
else
  echo -n -e ${SET_RED}
  echo "删除失败"
fi
echo -n -e ${RESET}
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在创建./volums/mysql-volum/data"
echo -n -e ${RESET}
mkdir ./volums/redis-volum/data
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo "创建成功"
else
  echo -n -e ${SET_RED}
  echo "创建失败"
fi
echo -n -e ${RESET}
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在启动项目"
echo -n -e ${RESET}
docker compose -f docker-compose.yaml -p build up -d
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo  "----<启动成功>---"
else
  echo -n -e ${SET_RED}
  echo "---<启动失败>---"
fi
echo -n -e ${RESET}
echo "-----------------------------------------------------"
echo -n -e ${SET_BLUE}
echo "正在关闭服务器"
echo -n -e ${RESET}
docker stop project && docker rm project
if [ $? == "0" ]; then
  echo -n -e ${SET_GREEN}
  echo  "----<关闭成功>---"
else
  echo -n -e ${SET_RED}
  echo "---<关闭失败>---"
fi
echo -n -e ${RESET}
echo -n -e ${SET_RED}
echo "已完成对项目MySQL、Redis的格式化，并启动了MySQL和Redis"
echo -n -e ${RESET}
