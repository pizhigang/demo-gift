This is a project for demonstrating the second-killing activity for e-commerce shop.


1. 安装 redis
 wget http://download.redis.io/releases/redis-3.0.3.tar.gz
 tar xzf redis-3.0.3.tar.gz
 cd redis-3.0.3
 make
 src/redis-server
 src/redis-cli
 
2. 安装 wrk
 git clone https://github.com/wg/wrk.git
 cd wrk
 make
 
 
3. run demo
java -jar target/demo-1.0-SNAPSHOT.jar --redis.ip=localhost --redis.port=6379 --gift.count=10000
 
4. load test 
\\#This runs a benchmark for 10 seconds, using 4 threads, and keeping 500 HTTP connections open.
wrk -t4 -c500 -d10s http://localhost:8080/gift/
