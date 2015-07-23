This is a project for demonstrating the second-killing activity  typically used by e-commerce shop promotion.<br /><br />

It is based on [Spring Boot](http://projects.spring.io/spring-boot/), and making use of redis for the persistence.<br />

##1. install redis
 wget http://download.redis.io/releases/redis-3.0.3.tar.gz<br />
 tar xzf redis-3.0.3.tar.gz<br />
 cd redis-3.0.3<br />
 make<br />
 src/redis-server<br />
 src/redis-cli<br />
 
##2. install wrk
 git clone https://github.com/wg/wrk.git<br />
 cd wrk<br />
 make<br />
 
##3. run demo
git clone https://github.com/pizhigang/demo-gift.git<br />
cd demo-gift<br />
mvn clean package<br />
java -jar target/demo-1.0-SNAPSHOT.jar --redis.ip=localhost --redis.port=6379 --gift.count=10000<br />
 
##4. load test 
\#This runs a benchmark for 10 seconds, using 4 threads, and keeping 500 HTTP connections open.<br />
wrk -t4 -c500 -d10s http://localhost:8080/gift/<br />
